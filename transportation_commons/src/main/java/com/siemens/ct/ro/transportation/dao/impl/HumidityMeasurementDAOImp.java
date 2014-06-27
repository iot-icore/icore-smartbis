package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.HumidityMeasurementDAO;
import com.siemens.ct.ro.transportation.entities.HumidityMeasurement;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("HumidityMeasurementDAO")
public class HumidityMeasurementDAOImp implements HumidityMeasurementDAO {

	private EntityManager entityManager;

	@Transactional
	public HumidityMeasurement addHumidityMeasurement(
			HumidityMeasurement humidityMeasurementToAdd) {
		entityManager.persist(humidityMeasurementToAdd);
		return humidityMeasurementToAdd;
	}

	@Transactional
	public HumidityMeasurement getHumidityMeasurement(Long id) {
		HumidityMeasurement result = null;

		try {
			Query getHumidityMeasurement = entityManager
					.createQuery("FROM humiditymeasurement op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (HumidityMeasurement) getHumidityMeasurement
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteHumidityMeasurement(Long id) {
		HumidityMeasurement humidityMeasurementToBeDeleted = getHumidityMeasurement(id);

		try {
			entityManager.remove(humidityMeasurementToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			// Thrown if invoked on a container-managed entity manager of
			// type
			// PersistenceContextType.TRANSACTION and there is no
			// transaction
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached
			// entity
		}

		return false;
	}

	@Transactional
	public List<HumidityMeasurement> getHumidityMeasurements(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<HumidityMeasurement> result = null;

		try {
			// TypedQuery<HumidityMeasurement> getHumidityMeasurement =
			// entityManager.createQuery(
			// "FROM humiditymeasurement op WHERE op.humidity_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp, HumidityMeasurement.class);

			TypedQuery<HumidityMeasurement> getHumidityMeasurement = entityManager
					.createQuery(
							"FROM humiditymeasurement op WHERE op.humidity_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							HumidityMeasurement.class);

			result = getHumidityMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<HumidityMeasurement> getHumidityMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedHumidity, double maximumAcceptedHumidity) {

		List<HumidityMeasurement> result = null;

		try {

			TypedQuery<HumidityMeasurement> getHumidityMeasurement = entityManager
					.createQuery(
							"FROM humiditymeasurement op WHERE op.humidity_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp + " and (op.humidity < "
									+ minimumAcceptedHumidity
									+ " or op.humidity > "
									+ maximumAcceptedHumidity + ")"
									+ " order by op.timestamp ",
							HumidityMeasurement.class);

			result = getHumidityMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	// select * from icore_schema.humiditymeasurement where
	// icore_schema.humiditymeasurement.
	// humidity_sensor_id = "10:00:00:aa:bb:01" and
	// icore_schema.humiditymeasurement.timestamp > 1383213526839
	// and icore_schema.humiditymeasurement.timestamp < 1383213557576

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public HumidityMeasurement getLastHumidityMeasurement(String sensorID, long startTimestamp) {
		HumidityMeasurement result = null;

		try {


			TypedQuery<HumidityMeasurement> getHumidityMeasurement = entityManager
					.createQuery("FROM humiditymeasurement op WHERE op.humidity_sensor_id='"
							+ sensorID + "' and " + " op.timestamp >"
							+ startTimestamp
							+ " order by op.timestamp desc ", HumidityMeasurement.class);
			
			List<HumidityMeasurement> humidityMeasurements = getHumidityMeasurement.getResultList();
			if(humidityMeasurements.size()>0)
			result = (HumidityMeasurement) humidityMeasurements.get(humidityMeasurements.size()-1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

}
