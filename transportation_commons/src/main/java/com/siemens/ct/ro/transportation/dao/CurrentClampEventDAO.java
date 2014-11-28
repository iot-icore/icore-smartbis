package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.CurrentClampEvent;

public interface CurrentClampEventDAO {

	public CurrentClampEvent addCurrentClampEvent(
			CurrentClampEvent currentClampEventToAdd);

	public CurrentClampEvent getCurrentClampEvent(Long id);

	public boolean deleteCurrentClampEvent(Long id);

	public List<CurrentClampEvent> getCurrentClampEvents(String sensorID,
			long startTimestamp, long finishTimestamp);

	public List<CurrentClampEvent> getCurrentClampEventsOutsideBounds(
			String sensorID, long startTimestamp, long finishTimestamp,
			double minimumAcceptedCurrentClamp, double maximumAcceptedCurrentClamp);

	public CurrentClampEvent getLastCCEvent(String sensorID, long timpestamp20secAgo);

}
