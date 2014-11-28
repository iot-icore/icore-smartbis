package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.HumidityEvent;

public interface HumidityEventDAO {

	public HumidityEvent addHumidityEvent(
			HumidityEvent humidityEventToAdd);

	public HumidityEvent getHumidityEvent(Long id);

	public boolean deleteHumidityEvent(Long id);

	public List<HumidityEvent> getHumidityEvents(String sensorID,
			long startTimestamp, long finishTimestamp);

	public List<HumidityEvent> getHumidityEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedHumidity, double maximumAcceptedHumidity);

	public HumidityEvent getLastHumidityEvent(String sensorID,long startTimestamp);

}
