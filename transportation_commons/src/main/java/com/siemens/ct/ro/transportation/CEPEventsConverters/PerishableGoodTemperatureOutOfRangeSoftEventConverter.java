package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeSoftEvent;

public class PerishableGoodTemperatureOutOfRangeSoftEventConverter {

	static public String convertToXML(
			PerishableGoodTemperatureOutOfRangeSoftEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);
	}

	static public PerishableGoodTemperatureOutOfRangeSoftEvent convertToObject(
			String message) {

		Gson gson = new Gson();
		return (PerishableGoodTemperatureOutOfRangeSoftEvent) gson.fromJson(
				message, PerishableGoodTemperatureOutOfRangeSoftEvent.class);
	}

}
