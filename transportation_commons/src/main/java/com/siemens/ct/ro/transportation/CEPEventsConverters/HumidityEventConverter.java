package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.HumidityEvent;

public class HumidityEventConverter {

	static public String convertToXML(HumidityEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);
	}

	static public HumidityEvent convertToObject(String message) {

		Gson gson = new Gson();
		return (HumidityEvent) gson.fromJson(message, HumidityEvent.class);
	}

}
