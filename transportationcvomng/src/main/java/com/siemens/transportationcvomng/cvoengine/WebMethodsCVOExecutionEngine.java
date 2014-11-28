package com.siemens.transportationcvomng.cvoengine;

import java.util.List;

import org.kohsuke.graphviz.Graph;

public class WebMethodsCVOExecutionEngine implements CvoExecutionEngine {
	@Override
	public void startEngine() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopEngine() {
		// TODO Auto-generated method stub

	}


	

	@Override
	public void loadTimeSeriesPredictionModel(
			String pathToSerialisedPredictionModel) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void addNewACUnitControlEPN(String epnID, String truckID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String hvacActuatorID,
			double maxTemperature, double minTemperature,
			double targetvalueForMaxTemperature,
			double targetvalueForMinTemperature) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteEpn(String epnID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerNewTemperatureDataSource(String temperatureSensorID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerNewHumidityDataSource(String humiditySensorID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerNewcurrentClampDataSource(String currentClampSensorID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerNewACUnitDataSource(String acUnitID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewPerishableGoodsMonitoringEPN(String epnID, String packageID,
			String temperatureSensorID, String humiditySensorID,
			String truckID, double hardMinTemperature,
			double hardMaxTemperature, double softMinTemperature,
			double softMaxTemperature, double softTemperatureMonitoringPeriod,
			double hardMinHumdity, double hardMaxHumdity) {
		// TODO Auto-generated method stub
		
	}



	

	@Override
	public void addNewNonPerishableGoodsMonitoringEPN(String epnID,
			String packageID, String temperatureSensorID,
			String humiditySensorID, String truckID, double hardMinTemperature,
			double hardMaxTemperature, double hardMinHumdity,
			double hardMaxHumdity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewTimeSeriesPredictionEPN(String epnID, String packageID,
			String gatewayID, String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			String parcelTemperatureSensorID, double reportingPeriod,
			double reportingWindowLength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Graph getGetCurrentCVOsStructure() {
		// TODO Auto-generated method stub
		return null;
	}

}
