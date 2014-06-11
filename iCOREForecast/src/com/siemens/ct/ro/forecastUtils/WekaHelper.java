package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WekaHelper {

	/**
	 * 
	 * @param data Measurements instance (iCore friendly class, no Weka internals needed to be known)
	 * @return an Instances (Weka API) dataset
	 */
	public static Instances createInstances(Measurements data) {
		Instances instances = new Instances("someData", buildAttributes(), 0);
		for(MeasurementData measurementData : data)
		{
			instances.add(buildInstance(measurementData, instances));
		}
		return instances;
	}
	
	public static Measurements createMeasurements(Instances data)
	{
		Measurements measurements = new Measurements(data.numInstances());
		for(Instance instance : data)
		{
			measurements.add(getMeasurementFromInstance(instance));
		}
		return measurements;
	}
	
	private static MeasurementData getMeasurementFromInstance(Instance instance) {
		MeasurementData measurementData = new MeasurementData();
		
		//TODO: de refacut
		measurementData.setTemperatureInfrastructureSensor1(instance.value(0));
		measurementData.setTemperatureInfrastructureSensor2(instance.value(1));
		measurementData.setTemperatureInfrastructureSensor3(instance.value(2));
		measurementData.setHumidityInfrastructureSensor(instance.value(3));
		measurementData.setHvacStateOn(instance.value(4) == 0 ? false : true);
		measurementData.setHvacStateValue(instance.value(5));
		measurementData.setCurrentClampValue(instance.value(6));
		measurementData.setExternalTemperature(instance.value(7));
		measurementData.setTemperatureParcel(instance.value(8));
		measurementData.setTime((int)instance.value(9));
		
		return measurementData;
	}

	private static Instance buildInstance(MeasurementData measurementData, Instances instances) {
		double[] attValues = new double[instances.numAttributes()];
		attValues[0] = measurementData.getTemperatureInfrastructureSensor1();
		attValues[1] = measurementData.getTemperatureInfrastructureSensor2();
		attValues[2] = measurementData.getTemperatureInfrastructureSensor3();
		attValues[3] = measurementData.getHumidityInfrastructureSensor();
		attValues[4] = measurementData.isHvacStateOn() == false ? 0.0 : 1.0;
		attValues[5] = measurementData.getHvacStateValue();
		attValues[6] = measurementData.getCurrentClampValue();
		attValues[7] = measurementData.getExternalTemperature();
		attValues[8] = measurementData.getTemperatureParcel();
		attValues[9] = measurementData.getTime();
		Instance instance = new DenseInstance(1.0, attValues);
		return instance;
	}

	private static ArrayList<Attribute> buildAttributes()
	{
		ArrayList<Attribute> result = new ArrayList<Attribute>();
		
		Attribute attributeTemperatureInfrastructureSensor1 = new Attribute("temperatureInfrastructureSensor1");
		Attribute attributeTemperatureInfrastructureSensor2 = new Attribute("temperatureInfrastructureSensor2");
		Attribute attributeTemperatureInfrastructureSensor3 = new Attribute("temperatureInfrastructureSensor3");
		Attribute attributeHumidityInfrastructureSensor = new Attribute("humidityInfrastructureSensor");
		
		List<String> falseTrue = new ArrayList<String>();
		falseTrue.add("false");
		falseTrue.add("true");
		Attribute attributeHvacStateOn = new Attribute("hvacStateOn", falseTrue);
		
		Attribute attributeHvacStateValue = new Attribute("hvacStateValue");
		Attribute attributeCurrentClampValue = new Attribute("currentClampValue");
		Attribute attributeeExternalTemperature = new Attribute("externalTemperature");
		Attribute attributeTemperatureParcel = new Attribute("temperatureParcel");
		Attribute attributeTime = new Attribute("time");
		
		result.add(attributeTemperatureInfrastructureSensor1);
		result.add(attributeTemperatureInfrastructureSensor2);
		result.add(attributeTemperatureInfrastructureSensor3);
		result.add(attributeHumidityInfrastructureSensor);
		result.add(attributeHvacStateOn);
		result.add(attributeHvacStateValue);
		result.add(attributeCurrentClampValue);
		result.add(attributeeExternalTemperature);
		result.add(attributeTemperatureParcel);
		result.add(attributeTime);
		return result;
	}
	
	public static void main(String[] args) {
		MeasurementData data1 = new MeasurementData();
		data1.setCurrentClampValue(10);
		data1.setExternalTemperature(20);
		data1.setHumidityInfrastructureSensor(0.7);
		data1.setHvacStateOn(true);
		data1.setTemperatureInfrastructureSensor1(10);
		data1.setTemperatureInfrastructureSensor2(10);
		data1.setTemperatureInfrastructureSensor3(10);
		data1.setTime(1);
		
		MeasurementData data2 = new MeasurementData();
		data2.setCurrentClampValue(100);
		data2.setExternalTemperature(200);
		data2.setHumidityInfrastructureSensor(0.75);
		data2.setHvacStateOn(false);
		data2.setTemperatureInfrastructureSensor1(100);
		data2.setTemperatureInfrastructureSensor2(100);
		data2.setTemperatureInfrastructureSensor3(100);
		data2.setTime(2);
		
		Measurements measurements = new Measurements();
		measurements.add(data1);
		measurements.add(data2);
		
		Instances instances = createInstances(measurements);
		for(Instance instance : instances)
		{
			System.out.println(instance.toString());
		}
	}

}
