package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.ACUnitEventDAO;
import com.softwareag.transportation.CEPevents.ACUnitEvent;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("ACUnitEventDAO")
public class ACUnitEventDAOImpl implements ACUnitEventDAO {

	private EntityManager entityManager;

	@Transactional
	public ACUnitEvent addACUnitEvent(
			ACUnitEvent ACUnitEventToAdd) {
		entityManager.persist(ACUnitEventToAdd);
		return ACUnitEventToAdd;
	}

	@Transactional
	public ACUnitEvent getACUnitEvent(Long id) {
		ACUnitEvent result = null;

		try {
			Query getACUnitEvent = entityManager
					.createQuery("FROM acunitevent op WHERE op.id='" + id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (ACUnitEvent) getACUnitEvent
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteACUnitEvent(Long id) {
		ACUnitEvent ACUnitEventToBeDeleted = getACUnitEvent(id);

		try {
			entityManager.remove(ACUnitEventToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			transactionRequiredException.printStackTrace();
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace();
		}

		return false;
	}

	@Transactional
	public List<ACUnitEvent> getACUnitEvents(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<ACUnitEvent> result = null;

		try {

			TypedQuery<ACUnitEvent> getACUnitEvent = entityManager
					.createQuery(
							"FROM acunitevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							ACUnitEvent.class);

			result = getACUnitEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}
		return result;
	}


	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public List<ACUnitEvent> getACUnitEvent(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<ACUnitEvent> result = null;

		try {

			TypedQuery<ACUnitEvent> getACUnitEvent = entityManager
					.createQuery(
							"FROM acunitevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
									ACUnitEvent.class);

			result = getACUnitEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public ACUnitEvent getLastACUnitEvent(String acUnitID1, long startTime) {
		ACUnitEvent result = null;
		
		try {
			TypedQuery<ACUnitEvent> getACUnitEvent = entityManager
					.createQuery("FROM acunitevent op WHERE op.sensorID='"
							+ acUnitID1 + "' and " + " op.timestamp >"
							+ startTime
							+ " order by op.timestamp desc ",ACUnitEvent.class );

			List<ACUnitEvent> acUnitEvent = getACUnitEvent.getResultList();
			result = (ACUnitEvent) acUnitEvent.get(acUnitEvent.size()-1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}
}
