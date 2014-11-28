package com.siemens.ct.ro.transportation.commons;

/**
 * This class contains the MQTT where the CEP engine publish the transportation
 * process complex events.
 * 
 * @author dan.puiu
 * 
 */

public class MqttTopics {

	public final static String TEMERATURE_PREDICTION_TEN_MINUTES_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC = "tempreature/prediction/tenminutes/validation/withACunitfiltering";
	public final static String TEMERATURE_PREDICTION_ONE_HOUR_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC = "tempreature/prediction/onehour/validationwithACunitfiltering";
	public final static String TEMERATURE_PREDICTION_TWO_HOURS_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC = "tempreature/prediction/twohours/validationwithACunitfiltering";
	public final static String NON_PERISHABLE_GOODS_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC = "temperature/nonPerishableGoodsOutOfRangeVarnings";
	public final static String HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC = "humidity/outOfRangeWarning";
	public final static String PERISHABLE_GOODS_SOFT_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC = "temperature/perishableGoodsSoftOutOfRangeWarning";
	public final static String PERISHABLE_GOODS_HARD_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC = "temperature/perishableGoodsHardOutOfRangeWarning";
	public final static String TEMPERATURE_PREDICTION_MQTT_TOPIC = "icore/temperature/predictions";
	public final static String TRUCK_SENSOR_JOINED_TYPE = "icore/TruckSensorJoinedType";
	
	public final static String mqttTopicToTemperatureSensor = "iCore/Temperature/TMP102/Temperature/PUSH/s";
	public final static String mqttTopicToHumiditySensor = "iCore/Humidity/SHT_H/Humidity/PUSH/s";
	public final static String mqttTopicToCurrentClampSensor = "iCore/ADC/ADS124s8/Sample/PUSH/s";
	public final static String mqttTopicToAcUnitActuator = "iCore/ACunit/PUSH/s";
	public final static String mqttTopicAcUnitCommand = "iCore/ACunitCommand/PUSH";
	public final static String mqttTopicOutsideTempCommand = "iCore/OutsideTempCommand/PUSH";
	public final static String mqttTopicTranspFinished= "iCore/TransportationFinished/PUSH";

}
