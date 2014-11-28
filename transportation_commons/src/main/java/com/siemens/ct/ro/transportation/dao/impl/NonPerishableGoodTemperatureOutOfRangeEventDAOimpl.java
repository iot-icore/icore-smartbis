package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.NonPerishableGoodTemperatureOutOfRangeEventDAO;
import com.softwareag.transportation.CEPevents.NonPerishableGoodTemperatureOutOfRangeEvent;

@Transactional
@Repository("NonPerishableGoodTemperatureOutOfRangeEventDAO")
public class NonPerishableGoodTemperatureOutOfRangeEventDAOimpl implements
		NonPerishableGoodTemperatureOutOfRangeEventDAO {
	
	private EntityManager entityManager;

	@Transactional
	public NonPerishableGoodTemperatureOutOfRangeEvent addNonPerishableGoodTemperatureOutOfRangeEvent(
			NonPerishableGoodTemperatureOutOfRangeEvent nonPerishableGoodTemperatureOutOfRangeEventToAdd) {
		entityManager.persist(nonPerishableGoodTemperatureOutOfRangeEventToAdd);
		return nonPerishableGoodTemperatureOutOfRangeEventToAdd;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
