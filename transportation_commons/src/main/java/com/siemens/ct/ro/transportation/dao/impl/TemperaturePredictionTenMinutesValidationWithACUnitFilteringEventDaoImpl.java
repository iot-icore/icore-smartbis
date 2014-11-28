package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventDao;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent;

public class TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventDaoImpl
		implements
		TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventDao {

	private EntityManager entityManager;

	@Transactional
	public TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent add(
			TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent) {
		entityManager
				.persist(temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent);
		return temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
