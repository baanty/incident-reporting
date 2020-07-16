package com.ing.reporting.util;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ing.reporting.common.to.AssetTo;
import com.ing.reporting.common.to.EventTo;
import com.ing.reporting.common.util.MappingUtil;
import com.ing.reporting.persistence.entity.AssetEntity;
import com.ing.reporting.persistence.entity.EventEntity;

class MappingUtilTest {

	@Test
	void testBuildBusinessObjectToTransferObjectEventEntity() {
		EventEntity entity = MappingUtil.buildTransferObjectToBusinessObject(EventTo.builder()
									.severity(98)
									.assetName("Screen")
									.build());
		assertNotNull(entity);
		assertEquals("Screen", entity.getAssetName());
		assertEquals(98, entity.getSeverity());
	}

	@Test
	void testBuildTransferObjectToBusinessObjectEventTo() {
		EventTo entity = MappingUtil.buildBusinessObjectToTransferObject(EventEntity.builder()
				.severity(98)
				.assetName("Screen")
				.build());
		assertNotNull(entity);
		assertEquals("Screen", entity.getAssetName());
		assertEquals(98, entity.getSeverity());
	}

	@Test
	void testBuildBusinessObjectToTransferObjectAssetEntity() {
		AssetEntity entity = MappingUtil.buildTransferObjectToBusinessObject(AssetTo
				.builder()
				.assetName("Siebel")
				.rating(300)
				.totalDownTime(23)
				.totalIncidents(89)
				.build());
		assertNotNull(entity);
		assertEquals("Siebel", entity.getAssetName());
		assertEquals(23, entity.getTotalDownTime());
		assertEquals(89, entity.getTotalIncidents());
	}

	@Test
	void testBuildTransferObjectToBusinessObjectAssetTo() {
		AssetTo to = MappingUtil.buildBusinessObjectToTransferObject(AssetEntity
				.builder()
				.assetName("Siebel")
				.rating(300)
				.totalDownTime(23)
				.totalIncidents(89)
				.build());
		assertNotNull(to);
		assertEquals("Siebel", to.getAssetName());
		assertEquals(23, to.getTotalDownTime());
		assertEquals(89, to.getTotalIncidents());
	}

}
