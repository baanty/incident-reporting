package com.ing.reporting.dao;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ing.reporting.entity.EventEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface EventDao extends  GenericDao<EventEntity, Integer>{
}
