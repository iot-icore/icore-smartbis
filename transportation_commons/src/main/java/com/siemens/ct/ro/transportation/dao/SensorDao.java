package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.Sensor;

/**
 * 
 * DAO interface for sensor.
 * 
 * @author Anca Petrescu
 * 
 */
public interface SensorDao {

	public Sensor addSensor(Sensor sensor);

	public Sensor getSensor(Long id);

	public boolean deleteSensor(String sensorList);

	public List<Sensor> getSensors();
}
