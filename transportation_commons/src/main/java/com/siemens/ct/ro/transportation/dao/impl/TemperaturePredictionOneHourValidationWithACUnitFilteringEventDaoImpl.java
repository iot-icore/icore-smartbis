package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TemperaturePredictionOneHourValidationWithACUnitFilteringEventDao;
import com.softwareag.transportation.CEPevents.TemperaturePredictionOneHourValidationWithACUnitFilteringEvent;

public class TemperaturePredictionOneHourValidationWithACUnitFilteringEventDaoImpl
		implements
		TemperaturePredictionOneHourValidationWithACUnitFilteringEventDao {

	private EntityManager entityManager;

	@Transactional
	public TemperaturePredictionOneHourValidationWithACUnitFilteringEvent add(
			TemperaturePredictionOneHourValidationWithACUnitFilteringEvent temperaturePredictionOneHourValidationWithACUnitFilteringEvent) {
		entityManager
				.persist(temperaturePredictionOneHourValidationWithACUnitFilteringEvent);
		return temperaturePredictionOneHourValidationWithACUnitFilteringEvent;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
