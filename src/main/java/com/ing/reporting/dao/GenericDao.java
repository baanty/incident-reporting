package com.ing.reporting.dao;

import org.springframework.data.repository.CrudRepository;

import com.ing.reporting.entity.GenericEntity;

public interface GenericDao<E extends GenericEntity, I> extends CrudRepository<E, I>{

}
