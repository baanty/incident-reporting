package com.ing.reporting.sercvice.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ing.reporting.persistence.dao.AssetDao;
import com.ing.reporting.persistence.entity.AssetEntity;
import com.ing.reporting.service.AssetServiceImpl;
import com.ing.reporting.to.AssetTo;

class AssetServiceImplTest {

	@InjectMocks
	AssetServiceImpl assetServiceImpl;
	
	@Mock
	AssetDao assetDao;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		List<AssetEntity> entites = new ArrayList<AssetEntity>();
		AssetEntity boOne = AssetEntity
								.builder()
								.assetName("CRM")
								.totalDownTime(100)
								.build();
		entites.add(boOne);
		when(assetDao.findAssetEntitiesBetweenTimeRange(any(), any())).thenReturn(entites);
	}
	
	@Test
	void testFindAssetStatisticsForThePresentDay() {
		List<AssetTo> tos = assetServiceImpl.findAssetStatisticsForThePresentDay();
		assertNotNull(tos);
		assertEquals(1, tos.size());
		assertEquals("CRM", tos.get(0).getAssetName());
		assertEquals(100, tos.get(0).getTotalDownTime());
	}

}
