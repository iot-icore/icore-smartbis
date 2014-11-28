package com.siemens.ct.ro.forecastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.siemens.ct.ro.transportation.utils.XMLGregorianCalendarConverter;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Weka helper methods
 * @author Lucian Sasu, lucian.sasu@siemens.com
 */
@SuppressWarnings("unused")
public class Utils {
	
	private static DatatypeFactory dataTypeFactory = null;
	
	static
	{
		try {
			dataTypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			System.out.println("Error while trying to create a DatatypeFactory instance \n" + e.getMessage());
		}
	}
	

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
	public static Instances createInstances(List<TruckSensorJointEvent> data) {
		Instances instances = new Instances("Relation", buildAttributesFromCSV(), 0);
		for (TruckSensorJointEvent item : data) {
			Instance instance = buildInstance(item, instances);
			instances.add(instance);
		}
		instances.sort(instances.attribute("time"));
		return instances;
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * Create a Weka Instance from a TruckSensorJoinedType object
	 * @param item the TruckSensorJoinedType item used to populate a Weka Instance object
	 * @param instances the dataset whose schema is to be considered
	 * @return the Instance holding values from the given TruckSensorJoinedType object
	 */
	private static Instance buildInstance(TruckSensorJointEvent item,
			Instances instances) {

		Instance instance = new DenseInstance(instances.numAttributes());
		// for now, all values are marked as missing
		
		instance.setDataset(instances);// put the instance in sync with
										// dataset's schema
		if (item.getContainerTemperature1Value() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(0, item.getContainerTemperature1Value());
		}
		if (item.getContainerTemperature2Value() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(1, item.getContainerTemperature2Value());
		}
		if (item.getContainerTemperature3Value() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(2, item.getContainerTemperature3Value());
		}
		if (item.getExternalTemperatureValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(3, item.getExternalTemperatureValue());
		}
		if (item.getParcelTemperatureValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(4, item.getParcelTemperatureValue());
		}
		if (item.getHumidityValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(5, item.getHumidityValue());
		}
		if (item.getCurrentClampValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(6, item.getCurrentClampValue());
		}
		instance.setValue(7, item.isAcUnitState() ? 1.0 : 0.0);
		
		if (item.getAcUnitTarghetValue() != Constants.NULL_OR_MISSING_VALUE) {
			instance.setValue(8, item.getAcUnitTarghetValue());
		}
		
		//instance.setValue(9, item.getTimestamp().toGregorianCalendar().getTimeInMillis()/1000);
//		try {
//			String timeValueAsString = sdf.format(new Date(item.getTimestamp()));
//			double timeValue = instances.attribute(Constants.TIME_FIELDNAME).parseDate(timeValueAsString);
//			instance.setValue(9, timeValue);
			instance.setValue(9, item.getTimestamp());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		instance.setValue(9, item.getTimestamp().toGregorianCalendar().getTimeInMillis()/1000);

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
	private static final class CompareTruckSensorJoinedType implements Comparator<TruckSensorJointEvent>{

		@Override
		public int compare(TruckSensorJointEvent o1, TruckSensorJointEvent o2) {
			if (o1.getTimestamp() < o2.getTimestamp())
			{
				return -1;
			}
			if (o1.getTimestamp() > o2.getTimestamp())
			{
				return +1;
			}
			return 0;
		}
	}
	
	/**
	 * comparator object
	 */
	private static final CompareTruckSensorJoinedType comparator = new CompareTruckSensorJoinedType();

	/**
	 * Orders ascending a list of TruckSensorJoinedType items based on the timestamp field
	 * @param list
	 */
	public static void orderList(List<TruckSensorJointEvent> list)
	{
		Collections.sort(list, comparator);
	}
	
	/**
	 * Creates a hashmap from a list of {@link TruckSensorJoinedType}; the keys are "0", "1" etc
	 * @param data the list to be casted to a hashmap
	 * @return a hashmap of "0", "1" etc paired with items from the list
	 */
	public static HashMap<String, TruckSensorJointEvent> createHash(List<TruckSensorJointEvent> data)
	{
		HashMap<String, TruckSensorJointEvent> result = new HashMap<String, TruckSensorJointEvent>(data.size());
		int i = 0;
		for (TruckSensorJointEvent truckSensorJoinedType : data) {
			result.put(""+i, truckSensorJoinedType);
			i++;
		}
		return result;
	}
	
	public static String toString(TemperaturePredictionEvent temperaturePrediction)
	{
		return 	"prediction: in ten minutes: " + temperaturePrediction.getPedictedTempTenMin() + "\n" +
				"prediction: in one hour: " + temperaturePrediction.getPredictedTempOneHour() + "\n" + 
				"prediction: in two hours: " + temperaturePrediction.getPredictedTempTwoHours();
	}

	/**
	 * Creates a list of {@link TruckSensorJoinedType} from a hashmap; the keys are discarded; the list is ordered by time
	 * @param data the hashmap to be converted
	 * @return a list of {@link TruckSensorJoinedType}
	 */
	public static List<TruckSensorJointEvent> createInstances(
			HashMap<String, TruckSensorJointEvent> data) {
			
		List<TruckSensorJointEvent> result = new ArrayList<TruckSensorJointEvent>(data.size());
		
		for(Map.Entry<String, TruckSensorJointEvent> item : data.entrySet())
		{
			result.add(item.getValue());
		}
		
		Utils.orderList(result);
		
		return result;
	}

	/**
	 * Shifts data to be adjacent to @param lastTimeInTrainSet
	 * @param data to be shifted
	 * @param lastTimeInTrainSet the last time in training set; the priming data will follow 
	 * @param secondsBetweenMeasurements increment between the measurements
	 * @return shifted data
	 */
	public static List<TruckSensorJointEvent> alignData(List<TruckSensorJointEvent> data,
			long lastTimeInTrainSet, int secondsBetweenMeasurements) {
		List<TruckSensorJointEvent> result = new  LinkedList<TruckSensorJointEvent>();
		for(TruckSensorJointEvent truckSensorJointEvent : data)
		{
			TruckSensorJointEvent cloneTruckSensorJointEvent = getCloneOf(truckSensorJointEvent);
			lastTimeInTrainSet += secondsBetweenMeasurements * 1000;
			cloneTruckSensorJointEvent.setTimestamp(lastTimeInTrainSet);
			result.add(cloneTruckSensorJointEvent);
		}
		return result;
	}

	private static TruckSensorJointEvent getCloneOf(
			TruckSensorJointEvent truckSensorJoinedType) {
		TruckSensorJointEvent result= new TruckSensorJointEvent();
		result.setContainerTemperature1Value(truckSensorJoinedType.getContainerTemperature1Value());
		result.setContainerTemperature2Value(truckSensorJoinedType.getContainerTemperature2Value());
		result.setContainerTemperature3Value(truckSensorJoinedType.getContainerTemperature3Value());
		result.setContainerTemperatureSensorId1(truckSensorJoinedType.getContainerTemperatureSensorId1());
		result.setContainerTemperatureSensorId2(truckSensorJoinedType.getContainerTemperatureSensorId2());
		result.setContainerTemperatureSensorId3(truckSensorJoinedType.getContainerTemperatureSensorId3());
		result.setCurrentClampSensorId(truckSensorJoinedType.getCurrentClampSensorId());
		result.setCurrentClampValue(truckSensorJoinedType.getCurrentClampValue());
		result.setExternalTemperatureValue(truckSensorJoinedType.getExternalTemperatureValue());
		result.setExternalTemperatureId(truckSensorJoinedType.getExternalTemperatureId());
		result.setGatewayId(truckSensorJoinedType.getGatewayId());
		result.setHumiditySensorId(truckSensorJoinedType.getHumiditySensorId());
		result.setHumidityValue(truckSensorJoinedType.getHumidityValue());
		result.setHumidityValue(truckSensorJoinedType.getHumidityValue());
		result.setAcUnitActuatorId(truckSensorJoinedType.getAcUnitActuatorId());
		result.setAcUnitState(truckSensorJoinedType.isAcUnitState());
		result.setAcUnitTarghetValue(truckSensorJoinedType.getAcUnitTarghetValue());
		result.setParcelTemperatureValue(truckSensorJoinedType.getParcelTemperatureValue());
		result.setParcelTemperatureSensorId(truckSensorJoinedType.getParcelTemperatureSensorId());
		result.setTimestamp(truckSensorJoinedType.getTimestamp());
		
		return result;
	}

	/**
	 * @param date the date to be pushed up with a number of seconds
	 * @param seconds the seconds to push up the date
	 * @return the new date if the DatatypeFactory instance can be instantiated, or the old date otherwise
	 */
	private static XMLGregorianCalendar incrementTime(XMLGregorianCalendar date, int seconds)
	{
		if (dataTypeFactory != null)
		{
			Duration duration = dataTypeFactory.newDuration(seconds * 1000);
			date.add(duration);
			return date;
		} 
		else
		{
			System.out.println("The datatype factory is null!");
			return date;
		}
	}
}
