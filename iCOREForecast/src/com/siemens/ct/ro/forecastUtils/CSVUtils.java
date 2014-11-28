/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

/**
 * Data reading methods from CSV files
 * @author Lucian Sasu, lucian.sasu@siemens.com
 */
public class CSVUtils {
	
	/**
	 * Read CSV records from a file and returns a collection of TruckSensorJoinedType objects
	 * @param fileName the path of the CSV file
	 * @param skipFirstLine whether to skip the first line of the CSV or not
	 * @return a list of TruckSensorJoinedType objects
	 * @throws FileNotFoundException if the CSV file is not found
	 */
	public static List<TruckSensorJointEvent> readData(String fileName, boolean skipFirstLine) throws FileNotFoundException
	{
		List<TruckSensorJointEvent> result = new ArrayList<TruckSensorJointEvent>();
		
		Scanner scanner = new Scanner(new File(fileName));
		try
		{
			if (skipFirstLine && scanner.hasNextLine())
			{
				scanner.nextLine();
			}
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				TruckSensorJointEvent item = new TruckSensorJointEvent();
				
//				temperatureInfrastructureSensor1,temperatureInfrastructureSensor2,temperatureInfrastructureSensor3,externalTemperature,temperatureParcel,humidityInfrastructureSensor,currentClampValue,hvacStateOn,hvacStateValue,time
				
				item.setContainerTemperature1Value(DataConverter.fromStringTo_double(tokens[0]));
				item.setContainerTemperature2Value(DataConverter.fromStringTo_double(tokens[1]));
				item.setContainerTemperature3Value(DataConverter.fromStringTo_double(tokens[2]));
				item.setExternalTemperatureValue(DataConverter.fromStringTo_double(tokens[3]));
				item.setParcelTemperatureValue(DataConverter.fromStringTo_double(tokens[4]));
				item.setHumidityValue(DataConverter.fromStringTo_double(tokens[5]));
				item.setCurrentClampValue(DataConverter.fromStringTo_double(tokens[6]));
				item.setAcUnitState(DataConverter.fromStringToBoolean(tokens[7]));
				item.setAcUnitTarghetValue(DataConverter.fromStringTo_double(tokens[8]));
//				item.setTimestamp(DataConverter.fromStringToXMLGregorianCalendar(tokens[9]));
				item.setTimestamp(DataConverter.fromStringToLong(tokens[9]));
				
				result.add(item);
			}
		}
		finally
		{
			scanner.close();
		}
		
		return result;
	}
	
	/**
	 * Read a CSV file with header: id, gatewayId, containerTemperature1, containerTemperatureSensorId1, containerTemperature2, containerTemperatureSensorId2, containerTemperature3, containerTemperatureSensorId3, humidityValue, humidityTemperature, humiditySensorId, hvacStateOn, hvacStateValue, hvacActuatorId, currentClampValue, currentClampSensorId, externalTemperature, externalTemperatureId, parcelTemperature, parcelTemperatureSensorId, timestamp;
	 * @param fileName the path of the CSV file
	 * @param skipFirstLine whether to skip the first CSV file or not
	 * @return a list of TruckSensorJoinedType objects
	 * @throws FileNotFoundException if the CSV file is not found
	 */
	public static List<TruckSensorJointEvent> readData2(String fileName, boolean skipFirstLine) throws FileNotFoundException
	{
		List<TruckSensorJointEvent> result = new ArrayList<TruckSensorJointEvent>();
		
		Scanner scanner = new Scanner(new File(fileName));
		try
		{
			if (skipFirstLine && scanner.hasNextLine())
			{
				scanner.nextLine();
			}
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				TruckSensorJointEvent item = new TruckSensorJointEvent();
				
				item.setGatewayId(tokens[1]);
				item.setContainerTemperature1Value(DataConverter.fromStringTo_double(tokens[2]));
				item.setContainerTemperatureSensorId1(tokens[3]);
				item.setContainerTemperature2Value(DataConverter.fromStringTo_double(tokens[4]));
				item.setContainerTemperatureSensorId2(tokens[5]);
				item.setContainerTemperature3Value(DataConverter.fromStringTo_double(tokens[6]));
				item.setContainerTemperatureSensorId3(tokens[7]);
				item.setHumidityValue(DataConverter.fromStringTo_double(tokens[8]));
				item.setHumidityValue(DataConverter.fromStringTo_double(tokens[9]));
				item.setHumiditySensorId(tokens[10]);
				item.setAcUnitState(DataConverter.fromStringToBoolean(tokens[11]));
				item.setAcUnitTarghetValue(DataConverter.fromStringTo_double(tokens[12]));
				item.setAcUnitActuatorId(tokens[13]);
				item.setCurrentClampValue(DataConverter.fromStringTo_double(tokens[14]));
				item.setCurrentClampSensorId(tokens[15]);
				item.setExternalTemperatureValue(DataConverter.fromStringTo_double(tokens[16]));
				item.setExternalTemperatureId(tokens[17]);
				item.setParcelTemperatureValue(DataConverter.fromStringTo_double(tokens[18]));
				item.setParcelTemperatureSensorId(tokens[19]);
//				item.setTimestamp(DataConverter.fromStringToXMLGregorianCalendar(tokens[20]));
				item.setTimestamp(DataConverter.fromStringToLong(tokens[20]));
				
				result.add(item);
			}
		}
		finally
		{
			scanner.close();
		}
		
		return result;
	}
	
	
}
