package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.PackageUnit;

/**
 * 
 * DAO interface for package unit
 * 
 * @author Anca Petrescu
 * 
 */

public interface PackageDao {

	public PackageUnit addPackage(PackageUnit packageToAdd);

	public PackageUnit getPackage(Long id);
	
	public PackageUnit getPackage(String packId);

	public boolean deletePackage(Long id);

	public List<PackageUnit> getPackages();
	
	public PackageUnit updateBinding(long id, boolean binding);
	
	public PackageUnit update(PackageUnit pack);
}
