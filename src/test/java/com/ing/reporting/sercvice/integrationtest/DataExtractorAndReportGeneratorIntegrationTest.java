package com.ing.reporting.sercvice.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.service.extractor.DataExtractorService;
import com.ing.reporting.service.parser.InputFileParseService;

@SpringBootTest
@Ignore
class DataExtractorAndReportGeneratorIntegrationTest {

	@Value("${output.report.location}")
	String outputReportLocation; 
	
	@Value("${output.error.report.location}")
	String outputErrorReportLocation; 
	
	
	@Autowired
	DataExtractorService extractor;
	
	@Autowired
	InputFileParseService parser;
	
	
	private static final String EXPECTED_OUTPUT_ASSET_FILE = "Asset Name,Total Incidents,Total Down Time,Rating" + 
																"Homeloans,7,3%,130" + 
																"Payments Gateway,11,0%,150" + 
																"CRM,9,4%,230" + 
																"Lending Department,4,6%,80" + 
																"Insurance,16,0%,260";
	
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
