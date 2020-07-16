package com.ing.reporting.persistence.dao;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ing.reporting.persistence.entity.AssetEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface AssetDao extends GenericDao<AssetEntity, String> {

	@Query("select a from AssetEntity a where a.currentTimestamp >= :beginingOffset and a.currentTimestamp <= :endingOffset")
	List<AssetEntity> findAssetEntitiesBetweenTimeRange(@Param("beginingOffset") Timestamp beginingOffset,@Param("endingOffset") Timestamp endingOffset);
}
