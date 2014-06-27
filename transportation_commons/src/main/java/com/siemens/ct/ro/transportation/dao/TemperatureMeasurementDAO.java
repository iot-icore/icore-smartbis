/**
 * 
 */
package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.TemperatureMeasurement;

/**
 * 
 * dao interface for temperature measurements
 * 
 * @author dan.puiu
 * 
 */
public interface TemperatureMeasurementDAO {

	public TemperatureMeasurement addTemperatureMeasurement(
			TemperatureMeasurement temperatureMeasurementToAdd);

	public TemperatureMeasurement getTemperatureMeasurement(Long id);

	public boolean deleteTemperatureMeasurement(Long id);

	public List<TemperatureMeasurement> getTemperatureMeasurements(
			String sensorID, long startTimestamp, long finishTimestamp);

	public List<TemperatureMeasurement> getTemperatureMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature);

	public TemperatureMeasurement getLastMeasurement(String sensorID,long startTimestamp);

}
