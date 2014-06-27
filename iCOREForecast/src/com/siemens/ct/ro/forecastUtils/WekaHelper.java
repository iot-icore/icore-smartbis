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
				Constants.TEMPERATURE1);
		Attribute attributeTemperatureInfrastructureSensor2 = new Attribute(
				Constants.TEMPERATURE2);
		Attribute attributeTemperatureInfrastructureSensor3 = new Attribute(
				Constants.TEMPERATURE3);
		Attribute attributeExternalTemperature = new Attribute(
				Constants.EXTERNAL_TEMPERATURE);
		Attribute attributeTemperatureParcel = new Attribute(
				Constants.PARCEL_TEMPERATURE);
		Attribute attributeHumidityInfrastructureSensor = new Attribute(
				Constants.HUMIDITY_VALUE);
		Attribute attributeCurrentClampValue = new Attribute(
				Constants.CURRENT_CLAMP_VALUE);

		List<String> falseTrue = new ArrayList<String>();
		falseTrue.add("false");
		falseTrue.add("true");
		Attribute attributeHvacStateOn = new Attribute(Constants.HVAC_STATE_ON, falseTrue);

		Attribute attributeHvacStateValue = new Attribute(Constants.HVAC_STATE_VALUE);
		Attribute attributeTime = new Attribute(Constants.TIME_FIELDNAME, "yyyy-MM-dd HH:mm:ss");

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
		if (item.getContainerTemperature1() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(0, item.getContainerTemperature1());
		}
		if (item.getContainerTemperature2() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(1, item.getContainerTemperature2());
		}
		if (item.getContainerTemperature3() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(2, item.getContainerTemperature3());
		}
		if (item.getExternalTemperature() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(3, item.getExternalTemperature());
		}
		if (item.getParcelTemperature() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(4, item.getParcelTemperature());
		}
		if (item.getHumidityValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(5, item.getHumidityValue());
		}
		if (item.getCurrentClampValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(6, item.getCurrentClampValue());
		}
		instance.setValue(7, item.isHvacStateOn() ? 1.0 : 0.0);
		if (item.getHvacStateValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(8, item.getHvacStateValue());
		}
		instance.setValue(9, item.getTimestamp().toGregorianCalendar()
				.getTimeInMillis());

		return instance;
	}
	
	/**
	 * If needed, removes the values of the data to be forecasted
	 * @param dataset the dataset containing recent measurements
	 * @param fieldNamesToSetAsUnknown comma separated names of the 
	 * @return
	 */
	public static Instances setUnknownValues(Instances dataset, String fieldNamesToSetAsUnknown) {
		String[] tokensFieldNamesToBeSetUnknown = fieldNamesToSetAsUnknown.split(",");
		Attribute[] attributesToBePredicted = new Attribute[tokensFieldNamesToBeSetUnknown.length];
		Instances result = new Instances(dataset);
		for(int i = 0; i<attributesToBePredicted.length; i++)
		{
			attributesToBePredicted[i] = result.attribute(tokensFieldNamesToBeSetUnknown[i]);
		}
		for(Instance instance : result)
		{
			for(Attribute attribute: attributesToBePredicted)
			{
				instance.setMissing(attribute);
			}
		}
		return result;
	}

}
