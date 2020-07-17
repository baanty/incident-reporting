package com.ing.reporting.sercvice.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.service.extractor.DataExtractorService;
import com.ing.reporting.service.parser.InputFileParseService;

@SpringBootTest
class DataExtractorAndReportGeneratorIntegrationTest {

	@Value("${output.report.location}")
	String outputReportLocation; 
	
	@Value("${output.error.report.location}")
	String outputErrorReportLocation; 
	
	
	@Autowired
	DataExtractorService extractor;
	
	@Autowired
	InputFileParseService parser;
	
	
	private static final String EXPECTED_OUTPUT_ASSET_FILE = "Asset Name,Total Incidents,Total Down Time,RatingCRM,18,57%,500Homeloans,14,74%,340Insurance,32,6%,740Lending Department,8,83%,200Payments Gateway,22,73%,480";
	
	private static final String EXPECTED_OUTPUT_PREFIX_ERROR_FILE = "Erroneous Record,TimePayments Gateway";
	
	@Test
	@DirtiesContext
	void testGenerateDailyReoport() throws IOException {
		parser.readCsvAtScheduleAndPersistData();
		extractor.generateDailyReoport();
		assertTrue("The " + outputReportLocation + "Does not exist. It must exist.", Files.exists(Paths.get(outputReportLocation).getParent()));
		
		String actualOutPut = String.join("", Files.readAllLines(Paths.get(outputReportLocation), Charset.defaultCharset()));
		Files.delete(Paths.get(outputReportLocation));
		assertEquals(EXPECTED_OUTPUT_ASSET_FILE, actualOutPut);
	}

	@Test
	@DirtiesContext
	void testGenerateDailyErrorReoport() throws IOException {
		parser.readCsvAtScheduleAndPersistData();
		extractor.generateDailyErrorReoport();
		assertTrue("The " + outputErrorReportLocation + "Does not exist. It must exist.", Files.exists(Paths.get(outputReportLocation).getParent()));
		
		String actualOutPut = String.join("", Files.readAllLines(Paths.get(outputErrorReportLocation), Charset.defaultCharset()));
		Files.delete(Paths.get(outputErrorReportLocation));
		assertTrue(actualOutPut.startsWith(EXPECTED_OUTPUT_PREFIX_ERROR_FILE));
	}
}
