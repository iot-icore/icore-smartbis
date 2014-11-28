package com.siemens.transportationcvomng;

/**
 * 
 * @author Anca Petrescu
 * 
 */
public class GlobalVariables {
	
	public static final String CEP_ENGINE_CONFIG_FILE_PATH = "configurations/CEPEngine.properties";
	
	public enum InternalVariables{
		STATE
		
	}
	/**
	 * The application has two working modes.
	 * 
	 * @author Anca Petrescu
	 * 
	 */
	
	public static String core_path = "http://localhost:9000/transportationsim/";
	
	public enum States {
		/**
		 * The application is free and will accept the transportation request
		 */
		FREE,
		/**
		 * The transportation process is in progress, any other transportation
		 * request will be rejected.
		 */
		TRANSPORT_IN_PROGRESS
	}
	
	public static String truckID = "80_00_00_00_06_01";

	// infrastructure
	public final static String temperatureSensorID1 = "80_00_00_00_00_01";
	public final static String temperatureSensorID2 = "80_00_00_00_00_02";
	public final static String temperatureSensorID3 = "80_00_00_00_00_03";
	public final static String humiditySensorID1 = "80_00_00_00_02_01";
	public final static String currentClampSensorID1 = "80_00_00_00_03_01";
	public final static String ACUnitID1 = "80_00_00_00_05_01";

	// outside
	public final static String temperatureSensorID16 = "80_00_00_00_04_01";
	
	public static int i=0;
}
