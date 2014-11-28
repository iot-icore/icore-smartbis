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

import com.siemens.ct.ro.transportation.dao.TemperaturePredictionEventDAO;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("TemperaturePredictionEventDAOImpl")
public class TemperaturePredictionEventDAOImpl implements
		TemperaturePredictionEventDAO {

	private EntityManager entityManager;

	@Transactional
	public TemperaturePredictionEvent addTemperaturePredictionEvent(
			TemperaturePredictionEvent TemperaturePredictionToAdd) {
		entityManager.persist(TemperaturePredictionToAdd);
		return TemperaturePredictionToAdd;
	}

	@Transactional
	public TemperaturePredictionEvent getTemperaturePredictionEvent(Long id) {
		TemperaturePredictionEvent result = null;

		try {
			Query getTemperaturePrediction = entityManager
					.createQuery("FROM temperaturepredictionevent op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TemperaturePredictionEvent) getTemperaturePrediction
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteTemperaturePredictionEvent(Long id) {
		TemperaturePredictionEvent TemperaturePredictionToBeDeleted = getTemperaturePredictionEvent(id);

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
	public List<TemperaturePredictionEvent> getTemperaturePredictionEvents(
			String sensorID, long startTimestamp, long finishTimestamp) {

		List<TemperaturePredictionEvent> result = null;
		
		try {
			// TypedQuery<TemperaturePrediction> getTemperaturePrediction =
			// entityManager.createQuery(
			// "FROM TemperaturePrediction op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp, TemperaturePrediction.class);

			TypedQuery<TemperaturePredictionEvent> getTemperaturePrediction = entityManager
					.createQuery(
							"FROM temperaturepredictionevent op WHERE op.temperatureSensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							TemperaturePredictionEvent.class);
			
			result = getTemperaturePrediction.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<TemperaturePredictionEvent> getTemperaturePredictionEventssOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature) {

		List<TemperaturePredictionEvent> result = null;

		try {

			TypedQuery<TemperaturePredictionEvent> getTemperaturePrediction = entityManager
					.createQuery(
							"FROM temperaturepredictionevent op WHERE op.temperatureSensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " and (op.temperature < "
									+ minimumAcceptedTemperature
									+ " or op.temperature > "
									+ maximumAcceptedTemperature + ")"
									+ " order by op.timestamp ",
							TemperaturePredictionEvent.class);

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
	public List<TemperaturePredictionEvent> getTemperaturePredictionEvents(
			String sensorID) {
		List<TemperaturePredictionEvent> result = null;

		try {

			System.out.println("getTemperaturePredictionEvents sensorID= " + sensorID);
			
			TypedQuery<TemperaturePredictionEvent> getTemperaturePrediction = entityManager.createQuery(
							"FROM temperaturepredictionevent op WHERE op.temperatureSensorID='"
									+ sensorID + "' "
									+ " order by op.timestamp ",
									TemperaturePredictionEvent.class);
			
			System.out.println("in getTemperaturePredictionEvents: query made " + getTemperaturePrediction.toString() );
			
			result = getTemperaturePrediction.getResultList();
		} catch (Exception exception) {
			System.out.println("Exception: " + exception.getMessage());
			exception.printStackTrace();
		}

		return result;
	}



}
