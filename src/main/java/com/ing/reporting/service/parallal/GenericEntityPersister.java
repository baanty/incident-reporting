package com.ing.reporting.service.parallal;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.ing.reporting.dao.GenericDao;
import com.ing.reporting.entity.GenericEntity;

/**
 * Use this Runner Thread to save the entity in parallal.
 * @author Pijush Kanti Das
 *
 */
public class GenericEntityPersister<E extends GenericEntity> implements Runnable {
	
	private final GenericDao<E, ?> dao;
	
	private final E entity;
	
	private final Collection<E> entities;

	@Override
	public void run() {
		
		if ( dao != null ) {
			
			if (entity != null ) {
				dao.save(entity);
				return;
			} else if ( !CollectionUtils.isEmpty(entities)) {
				dao.saveAll(entities);
			}
		}
		
		
	}

	/**
	 * Use this constructor to create a <code>Runnable</code> instance to
	 * save the single entity in the DB.
	 * @param dao : The DAO for appropriate entity persisting.
	 * @param entity : The Single enity to persist.
	 */
	public GenericEntityPersister(final GenericDao<E, ?> dao, final E entity) {
		super();
		this.dao = dao;
		this.entity = entity;
		this.entities = null;
	}

	
	/**
	 * Use this constructor to create a <code>Runnable</code> instance to
	 * save the entities in the DB.
	 * @param dao : The DAO for appropriate entity persisting.
	 * @param entity : The Single enity to persist.
	 */
	public GenericEntityPersister(final GenericDao<E, ?> dao, final Collection<E> entities) {
		super();
		this.dao = dao;
		this.entities = entities;
		this.entity = null;
	}
	
	

}
