package com.ing.reporting.sercvice.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ing.reporting.persistence.dao.ErrorEventDao;
import com.ing.reporting.persistence.entity.ErrorEventEntity;
import com.ing.reporting.service.ErrorEventService;
import com.ing.reporting.to.ErrorEventTo;

class ErrorEvenTest {

	@InjectMocks
	ErrorEventService service;
	
	@Mock
	ErrorEventDao errorEventDao;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		List<ErrorEventEntity> entites = new ArrayList<ErrorEventEntity>();
		ErrorEventEntity boOne = ErrorEventEntity
								.builder()
								.erroneousRecord("8855-XXX")
								.currentTimestamp(Timestamp.valueOf(LocalDateTime.now()))
								.build();
		entites.add(boOne);
		when(errorEventDao.findErrorEventEntitiesBetweenTimeRange(any(), any())).thenReturn(entites);
	}
	
	@Test
	void testFindAssetStatisticsForThePresentDay() {
		List<ErrorEventTo> tos = service.findErrorEventsForThePresentDay();
		assertNotNull(tos);
		assertEquals(1, tos.size());
		assertEquals("8855-XXX", tos.get(0).getErroneousRecord());
	}

}
