package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.NonPerishableGoodTemperatureOutOfRangeEvent;

public class NonPerishableGoodTemperatureOutOfRangeEventConverter {

	static public String convertToXML(
			NonPerishableGoodTemperatureOutOfRangeEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);
	}

	static public NonPerishableGoodTemperatureOutOfRangeEvent convertToObject(String message) {

		Gson gson = new Gson();
		return (NonPerishableGoodTemperatureOutOfRangeEvent) gson.fromJson(
				message, NonPerishableGoodTemperatureOutOfRangeEvent.class);
	}

}
