package com.ing.reporting.dao;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ing.reporting.entity.AssetEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface AssetDao extends GenericDao<AssetEntity, String> {

}
