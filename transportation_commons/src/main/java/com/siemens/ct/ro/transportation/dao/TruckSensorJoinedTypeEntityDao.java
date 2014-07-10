package com.siemens.ct.ro.transportation.dao;

import com.siemens.ct.ro.transportation.entities.TruckSensorJoinedTypeEntity;


public interface TruckSensorJoinedTypeEntityDao {

	public TruckSensorJoinedTypeEntity addTruckSensorJoinedTypeEntity(
			TruckSensorJoinedTypeEntity truckSensorJoinedTypeEntity);

	public TruckSensorJoinedTypeEntity getTruckSensorJoinedTypeEntity(Long id);

	public boolean deleteTruckSensorJoinedTypeEntity(Long id);
	
}
