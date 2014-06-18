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
 * @author Lucian Sasu
 *
 */
public class CSVUtils {
	
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
}
