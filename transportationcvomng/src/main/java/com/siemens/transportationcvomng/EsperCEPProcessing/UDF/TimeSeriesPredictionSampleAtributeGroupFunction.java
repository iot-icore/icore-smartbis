package com.siemens.transportationcvomng.EsperCEPProcessing.UDF;

import java.util.HashMap;

import com.espertech.esper.epl.agg.aggregator.AggregationMethod;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TimeSeriesPredictionSample;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

public class TimeSeriesPredictionSampleAtributeGroupFunction implements
		AggregationMethod {

	public static final double NULL_OR_MISSING_VALUE = -1000000.0;

	HashMap<String, TruckSensorJointEvent> measurements;

	public TimeSeriesPredictionSampleAtributeGroupFunction() {
		super();
		measurements = new HashMap<String, TruckSensorJointEvent>();

		//System.out.println("Test");

	}

	@Override
	public void clear() {
		measurements = new HashMap<String, TruckSensorJointEvent>();
	}

	@Override
	public void enter(Object arg0) {

		if (arg0 != null) {
			HashMap<String, Object> event = (HashMap<String, Object>) arg0;

			String timestamp = event.get("timestamp").toString();

			if (!measurements.containsKey(timestamp)) {

				TruckSensorJointEvent truckSensorJointEvent = new TruckSensorJointEvent();

				truckSensorJointEvent.setContainerTemperature1Value(Double
						.parseDouble(event.get("container_temperature_1")
								.toString()));
				truckSensorJointEvent.setContainerTemperature2Value(Double
						.parseDouble(event.get("container_temperature_2")
								.toString()));
				truckSensorJointEvent.setContainerTemperature3Value(Double
						.parseDouble(event.get("container_temperature_3")
								.toString()));
				truckSensorJointEvent
						.setCurrentClampValue(Double.parseDouble(event.get(
								"currentClampValue").toString()));
				truckSensorJointEvent.setExternalTemperatureValue(Double
						.parseDouble(event.get("external_temperature")
								.toString()));
				truckSensorJointEvent.setAcUnitState(Boolean.parseBoolean(event
						.get("hvacStateValue").toString()));
				truckSensorJointEvent.setAcUnitTarghetValue(Double
						.parseDouble(event.get("hvacStateValue").toString()));
				truckSensorJointEvent.setHumidityValue(Double.parseDouble(event
						.get("humidity_value").toString()));
				truckSensorJointEvent
						.setParcelTemperatureValue(Double.parseDouble(event
								.get("parcel_temperature").toString()));
				truckSensorJointEvent.setTimestamp(Long.parseLong(timestamp));

				measurements.put(truckSensorJointEvent.getTimestamp()
						.toString(), truckSensorJointEvent);

//				System.out
//						.println("============= am adaugat masuratoare total = "
//								+ measurements.size());

			}
		}

	}

	@Override
	public Object getValue() {

		// System.out.println("============= o sa dau predictie total ="
		// + measurements.size());

		TimeSeriesPredictionSample timeSeriesPredictionSample = new TimeSeriesPredictionSample();
		timeSeriesPredictionSample.setMeasurements(measurements);
		return timeSeriesPredictionSample;

		// if (measurements.size() != 0)
		// return TimeSeriesPredictionService.predict(measurements);
		// else {
		// System.out.println("Missing value!!!!");
		// return NULL_OR_MISSING_VALUE;
		//
		// }
	}

	@Override
	public Class getValueType() {

		return TemperaturePredictionEvent.class;
	}

	@Override
	public void leave(Object arg0) {

		if (arg0 != null) {
			HashMap<String, Object> event = (HashMap<String, Object>) arg0;

			String timestamp = event.get("timestamp").toString();

			

			if (measurements.containsKey(timestamp.toString())) {
				measurements.remove(timestamp.toString());

			}

//			System.out.println("============= am scos masuratoare total ="
//					+ measurements.size());
		}

	}

}
