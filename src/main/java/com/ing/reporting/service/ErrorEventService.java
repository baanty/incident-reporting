package com.ing.reporting.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.reporting.common.to.ErrorEventTo;
import com.ing.reporting.common.util.MappingUtil;
import com.ing.reporting.persistence.dao.ErrorEventDao;

@Service
public class ErrorEventService {

	@Autowired
	ErrorEventDao errorEventDao;

	
	/**
	 * USe this method to get all the daily erroneous records from 
	 * the last run. If there was any invalid data sent in the CSV, this method 
	 * will return you a collection of all those records.
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<ErrorEventTo> findErrorEventsForThePresentDay() {
		Timestamp beginingOffset = Timestamp.valueOf(LocalDateTime.now());
		beginingOffset.setHours(0);
		beginingOffset.setMinutes(0);
		beginingOffset.setSeconds(0);
		
		Timestamp endingOffset = Timestamp.valueOf(LocalDateTime.now());
		endingOffset.setHours(23);
		endingOffset.setMinutes(59);
		endingOffset.setSeconds(59);
		return errorEventDao.findErrorEventEntitiesBetweenTimeRange(beginingOffset, endingOffset)
				.stream()
				.map(MappingUtil::buildErrorEventToFromErrorEventEntity)
				.collect(Collectors.toList());
	}


}