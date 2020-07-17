package com.ing.reporting.persistence.dao.interationtest.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.common.to.AssetTo;
import com.ing.reporting.service.EventService;

@SpringBootTest
class EventServiceTest {


	@Autowired
	EventService service;
	
	@Test
	@DirtiesContext
	public void testAssetsForDay() {
		List<AssetTo> assets = service.getAssetsForDay();
		assertNotNull(assets);
		assertEquals(5, assets.size());
		AssetTo to = assets.get(0);
		assertNotNull(to);
		assertEquals("CRM", to.getAssetName());
		assertEquals(9, to.getTotalIncidents());
		assertEquals(79, to.getTotalDownTime());
		assertEquals(250, to.getRating());
		assertEquals(21, to.getTotalUpTime());
	}
	
	@Test
	@DirtiesContext
	public void testIsTodaysDataLoaded() {
		boolean isTodaysDataLoaded = service.isTodaysDataLoaded();
		assertTrue(isTodaysDataLoaded);
	}
	

}
