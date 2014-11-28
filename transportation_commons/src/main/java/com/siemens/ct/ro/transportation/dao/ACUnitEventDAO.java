package com.siemens.ct.ro.transportation.dao;

import java.util.List;

import com.softwareag.transportation.CEPevents.ACUnitEvent;

public interface ACUnitEventDAO {

	public ACUnitEvent addACUnitEvent(ACUnitEvent ACUnitEventToAdd);

	public ACUnitEvent getACUnitEvent(Long id);

	public boolean deleteACUnitEvent(Long id);

	public List<ACUnitEvent> getACUnitEvent(String sensorID,
			long startTimestamp, long finishTimestamp);

	public ACUnitEvent getLastACUnitEvent(String acUnitID1,
			long timpestamp20secAgo);

}
