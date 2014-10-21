package com.softwareag.queryinstantiation;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.softwareag.wep.api.client.DirectConnection;
import com.softwareag.wep.api.client.DirectConnectionProvider;
import com.softwareag.wep.api.client.DirectDeploymentConnection;
import com.softwareag.wep.api.client.TcpIpEventServerAddress;

public class TemperaturePredictionDeployment implements CvoExecutionEngine {

	public static void main(String[] args) throws UnknownHostException {

		// -------------------------------------------------------------------------
		// SetUp
		// -------------------------------------------------------------------------
		TemperaturePredictionDeployment test = new TemperaturePredictionDeployment();

		String user = "ColdChainManagementPrediction";
		String password = "";
		String eventServerHost = "localhost";
		int eventServerPort = 7867;
		DirectConnection conn = test.startEngine(user, password,
				eventServerHost, eventServerPort);
		String epnID = ""; // queryID
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
		String temperatureSensorID = "s80_00_00_00_01_05";
		Double hardMinTemperature = 2.0;
		Double hardMaxTemperature = 8.0;
		Double hardMinHumdity = 2.0;
		Double hardMaxHumdity = 10.0;
		Double softMinTemperature = 0.0;
		Double softMaxTemperature = 10.0;
		Integer softTemperatureMonitoringPeriod = 5;

		// -------------------------------------------------------------------------
		// Predicition initialization
		// -------------------------------------------------------------------------

		epnID = "Prediction";
		test.addNewTimeSeriesPredictionEPN(conn, user, epnID, gatewayID,
				containerTemperatureSensorID1, containerTemperatureSensorID2,
				containerTemperatureSensorID3, humiditySensorID,
				hvacActuatorID, currentClampSensorID,
				externalContainerTemperatureSensorID,
				parcelTemperatureSensorID, reportingPeriod,
				reportingWindowLength);
		// -------------------------------------------------------------------------
		// Non Perishable initialization
		// -------------------------------------------------------------------------

		epnID = "NonPerishable";
		test.addNewNonPerishableGoodsMonitoringEPN(conn, user, epnID,
				temperatureSensorID, humiditySensorID, gatewayID,
				hardMinTemperature, hardMaxTemperature, hardMinHumdity,
				hardMaxHumdity);

		// -------------------------------------------------------------------------
		// Perishable initialization
		// -------------------------------------------------------------------------

		epnID = "Perishable";
		test.addNewPerishableGoodsMonitoringEPN(conn, user, epnID,
				temperatureSensorID, humiditySensorID, gatewayID,
				hardMinTemperature, hardMaxTemperature, softMinTemperature,
				softMaxTemperature, softTemperatureMonitoringPeriod,
				hardMinHumdity, hardMaxHumdity);
	}

	@Override
	public DirectConnection startEngine(String user, String password,
			String eventServerHost, int eventServerPort)
			throws UnknownHostException {
		DirectConnection connect = DirectConnectionProvider.connect(user,
				password, new TcpIpEventServerAddress(eventServerHost,
						eventServerPort));
		return connect;
	}

	@Override
	public void stopEngine(DirectConnection conn) {
		conn.close();
	}

	@Override
	public void addNewNonPerishableGoodsMonitoringEPN(DirectConnection conn,
			String user, String epnID, String temperatureSensorID,
			String humiditySensorID, String gatewayID,
			double hardMinTemperature, double hardMaxTemperature,
			double hardMinHumdity, double hardMaxHumdity)
			throws UnknownHostException {
		// Check if the Query already init
		DirectDeploymentConnection deployConn = conn.connectDeployment(user);
		Set<String> queryNames = deployConn.getQueryNames();
		if (queryNames.contains(user + "." + epnID)) {
			deleteEpn(conn, user, epnID);
		}
		// Initialize Queries
		String queryNonPerishable = "SELECT temperature,timestamp_, \"@temperature_sensor_id\","
				+ " \"@gateway_id\","
				+ "CASE WHEN temperature > $02 THEN 1"
				+ " WHEN temperature < $01 THEN -1"
				+ " ELSE 0 END AS limit_value"
				+ " FROM $00"
				+ " WHERE (temperature > $02"
				+ " OR temperature < $01)"
				+ " AND \"@gateway_id\" = '" + gatewayID + "';";

		QueryInstantiation queryPosNonPerishable = new QueryInstantiation();
		ArrayList<String> valuesPositionNonPerishable = new ArrayList<String>(
				Arrays.asList(temperatureSensorID, "" + hardMinTemperature, ""
						+ hardMaxTemperature));
		String queryContentPosNonPerishable = queryPosNonPerishable
				.prepareCVO_Template(queryNonPerishable,
						valuesPositionNonPerishable);
		queryPosNonPerishable.initAndStart(epnID, queryContentPosNonPerishable,
				"iCore::Temperature::NonPerishableGoods::" + epnID,
				"SmartBusiness/TemperatureOutOfRangeWarning");
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
	public void addNewPerishableGoodsMonitoringEPN(DirectConnection conn,
			String user, String epnID, String temperatureSensorID,
			String humiditySensorID, String gatewayID,
			double hardMinTemperature, double hardMaxTemperature,
			double softMinTemperature, double softMaxTemperature,
			int softTemperatureMonitoringPeriod, double hardMinHumdity,
			double hardMaxHumdity) throws UnknownHostException {

		// -------------------------------------------------------
		// Check if the Query already init
		// --------------------------------------------------------
		DirectDeploymentConnection deployConn = conn.connectDeployment(user);
		Set<String> queryNames = deployConn.getQueryNames();

		if (queryNames.contains(user + "." + epnID)) {
			deleteEpn(conn, user, epnID);
		}

		// --------------------------------------------------------
		// Initialize Queries
		// --------------------------------------------------------
		String querySoftPerishableGood = "SELECT temperature, timestamp_,"
				+ " \"@temperature_sensor_id\", \"@gateway_id\","
				+ " CASE WHEN temperature > $01 THEN 1"
				+ " WHEN temperature < $02 THEN -1"
				+ "	ELSE 0 END AS limit_value" + " FROM $00"
				+ " WHERE (temperature > $03" + " OR temperature < $04)"
				+ " AND \"@gateway_id\"= '$05';";

		QueryInstantiation queryPosSoftPerishableGood = new QueryInstantiation();
		ArrayList<String> valuesPositionSoftPerishableGood = new ArrayList<String>(
				Arrays.asList(temperatureSensorID, "" + hardMaxTemperature, ""
						+ hardMinTemperature, "" + softMaxTemperature, ""
						+ softMinTemperature, "" + gatewayID));
		String queryContentSoftPerishableGood = queryPosSoftPerishableGood
				.prepareCVO_Template(querySoftPerishableGood,
						valuesPositionSoftPerishableGood);
		queryPosSoftPerishableGood.initAndStart(epnID + "_SoftPerishable",
				queryContentSoftPerishableGood,
				"iCore::Temperature::PerishableGoods::" + epnID,
				"SmartBusiness/TemperatureOutOfRangeWarning");

		String queryPerishableGoodChronon = "SELECT * FROM $00 MATCHING ("
				+ "	MEASURES temp DOUBLE, time_ STRING,"
				+ "	temp_sensorID STRING, gatewayID STRING"
				+ "	PATTERN 'a+' DURATION $01 SECONDS"
				+ "	DEFINE  a AS temperature > $02 OR temperature < $03"
				+ "	DO temp = temperature, time_ = CAST(timestamp_ AS STRING),"
				+ " temp_sensorID = \"@temperature_sensor_id\","
				+ " gatewayID = \"@gateway_id\");";
		QueryInstantiation queryPosPerishableGoodChronon = new QueryInstantiation();
		ArrayList<String> valuesPositionPerishableGoodChronon = new ArrayList<String>(
				Arrays.asList(temperatureSensorID, ""
						+ softTemperatureMonitoringPeriod, ""
						+ hardMaxTemperature, "" + hardMinTemperature));
		String queryContentPerishableGoodChronon = queryPosPerishableGoodChronon
				.prepareCVO_Template(queryPerishableGoodChronon,
						valuesPositionPerishableGoodChronon);
		queryPosPerishableGoodChronon.initAndStart(epnID
				+ "_PeriodPerishableChronon",
				queryContentPerishableGoodChronon,
				"Event::SmartBusiness::CEP_PerishableGoods::" + epnID
						+ "_PeriodPerishableChronon",
				"SmartBusiness/Q_PerishableGood");

		String queryPerishableGood = "SELECT temp AS temperature,"
				+ " CAST(time_ AS TIMESTAMP) AS timestamp_,"
				+ " temp_sensorID AS temperature_sensor_id,"
				+ " gatewayID AS gateway_id," + " CASE WHEN temp > $01 THEN 1"
				+ "	WHEN temp < $02 THEN -1" + "	ELSE 0 END AS limit_value"
				+ " FROM $00" + " WHERE gatewayID = '$03';";

		QueryInstantiation queryPosPerishableGood = new QueryInstantiation();
		ArrayList<String> valuesPositionPerishableGood = new ArrayList<String>(
				Arrays.asList(epnID + "_PeriodPerishableChronon", ""
						+ hardMaxTemperature, "" + hardMinTemperature,
						gatewayID));
		String queryContentPerishableGood = queryPosPerishableGood
				.prepareCVO_Template(queryPerishableGood,
						valuesPositionPerishableGood);
		queryPosPerishableGood.initAndStart(epnID, queryContentPerishableGood,
				"iCore::Temperature::PerishableGoods::" + epnID,
				"SmartBusiness/TemperatureOutOfRangeWarning");

	}

	@Override
	public void addNewTimeSeriesPredictionEPN(DirectConnection conn,
			String user, String epnID, String truckID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			String parcelTemperatureSensorID, int reportingPeriod,
			int reportingWindowLength) throws UnknownHostException {

		// -------------------------------------------------------
		// Check if the Query already init
		// --------------------------------------------------------
		DirectDeploymentConnection deployConn = conn.connectDeployment(user);
		Set<String> queryNames = deployConn.getQueryNames();

		if (queryNames.contains(user + "." + epnID)) {
			deleteEpn(conn, user, epnID);
		}

		// --------------------------------------------------------
		// Initialize Queries
		// --------------------------------------------------------
		// Query S01_Temperatures
		String queryTemperature = "SELECT UDA_lastTemperature(temperature) as lastTemperature,"
				+ " UDA_lastTimestamp(CAST(timestamp_ AS STRING)) as lastTimestamp,"
				+ " \"@gateway_id\" as gatewayID,"
				+ " \"@temperature_sensor_id\" as tempSensorID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '"
				+ truckID
				+ "'"
				+ " GROUP BY \"@gateway_id\", \"@temperature_sensor_id\"" + ";";

		// Query S02_Humidity
		String queryHumidity = "SELECT UDA_lastTemperature(temperature) as lastTemperature,"
				+ " UDA_lastHumidity(humidity) as lastHumidity,"
				+ " \"@humidity_sensor_id\" as humidityID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '"
				+ truckID
				+ "'"
				+ " GROUP BY \"@gateway_id\",\"@humidity_sensor_id\"" + ";";

		// Query S03_CurrentClamp
		String queryCurrentClamp = "SELECT UDA_lastClampValue(value) as lastValue,"
				+ " \"@sensor_id\" as sensorID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '"
				+ truckID
				+ "'"
				+ " GROUP BY \"@gateway_id\",\"@sensor_id\"" + ";";

		// Query S05_HVACActuatorStatus
		String queryHVACActuatorStatus = "SELECT UDA_lastHVACState(active) as lastActive,"
				+ " UDA_lastHVACValue(hvacStateValue) as lastHvac,"
				+ " \"@hvacActuatorID\" as hvacID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '"
				+ truckID
				+ "'"
				+ " GROUP BY \"@gateway_id\",\"@hvacActuatorID\"" + ";";

		// Queries - s800000000001
		QueryInstantiation queryPos001 = new QueryInstantiation();
		ArrayList<String> valuesPosition001 = new ArrayList<String>(
				Arrays.asList(containerTemperatureSensorID1, ""
						+ reportingPeriod + " MINUTES", "" + reportingPeriod
						+ " MINUTES")); // (TempSensor, RANGE & SLIDE)
		String queryContentPos001 = queryPos001.prepareCVO_Template(
				queryTemperature, valuesPosition001);

		queryPos001.initAndStart(epnID + "_containerTemperatureSensorID1",
				queryContentPos001,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_containerTemperatureSensorID1",
				"SmartBusiness/Q01_Temperature");

		// Queries - s800000000002
		QueryInstantiation queryPos002 = new QueryInstantiation();
		ArrayList<String> valuesPosition002 = new ArrayList<String>(
				Arrays.asList(containerTemperatureSensorID2, ""
						+ reportingPeriod + " MINUTES", "" + reportingPeriod
						+ " MINUTES")); // (TempSensor, RANGE & SLIDE)
		String queryContentPos002 = queryPos002.prepareCVO_Template(
				queryTemperature, valuesPosition002);

		queryPos002.initAndStart(epnID + "_containerTemperatureSensorID2",
				queryContentPos002,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_containerTemperatureSensorID2",
				"SmartBusiness/Q01_Temperature");

		// Queries - s800000000003
		QueryInstantiation queryPos003 = new QueryInstantiation();
		ArrayList<String> valuesPosition003 = new ArrayList<String>(
				Arrays.asList(containerTemperatureSensorID3, ""
						+ reportingPeriod + " MINUTES", "" + reportingPeriod
						+ " MINUTES")); // (TempSensor, RANGE & SLIDE)
		String queryContentPos003 = queryPos003.prepareCVO_Template(
				queryTemperature, valuesPosition003);

		queryPos003.initAndStart(epnID + "_containerTemperatureSensorID3",
				queryContentPos003,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_containerTemperatureSensorID3",
				"SmartBusiness/Q01_Temperature");

		// Queries - s800000000104
		QueryInstantiation queryPos104 = new QueryInstantiation();
		ArrayList<String> valuesPosition104 = new ArrayList<String>(
				Arrays.asList(parcelTemperatureSensorID, "" + reportingPeriod
						+ " MINUTES", "" + reportingPeriod + " MINUTES")); // (TempSensor,
																			// RANGE
																			// &
																			// SLIDE)
		String queryContentPos104 = queryPos104.prepareCVO_Template(
				queryTemperature, valuesPosition104);

		queryPos104.initAndStart(epnID + "_parcelTemperatureSensorID",
				queryContentPos104,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_parcelTemperatureSensorID",
				"SmartBusiness/Q01_Temperature");

		// Queries - s800000000401
		QueryInstantiation queryPos401 = new QueryInstantiation();
		ArrayList<String> valuesPosition401 = new ArrayList<String>(
				Arrays.asList(externalContainerTemperatureSensorID, ""
						+ reportingPeriod + " MINUTES", "" + reportingPeriod
						+ " MINUTES")); // (TempSensor, RANGE & SLIDE)
		String queryContentPos401 = queryPos401.prepareCVO_Template(
				queryTemperature, valuesPosition401);

		queryPos401.initAndStart(epnID
				+ "_externalContainerTemperatureSensorID", queryContentPos401,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_externalContainerTemperatureSensorID",
				"SmartBusiness/Q01_Temperature");

		// Queries - s800000000201
		QueryInstantiation queryPos201 = new QueryInstantiation();
		ArrayList<String> valuesPosition201 = new ArrayList<String>(
				Arrays.asList(humiditySensorID, "" + reportingPeriod
						+ " MINUTES", "" + reportingPeriod + " MINUTES")); // (TempSensor,
																			// RANGE
																			// &
																			// SLIDE)
		String queryContentPos201 = queryPos201.prepareCVO_Template(
				queryHumidity, valuesPosition201);

		queryPos201.initAndStart(epnID + "_humiditySensorID",
				queryContentPos201,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_humiditySensorID", "SmartBusiness/Q02_Humidity");

		// Queries - s800000000301
		QueryInstantiation queryPos301 = new QueryInstantiation();
		ArrayList<String> valuesPosition301 = new ArrayList<String>(
				Arrays.asList(currentClampSensorID, "" + reportingPeriod
						+ " MINUTES", "" + reportingPeriod + " MINUTES")); // (TempSensor,
																			// RANGE
																			// &
																			// SLIDE)
		String queryContentPos301 = queryPos301.prepareCVO_Template(
				queryCurrentClamp, valuesPosition301);

		queryPos301.initAndStart(epnID + "_currentClampSensorID",
				queryContentPos301,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_currentClampSensorID",
				"SmartBusiness/Q03_CurrentClamp");

		// Queries - s800000000501
		QueryInstantiation queryPos501 = new QueryInstantiation();
		ArrayList<String> valuesPosition501 = new ArrayList<String>(
				Arrays.asList(hvacActuatorID,
						"" + reportingPeriod + " MINUTES", "" + reportingPeriod
								+ " MINUTES")); // (TempSensor, RANGE & SLIDE)
		String queryContentPos501 = queryPos501.prepareCVO_Template(
				queryHVACActuatorStatus, valuesPosition501);

		queryPos501.initAndStart(epnID + "_hvacActuatorID", queryContentPos501,
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID
						+ "_hvacActuatorID",
				"SmartBusiness/Q05_HVACActuatorStatus");

		// Query S01_Temperatures to Chronon
		String queryTemperatureChronon = "SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES temp DOUBLE,"
				+ " time_ STRING,"
				+ " gateway STRING,"
				+ " tempId STRING"
				+ " PATTERN 'a'"
				+ " DEFINE a DO temp = lastTemperature, time_ = lastTimestamp, gateway = gatewayID, tempId = tempSensorID"
				+ ");";

		// Query S02_Humidity to Chronon
		String queryHumidityChronon = "SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES temp DOUBLE,"
				+ " hum DOUBLE,"
				+ " humId STRING"
				+ " PATTERN 'a'"
				+ " DEFINE a DO temp = lastTemperature, hum = lastHumidity, humId = humidityID"
				+ ");";

		// Query S03_CurrentClamp to Chronon
		String queryCurrentClampChronon = "SELECT * FROM $00" + " MATCHING ("
				+ "MEASURES value_ DOUBLE," + "	sensorId_ STRING"
				+ "	PATTERN 'a'"
				+ "	DEFINE a DO value_ = lastValue, sensorId_ = sensorID"
				+ ");";

		// Query S05_HVACActuatorStatus Chronon
		String queryHVACActuatorStatusChronon = "SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES active_ STRING,"
				+ "	hvac DOUBLE,"
				+ "	hvacId_ STRING"
				+ "	PATTERN 'a'"
				+ "	DEFINE a DO active_ = CAST(lastActive AS STRING), hvac = lastHvac, hvacId_ = hvacID"
				+ ");";

		// Queries - s800000000001 Chronon
		QueryInstantiation queryPos001Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition001Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_containerTemperatureSensorID1")); // (Stream)
		String queryContentPos001Chronon = queryPos001Chronon
				.prepareCVO_Template(queryTemperatureChronon,
						valuesPosition001Chronon);

		queryPos001Chronon.initAndStart(epnID
				+ "_containerTemperatureSensorID1_Chronon",
				queryContentPos001Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_containerTemperatureSensorID1_Chronon",
				"SmartBusiness/Q01_Temperature_Chronon");

		// Queries - s800000000002 Chronon
		QueryInstantiation queryPos002Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition002Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_containerTemperatureSensorID2")); // (Stream)
		String queryContentPos002Chronon = queryPos002Chronon
				.prepareCVO_Template(queryTemperatureChronon,
						valuesPosition002Chronon);

		queryPos002Chronon.initAndStart(epnID
				+ "_containerTemperatureSensorID2_Chronon",
				queryContentPos002Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_containerTemperatureSensorID2_Chronon",
				"SmartBusiness/Q01_Temperature_Chronon");

		// Queries - s800000000003 Chronon
		QueryInstantiation queryPos003Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition003Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_containerTemperatureSensorID3")); // (Stream)
		String queryContentPos003Chronon = queryPos003Chronon
				.prepareCVO_Template(queryTemperatureChronon,
						valuesPosition003Chronon);

		queryPos003Chronon.initAndStart(epnID
				+ "_containerTemperatureSensorID3_Chronon",
				queryContentPos003Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_containerTemperatureSensorID3_Chronon",
				"SmartBusiness/Q01_Temperature_Chronon");

		// Queries - s800000000104 Chronon
		QueryInstantiation queryPos104Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition104Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_parcelTemperatureSensorID")); // (Stream)
		String queryContentPos104Chronon = queryPos104Chronon
				.prepareCVO_Template(queryTemperatureChronon,
						valuesPosition104Chronon);

		queryPos104Chronon.initAndStart(epnID
				+ "_parcelTemperatureSensorID_Chronon",
				queryContentPos104Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_parcelTemperatureSensorID_Chronon",
				"SmartBusiness/Q01_Temperature_Chronon");

		// Queries - s800000000401 Chronon
		QueryInstantiation queryPos401Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition401Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_externalContainerTemperatureSensorID")); // (StreamE)
		String queryContentPos401Chronon = queryPos401Chronon
				.prepareCVO_Template(queryTemperatureChronon,
						valuesPosition401Chronon);

		queryPos401Chronon.initAndStart(epnID
				+ "_externalContainerTemperatureSensorID_Chronon",
				queryContentPos401Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID
						+ "_externalContainerTemperatureSensorID_Chronon",
				"SmartBusiness/Q01_Temperature_Chronon");

		// Queries - s800000000201 Chronon
		QueryInstantiation queryPos201Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition201Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_humiditySensorID")); // (Stream)
		String queryContentPos201Chronon = queryPos201Chronon
				.prepareCVO_Template(queryHumidityChronon,
						valuesPosition201Chronon);

		queryPos201Chronon.initAndStart(epnID + "_humiditySensorID_Chronon",
				queryContentPos201Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_humiditySensorID_Chronon",
				"SmartBusiness/Q02_Humidity_Chronon");

		// Queries - s800000000301 Chronon
		QueryInstantiation queryPos301Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition301Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_currentClampSensorID")); // (Stream)
		String queryContentPos301Chronon = queryPos301Chronon
				.prepareCVO_Template(queryCurrentClampChronon,
						valuesPosition301Chronon);

		queryPos301Chronon.initAndStart(
				epnID + "_currentClampSensorID_Chronon",
				queryContentPos301Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_currentClampSensorID_Chronon",
				"SmartBusiness/Q03_CurrentClamp_Chronon");

		// Queries - s800000000501 Chronon
		QueryInstantiation queryPos501Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition501Chronon = new ArrayList<String>(
				Arrays.asList(epnID + "_hvacActuatorID")); // (Stream)
		String queryContentPos501Chronon = queryPos501Chronon
				.prepareCVO_Template(queryHVACActuatorStatusChronon,
						valuesPosition501Chronon);

		queryPos501Chronon.initAndStart(epnID + "_hvacActuatorID_Chronon",
				queryContentPos501Chronon,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_hvacActuatorID_Chronon",
				"SmartBusiness/Q05_HVACActuatorStatus_Chronon");

		// Query Join Chronons
		String queryJoin = "SELECT $00.gateway as gatewayID_01,"
				+ "	$00.temp as temperature_01,"
				+ "	$00.tempId as temperature_sensorID_01,"
				+ "	$01.temp as temperature_02,"
				+ "	$01.tempId as temperature_sensorID_02,"
				+ "	$02.temp as temperature_03,"
				+ "	$02.tempId as temperature_sensorID_03,"
				+ "	$05.hum as humidity_201," + "	$05.temp as temperature_201,"
				+ "	$05.humId as humidity_sensorID_201,"
				+ "	$07.active_ as active_501,"
				+ " $07.hvac as hvacStateValue_501,"
				+ " $07.hvacId_ as hvacActuatorID_501,"
				+ "	$06.value_ as value_301,"
				+ " $06.sensorId_ as sensorID_301,"
				+ "	$04.temp as temperature_401,"
				+ "	$04.tempId as temperature_sensorID_401,"
				+ " $03.temp as temperature_104,"
				+ "	$03.tempId as temperature_sensorID_104,"
				+ "	$03.time_ as timestamp_104" + " FROM $00" + " JOIN $01"
				+ "	JOIN $02" + " JOIN $05" + "	JOIN $07" + " JOIN $06"
				+ "	JOIN $04" + " JOIN $03; ";

		QueryInstantiation queryPosJoin = new QueryInstantiation();
		ArrayList<String> valuesPositionJoin = new ArrayList<String>(
				Arrays.asList(
						epnID + "_containerTemperatureSensorID1_Chronon",
						epnID + "_containerTemperatureSensorID2_Chronon",
						epnID + "_containerTemperatureSensorID3_Chronon",
						epnID + "_parcelTemperatureSensorID_Chronon",
						epnID + "_externalContainerTemperatureSensorID_Chronon",
						epnID + "_humiditySensorID_Chronon", epnID
								+ "_currentClampSensorID_Chronon", epnID
								+ "_hvacActuatorID_Chronon")); // (Streams
																// to
																// join)
		String queryContentPosJoin = queryPosJoin.prepareCVO_Template(
				queryJoin, valuesPositionJoin);

		queryPosJoin.initAndStart(epnID + "_Join", queryContentPosJoin,
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::"
						+ epnID + "_Join", "SmartBusiness/Q_Join");

		String queryJoinAggr = "SELECT UDA_getiCoreForecast( UDF_sensorToDoc("
				+ " gatewayID_01," + " temperature_01,"
				+ " temperature_sensorID_01," + "	temperature_02,"
				+ "	temperature_sensorID_02," + " temperature_03,"
				+ "	temperature_sensorID_03," + "	humidity_201,"
				+ " temperature_201," + " humidity_sensorID_201,"
				+ "	active_501," + "	hvacStateValue_501,"
				+ "	hvacActuatorID_501," + "	value_301," + "	sensorID_301,"
				+ "	temperature_401," + "	temperature_sensorID_401,"
				+ "	temperature_104," + "	temperature_sensorID_104,"
				+ "	timestamp_104)) as predictionJDOM"
				+ " FROM $00 WINDOW(RANGE $01 SLIDE $02);";

		QueryInstantiation queryPosAggr = new QueryInstantiation();
		ArrayList<String> valuesPositionAggr = new ArrayList<String>(
				Arrays.asList(epnID + "_Join", "" + reportingWindowLength
						+ " MINUTES", "" + reportingPeriod + " MINUTES")); // (Stream,
																			// RANGE,
																			// SLIDE)
		String queryContentPosAggr = queryPosAggr.prepareCVO_Template(
				queryJoinAggr, valuesPositionAggr);

		queryPosAggr.initAndStart(epnID + "_JoinAggr", queryContentPosAggr,
				"Event::SmartBusiness::CEP_Sensors_Predictions::" + epnID
						+ "_JoinAggr", "SmartBusiness/Q_Prediction");

		String queryXPathPrediction = "SELECT"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[1]', predictionJDOM) as gateway_id,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[2]', predictionJDOM) as Predicted_temp_min,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[3]', predictionJDOM) as Predicted_temp_one_hour,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[4]', predictionJDOM) as Predicted_temp_two_hours,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[5]', predictionJDOM) as timestamp_,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[6]', predictionJDOM) as temperature_sensor_ID"
				+ " FROM $00;";

		QueryInstantiation queryPosXpath = new QueryInstantiation();
		ArrayList<String> valuesPositionXpath = new ArrayList<String>(
				Arrays.asList(epnID + "_JoinAggr")); // (Stream)
		String queryContentPosXpath = queryPosAggr.prepareCVO_Template(
				queryXPathPrediction, valuesPositionXpath);

		queryPosXpath.initAndStart(epnID + "_XPathPrediction",
				queryContentPosXpath,
				"Event::SmartBusiness::CEP_Sensors_Predictions::" + epnID
						+ "_XPathPrediction", "SmartBusiness/Q_XPath");

		String queryTemperaturePrediction = "SELECT gateway_id,"
				+ "	CAST(Predicted_temp_min AS DOUBLE) as Predicted_temp_min,"
				+ " CAST(Predicted_temp_one_hour AS DOUBLE) as Predicted_temp_one_hour,"
				+ "	CAST(Predicted_temp_two_hours AS DOUBLE) as Predicted_temp_two_hours,"
				+ "	CAST(UDF_transformTime(timestamp_) AS TIMESTAMP) as timestamp_,"
				+ "	temperature_sensor_ID" + " FROM $00;";

		QueryInstantiation queryPosPrediction = new QueryInstantiation();
		ArrayList<String> valuesPositionPrediction = new ArrayList<String>(
				Arrays.asList(epnID + "_XPathPrediction")); // (Stream)
		String queryContentPosPrediction = queryPosPrediction
				.prepareCVO_Template(queryTemperaturePrediction,
						valuesPositionPrediction);

		queryPosPrediction.initAndStart(epnID, queryContentPosPrediction,
				"iCore::Temperature::Predictions::" + epnID,
				"SmartBusiness/TemperaturePrediction");

	}

	@Override
	public void deleteEpn(DirectConnection conn, String user, String epnID) {
		DirectDeploymentConnection deployConn = conn.connectDeployment(user);
		Set<String> queryNames = deployConn.getQueryNames();

		if (queryNames.contains(user + "." + epnID)) {
			if (queryNames.contains(user + "." + epnID + "_XPathPrediction")) {
				deployConn.removeQuery(epnID);
				deployConn.removeQuery(epnID + "_XPathPrediction");
				deployConn.removeQuery(epnID + "_JoinAggr");
				deployConn.removeQuery(epnID + "_Join");
				deployConn.removeQuery(epnID + "_hvacActuatorID_Chronon");
				deployConn.removeQuery(epnID + "_currentClampSensorID_Chronon");
				deployConn.removeQuery(epnID + "_humiditySensorID_Chronon");
				deployConn.removeQuery(epnID
						+ "_externalContainerTemperatureSensorID_Chronon");
				deployConn.removeQuery(epnID
						+ "_parcelTemperatureSensorID_Chronon");
				deployConn.removeQuery(epnID
						+ "_containerTemperatureSensorID3_Chronon");
				deployConn.removeQuery(epnID
						+ "_containerTemperatureSensorID2_Chronon");
				deployConn.removeQuery(epnID
						+ "_containerTemperatureSensorID1_Chronon");
				deployConn.removeQuery(epnID + "_hvacActuatorID");
				deployConn.removeQuery(epnID + "_currentClampSensorID");
				deployConn.removeQuery(epnID + "_humiditySensorID");
				deployConn.removeQuery(epnID
						+ "_externalContainerTemperatureSensorID");
				deployConn.removeQuery(epnID + "_parcelTemperatureSensorID");
				deployConn
						.removeQuery(epnID + "_containerTemperatureSensorID3");
				deployConn
						.removeQuery(epnID + "_containerTemperatureSensorID2");
				deployConn
						.removeQuery(epnID + "_containerTemperatureSensorID1");
			} else {
				if (queryNames.contains(user + "." + epnID
						+ "_PeriodPerishableChronon")) {

					deployConn.removeQuery(epnID);
					deployConn.removeQuery(epnID + "_PeriodPerishableChronon");
					deployConn.removeQuery(epnID + "_SoftPerishable");
				}else{
					deployConn.removeQuery(epnID);
				}
			}

		} else
			System.err.println("The epnID \"" + epnID + "\" doesn't exist.");
	}

}
