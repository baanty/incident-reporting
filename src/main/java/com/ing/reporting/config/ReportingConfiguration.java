package com.ing.reporting.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ReportingConfiguration {

	@Bean
	public ExecutorService buidExecutor() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return executor;
	}
}
