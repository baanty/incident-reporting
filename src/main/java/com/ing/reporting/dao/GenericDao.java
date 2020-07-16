package com.ing.reporting.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao<E, I> extends CrudRepository<E, I>{

}
