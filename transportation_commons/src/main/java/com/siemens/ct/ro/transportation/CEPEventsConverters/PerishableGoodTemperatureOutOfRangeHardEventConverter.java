package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeHardEvent;

public class PerishableGoodTemperatureOutOfRangeHardEventConverter {

	static public String convertToXML(
			PerishableGoodTemperatureOutOfRangeHardEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);
	}

	static public PerishableGoodTemperatureOutOfRangeHardEvent convertToObject(
			String message) {

		Gson gson = new Gson();
		return (PerishableGoodTemperatureOutOfRangeHardEvent) gson.fromJson(
				message, PerishableGoodTemperatureOutOfRangeHardEvent.class);
	}

}
