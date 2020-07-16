package com.ing.reporting.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.service.parser.InputFileParser;
import com.ing.reporting.stub.HttpServletResponseStub;

@SpringBootTest
public class ReportControllerTest {

	@Autowired
	ReportController controller;
	
	@Autowired
	InputFileParser parser;
	
	private static final String EXPECTED_OUTPUT_ASSET_FILE = "Asset Name,Total Incidents,Total Down Time,Rating\r\n" + 
																"Homeloans,7,3%,130\r\n" + 
																"Payments Gateway,11,0%,150\r\n" + 
																"CRM,9,4%,230\r\n" + 
																"Lending Department,4,6%,80\r\n" + 
																"Insurance,16,0%,260\r\n";
	
	@Test
	@DirtiesContext
	public void testGetCurrentDateAssets() throws UnsupportedEncodingException {
		parser.readCsvAtScheduleAndPersistData();
		HttpServletResponseStub httpServletResponse = new HttpServletResponseStub();
		
		controller.findDailyAssets(httpServletResponse);
		ByteArrayOutputStream bos = httpServletResponse.getByteArrayOutputStream();
		String output = bos.toString(Charset.defaultCharset().toString());
		assertNotNull(output);
		System.out.println(output);
		assertEquals(EXPECTED_OUTPUT_ASSET_FILE, output );
	}
}
