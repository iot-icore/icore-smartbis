/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.util.List;

import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

/**
 * @author Lucian Sasu
 *
 */
public class Logger {

	public static void log(String message, List<TruckSensorJointEvent> orderedList) {
		for(TruckSensorJointEvent truckSensorJoinedType : orderedList)
		{
			System.out.println(message + " " + toString(truckSensorJoinedType));
		}
	}

	private static String toString(TruckSensorJointEvent truckSensorJoinedType) {
		
		String str = 
				
				truckSensorJoinedType.getContainerTemperature1Value() +"," +
				truckSensorJoinedType.getContainerTemperature2Value() + "," +
				truckSensorJoinedType.getContainerTemperature3Value() + ","+
				truckSensorJoinedType.getExternalTemperatureValue() + ","+
				truckSensorJoinedType.getParcelTemperatureValue()+","+
				truckSensorJoinedType.getHumidityValue()+","+
				truckSensorJoinedType.getCurrentClampValue()+","+
				(truckSensorJoinedType.isAcUnitState() ? "1" : "0") +","+
				truckSensorJoinedType.getAcUnitTarghetValue() + ","+
				truckSensorJoinedType.getTimestamp();
				
		return str;
	}

}
