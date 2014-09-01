/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.util.List;

import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

/**
 * @author ro1v0393
 *
 */
public class Logger {

	public static void log(String message, List<TruckSensorJoinedType> orderedList) {
		System.out.println("******" + message);
		for(TruckSensorJoinedType truckSensorJoinedType : orderedList)
		{
			System.out.println(toString(truckSensorJoinedType));
		}
	}

	private static String toString(TruckSensorJoinedType truckSensorJoinedType) {
		String str = "";
		str += "timeStamp= " + truckSensorJoinedType.getTimestamp().toString() + "; containerTemperature1= " + truckSensorJoinedType.getContainerTemperature1() + "; containerTemperature2=" + truckSensorJoinedType.getContainerTemperature2();
		return str;
	}

}
