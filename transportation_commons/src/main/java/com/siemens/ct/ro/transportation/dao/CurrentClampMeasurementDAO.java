package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.siemens.ct.ro.transportation.entities.CurrentClampMeasurement;

public interface CurrentClampMeasurementDAO {

	public CurrentClampMeasurement addCurrentClampMeasurement(
			CurrentClampMeasurement currentClampMeasurementToAdd);

	public CurrentClampMeasurement getCurrentClampMeasurement(Long id);

	public boolean deleteCurrentClampMeasurement(Long id);

	public List<CurrentClampMeasurement> getCurrentClampMeasurements(String sensorID,
			long startTimestamp, long finishTimestamp);

	public List<CurrentClampMeasurement> getCurrentClampMeasurementsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedCurrentClamp, double maximumAcceptedCurrentClamp);

}
