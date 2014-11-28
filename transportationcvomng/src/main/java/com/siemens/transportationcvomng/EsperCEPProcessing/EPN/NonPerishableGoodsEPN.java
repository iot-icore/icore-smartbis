package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

/** 
 * This class instantiates the CEP statements required for monitoring 
 * the temperature and humidity of a non perishable product.
 * 
 * @author dan.puiu
 */

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.siemens.ct.ro.transportation.CEPEventsConverters.HumidityOutOfRangeEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.NonPerishableGoodTemperatureOutOfRangeEventConverter;
import com.siemens.ct.ro.transportation.commons.MqttTopics;
import com.softwareag.transportation.CEPevents.HumidityOutOfRangeEvent;
import com.softwareag.transportation.CEPevents.NonPerishableGoodTemperatureOutOfRangeEvent;

public class NonPerishableGoodsEPN extends EsperEPN {

	private EPServiceProvider epService;
	private double hardMinTemperature, hardMaxTemperature, hardMinHumdity,
			hardMaxHumdity;
	private String temperatureSensorID;
	private String humiditySensorID;
	private String gatewayID;
	private String packageID;

	private MqttClient mqttAdaptersContainerForEsperOA;

	private final static int MQTT_QoS = 0;
	private final static boolean MQTT_MESSAGE_PERSISTENCE = false;

	public NonPerishableGoodsEPN(EPServiceProvider epService, String packageID,
			String temperaturesensorID, String humiditySensorID,
			String gatewayID, double hardMinTemperature,
			double hardMaxTemperature, double hardMinHumdity,
			double hardMaxHumdity, MqttClient mqttAdaptersContainerForEsperOA) {
		super();
		this.epService = epService;
		this.hardMaxTemperature = hardMaxTemperature;
		this.hardMinTemperature = hardMinTemperature;
		this.temperatureSensorID = temperaturesensorID;
		this.humiditySensorID = humiditySensorID;
		this.gatewayID = gatewayID;
		this.hardMinHumdity = hardMinHumdity;
		this.hardMaxHumdity = hardMaxHumdity;
		this.mqttAdaptersContainerForEsperOA = mqttAdaptersContainerForEsperOA;
		this.packageID = packageID;
	}

	@Override
	public void addEPN() {

		// hard temperature constraints
		String expression = "insert into NonPerishableGoodTemperatureConditionExceed select temperature, timestamp, sensorID, gatewayID  "
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
//						.println("NonPerishableGoodTemperatureConditionExceed: temperature = "
//								+ event.get("temperature")
//								+ ", sensor_ID = "
//								+ event.get("sensorID")
//								+ ", gatewayID = "
//								+ event.get("gatewayID"));

				NonPerishableGoodTemperatureOutOfRangeEvent nonPerishableGoodTemperatureOutOfRangeEvent = new NonPerishableGoodTemperatureOutOfRangeEvent();

				nonPerishableGoodTemperatureOutOfRangeEvent
						.setGatewayID(gatewayID);
				nonPerishableGoodTemperatureOutOfRangeEvent
						.setMaximumRangeValue(hardMaxTemperature);
				nonPerishableGoodTemperatureOutOfRangeEvent
						.setMinimumRangeValue(hardMinTemperature);
				nonPerishableGoodTemperatureOutOfRangeEvent
						.setPackageID(packageID);
				nonPerishableGoodTemperatureOutOfRangeEvent
						.setSensorID(temperatureSensorID);
				nonPerishableGoodTemperatureOutOfRangeEvent
						.setTemperature(Double.parseDouble(event.get(
								"temperature").toString()));
				nonPerishableGoodTemperatureOutOfRangeEvent.setTimestamp(Long
						.parseLong(event.get("timestamp").toString()));

				String message = NonPerishableGoodTemperatureOutOfRangeEventConverter
						.convertToXML(nonPerishableGoodTemperatureOutOfRangeEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.NON_PERISHABLE_GOODS_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.NON_PERISHABLE_GOODS_TEMPERATURE_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
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
		expression = "insert into NonPerishableGoodHumidityConditionExceed select humidity, timestamp, sensorID, gatewayID "
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
//						.println("NonPerishableGoodHumidityConditionExceed: humidity = "
//								+ event.get("humidity")
//								+ ", sensorID = "
//								+ event.get("sensorID")
//								+ ", gatewayID = "
//								+ event.get("gatewayID") + "pack ID " + packageID);

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
					mqttAdaptersContainerForEsperOA.publish(
							MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC,
							message.getBytes(), MQTT_QoS,
							MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out.println("Unable to publis mqtt message: "
							+ message + " on topic "
							+ MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC);

					e.printStackTrace();
				} catch (MqttException e) {
					System.out.println("Unable to publis mqtt message: "
							+ message + " on topic "
							+ MqttTopics.HUMIDITY_OUT_OF_RANGE_WARNING_MQTT_TOPIC);
					e.printStackTrace();
				}

			}
		});


	}

}
