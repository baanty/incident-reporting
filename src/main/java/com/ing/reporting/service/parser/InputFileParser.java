package com.ing.reporting.service.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.persistence.dao.AssetDao;
import com.ing.reporting.persistence.dao.ErrorEventDao;
import com.ing.reporting.persistence.dao.EventDao;
import com.ing.reporting.persistence.entity.AssetEntity;
import com.ing.reporting.persistence.entity.ErrorEventEntity;
import com.ing.reporting.persistence.entity.EventEntity;
import com.ing.reporting.service.parallal.GenericEntityPersister;
import com.ing.reporting.to.AssetTo;
import com.ing.reporting.util.MappingUtil;
import com.ing.reporting.validator.InputRecordValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * Use this class to load the sent Csv file to the 
 * Events data base. The Scheduled method will read the csv from project class path and
 * put the data in the in memory database.
 * 
 * For the time being, we use the Derby data base.
 * But for production like environment we can use Oracle.
 * 
 * @author Pijush Kanti Das
 *
 */
@Slf4j
@Component
public class InputFileParser {

	@Value("${input.csv.name:input.csv}")
	String inputCsvFile;
	
	@Value("${input.file.header}")
	String inputFileHeader;
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	ErrorEventDao errorEventDao;
	
	@Autowired
	InputRecordValidator validator;
	
	@Autowired
	ExecutorService executor;
	
	
	final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
	

	/**
	 * Use this method to read the data and get a collection of the
	 * Entity objects from the CSV.
	 * 
	 * The speciality of this method is that it reads the incoming file 
	 * sequentially and then writes to the output summery of assets.
	 * So, however big the incoming file is, the JVM heap is never much loaded.
	 * 
	 * Also parallal processing of the write DB operation ensures CPU utilization
	 * to improve performance.
	 * 
	 * @param allDailyAssets : This is the <code>Map<String, AssetTo></code> that 
	 * has the data of the daily summery of the assets. It can be safely assumed that
	 * even if number of events is large, the number of assets can not be large.
	 * 
	 * So, the collection of assets is kept in java heap. But the events are saved to DB.
	 * 
	 * @param futures : All the Future object references of the event table update.
	 * @return
	 */
	private synchronized void readCsvAndSaveEvents(Map<String, AssetTo> allDailyAssets, List<Future<?>> futures) {
		
		try (Scanner scanner = new Scanner(new File(inputCsvFile))) {

			while (scanner.hasNextLine()) {
				String lineString = scanner.nextLine();
				
				if ( StringUtils.isEmpty(lineString) || lineString.contains(inputFileHeader)) {
					continue;
				}
		    	List<String> stringRecord = getRecordFromLine(lineString);
		    	
		    	if ( validator.isValidRecord(stringRecord)) {
		    		saveErrorRecordToDb(stringRecord, futures);
		    		continue;
		    	}
		    	saveGoodEventToDb(stringRecord, futures);
		    	collectAssetTransferObjects(allDailyAssets, stringRecord);
		    }
		} catch (FileNotFoundException exception) {
			log.error("Got error while trying to load Excel to Database.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	
	/**
	 * USe this method to save the erroneous record to the 
	 * persistent store.
	 * 
	 * @param stringRecord : The single erroneous record.
	 * @param futures : All the Future object references of the event table update.
	 */
	private void saveErrorRecordToDb(List<String> stringRecord, List<Future<?>> futures) {
		ErrorEventEntity entity = ErrorEventEntity
									.builder()
									.erroneousRecord(String.join("", stringRecord))
									.build();
		futures.add(executor.submit(new GenericEntityPersister<ErrorEventEntity>(errorEventDao, entity)));
	}
	
	/**
	 * USe this method to save the good event record to the 
	 * persistent store.
	 * 
	 * @param stringRecord : The single good record.
	 * @param futures : All the Future object references of the event table update.
	 */
	private void saveGoodEventToDb(List<String> stringRecord, List<Future<?>> futures) {
		String assetName = stringRecord.get(0);
    	Timestamp startTime = Timestamp.valueOf(stringRecord.get(1));
    	Timestamp endTime = Timestamp.valueOf(stringRecord.get(2));
    	int severity = Integer.parseInt(stringRecord.get(3));
    	
		EventEntity  entity = EventEntity
							.builder()
							.assetName(assetName)
							.starTime(startTime)
							.endTIme(endTime)
							.severity(severity)
							.build();
		futures.add(executor.submit(new GenericEntityPersister<EventEntity>(eventDao, entity)));
	}
	
	/**
	 * USe this method to save all the 
	 * transfer object to the collection of 
	 * Transfer Objects.
	 * 
	 * @param allDailyAssets
	 * @param stringRecord
	 */
	private void collectAssetTransferObjects(Map<String, AssetTo> allDailyAssets, List<String> stringRecord) {
    	String assetName = stringRecord.get(0);
    	Timestamp startTime = Timestamp.valueOf(stringRecord.get(1));
    	Timestamp endTime = Timestamp.valueOf(stringRecord.get(2));
    	int severity = Integer.parseInt(stringRecord.get(3));
		AssetTo assetTo = allDailyAssets.get(assetName);
		long newDownTime = 0;
		long newUpTime = 100;

		if (severity == 1) {
			newDownTime = ((assetTo != null) ? assetTo.getTotalDownTime() : 0)
					+ ((endTime.getTime() - startTime.getTime()) / (10 * 24 * 3600));
			newUpTime = (100 - newDownTime);
		}

		AssetTo newAssetTo = AssetTo.builder().assetName(assetName).totalDownTime(newDownTime)
				.totalUpTime(newUpTime).totalIncidents(( (assetTo != null) ? assetTo.getTotalIncidents() : 0  ) + 1)
				.rating(( (assetTo != null) ? assetTo.getRating() : 0 ) + (severity == 1 ? 30 : 10)).build();
		allDailyAssets.remove(assetName);
		allDailyAssets.put(assetName, newAssetTo);					
	}
	
	/**
	 * Use this method to load the assets in the Assets table.
	 * 
	 * @param allDailyAssets
	 * @param futures : The future, which holds the result to Event Save.
	 */
	private void loadAssets(Map<String, AssetTo> allDailyAssets, List<Future<?>> futures) {
		
		if ( !CollectionUtils.isEmpty(allDailyAssets) ) {
			
			List<AssetEntity> assets = allDailyAssets
											.values()
											.parallelStream()
											.filter(anAsset -> anAsset != null )
											.map(MappingUtil::buildTransferObjectToBusinessObject)
											.collect(Collectors.toList());
			futures.add(executor.submit(new GenericEntityPersister<AssetEntity>(assetDao, assets)));
		}
	}
	
	/**
	 * Use this method to read the CSV files 
	 * and load it to in memeory H2 Database.
	 * Please mark the method for a cron job run at a certain point
	 * in the day.
	 */
	@Scheduled(cron = "${cron.expression.csv.load.job}")
	public void readCsvAtScheduleAndPersistData() {
		try {
			List<Future<?>> futures = new ArrayList<Future<?>>();
			Map<String, AssetTo> allDailyAssets = new ConcurrentHashMap<String, AssetTo>();
			readCsvAndSaveEvents(allDailyAssets, futures);
			loadAssets(allDailyAssets, futures);
			
		} catch (Exception exception) {
			log.error("Can not save event object to DB");
			throw new RuntimeException(exception);
		}
		
	}
	
	
	private List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(";");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
}
