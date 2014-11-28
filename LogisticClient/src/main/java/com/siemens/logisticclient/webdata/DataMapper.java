package com.siemens.logisticclient.webdata;

import java.util.List;

import com.softwareag.transportation.CEPevents.TemperatureEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;

public class DataMapper {
//	private static Logger logger = Logger.getLogger(DataMapper.class);
	
	public static SingleSeries fromSensorDataToSingleSeries(String sensorName, String color, List<TemperatureEvent> measurements)
	{
		SingleSeries result = new SingleSeries();
		result.setColor(color);
		result.setName(sensorName);
		for(TemperatureEvent measurement : measurements)
		{
			result.addPoint(new Point(measurement.getTimestamp()/1000, measurement.getTemperature().doubleValue()));
			//divide to 1000 as we want to represent seconds, not milliseconds
		}
		return result;
	}

	public static SingleSeries fromSensorDataToSingleSeries10Minutes(String sensorName,
			List<TemperaturePredictionEvent> predictions, String color) {
		SingleSeries result = new SingleSeries();
		result.setColor(color);
		result.setName(sensorName);
		for(TemperaturePredictionEvent prediction : predictions)
		{
			long time = prediction.getTimestamp()/1000;
			//divide to 1000 as we want to represent seconds in javascript chart, not milliseconds
			Point p = new Point(time, prediction.getPedictedTempTenMin());
			result.addPoint(p);
		}
		return result;
	}
	
	public static SingleSeries fromSensorDataToSingleSeries1Hour(String sensorName,
			List<TemperaturePredictionEvent> predictions, String color) {
		SingleSeries result = new SingleSeries();
		result.setColor(color);
		result.setName(sensorName);
		for(TemperaturePredictionEvent prediction : predictions)
		{
			long time = prediction.getTimestamp()/1000;
			//divide to 1000 as we want to represent seconds, not milliseconds
			Point p = new Point(time, prediction.getPredictedTempOneHour());
			result.addPoint(p);
		}
		return result;
	}
	
	public static SingleSeries fromSensorDataToSingleSeries2Hours(String sensorName,
			List<TemperaturePredictionEvent> predictions, String color) {
		SingleSeries result = new SingleSeries();
		result.setColor(color);
		result.setName(sensorName);
		for(TemperaturePredictionEvent prediction : predictions)
		{
			long time = prediction.getTimestamp()/1000;
			//divide to 1000 as we want to represent seconds, not milliseconds
			Point p = new Point(time, prediction.getPredictedTempTwoHours());
			result.addPoint(p);
		}
		return result;
	}
}
