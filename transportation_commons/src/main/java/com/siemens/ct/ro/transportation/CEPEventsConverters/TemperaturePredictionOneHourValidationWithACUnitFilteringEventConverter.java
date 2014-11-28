package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.TemperaturePredictionOneHourValidationWithACUnitFilteringEvent;

public class TemperaturePredictionOneHourValidationWithACUnitFilteringEventConverter {

	static public String convertToXML(
			TemperaturePredictionOneHourValidationWithACUnitFilteringEvent event) {

		Gson gson = new Gson();
		return gson.toJson(event);

	}

	static public TemperaturePredictionOneHourValidationWithACUnitFilteringEvent convertToObject(
			String message) {
		Gson gson = new Gson();
		return (TemperaturePredictionOneHourValidationWithACUnitFilteringEvent) gson
				.fromJson(
						message,
						TemperaturePredictionOneHourValidationWithACUnitFilteringEvent.class);
	}

}
