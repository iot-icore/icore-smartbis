package com.softwareag.queryinstantiation.test;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mchange.util.AssertException;
import com.softwareag.queryinstantiation.TemperaturePredictionDeployment;
import com.softwareag.wep.api.client.DirectConnection;
import com.softwareag.wep.api.client.DirectDeploymentConnection;

public class TemperaturePredictionDeploymentTest {

	private TemperaturePredictionDeployment temperaturePredictionDeployment;
	private DirectConnection connection;
	private String user;

	@Before
	public void setUp() throws UnknownHostException {
		temperaturePredictionDeployment = new TemperaturePredictionDeployment();
		user = "ColdChainManagementPrediction";
		connection = getConnection(temperaturePredictionDeployment);
	}

	@After
	public void tearDown() {
		shutDownConnection(temperaturePredictionDeployment, connection);
	}


	private void shutDownConnection(
			TemperaturePredictionDeployment tempDeployment,
			DirectConnection conn) {
		tempDeployment.stopEngine(conn);
		try {
			conn.connectDeployment("ColdChainManagementPrediction");
		} catch (IllegalStateException e) {
			assertNotNull(e);
		}
	}

	private DirectConnection getConnection(
			TemperaturePredictionDeployment tempDeployment)
			throws UnknownHostException {
		DirectConnection conn = tempDeployment.startEngine(
				user, "", "localhost", 7867);
		assertNotNull(conn);
		DirectDeploymentConnection deploymentConnection = conn
				.connectDeployment(user);
		assertNotNull(deploymentConnection);
		return conn;
	}

	@Test
	public void testAddNewNonPerishableGoodsMonitoringEPN() throws UnknownHostException {
		String epnID = "NonPerishable"; // queryID
		String gatewayID = "00:01:00:00:00:01"; // gatewayID
		String humiditySensorID = "s80_00_00_00_02_01";
		String temperatureSensorID = "s80_00_00_00_01_05";
		Double hardMinTemperature = 2.0;
		Double hardMaxTemperature = 8.0;
		Double hardMinHumdity = 2.0;
		Double hardMaxHumdity = 10.0;

		temperaturePredictionDeployment.addNewNonPerishableGoodsMonitoringEPN(connection, "ColdChainManagementPrediction", epnID,
				temperatureSensorID, humiditySensorID, gatewayID,
				hardMinTemperature, hardMaxTemperature, hardMinHumdity,
				hardMaxHumdity);
		DirectDeploymentConnection deploymentConnection = connection.connectDeployment("ColdChainManagementPrediction");
		Set<String> queryNames = deploymentConnection.getQueryNames();
		assertNotNull(queryNames);
	}

	@Test
	public void testAddNewPerishableGoodsMonitoringEPN() throws UnknownHostException {
		String epnID = "Perishable"; // queryID
		String gatewayID = "00:01:00:00:00:01"; // gatewayID
		String humiditySensorID = "s80_00_00_00_02_01";
		String temperatureSensorID = "s80_00_00_00_01_05";
		Double hardMinTemperature = 2.0;
		Double hardMaxTemperature = 8.0;
		Double hardMinHumdity = 2.0;
		Double hardMaxHumdity = 10.0;
		Double softMinTemperature = 0.0;
		Double softMaxTemperature = 10.0;
		Integer softTemperatureMonitoringPeriod = 5;

		temperaturePredictionDeployment.addNewPerishableGoodsMonitoringEPN(connection, user, epnID,
				temperatureSensorID, humiditySensorID, gatewayID,
				hardMinTemperature, hardMaxTemperature, softMinTemperature,
				softMaxTemperature, softTemperatureMonitoringPeriod,
				hardMinHumdity, hardMaxHumdity);
		DirectDeploymentConnection deploymentConnection = connection.connectDeployment(user);
		Set<String> queryNames = deploymentConnection.getQueryNames();
		assertNotNull(queryNames);
	}

	@Test
	public void testAddNewTimeSeriesPredictionEPN() throws UnknownHostException {
		String epnID = "time_series"; // queryID
		String gatewayID = "00:01:00:00:00:01"; // gatewayID
		String containerTemperatureSensorID1 = "s80_00_00_00_00_01";
		String containerTemperatureSensorID2 = "s80_00_00_00_00_02";
		String containerTemperatureSensorID3 = "s80_00_00_00_00_03";
		String humiditySensorID = "s80_00_00_00_02_01";
		String hvacActuatorID = "s80_00_00_00_05_01";
		String currentClampSensorID = "s80_00_00_00_03_01";
		String externalContainerTemperatureSensorID = "s80_00_00_00_04_01";
		String parcelTemperatureSensorID = "s80_00_00_00_01_05";
		int reportingWindowLength = 2; // range
		int reportingPeriod = 1; // slide

		temperaturePredictionDeployment.addNewTimeSeriesPredictionEPN(connection, user, 
				epnID, gatewayID, containerTemperatureSensorID1, containerTemperatureSensorID2,
				containerTemperatureSensorID3, humiditySensorID,
				hvacActuatorID, currentClampSensorID,
				externalContainerTemperatureSensorID,
				parcelTemperatureSensorID, reportingPeriod,
				reportingWindowLength);
		DirectDeploymentConnection deploymentConnection = connection.connectDeployment(user);
		Set<String> queryNames = deploymentConnection.getQueryNames();
		assertNotNull(queryNames);
	}

	@Test
	public void testDeleteEpn(){
		DirectDeploymentConnection deploymentConnection = connection.connectDeployment("ColdChainManagementPrediction");
		Set<String> queryNames_Before = deploymentConnection.getQueryNames();
		
		String epnID = "time_series";	
		temperaturePredictionDeployment.deleteEpn(connection, user, epnID);
		
		deploymentConnection = connection.connectDeployment("ColdChainManagementPrediction");
		Set<String> queryNames_After = deploymentConnection.getQueryNames();
	
		assertNotSame(queryNames_Before, queryNames_After);
	
	}
}
