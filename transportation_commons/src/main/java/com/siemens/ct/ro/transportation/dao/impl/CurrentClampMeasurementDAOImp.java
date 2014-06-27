package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.CurrentClampMeasurementDAO;
import com.siemens.ct.ro.transportation.entities.CurrentClampMeasurement;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("CurrentClampMeasurementDAO")
public class CurrentClampMeasurementDAOImp implements CurrentClampMeasurementDAO {

	private EntityManager entityManager;

	@Transactional
	public CurrentClampMeasurement addCurrentClampMeasurement(
			CurrentClampMeasurement currentClampMeasurementToAdd) {
		entityManager.persist(currentClampMeasurementToAdd);
		return currentClampMeasurementToAdd;
	}

	@Transactional
	public CurrentClampMeasurement getCurrentClampMeasurement(Long id) {
		CurrentClampMeasurement result = null;

		try {
			Query getCurrentClampMeasurement = entityManager
					.createQuery("FROM currentClampmeasurement op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (CurrentClampMeasurement) getCurrentClampMeasurement
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteCurrentClampMeasurement(Long id) {
		CurrentClampMeasurement currentClampMeasurementToBeDeleted = getCurrentClampMeasurement(id);

		try {
			entityManager.remove(currentClampMeasurementToBeDeleted);
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
	public List<CurrentClampMeasurement> getCurrentClampMeasurements(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<CurrentClampMeasurement> result = null;

		try {
			// TypedQuery<CurrentClampMeasurement> getCurrentClampMeasurement =
			// entityManager.createQuery(
			// "FROM currentClampmeasurement op WHERE op.currentClamp_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp, CurrentClampMeasurement.class);

			TypedQuery<CurrentClampMeasurement> getCurrentClampMeasurement = entityManager
					.createQuery(
							"FROM currentClampmeasurement op WHERE op.currentClamp_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							CurrentClampMeasurement.class);

			result = getCurrentClampMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<CurrentClampMeasurement> getCurrentClampMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedCurrentClamp, double maximumAcceptedCurrentClamp) {

		List<CurrentClampMeasurement> result = null;

		try {
			// TypedQuery<CurrentClampMeasurement> getCurrentClampMeasurement =
			// entityManager.createQuery(
			// "FROM currentClampmeasurement op WHERE op.currentClamp_sensor_id='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp + " and (op.currentClamp < " +
			// minimumAcceptedCurrentClamp + " or op.currentClamp > " +
			// maximumAcceptedCurrentClamp + ")", CurrentClampMeasurement.class);

			TypedQuery<CurrentClampMeasurement> getCurrentClampMeasurement = entityManager
					.createQuery(
							"FROM currentClampmeasurement op WHERE op.currentClamp_sensor_id='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp + " and (op.currentClamp < "
									+ minimumAcceptedCurrentClamp
									+ " or op.currentClamp > "
									+ maximumAcceptedCurrentClamp + ")"
									+ " order by op.timestamp ",
							CurrentClampMeasurement.class);

			result = getCurrentClampMeasurement.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	// select * from icore_schema.currentClampmeasurement where
	// icore_schema.currentClampmeasurement.
	// currentClamp_sensor_id = "10:00:00:aa:bb:01" and
	// icore_schema.currentClampmeasurement.timestamp > 1383213526839
	// and icore_schema.currentClampmeasurement.timestamp < 1383213557576

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public CurrentClampMeasurement getLastCCMeasurement(String sensorID, long startTime) {

		CurrentClampMeasurement result = null;

		try {
			TypedQuery<CurrentClampMeasurement> getCurrentClampMeasurement = entityManager
					.createQuery("FROM currentClampmeasurement op WHERE op.currentClamp_sensor_id='"+ sensorID
							+ "' and " + " op.timestamp >"
							+ startTime
							+ " order by op.timestamp desc ",CurrentClampMeasurement.class );

			List<CurrentClampMeasurement> ccMeasurements = getCurrentClampMeasurement.getResultList();
			result = (CurrentClampMeasurement) ccMeasurements.get(ccMeasurements.size()-1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

}
