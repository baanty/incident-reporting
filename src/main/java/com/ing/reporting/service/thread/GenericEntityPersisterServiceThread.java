package com.ing.reporting.service.thread;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.ing.reporting.persistence.dao.GenericDao;

/**
 * Use this Runner Thread to save the entity in parallal.
 * @author Pijush Kanti Das
 *
 */
public class GenericEntityPersisterServiceThread<E> implements Runnable {
	
	private final GenericDao<E, ?> dao;
	
	private final E entity;
	
	private final Collection<E> entities;

	/**
	 * Objective of this method is to save the entities 
	 * in different thread. So, processing becomes faster. 
	 * When the batch job runs, the Asset saving and Event save activities are independent
	 * of each other. So, this save should work.
	 */
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
	 * @param service : The DAO for appropriate entity persisting.
	 * @param entity : The Single enity to persist.
	 */
	public GenericEntityPersisterServiceThread(final GenericDao<E, ?> dao, final E entity) {
		super();
		this.dao = dao;
		this.entity = entity;
		this.entities = null;
	}

	
	/**
	 * Use this constructor to create a <code>Runnable</code> instance to
	 * save the entities in the DB.
	 * @param service : The DAO for appropriate entity persisting.
	 * @param entity : The Single enity to persist.
	 */
	public GenericEntityPersisterServiceThread(final GenericDao<E, ?> dao, final Collection<E> entities) {
		super();
		this.dao = dao;
		this.entities = entities;
		this.entity = null;
	}
	
	

}
