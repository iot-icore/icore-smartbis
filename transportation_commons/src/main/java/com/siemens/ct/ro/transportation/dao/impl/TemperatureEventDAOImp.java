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

import com.siemens.ct.ro.transportation.dao.TemperatureEventDAO;
import com.softwareag.transportation.CEPevents.TemperatureEvent;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("TemperatureEventDAO")
public class TemperatureEventDAOImp implements TemperatureEventDAO {

	private EntityManager entityManager;

	@Transactional
	public TemperatureEvent addTemperatureEvent(
			TemperatureEvent temperatureEventToAdd) {
		entityManager.persist(temperatureEventToAdd);
		return temperatureEventToAdd;
	}

	@Transactional
	public TemperatureEvent getTemperatureEvent(Long id) {
		TemperatureEvent result = null;

		try {
			Query getTemperatureEvent = entityManager
					.createQuery("FROM temperatureevent op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TemperatureEvent) getTemperatureEvent.getResultList()
					.get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteTemperatureEvent(Long id) {
		TemperatureEvent temperatureMeasurementToBeDeleted = getTemperatureEvent(id);

		try {
			entityManager.remove(temperatureMeasurementToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			// Thrown if invoked on a container-managed entity manager of type
			// PersistenceContextType.TRANSACTION and there is no transaction
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
		}

		return false;
	}

	@Transactional
	public List<TemperatureEvent> getTemperatureEvents(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<TemperatureEvent> result = null;

		try {
			// TypedQuery<TemperatureEvent> getTemperatureEvent =
			// entityManager.createQuery(
			// "FROM temperatureevent op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp, TemperatureEvent.class);

			TypedQuery<TemperatureEvent> getTemperatureEvent = entityManager
					.createQuery(
							"FROM temperatureevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							TemperatureEvent.class);

			result = getTemperatureEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<TemperatureEvent> getTemperatureEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature) {

		List<TemperatureEvent> result = null;

		try {

			TypedQuery<TemperatureEvent> getTemperatureEvent = entityManager
					.createQuery(
							"FROM temperatureevent op WHERE op.temperature_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " and (op.temperature < "
									+ minimumAcceptedTemperature
									+ " or op.temperature > "
									+ maximumAcceptedTemperature + ")"
									+ " order by op.timestamp ",
							TemperatureEvent.class);

			result = getTemperatureEvent.getResultList();

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
	public TemperatureEvent getLastEvent(String sensorID, long startTimestamp) {

		TemperatureEvent result = null;

		try {
			TypedQuery<TemperatureEvent> getTemperatureEvent = entityManager
					.createQuery(
							"FROM temperatureevent op WHERE op.temperature_sensor_id='"
									+ sensorID + "' and " + " op.timestamp >"
									+ startTimestamp
									+ " order by op.timestamp desc ",
							TemperatureEvent.class);

			List<TemperatureEvent> measurements = getTemperatureEvent
					.getResultList();
			if (measurements.size() > 0) {
				result = (TemperatureEvent) measurements.get(0);
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

}
