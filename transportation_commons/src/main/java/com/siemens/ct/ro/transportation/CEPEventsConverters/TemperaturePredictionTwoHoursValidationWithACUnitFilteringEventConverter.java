package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent;

public class TemperaturePredictionTwoHoursValidationWithACUnitFilteringEventConverter {
	static public String convertToXML(
			TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent convertToObject(
			String message) {
		Gson gson = new Gson();
		return (TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent) gson
				.fromJson(
						message,
						TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent.class);
	}
}
