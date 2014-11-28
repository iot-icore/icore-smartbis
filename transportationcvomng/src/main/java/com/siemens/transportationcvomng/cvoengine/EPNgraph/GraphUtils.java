package com.siemens.transportationcvomng.cvoengine.EPNgraph;

public class GraphUtils {
	
	private static final int START_INDEX = 13;


	public static String getNodeIDfromSensorID(String sensorID){
		return sensorID.substring(START_INDEX);
	}

}
