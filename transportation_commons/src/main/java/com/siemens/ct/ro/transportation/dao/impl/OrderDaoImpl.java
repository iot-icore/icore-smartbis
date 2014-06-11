package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionRequiredException;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.OrderDao;
import com.siemens.ct.ro.transportation.entities.Order;

/**
 * 
 * Implementation of DAO interface for order.
 * 
 * @author Anca Petrescu
 * 
 */
@Transactional
public class OrderDaoImpl implements OrderDao {

	private EntityManager entityManager;

	@Transactional
	public Order add(Order order) {
		entityManager.merge(order);
		return order;
	}

	@Transactional
	public Order get(String id) {
		Order result = null;

		try {
			Query getPackageQuery = entityManager
					.createQuery("FROM orderunit op left join fetch op.packages WHERE op.orderId='"
							+ id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (Order) getPackageQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public List<Order> getAll() {
		List<Order> result = null;

		try {
			TypedQuery<Order> orders = entityManager.createQuery(
					"FROM orderunit op", Order.class);
			result = orders.getResultList();
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace();
		}
		return result;
	}

	@Transactional
	public boolean delete(String id) {
		Order order = get(id);

		try {
			entityManager.remove(order);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
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
