package com.ing.reporting.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.reporting.dao.EventDao;
import com.ing.reporting.entity.EventEntity;
import com.ing.reporting.to.EventTo;
import com.ing.reporting.util.MappingUtil;

@Service
public class EventService {

	@Autowired
	EventDao eventDao;
	
	/**
	 * Use this service method to save the events.
	 * @param eventTo
	 * @return
	 */
	EventTo saveEvent(final EventTo eventTo) {
		eventDao.save(MappingUtil.buildTransferObjectToBusinessObject(eventTo));
		return eventTo;
	}
	
	
	/**
	 * Yse this method to save a collection of events.
	 * @param eventTos
	 * @return
	 */
	Iterable<EventEntity> saveEvent(final List<EventTo> eventTos) {
		return eventDao.saveAll(eventTos
							.stream()
							.map(MappingUtil::buildTransferObjectToBusinessObject)
							.collect(Collectors.toList())
							);
	}


}