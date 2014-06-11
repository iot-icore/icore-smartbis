package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.ProductType;

/**
 * 
 * DAO interface for product type.
 * 
 * @author Anca Petrescu
 * 
 */

public interface ProductTypesDao {

	public ProductType addProductType(ProductType productType);

	public ProductType getProductType(Long id);
	
	public ProductType getProductType(String type);

	public boolean deleteProductType(Long id);

	public List<ProductType> getAllProductTypes();
}
