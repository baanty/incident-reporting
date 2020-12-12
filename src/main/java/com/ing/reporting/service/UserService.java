package com.ing.reporting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.reporting.common.to.UserTo;
import com.ing.reporting.persistence.dao.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	Iterable<UserTo> findAll(){
		
		if ( userDao.findAll() != null ) {
			
		}
		return null;
	}



}