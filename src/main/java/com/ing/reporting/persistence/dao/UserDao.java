package com.ing.reporting.persistence.dao;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.ing.reporting.persistence.entity.UserEntity;

@Repository
@Profile({"TEST", "ACCP", "PROD"})
public interface UserDao extends  GenericDao<UserEntity, Integer>{}
