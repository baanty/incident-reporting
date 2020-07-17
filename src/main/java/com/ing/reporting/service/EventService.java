package com.ing.reporting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.reporting.common.to.AssetTo;
import com.ing.reporting.persistence.dao.EventDao;

@Service
public class EventService {

	@Autowired
	EventDao eventDao;

	
	/**
	 * This method Returns a boolean result to 
	 * inform the caller if todays results are
	 * already loaded to the database. Handy, when doing 
	 * day to day check of data load.
	 * 
	 * @return : Returns a boolean result to 
	 * inform the caller if todays results are
	 * already loaded to the database. Handy, when doing 
	 * day to day check of data load. 
	 */
	public boolean isTodaysDataLoaded() {
		return eventDao.findDataForCurrentDate() > 0 ;
	}

	/**
	 * USe this method to get all the assets for the
	 * day.
	 * @return : A collection of assets for the day.
	 */
	public List<AssetTo> getAssetsForDay() {
		return eventDao.getAssetsForDay();
	}

}