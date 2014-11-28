package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.CurrentClampEventDAO;
import com.softwareag.transportation.CEPevents.CurrentClampEvent;

/**
 * @author dan.puiu
 * 
 */

@Transactional
@Repository("CurrentClampEventDAO")
public class CurrentClampEventDAOImp implements CurrentClampEventDAO {

	private EntityManager entityManager;

	@Transactional
	public CurrentClampEvent addCurrentClampEvent(
			CurrentClampEvent currentClampValueEventToAdd) {
		entityManager.persist(currentClampValueEventToAdd);
		return currentClampValueEventToAdd;
	}

	@Transactional
	public CurrentClampEvent getCurrentClampEvent(Long id) {
		CurrentClampEvent result = null;

		try {
			Query getCurrentClampEvent = entityManager
					.createQuery("FROM currentclampevent op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold
			// exactly
			// one element
			result = (CurrentClampEvent) getCurrentClampEvent.getResultList()
					.get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deleteCurrentClampEvent(Long id) {
		CurrentClampEvent currentClampValueEventToBeDeleted = getCurrentClampEvent(id);

		try {
			entityManager.remove(currentClampValueEventToBeDeleted);
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
	public List<CurrentClampEvent> getCurrentClampEvents(String sensorID,
			long startTimestamp, long finishTimestamp) {

		List<CurrentClampEvent> result = null;

		try {
			// TypedQuery<CurrentClampEvent> getCurrentClampEvent =
			// entityManager.createQuery(
			// "FROM currentclampevent op WHERE op.sensorID='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp, CurrentClampEvent.class);

			TypedQuery<CurrentClampEvent> getCurrentClampEvent = entityManager
					.createQuery(
							"FROM currentclampevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " order by op.timestamp ",
							CurrentClampEvent.class);

			result = getCurrentClampEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<CurrentClampEvent> getCurrentClampEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedCurrentClamp,
			double maximumAcceptedCurrentClamp) {

		List<CurrentClampEvent> result = null;

		try {
			// TypedQuery<CurrentClampEvent> getCurrentClampEvent =
			// entityManager.createQuery(
			// "FROM currentclampevent op WHERE op.sensorID='"
			// + sensorID + "' and "
			// + "op.timestamp > " + startTimestamp + " and op.timestamp <"
			// +
			// finishTimestamp + " and (op.currentClampValue < " +
			// minimumAcceptedCurrentClamp + " or op.currentClampValue > " +
			// maximumAcceptedCurrentClamp + ")", CurrentClampEvent.class);

			TypedQuery<CurrentClampEvent> getCurrentClampEvent = entityManager
					.createQuery(
							"FROM currentclampevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp <"
									+ finishTimestamp
									+ " and (op.currentClampValue < "
									+ minimumAcceptedCurrentClamp
									+ " or op.currentClampValue > "
									+ maximumAcceptedCurrentClamp + ")"
									+ " order by op.timestamp ",
							CurrentClampEvent.class);

			result = getCurrentClampEvent.getResultList();

		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	// select * from icore_schema.currentclampevent where
	// icore_schema.currentclampevent.
	// sensorID = "10:00:00:aa:bb:01" and
	// icore_schema.currentclampevent.timestamp > 1383213526839
	// and icore_schema.currentclampevent.timestamp < 1383213557576

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public CurrentClampEvent getLastCCEvent(String sensorID, long startTime) {

		CurrentClampEvent result = null;

		try {
			TypedQuery<CurrentClampEvent> getCurrentClampEvent = entityManager
					.createQuery(
							"FROM currentclampevent op WHERE op.sensorID='"
									+ sensorID + "' and " + " op.timestamp >"
									+ startTime
									+ " order by op.timestamp desc ",
							CurrentClampEvent.class);

			List<CurrentClampEvent> ccEvents = getCurrentClampEvent
					.getResultList();
			if (ccEvents.size() > 0)
				result = (CurrentClampEvent) ccEvents.get(ccEvents.size() - 1);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

}
