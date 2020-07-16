package com.ing.reporting.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.service.WriterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReportController {

	@Autowired
	WriterService service;

	@Value("${user.download.file.name}")
	private String csvFileName;

	/**
	 * Use this method to find all the customers from the controller.
	 * 
	 * @return
	 */
	@GetMapping("/findDailyAssets")
	void findDailyAssets(final HttpServletResponse httpServletResponse) {

		try {
			httpServletResponse.setContentType("text/csv");
			httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileName + "\"");
			service.writeOutStream(httpServletResponse.getWriter());
		} catch (IOException exception) {
			log.error("And error occured while writing data to CSV file.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}

	}
	
	

}