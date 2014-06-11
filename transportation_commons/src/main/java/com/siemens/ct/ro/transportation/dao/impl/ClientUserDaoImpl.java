package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.ClientUserDao;
import com.siemens.ct.ro.transportation.entities.ClientUser;

/**
 * 
 * Implementation of DAO interface for ClientUser.
 * 
 * @author Anca Petrescu
 * 
 */
public class ClientUserDaoImpl implements ClientUserDao {

	private EntityManager entityManager;

	@Transactional
	public ClientUser add(ClientUser user) {
		entityManager.persist(user);
		entityManager.merge(user);
		entityManager.flush();
		return user;
	}

	@Transactional
	public ClientUser get(String username) {
		ClientUser result = null;

		try {
			Query getOperatorQuery = entityManager
					.createQuery("FROM clientuser client WHERE client.username='"
							+ username + "'");

			// The operator id is unique so the list will always hold exactly
			// one element
			result = (ClientUser) getOperatorQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			System.err.println("ERROR: No person with the specified id!");
		}
		return result;
	}

	public List<ClientUser> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public boolean exist(String username) {
		List<ClientUser> list = entityManager.createQuery(
				"FROM clientuser u WHERE u.username='" + username + "'")
				.getResultList();

		if (list.size() > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public ClientUser login(String username, String password) {
		List<ClientUser> user = (List<ClientUser>) entityManager.createQuery(
				"FROM clientuser u WHERE u.username='" + username
						+ "' AND u.password = '" + password + "')")
				.getResultList();

		if (user == null || user.size() == 0) {
			return null;
		}

		return user.get(0);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
