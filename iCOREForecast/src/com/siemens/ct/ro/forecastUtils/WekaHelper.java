package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Weka helper methods
 * @author Lucian Sasu, lucian.sasu@siemens.com
 */
@SuppressWarnings("unused")
public class WekaHelper {

	/**
	 * Creates the list of ARFF attributes
	 * @return a list of ARFF attributes
	 */
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

	/**
	 * Creates a Weka Instances object from a list of TruckSensorJoinedType objects
	 * @param data the list of TruckSensorJoinedType items
	 * @return an Instances object, with ARFF header set 
	 */
	public static Instances createInstances(List<TruckSensorJoinedType> data) {
		Instances instances = new Instances("someData",
				buildAttributesFromCSV(), 0);
		for (TruckSensorJoinedType item : data) {
			instances.add(buildInstance(item, instances));
		}
		instances.sort(instances.attribute("time"));
		return instances;
	}

	/**
	 * Create a Weka Instance from a TruckSensorJoinedType object
	 * @param item the TruckSensorJoinedType item used to populate a Weka Instance object
	 * @param instances the dataset whose schema is to be considered
	 * @return the Instance holding values from the given TruckSensorJoinedType object
	 */
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
	 * @return a set of Instance data, with the same schema as the passed dataset argument; the columns whose field names are specified are set to missing values
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
	
	/**
	 * Helper class used to compare two TruckSensorJoinedType instances
	 * @author Lucian Sasu, lucian.sasu@siemens.com
	 */
	private static final class CompareTruckSensorJoinedType implements Comparator<TruckSensorJoinedType>{

		@Override
		public int compare(TruckSensorJoinedType o1, TruckSensorJoinedType o2) {
			return o1.getTimestamp().compare(o2.getTimestamp());
		}
	}
	
	/**
	 * comparator object
	 */
	private static final CompareTruckSensorJoinedType comparator = new CompareTruckSensorJoinedType();

	/**
	 * Orders asecendingly a list of TruckSensorJoinedType items based on the timestamp field
	 * @param list
	 */
	public static void orderList(List<TruckSensorJoinedType> list)
	{
		Collections.sort(list, comparator);
	}

	public static void log(TemperaturePredictionType temperaturePredictionType) {
		System.out.println("#############Prediction ten minutes: " + temperaturePredictionType.getPedictedTempTenMin());
	}
}
