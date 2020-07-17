package com.ing.reporting.util;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ing.reporting.common.to.EventTo;
import com.ing.reporting.common.util.MappingUtil;
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

}
