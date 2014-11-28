package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.HumidityOutOfRangeEventDAO;
import com.softwareag.transportation.CEPevents.HumidityOutOfRangeEvent;

@Transactional
@Repository("HumidityOutOfRangeEventDAO")
public class HumidityOutOfRangeEventDAOImpl implements HumidityOutOfRangeEventDAO{

	private EntityManager entityManager;
	
	@Override
	@Transactional
	public HumidityOutOfRangeEvent addHumidityOutOfRangeEvent(
			HumidityOutOfRangeEvent humidityOutOfRangeEventToAdd) {
		entityManager.persist(humidityOutOfRangeEventToAdd);
		return humidityOutOfRangeEventToAdd;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}




}
