package com.siemens.transportationcvomng.EsperCEPProcessing.UDF;

import java.util.HashMap;

import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TimeSeriesPredictionSample;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

public class TimeSeriesPredictionService {

	private static ICOREForecaster icoreForecaster;

	public static void setIcoreForecaster(ICOREForecaster icoreForecaster) {
		TimeSeriesPredictionService.icoreForecaster = icoreForecaster;
	}

	public static TemperaturePredictionEvent predict(
			TimeSeriesPredictionSample timeSeriesPredictionSample) {

		if (timeSeriesPredictionSample != null) {

			HashMap<String, TruckSensorJointEvent> data = timeSeriesPredictionSample
					.getMeasurements();

			TemperaturePredictionEvent temperaturePrediction = icoreForecaster
					.feedWithDataAndForecast(data);
			
			return temperaturePrediction;
		}

		TemperaturePredictionEvent temperaturePrediction = new TemperaturePredictionEvent();
		return temperaturePrediction;
	}

}
