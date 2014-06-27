package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.ACUnitMeasurementDAO;
import com.siemens.ct.ro.transportation.entities.ACUnitMeasurement;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("ACUnitMeasurementDAO")
public class ACUnitMeasurementDAOImpl implements ACUnitMeasurementDAO {

	private EntityManager entityManager;

	@Transactional
	public ACUnitMeasurement addACUnitMeasurement(
			ACUnitMeasurement ACUnitMeasurementToAdd) {
		entityManager.persist(ACUnitMeasurementToAdd);
		return ACUnitMeasurementToAdd;
	}

	@Transactional
	public ACUnitMeasurement getACUnitMeasurement(Long id) {
		ACUnitMeasurement result = null;

		try {
			Query getACUnitMeasurement = entityManager
					.createQuery("FROM acunitmeasurement op WHERE op.id='" + id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (ACUnitMeasurement) getACUnitMeasurement
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteACUnitMeasurement(Long id) {
		ACUnitMeasurement ACUnitMeasurementToBeDeleted = getACUnitMeasurement(id);

		try {
			entityManager.remove(ACUnitMeasurementToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			transactionRequiredException.printStackTrace();
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace();
		}

		return false;
	}

	@Transactional
	public List<ACUnitMeasurement> getACUnitMeasurements(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<ACUnitMeasurement> result = null;

		try {

			TypedQuery<ACUnitMeasurement> getACUnitMeasurement = entityManager
					.createQuery(
							"FROM acunitmeasurement op WHERE op.acunit_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							ACUnitMeasurement.class);

			result = getACUnitMeasurement.getResultList();

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
	public List<ACUnitMeasurement> getACUnitMeasurement(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<ACUnitMeasurement> result = null;

		try {

			TypedQuery<ACUnitMeasurement> getACUnitMeasurement = entityManager
					.createQuery(
							"FROM acunitmeasurement op WHERE op.acunit_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
									ACUnitMeasurement.class);

			result = getACUnitMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public ACUnitMeasurement getLastACUnitMeasurement(String acUnitID1, long startTime) {
		ACUnitMeasurement result = null;
		
		try {
			TypedQuery<ACUnitMeasurement> getACUnitMeasurement = entityManager
					.createQuery("FROM acunitmeasurement op WHERE op.acunit_id='"
							+ acUnitID1 + "' and " + " op.timestamp >"
							+ startTime
							+ " order by op.timestamp desc ",ACUnitMeasurement.class );

			List<ACUnitMeasurement> acUnitMeasurement = getACUnitMeasurement.getResultList();
			result = (ACUnitMeasurement) acUnitMeasurement.get(acUnitMeasurement.size()-1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}
}
