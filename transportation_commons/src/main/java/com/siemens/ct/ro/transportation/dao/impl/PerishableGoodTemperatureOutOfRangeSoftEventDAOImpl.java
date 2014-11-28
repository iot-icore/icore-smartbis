package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.PerishableGoodTemperatureOutOfRangeSoftEventDAO;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeSoftEvent;

@Transactional
@Repository("PerishableGoodTemperatureOutOfRangeSoftEventDAO")
public class PerishableGoodTemperatureOutOfRangeSoftEventDAOImpl implements
		PerishableGoodTemperatureOutOfRangeSoftEventDAO {
	
	private EntityManager entityManager;

	@Override
	public PerishableGoodTemperatureOutOfRangeSoftEvent addPerishableGoodTemperatureOutOfRangeSoftEvent(
			PerishableGoodTemperatureOutOfRangeSoftEvent perishableGoodTemperatureOutOfRangeSoftEventAdd) {
		entityManager.persist(perishableGoodTemperatureOutOfRangeSoftEventAdd);
		return perishableGoodTemperatureOutOfRangeSoftEventAdd;
	}
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
