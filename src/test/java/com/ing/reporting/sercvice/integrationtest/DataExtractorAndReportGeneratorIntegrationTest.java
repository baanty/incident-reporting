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

import com.ing.reporting.service.extractor.DataExtractorAndReportGenerator;
import com.ing.reporting.service.parser.InputFileParser;

@SpringBootTest
class DataExtractorAndReportGeneratorIntegrationTest {

	@Value("${output.report.location}")
	String outputReportLocation; 
	
	@Autowired
	DataExtractorAndReportGenerator extractor;
	
	@Autowired
	InputFileParser parser;
	
	
	private static final String EXPECTED_OUTPUT_ASSET_FILE = "Asset Name,Total Incidents,Total Down Time,Rating\r\n" + 
																"Homeloans,7,3%,130\r\n" + 
																"Payments Gateway,11,0%,150\r\n" + 
																"CRM,9,4%,230\r\n" + 
																"Lending Department,4,6%,80\r\n" + 
																"Insurance,16,0%,260";
	
	@Test
	@DirtiesContext
	void testGenerateDailyReoport() throws IOException {
		assertTrue("The " + outputReportLocation + "", Files.exists(Paths.get(outputReportLocation).getParent()));
		parser.readCsvAtScheduleAndPersistData();
		extractor.generateDailyReoport();
		assertTrue(Files.exists(Paths.get(outputReportLocation)));
		
		String actualOutPut = String.join("\r\n", Files.readAllLines(Paths.get(outputReportLocation), Charset.defaultCharset()));
		System.out.println(actualOutPut);
		assertEquals(EXPECTED_OUTPUT_ASSET_FILE, actualOutPut);
		Files.delete(Paths.get(outputReportLocation));
	}

}
