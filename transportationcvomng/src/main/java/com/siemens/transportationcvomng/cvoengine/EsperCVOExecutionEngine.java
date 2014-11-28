package com.siemens.transportationcvomng.cvoengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.siemens.ct.mqttadapter.MqttAdaptersContainer;
import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.transportationcvomng.EsperCEPProcessing.EPN.ACUnitControlEPN;
import com.siemens.transportationcvomng.EsperCEPProcessing.EPN.EsperEPN;
import com.siemens.transportationcvomng.EsperCEPProcessing.EPN.NonPerishableGoodsEPN;
import com.siemens.transportationcvomng.EsperCEPProcessing.EPN.PerishableGoodsEPN;
import com.siemens.transportationcvomng.EsperCEPProcessing.EPN.TimeSeriesPredictionEPN;
import com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources.ACUnitAdapter;
import com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources.CurrentClampSensorAdapter;
import com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources.HumiditySensorAdapter;
import com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources.TemperatureSensorAdapter;
import com.siemens.transportationcvomng.EsperCEPProcessing.UDF.TimeSeriesPredictionService;
import com.siemens.transportationcvomng.cvoengine.EPNgraph.GraphUtils;
import com.siemens.transportationcvomng.cvoengine.EPNgraph.NonPerishableGoodsMonitoringEPNGraph;
import com.siemens.transportationcvomng.cvoengine.EPNgraph.PerishableGoodsMonitoringEPNGraph;
import com.siemens.transportationcvomng.cvoengine.EPNgraph.TimeSeriesPredictionEPNGraph;
import com.siemens.transportationcvomng.startup.SimulatorStartupListener;

public class EsperCVOExecutionEngine implements CvoExecutionEngine {

	Logger logger = Logger.getLogger(EsperCVOExecutionEngine.class);

	private final static String TEMPERATURE_SENSOR_TOPIC_BASE_PATH = "iCore/Temperature/TMP102/Temperature/PUSH/s";
	private final static String HUMIDITY_SENSOR_TOPIC_BASE_PATH = "iCore/Humidity/SHT_H/Humidity/PUSH/s";
	private final static String CURRENT_CLAMP_SENSOR_TOPIC_BASE_PATH = "iCore/ADC/ADS124s8/Sample/PUSH/s";
	private final static String AC_UNIT_SENSOR_TOPIC_BASE_PATH = "iCore/ACunit/PUSH/s";

	private static int MQTT_MESSAGES_QOS = 0;

	public final static String ESPER_CEP_ENGINE_CONFIG_FILE_PATH = "configurations/EsperCEPEngine.properties";

	private EPServiceProvider epService;
	private MqttAdaptersContainer mqttAdaptersContainerForEsperIA = null;
	private MqttClient mqttAdaptersContainerForEsperOA = null;

	private HashMap<String, EsperEPN> statementsHashMap = new HashMap<String, EsperEPN>();

	private HashMap<String, Node> voNodes = new HashMap<String, Node>();
	private HashMap<String, NonPerishableGoodsMonitoringEPNGraph> nonPerishableGoodsMonitoringEPNGraphs = new HashMap<String, NonPerishableGoodsMonitoringEPNGraph>();
	private HashMap<String, PerishableGoodsMonitoringEPNGraph> perishableGoodsMonitoringEPNGraphs = new HashMap<String, PerishableGoodsMonitoringEPNGraph>();
	private HashMap<String, TimeSeriesPredictionEPNGraph> timeSeriesPredictionEPNGraphs = new HashMap<String, TimeSeriesPredictionEPNGraph>();

	@Override
	public void startEngine() {

		// Initialize the CEP engine
		System.out.println("Initializing CEP engine...");

		Configuration config = new Configuration();
		config.addEventTypeAutoName("com.softwareag.transportation.CEPevents");
		// config.addPlugInAggregationFunctionFactory(
		// "predictParcelTemperature",
		// "com.siemens.transportationcvomng.EsperCEPProcessing.UDF.TimeSeriesPredictionAgregationFunctionFactory");

		config.addPlugInSingleRowFunction(
				"predictParcelTemperature",
				"com.siemens.transportationcvomng.EsperCEPProcessing.UDF.TimeSeriesPredictionService",
				"predict");

		config.addPlugInAggregationFunctionFactory(
				"timeSeriesPredictionsGroupSamples",
				"com.siemens.transportationcvomng.EsperCEPProcessing.UDF.TimeSeriesPredictionSampleAtributeGroupFactory");

		epService = EPServiceProviderManager.getDefaultProvider(config);
		System.out.println("Initializing CEP engine...DONE");

		// read the properties file to identify the mqtt connection for CEP
		// input and output adapters.

		InputStream inputStream = null;
		String realPath = SimulatorStartupListener.getRealPath(ESPER_CEP_ENGINE_CONFIG_FILE_PATH);
		// String realPath = ESPER_CEP_ENGINE_CONFIG_FILE_PATH;
		try {
			inputStream = new FileInputStream(realPath);
		} catch (FileNotFoundException e) {
			logger.error("The configuration file " + realPath
					+ " is missing!!!", e);
		}

		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			logger.error("Error while reading the congiguration file "
					+ ESPER_CEP_ENGINE_CONFIG_FILE_PATH, e);
		}

		String mqttBrokerUrl = prop.getProperty("mqttBrokerUrl");
		Boolean mqttCleanSession = Boolean.parseBoolean(prop
				.getProperty("mqttCleanSeasion"));
		String mqttClientIDForSubscribe = prop
				.getProperty("mqttClientIDForSubscribe");
		String mqttClientIDForPublish = prop
				.getProperty("mqttClientIDForPublish");

		// initialize the MQTT container for fetching the raw events (the inputs
		// adapter for CEP)
		mqttAdaptersContainerForEsperIA = new MqttAdaptersContainer(
				mqttBrokerUrl, mqttCleanSession, mqttClientIDForSubscribe);
		mqttAdaptersContainerForEsperIA.connect();

		// initialize the MQTT container for publishing the complex events
		MqttConnectOptions conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(mqttCleanSession);

		mqttAdaptersContainerForEsperOA = null;
		try {
			mqttAdaptersContainerForEsperOA = new MqttClient(mqttBrokerUrl,
					mqttClientIDForPublish);
			mqttAdaptersContainerForEsperOA.connect(conOpt);
		} catch (MqttException e) {
			logger.error("Mqtt publisher: Unable to connect to the broker" + e);
		}

	}

	@Override
	public void stopEngine() {

		if (mqttAdaptersContainerForEsperIA != null) {
			mqttAdaptersContainerForEsperIA.disconect();
		}

		epService.destroy();

		if (mqttAdaptersContainerForEsperOA != null) {
			try {
				mqttAdaptersContainerForEsperOA.disconnect();
			} catch (MqttException e) {
				logger.error("Mqtt publisher: Unable to disconnect from broker. "
						+ e);
			}
		}
	}

	@Override
	public void addNewNonPerishableGoodsMonitoringEPN(String epnID,
			String packageID, String temperatureSensorID,
			String humiditySensorID, String gatewayID,
			double hardMinTemperature, double hardMaxTemperature,
			double hardMinHumdity, double hardMaxHumdity) {

		if (!statementsHashMap.containsKey(epnID)) {

			NonPerishableGoodsEPN nonPerishableGoodsEPN = new NonPerishableGoodsEPN(
					epService, packageID, temperatureSensorID,
					humiditySensorID, gatewayID, hardMinTemperature,
					hardMaxTemperature, hardMinHumdity, hardMaxHumdity,
					mqttAdaptersContainerForEsperOA);

			nonPerishableGoodsEPN.addEPN();

			statementsHashMap.put(epnID, nonPerishableGoodsEPN);

			NonPerishableGoodsMonitoringEPNGraph nonPerishableGoodsMonitoringEPNGraph = new NonPerishableGoodsMonitoringEPNGraph(
					packageID, (Node) voNodes.get(temperatureSensorID),
					(Node) voNodes.get(humiditySensorID));

			nonPerishableGoodsMonitoringEPNGraphs.put(epnID,
					nonPerishableGoodsMonitoringEPNGraph);

		} else {
			logger.error("The EPN with the ID: " + epnID
					+ "was not added because the ID was already registered!");
		}

	}

	@Override
	public void addNewPerishableGoodsMonitoringEPN(String epnID,
			String packageID, String temperatureSensorID,
			String humiditySensorID, String gatewayID,
			double hardMinTemperature, double hardMaxTemperature,
			double softMinTemperature, double softMaxTemperature,
			double softTemperatureMonitoringPeriod, double hardMinHumdity,
			double hardMaxHumdity) {

		if (!statementsHashMap.containsKey(epnID)) {

			PerishableGoodsEPN perishableGoodsEPN = new PerishableGoodsEPN(
					epService, packageID, temperatureSensorID,
					humiditySensorID, gatewayID, hardMinTemperature,
					hardMaxTemperature, softMinTemperature, softMaxTemperature,
					hardMinHumdity, hardMaxHumdity,
					softTemperatureMonitoringPeriod,
					mqttAdaptersContainerForEsperOA);

			perishableGoodsEPN.addEPN();

			statementsHashMap.put(epnID, perishableGoodsEPN);

			PerishableGoodsMonitoringEPNGraph perishableGoodsMonitoringEPNGraph = new PerishableGoodsMonitoringEPNGraph(
					packageID, (Node) voNodes.get(temperatureSensorID),
					(Node) voNodes.get(humiditySensorID));

			perishableGoodsMonitoringEPNGraphs.put(epnID,
					perishableGoodsMonitoringEPNGraph);

		} else {
			logger.error("The EPN with the ID: " + epnID
					+ "was not added because the ID was already registered!");
		}

	}

	@Override
	public void loadTimeSeriesPredictionModel(
			String pathToSerialisedPredictionModel) {

		ICOREForecaster icoreForecaster = ICORESerializer
				.deserialize(pathToSerialisedPredictionModel);
		TimeSeriesPredictionService.setIcoreForecaster(icoreForecaster);

	}

	@Override
	public void addNewACUnitControlEPN(String epnID, String gatewayID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String hvacActuatorID,
			double maxTemperature, double minTemperature,
			double targetvalueForMaxTemperature,
			double targetvalueForMinTemperature) {

		if (!statementsHashMap.containsKey(epnID)) {

			ACUnitControlEPN acUnitControlEPN = new ACUnitControlEPN(epService,
					gatewayID, containerTemperatureSensorID1,
					containerTemperatureSensorID2,
					containerTemperatureSensorID3, hvacActuatorID,
					maxTemperature, minTemperature,
					targetvalueForMaxTemperature, targetvalueForMinTemperature,
					mqttAdaptersContainerForEsperOA);

			acUnitControlEPN.addEPN();

			statementsHashMap.put(epnID, acUnitControlEPN);

		} else {
			logger.error("The EPN with the ID: " + epnID
					+ "was not added because the ID was already registered!");
		}

	}

	@Override
	public void deleteEpn(String epnID) {

		if (statementsHashMap.containsKey(epnID)) {
			EsperEPN esperEPN = statementsHashMap.remove(epnID);
			esperEPN.removeEPN();
		} else {
			logger.error("The EPN with the ID: " + epnID
					+ "was not deleted because the ID was not registered!");
		}

	}

	@Override
	public void registerNewTemperatureDataSource(String temperatureSensorID) {

		TemperatureSensorAdapter temperatureSensorAdapter = new TemperatureSensorAdapter(
				TEMPERATURE_SENSOR_TOPIC_BASE_PATH + temperatureSensorID,
				MQTT_MESSAGES_QOS, epService);
		mqttAdaptersContainerForEsperIA
				.addSubscribeAdapter(temperatureSensorAdapter);

		Node voNone = new Node();
		voNone.id("VOT_"
				+ GraphUtils.getNodeIDfromSensorID(temperatureSensorID));
		voNone.attr("color", "blue");
		voNodes.put(temperatureSensorID, voNone);

	}

	@Override
	public void registerNewHumidityDataSource(String humiditySensorID) {
		HumiditySensorAdapter humiditySensorAdapter = new HumiditySensorAdapter(
				HUMIDITY_SENSOR_TOPIC_BASE_PATH + humiditySensorID,
				MQTT_MESSAGES_QOS, epService);
		mqttAdaptersContainerForEsperIA
				.addSubscribeAdapter(humiditySensorAdapter);

		Node voNone = new Node();
		voNone.id("VOH_" + GraphUtils.getNodeIDfromSensorID(humiditySensorID));
		voNone.attr("color", "blue");
		voNodes.put(humiditySensorID, voNone);

	}

	@Override
	public void registerNewcurrentClampDataSource(String currentClampSensorID) {
		CurrentClampSensorAdapter currentClampSensorAdapter = new CurrentClampSensorAdapter(
				CURRENT_CLAMP_SENSOR_TOPIC_BASE_PATH + currentClampSensorID,
				MQTT_MESSAGES_QOS, epService);
		mqttAdaptersContainerForEsperIA
				.addSubscribeAdapter(currentClampSensorAdapter);

		Node voNone = new Node();
		voNone.id("VOCC_"
				+ GraphUtils.getNodeIDfromSensorID(currentClampSensorID));
		voNone.attr("color", "blue");
		voNodes.put(currentClampSensorID, voNone);

	}

	@Override
	public void registerNewACUnitDataSource(String acUnitID) {
		ACUnitAdapter acUnitAdapter = new ACUnitAdapter(
				AC_UNIT_SENSOR_TOPIC_BASE_PATH + acUnitID, MQTT_MESSAGES_QOS,
				epService);
		mqttAdaptersContainerForEsperIA.addSubscribeAdapter(acUnitAdapter);

		Node voNone = new Node();
		voNone.id("VOACU_" + GraphUtils.getNodeIDfromSensorID(acUnitID));
		voNone.attr("color", "blue");
		voNodes.put(acUnitID, voNone);

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

		if (!statementsHashMap.containsKey(epnID)) {

			TimeSeriesPredictionEPN timeSeriesPredictionEPN = new TimeSeriesPredictionEPN(
					epService, packageID, gatewayID,
					containerTemperatureSensorID1,
					containerTemperatureSensorID2,
					containerTemperatureSensorID3, humiditySensorID,
					hvacActuatorID, currentClampSensorID,
					externalContainerTemperatureSensorID,
					parcelTemperatureSensorID, reportingPeriod,
					reportingWindowLength, mqttAdaptersContainerForEsperOA);

			timeSeriesPredictionEPN.addEPN();

			statementsHashMap.put(epnID, timeSeriesPredictionEPN);

			TimeSeriesPredictionEPNGraph timeSeriesPredictionEPNGraph = new TimeSeriesPredictionEPNGraph(
					packageID,
					(Node) voNodes.get(containerTemperatureSensorID1),
					(Node) voNodes.get(containerTemperatureSensorID2),
					(Node) voNodes.get(containerTemperatureSensorID3),
					(Node) voNodes.get(humiditySensorID),
					(Node) voNodes.get(hvacActuatorID),
					(Node) voNodes.get(currentClampSensorID),
					(Node) voNodes.get(externalContainerTemperatureSensorID),
					(Node) voNodes.get(parcelTemperatureSensorID));

			timeSeriesPredictionEPNGraphs.put(epnID,
					timeSeriesPredictionEPNGraph);

		} else {
			logger.error("The EPN with the ID: " + epnID
					+ "was not added because the ID was already registered!");
		}

	}

	public void addNewTrainingSetGeneratorEPN(String epnID, String gatewayID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			List<String> parcelTemperatureSensorIDs,
			double desiredFortcastInterval) {

	}

	@Override
	public Graph getGetCurrentCVOsStructure() {

		Graph epnGraph = new Graph();

		for (String voID : voNodes.keySet()) {
			epnGraph.node(voNodes.get(voID));
		}

		for (String epnID : nonPerishableGoodsMonitoringEPNGraphs.keySet()) {
			NonPerishableGoodsMonitoringEPNGraph nonPerishableGoodsMonitoringEPNGraph = nonPerishableGoodsMonitoringEPNGraphs
					.get(epnID);
			nonPerishableGoodsMonitoringEPNGraph.addEPNNodesToGraph(epnGraph);
		}

		for (String epnID : perishableGoodsMonitoringEPNGraphs.keySet()) {
			PerishableGoodsMonitoringEPNGraph perishableGoodsMonitoringEPNGraph = perishableGoodsMonitoringEPNGraphs
					.get(epnID);
			perishableGoodsMonitoringEPNGraph.addEPNNodesToGraph(epnGraph);
		}

		for (String epnID : timeSeriesPredictionEPNGraphs.keySet()) {

			TimeSeriesPredictionEPNGraph timeSeriesPredictionEPNGraph = timeSeriesPredictionEPNGraphs
					.get(epnID);
			timeSeriesPredictionEPNGraph.addEPNNodesToGraph(epnGraph);
		}

		return epnGraph;
	}

}
