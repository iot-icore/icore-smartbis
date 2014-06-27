/**
 * 
 */
package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TemperaturePredictionDAO;
import com.siemens.ct.ro.transportation.dao.TemperaturePredictionDAO;
import com.siemens.ct.ro.transportation.entities.TemperaturePrediction;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("TemperaturePredictionDAOImpl")
public class TemperaturePredictionDAOImpl implements TemperaturePredictionDAO {


	private EntityManager entityManager;

	@Transactional
	public TemperaturePrediction addTemperaturePrediction(
			TemperaturePrediction TemperaturePredictionToAdd) {
		entityManager.persist(TemperaturePredictionToAdd);
		return TemperaturePredictionToAdd;
	}

	@Transactional
	public TemperaturePrediction getTemperaturePrediction(Long id) {
		TemperaturePrediction result = null;

		try {
			Query getTemperaturePrediction = entityManager
					.createQuery("FROM temperatureprediction op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TemperaturePrediction) getTemperaturePrediction
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteTemperaturePrediction(Long id) {
		TemperaturePrediction TemperaturePredictionToBeDeleted = getTemperaturePrediction(id);

		try {
			entityManager.remove(TemperaturePredictionToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			// Thrown if invoked on a container-managed entity manager of type
			// PersistenceContextType.TRANSACTION and there is no transaction
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
		}

		return false;
	}

	@Transactional
	public List<TemperaturePrediction> getTemperaturePredictions(
			String sensorID, long startTimestamp, long finishTimestamp) {

		List<TemperaturePrediction> result = null;

		try {
			// TypedQuery<TemperaturePrediction> getTemperaturePrediction =
			// entityManager.createQuery(
			// "FROM TemperaturePrediction op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp, TemperaturePrediction.class);

			TypedQuery<TemperaturePrediction> getTemperaturePrediction = entityManager
					.createQuery(
							"FROM temperatureprediction op WHERE op.temperatureSensorIdContainerTemp='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							TemperaturePrediction.class);

			result = getTemperaturePrediction.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<TemperaturePrediction> getTemperaturePredictionsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature) {

		List<TemperaturePrediction> result = null;

		try {

			TypedQuery<TemperaturePrediction> getTemperaturePrediction = entityManager
					.createQuery(
							"FROM temperatureprediction op WHERE op.temperatureSensorIdContainerTemp='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " and (op.temperature < "
									+ minimumAcceptedTemperature
									+ " or op.temperature > "
									+ maximumAcceptedTemperature + ")"
									+ " order by op.timestamp ",
							TemperaturePrediction.class);

			result = getTemperaturePrediction.getResultList();

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

	@Override
	public List<TemperaturePrediction> getTemperaturePredictions(
			String sensorID) {
		List<TemperaturePrediction> result = null;

		try {
			// TypedQuery<TemperaturePrediction> getTemperaturePrediction =
			// entityManager.createQuery(
			// "FROM TemperaturePrediction op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp, TemperaturePrediction.class);

			TypedQuery<TemperaturePrediction> getTemperaturePrediction = entityManager
					.createQuery(
							"FROM temperatureprediction op WHERE op.temperatureSensorIdContainerTemp='"
									+ sensorID + "' " 
									+ " order by op.timestamp ",
							TemperaturePrediction.class);

			result = getTemperaturePrediction.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}
}
