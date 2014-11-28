package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;


public interface TruckSensorJointEventDao {

	public TruckSensorJointEvent addTruckSensorJointEvent(
			TruckSensorJointEvent truckSensorJointEvent);

	public TruckSensorJointEvent getTruckSensorJointEvent(Long id);

	public boolean deleteTruckSensorJointEvent(Long id);
	
	public List<TruckSensorJointEvent> getAllTruckSensorJointEvent(String sensorID, Long startTime, Long endTime);
	
}
