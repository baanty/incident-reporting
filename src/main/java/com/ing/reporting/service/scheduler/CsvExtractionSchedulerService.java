package com.ing.reporting.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ing.reporting.service.extractor.DataExtractorService;

/**
 * Use this Scheduler to schedule the CSV Extraction and Reading.
 * @author Pijush Kanti Das.
 *
 */
@Component
public class CsvExtractionSchedulerService {
	
	@Autowired
	DataExtractorService extractor;

	/**
	 * Schedules the daily data report generation.
	 */
	@Scheduled(cron = "${cron.expression.error.report.generator.job}")
	public void generateDailyErrorReoport() {
		extractor.generateDailyErrorReoport();
	}
	
	/**
	 * Schedules the daily error data report generation.
	 */
	@Scheduled(cron = "${cron.expression.report.generator.job}")
	public void generateDailyReoport() {
		extractor.generateDailyReoport();
	}
}
