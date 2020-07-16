package com.ing.reporting.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ing.reporting.service.EventService;
import com.ing.reporting.service.parser.InputFileParser;

/**
 * USe this class to do initial activites in Spring. SHould not be used as a bean in other
 * Beans.
 * @author Pijush Kanti Das
 *
 */
@Component
public class BootingDataLoad {

	@Autowired
	EventService eventService;
	 
	@Autowired
	InputFileParser parser;
	
	@PostConstruct
	public void bootApplication() {
		
		if( !eventService.isTodaysDataLoaded() ) {
			parser.readCsvAtScheduleAndPersistData();
		}
	}
}
