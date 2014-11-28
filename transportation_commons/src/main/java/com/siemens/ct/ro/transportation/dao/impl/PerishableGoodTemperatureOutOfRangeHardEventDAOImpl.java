package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.PerishableGoodTemperatureOutOfRangeHardEventDAO;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeHardEvent;

@Transactional
@Repository("PerishableGoodTemperatureOutOfRangeHardEventDAO")
public class PerishableGoodTemperatureOutOfRangeHardEventDAOImpl implements
		PerishableGoodTemperatureOutOfRangeHardEventDAO {
	
	private EntityManager entityManager;

	@Override
	public PerishableGoodTemperatureOutOfRangeHardEvent addPerishableGoodTemperatureOutOfRangeHardEvent(
			PerishableGoodTemperatureOutOfRangeHardEvent perishableGoodTemperatureOutOfRangeHardEventAdd) {
		entityManager.persist(perishableGoodTemperatureOutOfRangeHardEventAdd);
		return perishableGoodTemperatureOutOfRangeHardEventAdd;
	}
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
