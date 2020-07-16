package com.ing.reporting.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.reporting.exception.GenericReportingApplicationRuntimeException;
import com.ing.reporting.service.AssetService;
import com.ing.reporting.to.AssetTo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReportController {

	@Autowired
	AssetService service;

	@Value("${user.download.file.name}")
	private String csvFileName;

	/**
	 * Use this method to find all the customers from the controller.
	 * 
	 * @return
	 */
	@GetMapping("/findDailyAssets")
	void findDailyAssets(final HttpServletResponse httpServletResponse) {

		httpServletResponse.setContentType("text/csv");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileName + "\"");
		try (final CSVPrinter csvPrinter = new CSVPrinter(httpServletResponse.getWriter(),
				CSVFormat.DEFAULT.withHeader("Asset Name", "Total Incidents", "Total Down Time", "Rating"))) {

			List<AssetTo> daysAssets = service.findAssetStatisticsForThePresentDay();

			if (!CollectionUtils.isEmpty(daysAssets)) {
				daysAssets.stream().filter(anAssetTo -> anAssetTo != null).forEach(anAssetTo -> {
					try {
						csvPrinter.printRecord(Arrays.asList(anAssetTo.getAssetName(), anAssetTo.getTotalIncidents(),
								anAssetTo.getTotalDownTime() + "%", anAssetTo.getRating()));
					} catch (IOException exception) {
						log.error("And error occured while writing data to CSV file.", exception);
						throw new GenericReportingApplicationRuntimeException(exception);
					}
				});
			}

		} catch (Exception exception) {
			log.error("And error occured while writing data to CSV file.", exception);
			throw new GenericReportingApplicationRuntimeException(exception);
		}
	}

}