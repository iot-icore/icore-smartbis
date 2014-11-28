package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TemperaturePredictionTwoHourValidationWithACUnitFilteringEventDao;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent;

public class TemperaturePredictionTwoHoursValidationWithACUnitFilteringEventDaoImpl
		implements
		TemperaturePredictionTwoHourValidationWithACUnitFilteringEventDao {

	private EntityManager entityManager;

	@Transactional
	public TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent add(
			TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent) {

		
		entityManager
				.persist(temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent);
		return temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
