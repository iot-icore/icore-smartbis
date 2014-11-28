package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent;

public class TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventConverter {

	static public String convertToXML(
			TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent convertToObject(
			String message) {
		Gson gson = new Gson();
		return (TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent) gson
				.fromJson(
						message,
						TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent.class);
	}
}
