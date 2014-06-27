package com.siemens.ct.ro.forecastUtils;

public class Constants {
	public static final double NULL_OR_MISSING_VALUE = -1000000.0;//this value is used as a marker for null (missing) data
	
	//the name of the field containing the time values for the time series data
	public static final String TIME_FIELDNAME = "time";
	
	//the name of the field containing the predicted (Dependent) variable
	public static final String FIELD_NAMES_TO_PREDICT = "temperatureParcel";
	
	public static final String TEMPERATURE1 = "temperatureInfrastructureSensor1";
	public static final String TEMPERATURE2 = "temperatureInfrastructureSensor2";
	public static final String TEMPERATURE3 = "temperatureInfrastructureSensor3";
	public static final String EXTERNAL_TEMPERATURE = "externalTemperature";
	public static final String PARCEL_TEMPERATURE = "temperatureParcel";
	public static final String HUMIDITY_VALUE = "humidityInfrastructureSensor";
	public static final String CURRENT_CLAMP_VALUE = "currentClampValue";
	public static final String HVAC_STATE_ON = "hvacStateOn";
	public static final String HVAC_STATE_VALUE = "hvacStateValue";
}
