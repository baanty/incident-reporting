package com.ing.reporting.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.jsonbody.CustomErrorResponse;

class CustomGlobalExceptionHandlerTest {

	@InjectMocks
	CustomGlobalExceptionHandler handler;
	
	@Mock
	HttpServletResponse httpResponse;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	void testSpringHandleNotFound() throws IOException {
		ResponseEntity<CustomErrorResponse> response = handler.springHandleNotFound(httpResponse, new GenericReportingApplicationRuntimeException(new Exception()));
		assertNotNull(response);
		assertEquals("404 NOT_FOUND", response.getStatusCode().toString());
	}

}
