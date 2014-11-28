package com.siemens.ct.ro.transportation.dao.impl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TruckSensorJointEventDao;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

@Transactional
public class TruckSensorJointEventDaoImpl implements TruckSensorJointEventDao{

	private EntityManager entityManager;
	
	@Transactional
	public TruckSensorJointEvent addTruckSensorJointEvent(
			TruckSensorJointEvent truckSensorJointEvent) {
		entityManager.persist(truckSensorJointEvent);
		return null;
	}

	@Transactional
	public TruckSensorJointEvent getTruckSensorJointEvent(Long id) {
		TruckSensorJointEvent result = null;

		try {
			Query getTruckSensorJointEvent = entityManager
					.createQuery("FROM trucksensorjointevent op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TruckSensorJointEvent) getTruckSensorJointEvent
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

	@Transactional
	public boolean deleteTruckSensorJointEvent(Long id) {
		TruckSensorJointEvent truckSensorJointEventToBeDeleted = getTruckSensorJointEvent(id);

		try {
			entityManager.remove(truckSensorJointEventToBeDeleted);
		} catch (TransactionRequiredException transactionRequiredException) {
			// Thrown if invoked on a container-managed entity manager of type
			// PersistenceContextType.TRANSACTION and there is no transaction
			transactionRequiredException.printStackTrace();
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
			illegalArgumentException.printStackTrace();
		}

		return false;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<TruckSensorJointEvent> getAllTruckSensorJointEvent(
			String sensorID, Long startTime, Long endTime) {
		try {
			Query getTruckSensorJointEvent = entityManager
					.createQuery("FROM trucksensorjointevent op WHERE op.parcelTemperatureSensorId='"
							+ sensorID + "' and op.timestamp between " + startTime + " and " + endTime + " order by op.timestamp");
			return (List<TruckSensorJointEvent>) getTruckSensorJointEvent.getResultList();
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
			return new LinkedList<TruckSensorJointEvent>();
		}
	}

}
