package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.SensorDao;
import com.siemens.ct.ro.transportation.entities.Sensor;

/**
 * Implementation of DAO interface for sensor.
 * 
 * @author Anca Petrescu
 * 
 */

@Transactional
public class SensorDaoImpl implements SensorDao {

	/**
	 * The interface used to interact with the persistence context.
	 */

	private EntityManager entityManager;

	@Transactional
	public Sensor addSensor(Sensor sensorToAdd) {
		entityManager.persist(sensorToAdd);
		return sensorToAdd;
	}

	@Transactional
	public Sensor getSensor(Long id) {
		Sensor result = null;

		try {
			Query getSensorQuery = entityManager
					.createQuery("FROM sensor op WHERE op.id='" + id + "'");

			result = (Sensor) getSensorQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

	@Transactional
	public Sensor getSensor(String sensorID) {
		Sensor result = null;

		try {
			Query getSensorQuery = entityManager
					.createQuery("FROM sensor op WHERE op.sensorID='" + sensorID + "'");

			result = (Sensor) getSensorQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}
	
	@Transactional
	public Sensor updatePackage(Long id, String name) {
		return null;
	}

	@Transactional
	public boolean deleteSensor(String id) {

		Sensor sensor = getSensor(id);

		try {
			entityManager.remove(sensor);
		} catch (TransactionRequiredException transactionRequiredException) {
			// Thrown if invoked on a container-managed entity manager of type
			// PersistenceContextType.TRANSACTION and there is no transaction
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
		}

		return false;
	}

	@Transactional
	public List<Sensor> getSensors() {

		List<Sensor> result = null;

		try {
			TypedQuery<Sensor> getSensorsQuery = entityManager.createQuery(
					"FROM sensor op", Sensor.class);
			result = getSensorsQuery.getResultList();
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid or if the query
			// result is found to not be assignable to the specified type
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
}
