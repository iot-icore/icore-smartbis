/**
 * 
 */
package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.TemperatureEvent;

/**
 * 
 * dao interface for temperature measurements
 * 
 * @author dan.puiu
 * 
 */
public interface TemperatureEventDAO {

	public TemperatureEvent addTemperatureEvent(
			TemperatureEvent temperatureEventToAdd);

	public TemperatureEvent getTemperatureEvent(Long id);

	public boolean deleteTemperatureEvent(Long id);

	public List<TemperatureEvent> getTemperatureEvents(String sensorID,
			long startTimestamp, long finishTimestamp);

	public List<TemperatureEvent> getTemperatureEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedTemperature, double maximumAcceptedTemperature);

	public TemperatureEvent getLastEvent(String sensorID, long startTimestamp);

}
