package com.ing.reporting.controller.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.presentation.controller.ReportController;
import com.ing.reporting.presentation.httpresponsestub.HttpServletResponseStub;
import com.ing.reporting.service.parser.InputFileParseService;

@SpringBootTest
public class ReportControllerTest {

	@Autowired
	ReportController controller;
	
	@Autowired
	InputFileParseService parser;
	
	private static final String EXPECTED_OUTPUT_ASSET_FILE = "Asset Name,Total Incidents,Total Down Time,Rating\r\n" + 
			"CRM,18,57%,500\r\n" + 
			"Homeloans,14,74%,340\r\n" + 
			"Insurance,32,6%,740\r\n" + 
			"Lending Department,8,83%,200\r\n" + 
			"Payments Gateway,22,73%,480\r\n";
	private static final String EXPECTED_OUTPUT_PREFIX_ERROR_FILE = "Erroneous Record,Time\r\n" + 
			"Payments Gateway";
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
	
	
	@Test
	@DirtiesContext
	public void testGetErroneousEvents() throws UnsupportedEncodingException {
		parser.readCsvAtScheduleAndPersistData();
		HttpServletResponseStub httpServletResponse = new HttpServletResponseStub();
		
		controller.findDailyErrors(httpServletResponse);
		ByteArrayOutputStream bos = httpServletResponse.getByteArrayOutputStream();
		String output = bos.toString(Charset.defaultCharset().toString());
		assertNotNull(output);
		assertTrue( output.startsWith(EXPECTED_OUTPUT_PREFIX_ERROR_FILE) );
	}
}
