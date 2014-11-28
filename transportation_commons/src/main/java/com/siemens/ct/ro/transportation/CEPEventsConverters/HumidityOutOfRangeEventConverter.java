package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.HumidityOutOfRangeEvent;

public class HumidityOutOfRangeEventConverter {
	
	static public String convertToXML(
			HumidityOutOfRangeEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);
	}

	static public HumidityOutOfRangeEvent convertToObject(String message) {

		Gson gson = new Gson();
		return (HumidityOutOfRangeEvent) gson.fromJson(
				message, HumidityOutOfRangeEvent.class);
	}

}
