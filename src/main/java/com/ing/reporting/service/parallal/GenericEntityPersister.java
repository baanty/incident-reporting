package com.ing.reporting.service.parallal;

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

	@Override
	public void run() {
		if ( entity != null && dao != null ) {
			dao.save(entity);
		}
	}

	public GenericEntityPersister(final GenericDao<E, ?> dao, final E entity) {
		super();
		this.dao = dao;
		this.entity = entity;
	}

}
