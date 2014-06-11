package com.siemens.ct.ro.transportation.entities;

/**
 * The employee users are the one allowed to use the LogisticEmployee application.
 * 
 * @author Anca Petrescu
 * 
 */
public class EmployeeUser {

	private long id;
	private String username;
	private String password;
	private String name;

	public EmployeeUser() {
		super();
	}

	public EmployeeUser(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
