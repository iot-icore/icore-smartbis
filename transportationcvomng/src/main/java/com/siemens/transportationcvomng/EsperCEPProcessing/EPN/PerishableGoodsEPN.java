package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.siemens.ct.ro.transportation.CEPEventsConverters.HumidityOutOfRangeEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.PerishableGoodTemperatureOutOfRangeHardEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.PerishableGoodTemperatureOutOfRangeSoftEventConverter;
import com.siemens.ct.ro.transportation.commons.MqttTopics;
import com.softwareag.transportation.CEPevents.HumidityOutOfRangeEvent;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeHardEvent;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeSoftEvent;

public class PerishableGoodsEPN extends EsperEPN {

	private EPServiceProvider epService;
	private double hardMinTemperature, hardMaxTemperature, softMinTemperature,
			softMaxTemperature, hardMinHumdity, hardMaxHumdity;
	private String temperatureSensorID;
	private String humiditySensorID;
	private String gatewayID;
	private double softTemperatureMonitoringPeriod;
	private String packageID;

	private MqttClient mqttAdaptersContainerForEsperOA;

	private final static int MQTT_QoS = 0;
	private final static boolean MQTT_MESSAGE_PERSISTENCE = false;

	public PerishableGoodsEPN(EPServiceProvider epService, String packageID,
			String temperatureSensorID, String humiditySensorID,
			String gatewayID, double hardMinTemperature,
			double hardMaxTemperature, double softMinTemperature,
			double softMaxTemperature, double hardMinHumdity,
			double hardMaxHumdity, double softTemperatureMonitoringPeriod,
			MqttClient mqttAdaptersContainerForEsperOA) {
		super();
		this.epService = epService;
		this.hardMinTemperature = hardMinTemperature;
		this.hardMaxTemperature = hardMaxTemperature;
		this.softMinTemperature = softMinTemperature;
		this.softMaxTemperature = softMaxTemperature;
		this.hardMinHumdity = hardMinHumdity;
		this.hardMaxHumdity = hardMaxHumdity;
		this.temperatureSensorID = temperatureSensorID;
		this.humiditySensorID = humiditySensorID;
		this.gatewayID = gatewayID;
		this.softTemperatureMonitoringPeriod = softTemperatureMonitoringPeriod;
		this.mqttAdaptersContainerForEsperOA = mqttAdaptersContainerForEsperOA;
		this.packageID = packageID;

	}

	public void addEPN() {
		// hard temperature constraints
		String expression = "insert into PerishableGoodTemperatureConditionExceed select temperature, timestamp, sensorID, gatewayID  "
				+ "from TemperatureEvent(temperature < "
				+ hardMinTemperature
				+ " or temperature > "
				+ hardMaxTemperature
				+ " , sensorID = \""
				+ temperatureSensorID
				+ "\" , gatewayID = \"" + gatewayID + "\")";

//		System.out.println("Registerd new statement in the CEP engine: "
//				+ expression);

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);
		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
//				System.out
//						.println("PerishableGoodTemperatureConditionExceed: temperature = "
//								+ event.get("temperature")
//								+ ", sensor_ID = "
//								+ event.get("sensorID")
//								+ ", gatewayID = "
//								+ event.get("gatewayID"));

				PerishableGoodTemperatureOutOfRangeHardEvent perishableGoodTemperatureOutOfRangeHardEvent = new PerishableGoodTemperatureOutOfRangeHardEvent();

				perishableGoodTemperatureOutOfRangeHardEvent
						.setGatewayID(gatewayID);
				perishableGoodTemperatureOutOfRangeHardEvent
						.setHardMaximumRangeValue(hardMaxTemperature);
				perishableGoodTemperatureOutOfRangeHardEvent
						.setHardMinimumRangeValue(hardMinTemperature);
				perishableGoodTemperatureOutOfRangeHardEvent
						.setPackageID(packageID);
				perishableGoodTemperatureOutOfRangeHardEvent
						.setSensorID(temperatureSensorID);
				perishableGoodTemperatureOutOfRangeHardEvent
						.setTemperature(Double.parseDouble(event.get(
								"temperature").toString()));
				perishableGoodTemperatureOutOfRangeHardEvent.setTimestamp(Long
						.parseLong(event.get("timestamp").toString()));

				String message = PerishableGoodTemperatureOutOfRangeHardEventConverter
						.convertToXML(perishableGoodTemperatureOutOfRangeHardEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.PERISHABLE_GOODS_HARD_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.PERISHABLE_GOODS_HARD_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
				} catch (MqttException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.NON_PERISHABLE_GOODS_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
					e.printStackTrace();
				}

			}
		});

		// hard humidity constraints
		expression = "insert into PerishableGoodHumidityConditionExceed select humidity, timestamp, sensorID, gatewayID "
				+ " from HumidityEvent(humidity < "
				+ hardMinHumdity
				+ " or humidity > "
				+ hardMaxHumdity
				+ " , sensorID = \""
				+ humiditySensorID + "\" , gatewayID = \"" + gatewayID + "\")";

		/*
		 * System.out.println("Registerd new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
//				System.out
//						.println("PerishableGoodHumidityConditionExceed: humidity = "
//								+ event.get("humidity")
//								+ ", sensorID = "
//								+ event.get("sensorID")
//								+ ", gatewayID = "
//								+ event.get("gatewayID")
//								+ "pack ID "
//								+ packageID);

				HumidityOutOfRangeEvent humidityOutOfRangeEvent = new HumidityOutOfRangeEvent();

				humidityOutOfRangeEvent.setGatewayID(gatewayID);
				humidityOutOfRangeEvent.setHumidity(Double.parseDouble(event
						.get("humidity").toString()));
				humidityOutOfRangeEvent.setMaximumRangeValue(hardMaxHumdity);
				humidityOutOfRangeEvent.setMinimumRangeValue(hardMinHumdity);
				humidityOutOfRangeEvent.setPackageID(packageID);
				humidityOutOfRangeEvent.setSensorID(humiditySensorID);
				humidityOutOfRangeEvent.setTimestamp(Long.parseLong(event.get(
						"timestamp").toString()));

				String message = HumidityOutOfRangeEventConverter
						.convertToXML(humidityOutOfRangeEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC);

					e.printStackTrace();
				} catch (MqttException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
					e.printStackTrace();
				}

			}
		});

		// // hard temperature constraints method 1 (for each and every out or
		// // range temperature event that is not
		// // followed by a regular temperature event within 50 sec a complec
		// event
		// // is generated
		// expression =
		// "insert into PerishableGoodsSoftTemperatureConditionExceedMethod1 "
		// +
		// "select temperatureMeasurement.temperature as temperature, temperatureMeasurement.temperature_sensor_id as temperature_sensor_id, temperatureMeasurement.truck_id as truck_id from "
		// +
		// "pattern [every temperatureMeasurement = TemperatureMeasurement(temperature < "
		// + softMinTemperature
		// + " or temperature > "
		// + softMaxTemperature
		// + " , temperature_sensor_id = \""
		// + temperatureSensorID
		// + "\" , truck_id = \""
		// + truckID
		// + "\") -> (timer:interval("
		// +
		// GlobalVariables.PERISHABLE_GOODS_SOFT_TEMPERATURE_CONDITION_EXCEED_METHOD_1_PERIOD
		// + " sec) and not TemperatureMeasurement(temperature > "
		// + softMinTemperature
		// + " and temperature < "
		// + softMaxTemperature
		// + " , temperature_sensor_id = \""
		// + temperatureSensorID
		// + "\" , truck_id = \""
		// + truckID
		// + "\"))]";
		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);
		//
		// statement = epService.getEPAdministrator().createEPL(expression);
		//
		// expression =
		// "select temperature, temperature_sensor_id, truck_id from PerishableGoodsSoftTemperatureConditionExceedMethod1";
		// statement = epService.getEPAdministrator().createEPL(expression);
		//
		// statement.addListener(new UpdateListener() {
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out
		// .println("PerishableGoodsSoftTemperatureConditionExceedMethod1: temperature = "
		// + event.get("temperature")
		// + ", sensor_ID = "
		// + event.get("temperature_sensor_id")
		// + ", truck_ID = " + event.get("truck_id"));
		//
		// }
		// });

		// similar as the previous one but the complex event is generated only
		// one every 50 sec

		expression = "create window PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID
				+ ".win:keepall () as "
				+ " select temperature, timestamp, sensorID, gatewayID  "
				+ "from TemperatureEvent";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		expression = "insert into PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID
				+ " select temperature, timestamp, sensorID, gatewayID from "
				+ "TemperatureEvent(temperature < "
				+ softMinTemperature
				+ " or temperature > "
				+ softMaxTemperature
				+ " , sensorID = \""
				+ temperatureSensorID
				+ "\" , gatewayID = \""
				+ gatewayID
				+ "\") where ((select count(*) from PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID + ") = 0)";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		expression = "insert into PerishableGoodsSoftTemperatureConditionNotExceedEvent "
				+ "select temperature, timestamp, sensorID, gatewayID from "
				+ "TemperatureEvent(temperature > "
				+ softMinTemperature
				+ " and temperature < "
				+ softMaxTemperature
				+ " , sensorID = \""
				+ temperatureSensorID
				+ "\" , gatewayID = \"" + gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		expression = "on PerishableGoodsSoftTemperatureConditionNotExceedEvent delete from PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID;

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		expression = "insert into PerishableGoodsSoftTemperatureConditionExceedMethod2 "
				+ "select temperatureMeasurementCurrent.temperature as temperature, temperatureMeasurementCurrent.sensorID as sensorID, temperatureMeasurementCurrent.gatewayID as gatewayID, temperatureMeasurementCurrent.timestamp as timestamp "
				+ " from TemperatureEvent.std:lastevent() as temperatureMeasurementCurrent, PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID
				+ " as temperatureMeasurementOld "
				+ " where (temperatureMeasurementCurrent.timestamp - temperatureMeasurementOld.timestamp > "
				+ softTemperatureMonitoringPeriod
				+ " * 1000) and (temperatureMeasurementOld.temperature < "
				+ softMinTemperature
				+ " or temperatureMeasurementOld.temperature > "
				+ softMaxTemperature
				+ ") and (temperatureMeasurementOld.sensorID = \""
				+ temperatureSensorID
				+ "\") and (temperatureMeasurementOld.gatewayID = \""
				+ gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		expression = "select temperature, timestamp, sensorID, gatewayID from PerishableGoodsSoftTemperatureConditionExceedMethod2";
		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {
			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
//				System.out
//						.println("PerishableGoodsSoftTemperatureConditionExceedMethod2: temperature = "
//								+ event.get("temperature")
//								+ ", sensorID = "
//								+ event.get("sensorID")
//								+ ", gatewayID = "
//								+ event.get("gatewayID"));

				PerishableGoodTemperatureOutOfRangeSoftEvent perishableGoodTemperatureOutOfRangeSoftEvent = new PerishableGoodTemperatureOutOfRangeSoftEvent();

				perishableGoodTemperatureOutOfRangeSoftEvent
						.setGatewayID(gatewayID);
				perishableGoodTemperatureOutOfRangeSoftEvent
						.setPackageID(packageID);
				perishableGoodTemperatureOutOfRangeSoftEvent
						.setSensorID(temperatureSensorID);
				perishableGoodTemperatureOutOfRangeSoftEvent
						.setSoftMaximumRangeValue(softMaxTemperature);
				perishableGoodTemperatureOutOfRangeSoftEvent
						.setSoftMinimumRangeValue(softMinTemperature);
				perishableGoodTemperatureOutOfRangeSoftEvent
						.setSoftTemperatureMonitoringPeriod(softTemperatureMonitoringPeriod);
				perishableGoodTemperatureOutOfRangeSoftEvent.setTimestamp(Long
						.parseLong(event.get("timestamp").toString()));

				String message = PerishableGoodTemperatureOutOfRangeSoftEventConverter
						.convertToXML(perishableGoodTemperatureOutOfRangeSoftEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.PERISHABLE_GOODS_SOFT_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.PERISHABLE_GOODS_SOFT_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
				} catch (MqttException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.PERISHABLE_GOODS_SOFT_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
					e.printStackTrace();
				}

			}
		});

		expression = "on PerishableGoodsSoftTemperatureConditionExceedMethod2 delete from PerishableGoodsSoftTemperatureConditionExceedEvent"
				+ packageID;

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

	}
}
