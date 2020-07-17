package com.ing.reporting.persistence.dao.interationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.ing.reporting.common.to.AssetTo;
import com.ing.reporting.persistence.dao.EventDao;

@SpringBootTest
public class EventDaoTest {

	@Autowired
	EventDao dao;
	
	@Test
	@DirtiesContext
	public void testAssetsForDay() {
		List<AssetTo> assets = dao.getAssetsForDay();
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
	public void testFindDataForCurrentDate() {
		int totalEventsOfToday = dao.findDataForCurrentDate();
		assertEquals(47, totalEventsOfToday);
	}
	
	
}
