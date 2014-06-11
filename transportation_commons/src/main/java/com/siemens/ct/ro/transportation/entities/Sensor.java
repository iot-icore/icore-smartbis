package com.siemens.ct.ro.transportation.entities;

/**
 * Sensor entity.
 * 
 * @author Anca Petrescu
 * 
 */
public class Sensor {
	long id;
	String sensorID;
	String sensorType;

	public Sensor() {
	}

	public Sensor(long id, String sensorID, String sensorType) {
		super();
		this.id = id;
		this.sensorID = sensorID;
		this.sensorType = sensorType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

}
