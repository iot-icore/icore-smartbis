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

import com.siemens.ct.ro.transportation.dao.TemperatureMeasurementDAO;
import com.siemens.ct.ro.transportation.entities.TemperatureMeasurement;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("TemperatureMeasurementDAO")
public class TemperatureMeasurementDAOImp implements TemperatureMeasurementDAO {


	private EntityManager entityManager;

	@Transactional
	public TemperatureMeasurement addTemperatureMeasurement(
			TemperatureMeasurement temperatureMeasurementToAdd) {
		entityManager.persist(temperatureMeasurementToAdd);
		return temperatureMeasurementToAdd;
	}

	@Transactional
	public TemperatureMeasurement getTemperatureMeasurement(Long id) {
		TemperatureMeasurement result = null;

		try {
			Query getTemperatureMeasurement = entityManager
					.createQuery("FROM temperaturemeasurement op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TemperatureMeasurement) getTemperatureMeasurement
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteTemperatureMeasurement(Long id) {
		TemperatureMeasurement temperatureMeasurementToBeDeleted = getTemperatureMeasurement(id);

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
	public List<TemperatureMeasurement> getTemperatureMeasurements(
			String sensorID, long startTimestamp, long finishTimestamp) {

		List<TemperatureMeasurement> result = null;

		try {
			// TypedQuery<TemperatureMeasurement> getTemperatureMeasurement =
			// entityManager.createQuery(
			// "FROM temperaturemeasurement op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp, TemperatureMeasurement.class);

			TypedQuery<TemperatureMeasurement> getTemperatureMeasurement = entityManager
					.createQuery(
							"FROM temperaturemeasurement op WHERE op.temperature_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							TemperatureMeasurement.class);

			result = getTemperatureMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<TemperatureMeasurement> getTemperatureMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature) {

		List<TemperatureMeasurement> result = null;

		try {
			// TypedQuery<TemperatureMeasurement> getTemperatureMeasurement =
			// entityManager.createQuery(
			// "FROM temperaturemeasurement op WHERE op.temperature_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <" +
			// finishTimestamp + " and (op.temperature < " +
			// minimumAcceptedTemperature + " or op.temperature > " +
			// maximumAcceptedTemperature + ")", TemperatureMeasurement.class);

			TypedQuery<TemperatureMeasurement> getTemperatureMeasurement = entityManager
					.createQuery(
							"FROM temperaturemeasurement op WHERE op.temperature_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " and (op.temperature < "
									+ minimumAcceptedTemperature
									+ " or op.temperature > "
									+ maximumAcceptedTemperature + ")"
									+ " order by op.timestamp ",
							TemperatureMeasurement.class);

			result = getTemperatureMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	// select * from icore_schema.temperaturemeasurement where
	// icore_schema.temperaturemeasurement.
	// temperature_sensor_id = "10:00:00:aa:bb:01" and
	// icore_schema.temperaturemeasurement.timestamp > 1383213526839
	// and icore_schema.temperaturemeasurement.timestamp < 1383213557576

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
