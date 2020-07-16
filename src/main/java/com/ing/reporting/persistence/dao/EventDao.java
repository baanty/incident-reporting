package com.ing.reporting.persistence.dao;


import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.reporting.persistence.entity.EventEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface EventDao extends  GenericDao<EventEntity, Integer>{
	
	@Query("select count(*) from EventEntity e where to_char(e.currentTimestamp,'YYYY MM DD') = to_char(CURRENT_DATE,'YYYY MM DD') ")
	int findDataForCurrentDate();

}
