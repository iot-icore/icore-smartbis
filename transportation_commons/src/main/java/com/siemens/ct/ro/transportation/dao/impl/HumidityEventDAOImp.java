package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.HumidityEventDAO;
import com.softwareag.transportation.CEPevents.HumidityEvent;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("HumidityEventDAO")
public class HumidityEventDAOImp implements HumidityEventDAO {

	private EntityManager entityManager;

	@Transactional
	public HumidityEvent addHumidityEvent(
			HumidityEvent humidityEventToAdd) {
		entityManager.persist(humidityEventToAdd);
		return humidityEventToAdd;
	}

	@Transactional
	public HumidityEvent getHumidityEvent(Long id) {
		HumidityEvent result = null;

		try {
			Query getHumidityEvent = entityManager
					.createQuery("FROM humidityevent op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (HumidityEvent) getHumidityEvent
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteHumidityEvent(Long id) {
		HumidityEvent humidityEventToBeDeleted = getHumidityEvent(id);

		try {
			entityManager.remove(humidityEventToBeDeleted);
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
	public List<HumidityEvent> getHumidityEvents(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<HumidityEvent> result = null;

		try {
			// TypedQuery<HumidityEvent> getHumidityEvent =
			// entityManager.createQuery(
			// "FROM humidityevent op WHERE op.sensorID='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp, HumidityEvent.class);

			TypedQuery<HumidityEvent> getHumidityEvent = entityManager
					.createQuery(
							"FROM humidityevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							HumidityEvent.class);

			result = getHumidityEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<HumidityEvent> getHumidityEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedHumidity, double maximumAcceptedHumidity) {

		List<HumidityEvent> result = null;

		try {

			TypedQuery<HumidityEvent> getHumidityEvent = entityManager
					.createQuery(
							"FROM humidityevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp + " and (op.humidity < "
									+ minimumAcceptedHumidity
									+ " or op.humidity > "
									+ maximumAcceptedHumidity + ")"
									+ " order by op.timestamp ",
							HumidityEvent.class);

			result = getHumidityEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	// select * from icore_schema.humidityevent where
	// icore_schema.humidityevent.
	// sensorID = "10:00:00:aa:bb:01" and
	// icore_schema.humidityevent.timestamp > 1383213526839
	// and icore_schema.humidityevent.timestamp < 1383213557576

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public HumidityEvent getLastHumidityEvent(String sensorID,
			long startTimestamp) {
		HumidityEvent result = null;

		try {

			TypedQuery<HumidityEvent> getHumidityEvent = entityManager
					.createQuery(
							"FROM humidityevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp >"
									+ startTimestamp
									+ " order by op.timestamp desc ",
							HumidityEvent.class);

			List<HumidityEvent> humidityEvents = getHumidityEvent
					.getResultList();
			if (humidityEvents.size() > 0)
				result = (HumidityEvent) humidityEvents
.get(humidityEvents.size() - 1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

}
