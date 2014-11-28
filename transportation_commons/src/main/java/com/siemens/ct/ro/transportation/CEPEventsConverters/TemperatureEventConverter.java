package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TemperatureEvent;

public class TemperatureEventConverter {

	static public String convertToXML(TemperatureEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TemperatureEvent convertToObject(String message) {
		Gson gson = new Gson();
		return (TemperatureEvent) gson.fromJson(message, TemperatureEvent.class);
	}

}
