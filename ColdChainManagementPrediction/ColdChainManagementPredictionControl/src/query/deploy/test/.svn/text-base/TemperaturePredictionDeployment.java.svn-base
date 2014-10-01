package query.deploy.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemperaturePredictionDeployment implements CvoExecutionEngine{
	
	public static void main(String[] args) throws UnknownHostException {
		
		TemperaturePredictionDeployment test = new TemperaturePredictionDeployment();
		String epnID = "epnID"; //querID
		String truckID = "00:01:00:00:00:01"; //gatewayID
		String containerTemperatureSensorID1 = "s800000000001";
		String containerTemperatureSensorID2 = "s800000000002";
		String containerTemperatureSensorID3 = "s800000000003";
		String humiditySensorID = "s800000000201";
		String hvacActuatorID = "s800000000501";
		String currentClampSensorID = "s800000000301";
		String externalContainerTemperatureSensorID = "s800000000401";
		String parcelTemperatureSensorID = "s800000000104";
		int reportingWindowLength = 2; //range
		int reportingPeriod = 1;	//slide
		
		
		List<String> queryList = new ArrayList<String>();
		queryList =	test.addNewTimeSeriesPredictionEPN(epnID, truckID, containerTemperatureSensorID1, containerTemperatureSensorID2, 
				containerTemperatureSensorID3, humiditySensorID, hvacActuatorID, currentClampSensorID,
				externalContainerTemperatureSensorID, parcelTemperatureSensorID, reportingPeriod, reportingWindowLength);
		
		//Query S01_Temperatures
		String queryTemperature = 
				"SELECT UDA_lastTemperature(temperature) as lastTemperature,"
				+	" UDA_lastTimestamp(CAST(timestamp_ AS STRING)) as lastTimestamp,"
				+ 	" \"@gateway_id\" as gatewayID,"
				+ 	" \"@temperature_sensor_id\" as tempSensorID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '" + truckID + "'"
				+ " GROUP BY \"@gateway_id\", \"@temperature_sensor_id\""
				+ ";"	;
		
		//Query S02_Humidity
		String queryHumidity = 
				"SELECT UDA_lastTemperature(temperature) as lastTemperature,"
				+ 	" UDA_lastHumidity(humidity) as lastHumidity,"
				+	" \"@humidity_sensor_id\" as humidityID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '" + truckID + "'"
				+ " GROUP BY \"@gateway_id\",\"@humidity_sensor_id\""
				+ ";"	;
		
		//Query S03_CurrentClamp
		String queryCurrentClamp = 
				"SELECT UDA_lastClampValue(value) as lastValue,"
				+	" \"@sensor_id\" as sensorID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '" + truckID + "'"
				+ " GROUP BY \"@gateway_id\",\"@sensor_id\""
				+  ";"	;
	
		//Query S05_HVACActuatorStatus
		String queryHVACActuatorStatus = 
				"SELECT UDA_lastHVACState(active) as lastActive,"
				+	" UDA_lastHVACValue(hvacStateValue) as lastHvac,"
				+	" \"@hvacActuatorID\" as hvacID"
				+ " FROM $00"
				+ " WINDOW (RANGE $01 SLIDE $02"
				+ " RELATIVE TO '2010-12-31T01:00:00.000 Europe/Berlin')"
				+ " WHERE \"@gateway_id\" = '" + truckID + "'"
				+ " GROUP BY \"@gateway_id\",\"@hvacActuatorID\""
				+ ";"	;

		//Queries - s800000000001
		QueryInstantiation queryPos001 = new QueryInstantiation();
		ArrayList<String> valuesPosition001 = new ArrayList<String>(Arrays.asList(containerTemperatureSensorID1, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos001 = queryPos001.prepareCVO_Template(queryTemperature, valuesPosition001);
		queryPos001.initAndStart(epnID + "_" + containerTemperatureSensorID1, queryContentPos001, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + containerTemperatureSensorID1, "SmartBusiness/Q01_Temperature");
	
		//Queries - s800000000002
		QueryInstantiation queryPos002 = new QueryInstantiation();
		ArrayList<String> valuesPosition002 = new ArrayList<String>(Arrays.asList(containerTemperatureSensorID2, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos002 = queryPos002.prepareCVO_Template(queryTemperature, valuesPosition002);
		queryPos002.initAndStart(epnID + "_" + containerTemperatureSensorID2, queryContentPos002, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + containerTemperatureSensorID2, "SmartBusiness/Q01_Temperature");
		
		//Queries - s800000000003
		QueryInstantiation queryPos003 = new QueryInstantiation();
		ArrayList<String> valuesPosition003 = new ArrayList<String>(Arrays.asList(containerTemperatureSensorID3, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos003 = queryPos003.prepareCVO_Template(queryTemperature, valuesPosition003);
		queryPos003.initAndStart(epnID + "_" + containerTemperatureSensorID3, queryContentPos003, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + containerTemperatureSensorID3, "SmartBusiness/Q01_Temperature");
		
		//Queries - s800000000104
		QueryInstantiation queryPos104 = new QueryInstantiation();
		ArrayList<String> valuesPosition104 = new ArrayList<String>(Arrays.asList(parcelTemperatureSensorID, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos104 = queryPos104.prepareCVO_Template(queryTemperature, valuesPosition104);
		queryPos104.initAndStart(epnID + "_" + parcelTemperatureSensorID, queryContentPos104, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + parcelTemperatureSensorID, "SmartBusiness/Q01_Temperature");
	
		//Queries - s800000000401
		QueryInstantiation queryPos401 = new QueryInstantiation();
		ArrayList<String> valuesPosition401 = new ArrayList<String>(Arrays.asList(externalContainerTemperatureSensorID, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos401 = queryPos401.prepareCVO_Template(queryTemperature, valuesPosition401);
		queryPos401.initAndStart(epnID + "_" + externalContainerTemperatureSensorID, queryContentPos401, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + externalContainerTemperatureSensorID, "SmartBusiness/Q01_Temperature");
	
		//Queries - s800000000201
		QueryInstantiation queryPos201 = new QueryInstantiation();
		ArrayList<String> valuesPosition201 = new ArrayList<String>(Arrays.asList(humiditySensorID, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos201 = queryPos201.prepareCVO_Template(queryHumidity, valuesPosition201);
		queryPos201.initAndStart(epnID + "_" + humiditySensorID, queryContentPos201, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + humiditySensorID, "SmartBusiness/Q02_Humidity");
		
		//Queries - s800000000301
		QueryInstantiation queryPos301 = new QueryInstantiation();
		ArrayList<String> valuesPosition301 = new ArrayList<String>(Arrays.asList(currentClampSensorID, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos301 = queryPos301.prepareCVO_Template(queryCurrentClamp, valuesPosition301);
		queryPos301.initAndStart(epnID + "_" + currentClampSensorID, queryContentPos301, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + currentClampSensorID, "SmartBusiness/Q03_CurrentClamp");
		
		//Queries - s800000000501
		QueryInstantiation queryPos501 = new QueryInstantiation();
		ArrayList<String> valuesPosition501 = new ArrayList<String>(Arrays.asList(hvacActuatorID, "" + reportingPeriod + " MINUTES", "" + reportingPeriod + " MINUTES")); //(TempSensor, RANGE & SLIDE)
		String queryContentPos501 = queryPos501.prepareCVO_Template(queryHVACActuatorStatus, valuesPosition501);
		queryPos501.initAndStart(epnID + "_" + hvacActuatorID, queryContentPos501, 
				"Event::SmartBusiness::CEP_Sensors_LastValues::" + epnID + "_" + hvacActuatorID, "SmartBusiness/Q05_HVACActuatorStatus");
		
		//Query S01_Temperatures to Chronon
		String queryTemperatureChronon =
				"SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES temp DOUBLE,"
				+ " time_ STRING,"
				+ " gateway STRING,"
				+ " tempId STRING"
				+ " PATTERN 'a'"
				+ " DEFINE a DO temp = lastTemperature, time_ = lastTimestamp, gateway = gatewayID, tempId = tempSensorID"
				+ ");"		;
		
		//Query S02_Humidity to Chronon
		String queryHumidityChronon = 
				"SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES temp DOUBLE,"
				+ " hum DOUBLE,"
				+ " humId STRING"
				+ " PATTERN 'a'"
				+ " DEFINE a DO temp = lastTemperature, hum = lastHumidity, humId = humidityID"
				+ ");" 		;
		
		//Query S03_CurrentClamp to Chronon
		String queryCurrentClampChronon =
				"SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES value_ DOUBLE,"
				+ "	sensorId_ STRING"
				+ "	PATTERN 'a'"
				+ "	DEFINE a DO value_ = lastValue, sensorId_ = sensorID"
				+ ");"		;
		
		//Query S05_HVACActuatorStatus Chronon
		String queryHVACActuatorStatusChronon = 
				"SELECT * FROM $00"
				+ " MATCHING ("
				+ "MEASURES active_ STRING,"
				+ "	hvac DOUBLE,"
				+ "	hvacId_ STRING"
				+ "	PATTERN 'a'"
				+ "	DEFINE a DO active_ = CAST(lastActive AS STRING), hvac = lastHvac, hvacId_ = hvacID"
				+ ");"		;
		
		//Queries - s800000000001 Chronon
		QueryInstantiation queryPos001Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition001Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + containerTemperatureSensorID1)); //(Stream)
		String queryContentPos001Chronon = queryPos001Chronon.prepareCVO_Template(queryTemperatureChronon, valuesPosition001Chronon);
		queryPos001Chronon.initAndStart(epnID + "_" + containerTemperatureSensorID1 + "_Chronon", queryContentPos001Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + containerTemperatureSensorID1 + "_Chronon", "SmartBusiness/Q01_Temperature_Chronon");
		
		//Queries - s800000000002 Chronon
		QueryInstantiation queryPos002Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition002Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + containerTemperatureSensorID2)); //(Stream)
		String queryContentPos002Chronon = queryPos002Chronon.prepareCVO_Template(queryTemperatureChronon, valuesPosition002Chronon);
		queryPos002Chronon.initAndStart(epnID + "_" + containerTemperatureSensorID2 + "_Chronon", queryContentPos002Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + containerTemperatureSensorID2 + "_Chronon", "SmartBusiness/Q01_Temperature_Chronon");
		
		//Queries - s800000000003 Chronon
		QueryInstantiation queryPos003Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition003Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + containerTemperatureSensorID3)); //(Stream)
		String queryContentPos003Chronon = queryPos003Chronon.prepareCVO_Template(queryTemperatureChronon, valuesPosition003Chronon);
		queryPos003Chronon.initAndStart(epnID + "_" + containerTemperatureSensorID3 + "_Chronon", queryContentPos003Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + containerTemperatureSensorID3 + "_Chronon", "SmartBusiness/Q01_Temperature_Chronon");
		
		//Queries - s800000000104 Chronon
		QueryInstantiation queryPos104Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition104Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + parcelTemperatureSensorID)); //(Stream)
		String queryContentPos104Chronon = queryPos104Chronon.prepareCVO_Template(queryTemperatureChronon, valuesPosition104Chronon);
		queryPos104Chronon.initAndStart(epnID + "_" + parcelTemperatureSensorID + "_Chronon", queryContentPos104Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + parcelTemperatureSensorID + "_Chronon", "SmartBusiness/Q01_Temperature_Chronon");
		
		//Queries - s800000000401 Chronon
		QueryInstantiation queryPos401Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition401Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + externalContainerTemperatureSensorID)); //(StreamE)
		String queryContentPos401Chronon = queryPos401Chronon.prepareCVO_Template(queryTemperatureChronon, valuesPosition401Chronon);
		queryPos401Chronon.initAndStart(epnID + "_" + externalContainerTemperatureSensorID + "_Chronon", queryContentPos401Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + externalContainerTemperatureSensorID + "_Chronon", "SmartBusiness/Q01_Temperature_Chronon");
		
		//Queries - s800000000201 Chronon
		QueryInstantiation queryPos201Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition201Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + humiditySensorID)); //(Stream)
		String queryContentPos201Chronon = queryPos201Chronon.prepareCVO_Template(queryHumidityChronon, valuesPosition201Chronon);
		queryPos201Chronon.initAndStart(epnID + "_" + humiditySensorID + "_Chronon", queryContentPos201Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + humiditySensorID + "_Chronon", "SmartBusiness/Q02_Humidity_Chronon");
		
		//Queries - s800000000301 Chronon
		QueryInstantiation queryPos301Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition301Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + currentClampSensorID)); //(Stream)
		String queryContentPos301Chronon = queryPos301Chronon.prepareCVO_Template(queryCurrentClampChronon, valuesPosition301Chronon);
		queryPos301Chronon.initAndStart(epnID + "_" + currentClampSensorID + "_Chronon", queryContentPos301Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + currentClampSensorID + "_Chronon", "SmartBusiness/Q03_CurrentClamp_Chronon");
		
		//Queries - s800000000501 Chronon
		QueryInstantiation queryPos501Chronon = new QueryInstantiation();
		ArrayList<String> valuesPosition501Chronon = new ArrayList<String>(Arrays.asList(epnID + "_" + hvacActuatorID)); //(Stream)
		String queryContentPos501Chronon = queryPos501Chronon.prepareCVO_Template(queryHVACActuatorStatusChronon, valuesPosition501Chronon);
		queryPos501Chronon.initAndStart(epnID + "_" + hvacActuatorID + "_Chronon", queryContentPos501Chronon, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_" + hvacActuatorID + "_Chronon", "SmartBusiness/Q05_HVACActuatorStatus_Chronon");
		
		//Query Join Chronons
		String queryJoin =
				"SELECT $00.gateway as gatewayID_01,"
				+ "	$00.temp as temperature_01,"
				+ "	$00.tempId as temperature_sensorID_01,"
				+ "	$01.temp as temperature_02,"
				+ "	$01.tempId as temperature_sensorID_02,"
				+ "	$02.temp as temperature_03,"
				+ "	$02.tempId as temperature_sensorID_03,"
				+ "	$05.hum as humidity_201,"
				+ "	$05.temp as temperature_201,"
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
				+ "	$03.time_ as timestamp_104"
				+ " FROM $00"
				+ " JOIN $01"
				+ "	JOIN $02"
				+ " JOIN $05"
				+ "	JOIN $07"
				+ " JOIN $06"
				+ "	JOIN $04"
				+ " JOIN $03; ";
		
		QueryInstantiation queryPosJoin = new QueryInstantiation();
		ArrayList<String> valuesPositionJoin = new ArrayList<String>(Arrays.asList(
				epnID + "_" + containerTemperatureSensorID1 + "_Chronon", epnID + "_" + containerTemperatureSensorID2 + "_Chronon", epnID + "_" + containerTemperatureSensorID3 + "_Chronon", epnID + "_" + parcelTemperatureSensorID + "_Chronon",
				epnID + "_" + externalContainerTemperatureSensorID + "_Chronon", epnID + "_" + humiditySensorID + "_Chronon", epnID + "_" + currentClampSensorID + "_Chronon", epnID + "_" + hvacActuatorID + "_Chronon")); //(Streams to join)
		String queryContentPosJoin = queryPosJoin.prepareCVO_Template(queryJoin, valuesPositionJoin);
		queryPosJoin.initAndStart(epnID + "_Join", queryContentPosJoin, 
				"Event::SmartBusiness::CEP_Sensors_LastValues_Chronon::" + epnID + "_Join", "SmartBusiness/Q_Join");
		
		String queryJoinAggr =
				"SELECT UDA_getiCoreForecast( UDF_sensorToDoc("
				+ " gatewayID_01,"
				+ " temperature_01,"
				+ " temperature_sensorID_01,"
				+ "	temperature_02,"
				+ "	temperature_sensorID_02,"
				+ " temperature_03,"
				+ "	temperature_sensorID_03,"
				+ "	humidity_201,"
				+ " temperature_201,"
				+ " humidity_sensorID_201,"
				+ "	active_501,"
				+ "	hvacStateValue_501,"
				+ "	hvacActuatorID_501,"
				+ "	value_301,"
				+ "	sensorID_301,"
				+ "	temperature_401,"
				+ "	temperature_sensorID_401,"
				+ "	temperature_104,"
				+ "	temperature_sensorID_104,"
				+ "	timestamp_104)) as predictionJDOM"
				+ " FROM $00 WINDOW(RANGE $01 SLIDE $02);"		;
		
		QueryInstantiation queryPosAggr = new QueryInstantiation();
		ArrayList<String> valuesPositionAggr = new ArrayList<String>(Arrays.asList(epnID + "_Join", "" + reportingWindowLength + " MINUTES", "" + reportingPeriod + " MINUTES")); //(Stream, RANGE, SLIDE)
		String queryContentPosAggr = queryPosAggr.prepareCVO_Template(queryJoinAggr, valuesPositionAggr);
		queryPosAggr.initAndStart( epnID +"_JoinAggr", queryContentPosAggr, 
				"Event::SmartBusiness::CEP_Sensors_Predictions::" + epnID +"_JoinAggr", "SmartBusiness/Q_Prediction");
		
		String queryXPathPrediction =
				"SELECT UDF_XPATH('/*[1]/*[1]/*[1]/*[1]', predictionJDOM) as gateway_id,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[2]', predictionJDOM) as Predicted_temp_min,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[3]', predictionJDOM) as Predicted_temp_one_hour,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[4]', predictionJDOM) as Predicted_temp_two_hours,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[5]', predictionJDOM) as timestamp_,"
				+ " UDF_XPATH('/*[1]/*[1]/*[1]/*[6]', predictionJDOM) as temperature_sensor_ID"
				+ " FROM $00;"	;
		
		QueryInstantiation queryPosXpath = new QueryInstantiation();
		ArrayList<String> valuesPositionXpath = new ArrayList<String>(Arrays.asList(epnID +"_JoinAggr")); //(Stream)
		String queryContentPosXpath = queryPosAggr.prepareCVO_Template(queryXPathPrediction, valuesPositionXpath);
		queryPosXpath.initAndStart(epnID + "_XPathPrediction", queryContentPosXpath, 
				"Event::SmartBusiness::CEP_Sensors_Predictions::" + epnID + "_XPathPrediction", "SmartBusiness/Q_XPath");
			
		String queryTemperaturePrediction =
				"SELECT gateway_id,"
				+ "	CAST(Predicted_temp_min AS DOUBLE) as Predicted_temp_min,"
				+ " CAST(Predicted_temp_one_hour AS DOUBLE) as Predicted_temp_one_hour,"
				+ "	CAST(Predicted_temp_two_hours AS DOUBLE) as Predicted_temp_two_hours,"
				+ "	CAST(UDF_transformTime(timestamp_) AS TIMESTAMP) as timestamp_,"
				+ "	temperature_sensor_ID"
				+ " FROM $00;";
		
		QueryInstantiation queryPosPrediction = new QueryInstantiation();
		ArrayList<String> valuesPositionPrediction = new ArrayList<String>(Arrays.asList(epnID + "_XPathPrediction")); //(Stream)
		String queryContentPosPrediction = queryPosPrediction.prepareCVO_Template(queryTemperaturePrediction, valuesPositionPrediction);
		queryPosPrediction.initAndStart(epnID, queryContentPosPrediction, 
				"iCore::Temperature::Predictions::" + epnID, "SmartBusiness/TemperaturePrediction");
		
		/*
		 * To delete the queries with the main epnID, the generated queryList 
		 * from addNewTimeSeriesPredictionEPN has to be committed.
		 * */
		test.deleteEpn(queryList);
	}

	@Override
	public void startEngine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopEngine() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addNewNonPerishableGoodsMonitoringEPN(String epnID,
			String temperatureSensorID, String humiditySensorID,
			String truckID, double hardMinTemperature,
			double hardMaxTemperature, double hardMinHumdity,
			double hardMaxHumdity) {
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
	public void deleteEpn(List<String>queryList) {
		QueryInstantiation queryPos = new QueryInstantiation();
		try {
			for(int i=0; i<queryList.size();++i){
				queryPos.cancelQuery(queryList.get(i));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
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
	public void addNewPerishableGoodsMonitoringEPN(String epnID,
			String temperatureSensorID, String humiditySensorID,
			String truckID, double hardMinTemperature,
			double hardMaxTemperature, double softMinTemperature,
			double softMaxTemperature, double softTemperatureMonitoringPeriod,
			double hardMinHumdity, double hardMaxHumdity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> addNewTimeSeriesPredictionEPN(String epnID,
			String truckID, String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			String parcelTemperatureSensorID, int reportingPeriod,
			int reportingWindowLength) {
		
		List<String> queryList = new ArrayList<String>();
		queryList.add(epnID);
		queryList.add(epnID + "_XPathPrediction");
		queryList.add(epnID +"_JoinAggr");
		queryList.add(epnID + "_Join");
		queryList.add(epnID + "_" + hvacActuatorID + "_Chronon");
		queryList.add(epnID + "_" + currentClampSensorID + "_Chronon");
		queryList.add(epnID + "_" + humiditySensorID + "_Chronon");
		queryList.add(epnID + "_" + externalContainerTemperatureSensorID + "_Chronon");
		queryList.add(epnID + "_" + parcelTemperatureSensorID + "_Chronon");
		queryList.add(epnID + "_" + containerTemperatureSensorID3 + "_Chronon");
		queryList.add(epnID + "_" + containerTemperatureSensorID2 + "_Chronon");
		queryList.add(epnID + "_" + containerTemperatureSensorID1 + "_Chronon");
		queryList.add(epnID + "_" + hvacActuatorID);
		queryList.add(epnID + "_" + currentClampSensorID);
		queryList.add(epnID + "_" + humiditySensorID);
		queryList.add(epnID + "_" + externalContainerTemperatureSensorID);
		queryList.add(epnID + "_" + parcelTemperatureSensorID);
		queryList.add(epnID + "_" + containerTemperatureSensorID3);
		queryList.add(epnID + "_" + containerTemperatureSensorID2);
		queryList.add(epnID + "_" + containerTemperatureSensorID1);
		
		return queryList;
	}

}









