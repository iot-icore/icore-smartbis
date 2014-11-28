package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;

public interface TemperaturePredictionEventDAO {

	public TemperaturePredictionEvent addTemperaturePredictionEvent(
			TemperaturePredictionEvent temperaturePredictionEvent);

	public TemperaturePredictionEvent getTemperaturePredictionEvent(Long id);

	public boolean deleteTemperaturePredictionEvent(Long id);

	public List<TemperaturePredictionEvent> getTemperaturePredictionEvents(
			String sensorID, long startTimestamp, long finishTimestamp);

	public List<TemperaturePredictionEvent> getTemperaturePredictionEvents(
			String sensorID);
	
}
