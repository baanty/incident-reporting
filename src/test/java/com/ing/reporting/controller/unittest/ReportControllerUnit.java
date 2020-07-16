package com.ing.reporting.controller.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ing.reporting.controller.ReportController;
import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.service.WriterService;


public class ReportControllerUnit {

	@InjectMocks
	ReportController controller;
	
	@Mock
	WriterService service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindDailyAssetsException() {
		Assertions.assertThrows( 
				GenericReportingApplicationRuntimeException.class, () 
					-> controller.findDailyAssets(null));
	}

}
