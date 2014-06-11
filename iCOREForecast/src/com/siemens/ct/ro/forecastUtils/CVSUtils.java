/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

/**
 * @author Lucian Sasu
 *
 */
public class CVSUtils {
	
//	public static Measurements readMeasurements(String filename, boolean skipFirstLine) throws FileNotFoundException
//	{
//		Measurements result = new Measurements();
//		
//		Scanner scanner = new Scanner(new File(filename));
//		try
//		{
//			if (skipFirstLine && scanner.hasNextLine())
//			{
//				scanner.nextLine();
//			}
//			while(scanner.hasNextLine())
//			{
//				String line = scanner.nextLine();
//				String[] tokens = line.split(",");
//			}
//		}
//		finally
//		{
//			scanner.close();
//		}
//		scanner.close();
//		
//		return result;
//	}
	
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
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] tokens = line.split(",");
				TruckSensorJoinedType item = new TruckSensorJoinedType();
				item.setHumidity(DataConverter.fromStringToBigDecimal(tokens[5]));
				item.setTemperature800000201(DataConverter.fromStringToBigDecimal(tokens[0]));//first temperat
			}
		}
		finally
		{
			scanner.close();
		}
		scanner.close();
		
		return result;
	}
}
