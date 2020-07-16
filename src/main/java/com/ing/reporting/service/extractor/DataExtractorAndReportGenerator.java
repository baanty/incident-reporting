package com.ing.reporting.service.extractor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.service.WriterService;

import lombok.extern.slf4j.Slf4j;

/**
 * Use this class to load the sent Csv file to the Events data base. The
 * Scheduled method will read the csv from project class path and put the data
 * in the in memory database.
 * 
 * For the time being, we use the Derby data base. But for production like
 * environment we can use Oracle.
 * 
 * @author Pijush Kanti Das
 *
 */
@Slf4j
@Component
public class DataExtractorAndReportGenerator {

	@Value("${output.report.location}")
	String outputReportLocation;
	
	@Value("${output.error.report.location}")
	String outputErrorReportLocation;
	
	@Autowired
	WriterService writerService;

	/**
	 * Use this method to generate the report and store it at the specific location.
	 */
	@Scheduled(cron = "${cron.expression.report.generator.job}")
	public void generateDailyReoport() {
		Path outputFilePath = Paths.get(outputReportLocation);

		try {
			if (Files.exists(outputFilePath)) {
				Files.delete(outputFilePath);

			}
			Files.createFile(Paths.get(outputReportLocation));

			BufferedWriter writer = Files.newBufferedWriter(outputFilePath);
			writerService.writeDailyAssetRecordsOnOutStream(writer);
			writer.close();
		} catch (IOException exception) {
			log.error("Error occured while trying to write the output file.");
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	
	/**
	 * Use this method to generate the error 
	 * report and store it at the specific location.
	 */
	@Scheduled(cron = "${cron.expression.error.report.generator.job}")
	public void generateDailyErrorReoport() {
		Path outputFilePath = Paths.get(outputErrorReportLocation);

		try {
			if (Files.exists(outputFilePath)) {
				Files.delete(outputFilePath);

			}
			Files.createFile(Paths.get(outputErrorReportLocation));

			BufferedWriter writer = Files.newBufferedWriter(outputFilePath);
			writerService.writeDailyErrorRecordsOnOutStream(writer);
			writer.close();
		} catch (IOException exception) {
			log.error("Error occured while trying to write the error output file.");
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}

}