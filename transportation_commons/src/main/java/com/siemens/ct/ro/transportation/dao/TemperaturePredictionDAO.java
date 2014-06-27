package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.TemperaturePrediction;

public interface TemperaturePredictionDAO {

	public TemperaturePrediction addTemperaturePrediction(
			TemperaturePrediction temperaturePrediction);

	public TemperaturePrediction getTemperaturePrediction(Long id);

	public boolean deleteTemperaturePrediction(Long id);

	public List<TemperaturePrediction> getTemperaturePredictions(
			String sensorID, long startTimestamp, long finishTimestamp);

	public List<TemperaturePrediction> getTemperaturePredictions(
			String sensorID);
	
}
