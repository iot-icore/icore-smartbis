package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

public class TruckSensorJointEventConverter {

	static public String convertToXML(TruckSensorJointEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TruckSensorJointEvent convertToObject(String message) {
		Gson gson = new Gson();
		return (TruckSensorJointEvent) gson.fromJson(message, TruckSensorJointEvent.class);
	}
	
}
