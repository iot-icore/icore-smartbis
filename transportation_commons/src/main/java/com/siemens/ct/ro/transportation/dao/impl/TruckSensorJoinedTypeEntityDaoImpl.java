package com.siemens.ct.ro.transportation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.TruckSensorJoinedTypeEntityDao;
import com.siemens.ct.ro.transportation.entities.TruckSensorJoinedTypeEntity;

@Transactional
public class TruckSensorJoinedTypeEntityDaoImpl implements TruckSensorJoinedTypeEntityDao{

	private EntityManager entityManager;
	
	@Transactional
	public TruckSensorJoinedTypeEntity addTruckSensorJoinedTypeEntity(
			TruckSensorJoinedTypeEntity truckSensorJoinedTypeEntity) {
		entityManager.persist(truckSensorJoinedTypeEntity);
		return null;
	}

	@Transactional
	public TruckSensorJoinedTypeEntity getTruckSensorJoinedTypeEntity(Long id) {
		TruckSensorJoinedTypeEntity result = null;

		try {
			Query getTruckSensorJoinedTypeEntity = entityManager
					.createQuery("FROM trucksensorjoinedtype op WHERE op.id='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (TruckSensorJoinedTypeEntity) getTruckSensorJoinedTypeEntity
					.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

	@Transactional
	public boolean deleteTruckSensorJoinedTypeEntity(Long id) {
		TruckSensorJoinedTypeEntity truckSensorJoinedTypeEntityToBeDeleted = getTruckSensorJoinedTypeEntity(id);

		try {
			entityManager.remove(truckSensorJoinedTypeEntityToBeDeleted);
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

}
