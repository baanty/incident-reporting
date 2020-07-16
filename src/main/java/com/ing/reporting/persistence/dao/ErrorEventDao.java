package com.ing.reporting.persistence.dao;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ing.reporting.persistence.entity.ErrorEventEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface ErrorEventDao extends  GenericDao<ErrorEventEntity, Integer>{
	
	@Query("select a from ErrorEventEntity a where a.currentTimestamp >= :beginingOffset and a.currentTimestamp <= :endingOffset")
	List<ErrorEventEntity> findErrorEventEntitiesBetweenTimeRange(@Param("beginingOffset") Timestamp beginingOffset,@Param("endingOffset") Timestamp endingOffset);

}
