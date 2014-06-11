package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.EmployeeUser;


public interface EmployeeUserDao {

	public EmployeeUser add(EmployeeUser user);

	public EmployeeUser get(String username);

	public List<EmployeeUser> getAll();

	public boolean delete(Long id);
	
	public boolean exist(String username);
	
	public EmployeeUser login(String username, String password);
	
}
