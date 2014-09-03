/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

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
	public static List<TruckSensorJoinedType> readData(String fileName, boolean skipFirstLine) throws FileNotFoundException
	{
		List<TruckSensorJoinedType> result = new ArrayList<TruckSensorJoinedType>();
		
		Scanner scanner = new Scanner(new File(fileName));
		try
		{
			if (skipFirstLine && scanner.hasNextLine())
			{
				scanner.nextLine();
			}
			Date now = new Date();
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				TruckSensorJoinedType item = new TruckSensorJoinedType();
				
				item.setContainerTemperature1(DataConverter.fromStringTo_double(tokens[0]));
				item.setContainerTemperature2(DataConverter.fromStringTo_double(tokens[1]));
				item.setContainerTemperature3(DataConverter.fromStringTo_double(tokens[2]));
				item.setExternalTemperature(DataConverter.fromStringTo_double(tokens[3]));
				item.setParcelTemperature(DataConverter.fromStringTo_double(tokens[4]));
				item.setHumidityValue(DataConverter.fromStringTo_double(tokens[5]));
				item.setCurrentClampValue(DataConverter.fromStringTo_double(tokens[6]));
				item.setHvacStateOn(DataConverter.fromStringToBoolean(tokens[7]));
				item.setHvacStateValue(DataConverter.fromStringTo_double(tokens[8]));
//				item.setTime(DataConverter.fromStringToLong(tokens[9]));
				
				now.setTime(now.getTime() + 1000*60*10);//10 minutes
				
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(now);
				XMLGregorianCalendar date2;
				try {
					date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					item.setTimestamp(date2);
				} catch (DatatypeConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	public static List<TruckSensorJoinedType> readData2(String fileName, boolean skipFirstLine) throws FileNotFoundException
	{
		List<TruckSensorJoinedType> result = new ArrayList<TruckSensorJoinedType>();
		
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
				TruckSensorJoinedType item = new TruckSensorJoinedType();
				
				item.setGatewayId(tokens[1]);
				item.setContainerTemperature1(DataConverter.fromStringTo_double(tokens[2]));
				item.setContainerTemperatureSensorId1(tokens[3]);
				item.setContainerTemperature2(DataConverter.fromStringTo_double(tokens[4]));
				item.setContainerTemperatureSensorId2(tokens[5]);
				item.setContainerTemperature3(DataConverter.fromStringTo_double(tokens[6]));
				item.setContainerTemperatureSensorId3(tokens[7]);
				item.setHumidityValue(DataConverter.fromStringTo_double(tokens[8]));
				item.setHumidityTemperature(DataConverter.fromStringTo_double(tokens[9]));
				item.setHumiditySensorId(tokens[10]);
				item.setHvacStateOn(DataConverter.fromStringToBoolean(tokens[11]));
				item.setHvacStateValue(DataConverter.fromStringTo_double(tokens[12]));
				item.setHvacActuatorId(tokens[13]);
				item.setCurrentClampValue(DataConverter.fromStringTo_double(tokens[14]));
				item.setCurrentClampSensorId(tokens[15]);
				item.setExternalTemperature(DataConverter.fromStringTo_double(tokens[16]));
				item.setExternalTemperatureId(tokens[17]);
				item.setParcelTemperature(DataConverter.fromStringTo_double(tokens[18]));
				item.setParcelTemperatureSensorId(tokens[19]);
				item.setTimestamp(DataConverter.fromStringToXMLGregorianCalendar(tokens[20]));
				
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
