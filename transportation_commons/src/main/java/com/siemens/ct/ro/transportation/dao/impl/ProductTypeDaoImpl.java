package com.siemens.ct.ro.transportation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.siemens.ct.ro.transportation.dao.ProductTypesDao;
import com.siemens.ct.ro.transportation.entities.ProductType;

/**
 * Implementation of DAO interface for product type.
 * 
 * @author Anca Petrescu
 * 
 */
@Transactional
public class ProductTypeDaoImpl implements ProductTypesDao {

	private EntityManager entityManager;

	public ProductType addProductType(ProductType productType) {
		entityManager.persist(productType);
		return productType;
	}

	public ProductType getProductType(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProductType getProductType(String type) {
		ProductType result = null;

		try {
			Query getProductTypeQuery = entityManager
					.createQuery("FROM producttype p WHERE p.typeName='" + type
							+ "'");

			result = (ProductType) getProductTypeQuery.getResultList().get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			// Thrown if the query string is found to be invalid
			illegalArgumentException.printStackTrace();
		}

		return result;
	}

	public boolean deleteProductType(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Transactional
	public List<ProductType> getAllProductTypes() {
		List<ProductType> result = null;

		TypedQuery<ProductType> getProductTypeQuery = entityManager
				.createQuery("FROM producttype op", ProductType.class);
		result = getProductTypeQuery.getResultList();

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
