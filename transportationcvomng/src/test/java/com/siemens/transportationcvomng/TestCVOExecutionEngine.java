package com.siemens.transportationcvomng;

/**
 * This class is used for testing in a stand alone environment cep EPNs.
 */

import java.io.IOException;

import com.siemens.transportationcvomng.cvoengine.CEPEngineNotDefinedException;
import com.siemens.transportationcvomng.cvoengine.CvoExecutionEngine;
import com.siemens.transportationcvomng.cvoengine.CvoExecutionEngineFactory;

public class TestCVOExecutionEngine {

	public final static String CEP_ENGINE_CONFIG_FILE_PATH = "configurations/CEPEngine.properties";
	public final static String FORECAST_PATH = "serialized/icoreForecaster.ser";

	private static String truckID = "80_00_00_00_06_01";
	
	// infrastructure
	private static String temperatureSensorID1 = "80_00_00_00_00_01";
	private static String temperatureSensorID2 = "80_00_00_00_00_02";
	private static String temperatureSensorID3 = "80_00_00_00_00_03";
	private static String humiditySensorID1 = "80_00_00_00_02_01";
	private static String currentClampSensorID1 = "80_00_00_00_03_01";
	private static String ACUnitID1 = "80_00_00_00_05_01";
	
	// parcel iCore/Temperature/TMP102/Temperature/PUSH/s80_00_00_00_01_01
	private static String temperatureSensorID4 = "80_00_00_00_01_01";
	private static String temperatureSensorID5 = "80_00_00_00_01_02";
	private static String temperatureSensorID6 = "80_00_00_00_01_03";
	private static String temperatureSensorID7 = "80_00_00_00_01_04";
	private static String temperatureSensorID8 = "80_00_00_00_01_05";
	private static String temperatureSensorID9 = "80_00_00_00_01_06";
	private static String temperatureSensorID10 = "80_00_00_00_01_07";
	private static String temperatureSensorID11 = "80_00_00_00_01_08";
	private static String temperatureSensorID12 = "80_00_00_00_01_09";
	private static String temperatureSensorID13 = "80_00_00_00_01_10";
	private static String temperatureSensorID14 = "80_00_00_00_01_11";
	private static String temperatureSensorID15 = "80_00_00_00_01_12";

	// outside
	private static String temperatureSensorID16 = "80_00_00_00_04_01";

	public static void main(String[] args) {

		CvoExecutionEngineFactory cvoExecutionEngineFactory = new CvoExecutionEngineFactory();

		CvoExecutionEngine cvoExecutionEngine = null;
		try {
			cvoExecutionEngine = cvoExecutionEngineFactory
					.getCVOExecutionEngine(CEP_ENGINE_CONFIG_FILE_PATH);
		} catch (CEPEngineNotDefinedException e) {
			e.printStackTrace();
		}

		cvoExecutionEngine.startEngine();

		// adding the cep input adapters
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID1);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID2);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID3);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID4);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID5);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID6);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID7);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID8);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID9);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID10);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID11);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID12);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID13);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID14);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID15);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(temperatureSensorID16);

		cvoExecutionEngine.registerNewHumidityDataSource(humiditySensorID1);

		cvoExecutionEngine
				.registerNewcurrentClampDataSource(currentClampSensorID1);

		cvoExecutionEngine.registerNewACUnitDataSource(ACUnitID1);

		cvoExecutionEngine.loadTimeSeriesPredictionModel(FORECAST_PATH);
		
/*			
		
		String nonPerishableGoogsMonitoringEPNID = "perish goods";
		cvoExecutionEngine.addNewNonPerishableGoodsMonitoringEPN(nonPerishableGoogsMonitoringEPNID, "Pack1",
				temperatureSensorID4, humiditySensorID1, truckID, 0, 0, 0, 0);
	
		nonPerishableGoogsMonitoringEPNID = "perish goodsgfgffg";
		cvoExecutionEngine.addNewNonPerishableGoodsMonitoringEPN(nonPerishableGoogsMonitoringEPNID, "Pack2",
				temperatureSensorID5, humiditySensorID1, truckID, 0, 0, 0, 0);
		
		nonPerishableGoogsMonitoringEPNID = "perish goodsfg";
		cvoExecutionEngine.addNewNonPerishableGoodsMonitoringEPN(nonPerishableGoogsMonitoringEPNID, "Pack3",
				temperatureSensorID6, humiditySensorID1, truckID, 0, 0, 0, 0);
		
			
		

		
		
		String perishableGoogsMonitoringEPNID = "non perish goods";
		cvoExecutionEngine.addNewPerishableGoodsMonitoringEPN(perishableGoogsMonitoringEPNID, "Pack1",
				temperatureSensorID4, humiditySensorID1, truckID, 0, 0, 0, 0,
				40, 0, 0);
	 */		
				

//	
//		List<String> parcelTemperatureSensorsIDs = new ArrayList<String>();
//		parcelTemperatureSensorsIDs.add(temperatureSensorID4);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID5);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID6);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID7);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID8);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID9);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID10);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID11);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID12);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID13);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID14);
//		parcelTemperatureSensorsIDs.add(temperatureSensorID15);

		
//		String timeSeriesPredictionEPNID = "time seris";
//		cvoExecutionEngine.addNewTrainingSetGeneratorEPN(timeSeriesPredictionEPNID, truckID,
//				temperatureSensorID1, temperatureSensorID2,
//				temperatureSensorID3, humiditySensorID1, ACUnitID1,
//				currentClampSensorID1, temperatureSensorID16,
//				parcelTemperatureSensorsIDs, 30.0);
		
			

		String timeSeriesPredictionEPNID = "time seris2";
		cvoExecutionEngine.addNewTimeSeriesPredictionEPN(timeSeriesPredictionEPNID, "pack34:", truckID,
				temperatureSensorID1, temperatureSensorID2,
				temperatureSensorID3, humiditySensorID1, ACUnitID1,
				currentClampSensorID1, temperatureSensorID16,
				temperatureSensorID4, 10, 150);
/**/
		try {
			System.in.read();
		} catch (IOException e) {
			// If we can't read we'll just exit
		}

		cvoExecutionEngine.stopEngine();

	}

}
