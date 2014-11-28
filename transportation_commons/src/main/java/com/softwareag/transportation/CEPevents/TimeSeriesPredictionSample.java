package com.softwareag.transportation.CEPevents;

import java.util.HashMap;

public class TimeSeriesPredictionSample {

	HashMap<String, TruckSensorJointEvent> measurements;

	public HashMap<String, TruckSensorJointEvent> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(
			HashMap<String, TruckSensorJointEvent> measurements) {
		this.measurements = measurements;
	}

}
