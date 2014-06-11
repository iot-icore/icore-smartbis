package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.ClientUser;


public interface ClientUserDao {

	public ClientUser add(ClientUser user);

	public ClientUser get(String username);

	public List<ClientUser> getAll();

	public boolean delete(Long id);
	
	public boolean exist(String username);
	
	public ClientUser login(String username, String password);
	
}
