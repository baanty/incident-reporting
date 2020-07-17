package com.ing.reporting.service.extractor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ing.reporting.common.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.presentation.controller.ReportController;
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
public class DataExtractorService {

	@Value("${output.report.location}")
	String outputReportLocation;

	@Value("${output.error.report.location}")
	String outputErrorReportLocation;
	
	@Value("${http.url.for.daily.report.download}")
	String urlForDailyReport;
	
	@Value("${rest.user.id}")
	String restUserId;
	
	@Value("${rest.user.password}")
	String restPassword;

	@Autowired
	WriterService writerService;

	@Autowired
	ReportController controller;

	/**
	 * Use this method to generate the report and store it at the specific location. 
	 * This method uses Rest Call. This can not be tested by integration test. Becuase the Spring context
	 * does not bind the address at test time.
	 */
	public void generateDailyReportByRestApiCall() {
		Path outputFilePath = Paths.get(outputReportLocation);

		try {
			if (Files.exists(outputFilePath)) {
				Files.delete(outputFilePath);

			}
			Files.createFile(Paths.get(outputReportLocation));

			BufferedWriter writer = Files.newBufferedWriter(outputFilePath);

			RestTemplate restTemplate = new RestTemplate();
			
			String plainCreds = restUserId + ":" + restPassword;
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			headers.add("Accept", "text/csv");
			
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<byte[]> response = restTemplate.exchange(urlForDailyReport,HttpMethod.GET, entity, byte[].class);
			Files.write(Paths.get(outputReportLocation), response.getBody());
			writer.close();
		} catch (IOException exception) {
			log.error("Error occured while trying to write the output file.");
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	
	
	/**
	 * Use this method to generate the report and store it at the specific location.
	 */
	public void generateDailyReportByServiceCall() {
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
	 * Use this method to generate the error report and store it at the specific
	 * location.
	 */
	public void generateDailyErrorReport() {
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