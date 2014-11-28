package com.siemens.ct.ro.transportation.CEPEventsConverters;

import com.google.gson.Gson;
import com.softwareag.transportation.CEPevents.ACUnitEvent;

public class ACUnitEventConverter {
	
	static public String convertToXML(ACUnitEvent event){
		
		Gson gson = new Gson();
		return gson.toJson(event);
	}
	
	static public ACUnitEvent convertToObject(String message){
		
		Gson gson = new Gson();
		return (ACUnitEvent) gson.fromJson(message, ACUnitEvent.class);
	}

}
