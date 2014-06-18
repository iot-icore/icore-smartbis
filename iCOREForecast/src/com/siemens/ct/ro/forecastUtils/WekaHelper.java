package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WekaHelper {

	private static ArrayList<Attribute> buildAttributesFromCSV() {
		ArrayList<Attribute> result = new ArrayList<Attribute>();

		Attribute attributeTemperatureInfrastructureSensor1 = new Attribute(
				"temperatureInfrastructureSensor1");
		Attribute attributeTemperatureInfrastructureSensor2 = new Attribute(
				"temperatureInfrastructureSensor2");
		Attribute attributeTemperatureInfrastructureSensor3 = new Attribute(
				"temperatureInfrastructureSensor3");
		Attribute attributeExternalTemperature = new Attribute(
				"externalTemperature");
		Attribute attributeTemperatureParcel = new Attribute(
				"temperatureParcel");
		Attribute attributeHumidityInfrastructureSensor = new Attribute(
				"humidityInfrastructureSensor");
		Attribute attributeCurrentClampValue = new Attribute(
				"currentClampValue");

		List<String> falseTrue = new ArrayList<String>();
		falseTrue.add("false");
		falseTrue.add("true");
		Attribute attributeHvacStateOn = new Attribute("hvacStateOn", falseTrue);

		Attribute attributeHvacStateValue = new Attribute("hvacStateValue");
		Attribute attributeTime = new Attribute("time", "yyyy-MM-dd HH:mm:ss");

		result.add(attributeTemperatureInfrastructureSensor1);
		result.add(attributeTemperatureInfrastructureSensor2);
		result.add(attributeTemperatureInfrastructureSensor3);
		result.add(attributeExternalTemperature);
		result.add(attributeTemperatureParcel);
		result.add(attributeHumidityInfrastructureSensor);
		result.add(attributeCurrentClampValue);
		result.add(attributeHvacStateOn);
		result.add(attributeHvacStateValue);
		result.add(attributeTime);
		return result;
	}

	public static Instances createInstances(List<TruckSensorJoinedType> data) {
		Instances instances = new Instances("someData",
				buildAttributesFromCSV(), 0);
		for (TruckSensorJoinedType item : data) {
			instances.add(buildInstance(item, instances));
		}
		// dataset.sort(timeAttribute);
		return instances;
	}

	private static Instance buildInstance(TruckSensorJoinedType item,
			Instances instances) {

		Instance instance = new DenseInstance(instances.numAttributes());// for
																			// now,
																			// all
																			// values
																			// are
																			// marked
																			// as
																			// missing
		instance.setDataset(instances);// put the instance in sync with
										// dataset's schema
		if (item.getContainerTemperature1() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(0, item.getContainerTemperature1());
		}
		if (item.getContainerTemperature2() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(1, item.getContainerTemperature2());
		}
		if (item.getContainerTemperature3() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(2, item.getContainerTemperature3());
		}
		if (item.getExternalTemperature() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(3, item.getExternalTemperature());
		}
		if (item.getParcelTemperature() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(4, item.getParcelTemperature());
		}
		if (item.getHumidityValue() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(5, item.getHumidityValue());
		}
		if (item.getCurrentClampValue() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(6, item.getCurrentClampValue());
		}
		// if (item.isHvacStateOn() != null)
		// {
		instance.setValue(7, item.isHvacStateOn() == true ? 1.0 : 0.0);
		// }
		if (item.getHvacStateValue() != Constants.IMPOSSIBLE_VALUE) {
			instance.setValue(8, item.getHvacStateValue());
		}
		instance.setValue(9, item.getTimestamp().toGregorianCalendar()
				.getTimeInMillis());

		return instance;
	}

}
