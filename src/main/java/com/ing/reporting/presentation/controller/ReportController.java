package com.ing.reporting.presentation.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.reporting.common.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.service.WriterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReportController {

	@Autowired
	WriterService service;


	@Value("${user.download.file.name}")
	private String csvFileName;
	
	@Value("${user.download.error.file.name")
	private String errorReportName;
	/**
	 * Use this method to find all the customers from the controller.
	 * 
	 * @return
	 */
	@GetMapping(value = "/findDailyAssets" , produces = "text/csv")
	public void findDailyAssets(final HttpServletResponse httpServletResponse) {

		try {
			httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileName + "\"");			
			service.writeDailyAssetRecordsOnOutStream(httpServletResponse.getWriter());
		} catch (Exception exception) {
			log.error("And error occured while writing data to CSV file.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	
	
	/**
	 * Use this method to find all the customers from the controller.
	 * 
	 * @return
	 */
	@GetMapping(value = "/findDailyErrors" , produces = "text/csv")
	public void findDailyErrors(final HttpServletResponse httpServletResponse) {

		try {
			httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + errorReportName + "\"");
			service.writeDailyErrorRecordsOnOutStream(httpServletResponse.getWriter());
		} catch (Exception exception) {
			log.error("And error occured while writing error data to CSV file.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	

}