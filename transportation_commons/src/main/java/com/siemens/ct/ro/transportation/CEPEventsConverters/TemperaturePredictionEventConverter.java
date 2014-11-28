package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;

public class TemperaturePredictionEventConverter {
	
	static public String convertToXML(TemperaturePredictionEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TemperaturePredictionEvent convertToObject(String message) {
		Gson gson = new Gson();
		return (TemperaturePredictionEvent) gson.fromJson(message, TemperaturePredictionEvent.class);
	}


}
