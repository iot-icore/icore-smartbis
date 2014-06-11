package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.PackageDao;
import com.siemens.ct.ro.transportation.entities.PackageUnit;

/**
 * Implementation of DAO interface for package unit.
 * 
 * @author Anca Petrescu
 * 
 */

@Transactional
@Repository("PackageDao")
public class PackageDaoImpl implements PackageDao {

	/**
	 * The interface used to interact with the persistence context.
	 */

	private EntityManager entityManager;

	@Transactional
	public PackageUnit addPackage(PackageUnit packageToAdd) {
		System.out
				.println("====================== add package ================================= "
						+ packageToAdd.getPackageId());
		entityManager.persist(packageToAdd);
		entityManager.merge(packageToAdd);
		entityManager.flush();
		return packageToAdd;
	}

	@Transactional
	public PackageUnit getPackage(Long id) {
		PackageUnit result = null;

		try {
			Query getPackageQuery = entityManager
					.createQuery("FROM packageunit op WHERE op.id='" + id + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (PackageUnit) getPackageQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
		}

		return result;
	}

	@Transactional
	public boolean deletePackage(Long id) {

		PackageUnit PackageToBeDeleted = getPackage(id);

		try {
			entityManager.remove(PackageToBeDeleted);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the instance is not an entity or is a detached entity
		}

		return false;
	}

	@Transactional
	public List<PackageUnit> getPackages() {

		List<PackageUnit> result = null;

		try {
			TypedQuery<PackageUnit> getPackagesQuery = entityManager
					.createQuery("FROM packageunit op", PackageUnit.class);
			result = getPackagesQuery.getResultList();
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid or if the query
			// result is found to not be assignable to the specified type
		}
		return result;
	}

	@Transactional
	public PackageUnit updateBinding(long id, boolean binding) {
		PackageUnit result = null;
		PackageUnit pack = getPackage(id);

		pack.setBound(binding);

		try {
			result = entityManager.merge(pack);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if instance is not an entity or is a removed entity
			illegalArgumentException.printStackTrace();
		}
		return result;
	}

	public PackageUnit update(PackageUnit pack) {

		PackageUnit result = null;
		try {
			result = entityManager.merge(pack);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if instance is not an entity or is a removed entity
			illegalArgumentException.printStackTrace();
		}
		return result;
	}

	public PackageUnit getPackage(String packId) {
		PackageUnit result = null;

		try {
			Query getPackageQuery = entityManager
					.createQuery("FROM packageunit op WHERE op.packageId='"
							+ packId + "'");

			// The PackageUnit id is unique so the list will always hold exactly
			// one element
			result = (PackageUnit) getPackageQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
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
