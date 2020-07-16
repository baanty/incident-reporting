package com.ing.reporting.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.to.AssetTo;

import lombok.extern.slf4j.Slf4j;

/**
 * Use this service to write the response to an output stream.
 * 
 * @author Pijush Kanti Das
 *
 */
@Slf4j
@Service
public class WriterService {

	@Autowired
	AssetService service;

	/**
	 * USe this methdo to write the utput data to a 
	 * CSV file and export it.
	 * @param appendable : The CSV file output stream source.
	 * 
	 */
	public void writeOutStream(Appendable appendable) {
		
		try (final CSVPrinter csvPrinter = new CSVPrinter(appendable,
				CSVFormat.DEFAULT.withHeader("Asset Name", "Total Incidents", "Total Down Time", "Rating"))) {

			List<AssetTo> daysAssets = service.findAssetStatisticsForThePresentDay();

			if (!CollectionUtils.isEmpty(daysAssets)) {
				daysAssets.stream().filter(anAssetTo -> anAssetTo != null).forEach(anAssetTo -> {
					try {
						csvPrinter.printRecord(Arrays.asList(anAssetTo.getAssetName(), anAssetTo.getTotalIncidents(),
								anAssetTo.getTotalDownTime() + "%", anAssetTo.getRating()));
					} catch (IOException exception) {
						log.error("And error occured while writing data to CSV file.", exception);
						throw new GenericReportingApplicationRuntimeException(exception);
					}
				});
			}

		} catch (Exception exception) {
			log.error("And error occured while writing data to writer.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}
		
	}
}
