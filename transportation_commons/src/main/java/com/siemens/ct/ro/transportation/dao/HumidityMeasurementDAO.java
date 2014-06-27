package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.HumidityMeasurement;

public interface HumidityMeasurementDAO {

	public HumidityMeasurement addHumidityMeasurement(
			HumidityMeasurement humidityMeasurementToAdd);

	public HumidityMeasurement getHumidityMeasurement(Long id);

	public boolean deleteHumidityMeasurement(Long id);

	public List<HumidityMeasurement> getHumidityMeasurements(String sensorID,
			long startTimestamp, long finishTimestamp);

	public List<HumidityMeasurement> getHumidityMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedHumidity, double maximumAcceptedHumidity);

	public HumidityMeasurement getLastHumidityMeasurement(String sensorID,long startTimestamp);

}
