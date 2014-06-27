package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.ACUnitMeasurement;

public interface ACUnitMeasurementDAO {

	public ACUnitMeasurement addACUnitMeasurement(
			ACUnitMeasurement ACUnitMeasurementToAdd);

	public ACUnitMeasurement getACUnitMeasurement(Long id);

	public boolean deleteACUnitMeasurement(Long id);

	public List<ACUnitMeasurement> getACUnitMeasurement(String sensorID,
			long startTimestamp, long finishTimestamp);

	public ACUnitMeasurement getLastACUnitMeasurement(String acUnitID1, long timpestamp20secAgo);


}
