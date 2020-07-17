package com.ing.reporting.service.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ing.reporting.common.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.common.validator.InputRecordValidator;
import com.ing.reporting.persistence.dao.ErrorEventDao;
import com.ing.reporting.persistence.dao.EventDao;
import com.ing.reporting.persistence.entity.ErrorEventEntity;
import com.ing.reporting.persistence.entity.EventEntity;
import com.ing.reporting.service.thread.GenericEntityPersisterServiceThread;

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
public class InputFileParseService {

	@Value("${input.csv.name:input.csv}")
	String inputCsvFile;
	
	@Value("${input.file.header}")
	String inputFileHeader;
	
	@Autowired
	EventDao eventDao;
	
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
	 * @param futures : All the Future object references of the event table update.
	 * 
	 * @return
	 */
	private synchronized void readCsvAndSaveEvents(List<Future<?>> futures) {
		

		
		try (Scanner scanner = new Scanner(new File(inputCsvFile))) {

			while (scanner.hasNextLine()) {
				String lineString = scanner.nextLine();
				
				if ( StringUtils.isEmpty(lineString) || lineString.contains(inputFileHeader)) {
					continue;
				}
		    	List<String> stringRecord = getRecordFromLine(lineString);
		    	
		    	if ( !validator.isValidRecord(stringRecord)) {
		    		saveErrorRecordToDb(stringRecord, futures);
		    		continue;
		    	}
		    	saveGoodEventToDb(stringRecord, futures);
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
									.currentTimestamp(Timestamp.valueOf(LocalDateTime.now()))
									.build();
		futures.add(executor.submit(new GenericEntityPersisterServiceThread<ErrorEventEntity>(errorEventDao, entity)));
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
							.currentTimestamp(Timestamp.valueOf(LocalDateTime.now()))
							.build();
		futures.add(executor.submit(new GenericEntityPersisterServiceThread<EventEntity>(eventDao, entity)));
	}
	

	
	/**
	 * Use this method to read the CSV files 
	 * and load it to in memeory H2 Database.
	 * Please mark the method for a cron job run at a certain point
	 * in the day.
	 */
	public void readCsvAtScheduleAndPersistData() {
		try {
			List<Future<?>> futures = new ArrayList<Future<?>>();
			readCsvAndSaveEvents(futures);
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
