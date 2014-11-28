package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.CurrentClampEvent;

public class CurrentClampEventConverter {
	
static public String convertToXML(CurrentClampEvent event){
		
	Gson gson = new Gson();
	return gson.toJson(event);
	}
	
	static public CurrentClampEvent convertToObject(String message){
		
		Gson gson = new Gson();
		return (CurrentClampEvent) gson.fromJson(message, CurrentClampEvent.class);
	}

}
