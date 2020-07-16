package com.ing.reporting.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ing.reporting.service.parser.InputFileParseService;

/**
 * Use this Scheduler to schedule the CSV Extraction and Reading.
 * @author Pijush Kanti Das.
 *
 */
@Component
public class CsvReadingAndDataUploadSchedulerService {
	
	@Autowired
	InputFileParseService pasrser;


	/**
	 * Use this method to read the CSV files 
	 * and load it to in memeory H2 Database.
	 * Please mark the method for a cron job run at a certain point
	 * in the day.
	 */
	@Scheduled(cron = "${cron.expression.csv.load.job}")
	public void readCsvAtScheduleAndPersistData() {
		pasrser.readCsvAtScheduleAndPersistData();
	}

}
