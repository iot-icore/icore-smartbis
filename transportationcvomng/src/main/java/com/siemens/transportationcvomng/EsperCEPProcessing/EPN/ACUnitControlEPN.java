package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.siemens.ct.ro.transportation.CEPEventsConverters.ACUnitEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.PerishableGoodTemperatureOutOfRangeHardEventConverter;
import com.siemens.ct.ro.transportation.commons.MqttTopics;
import com.softwareag.transportation.CEPevents.ACUnitEvent;
import com.softwareag.transportation.CEPevents.PerishableGoodTemperatureOutOfRangeHardEvent;

public class ACUnitControlEPN extends EsperEPN {

	private EPServiceProvider epService;

	private String gatewayID;
	private String containerTemperatureSensorID1;
	private String containerTemperatureSensorID2;
	private String containerTemperatureSensorID3;
	private String hvacActuatorID;
	private double maxTemperature;
	private double minTemperature;
	private double targetvalueForMaxTemperature;
	private double targetvalueForMinTemperature;
	private MqttClient mqttAdaptersContainerForEsperOA;
	
	private final static int MQTT_QoS = 0;
	private final static boolean MQTT_MESSAGE_PERSISTENCE = false;

	public ACUnitControlEPN(EPServiceProvider epService, String gatewayID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String hvacActuatorID,
			double maxTemperature, double minTemperature,
			double targetvalueForMaxTemperature,
			double targetvalueForMinTemperature,
			MqttClient mqttAdaptersContainerForEsperOA) {
		super();
		this.gatewayID = gatewayID;
		this.containerTemperatureSensorID1 = containerTemperatureSensorID1;
		this.containerTemperatureSensorID2 = containerTemperatureSensorID2;
		this.containerTemperatureSensorID3 = containerTemperatureSensorID3;
		this.hvacActuatorID = hvacActuatorID;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.targetvalueForMaxTemperature = targetvalueForMaxTemperature;
		this.targetvalueForMinTemperature = targetvalueForMinTemperature;
		this.epService = epService;
		this.mqttAdaptersContainerForEsperOA = mqttAdaptersContainerForEsperOA;
	}

	@Override
	public void addEPN() {

		String expression = "insert into Container"
				+ gatewayID
				+ "AverageTemperature"
				+ " select (container_temperature_stream_1.temperature + container_temperature_stream_2.temperature + container_temperature_stream_3.temperature)/3 as averageTemperature  "
				+ "from" + " TemperatureEvent(sensorID = \""
				+ containerTemperatureSensorID1
				+ "\").std:lastevent () as container_temperature_stream_1"
				+ ", TemperatureEvent(sensorID = \""
				+ containerTemperatureSensorID2
				+ "\").std:lastevent () as container_temperature_stream_2"
				+ ", TemperatureEvent(sensorID = \""
				+ containerTemperatureSensorID3
				+ "\").std:lastevent () as container_temperature_stream_3";

		System.out.println("Registerd new statement in the CEP engine: "
				+ expression);

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);

		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("AverageTemperature: averageTemperature = "
		// + event.get("averageTemperature"));
		// }
		//
		// });

		expression = "select * from Container" + gatewayID
				+ "AverageTemperature (averageTemperature > " + maxTemperature
				+ ")";

		System.out.println("Registerd new statement in the CEP engine: "
				+ expression);

		statement = epService.getEPAdministrator().createEPL(expression);

		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				
				ACUnitEvent acUnitEvent = new ACUnitEvent();
				acUnitEvent.setAcUnitState(true);
				acUnitEvent.setAcUnitTarghetValue((new Double(targetvalueForMaxTemperature)).intValue());
				acUnitEvent.setGatewayID(gatewayID);
				acUnitEvent.setSensorID(hvacActuatorID);
				acUnitEvent.setTimestamp(System.currentTimeMillis());
				

				String message = ACUnitEventConverter
						.convertToXML(acUnitEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.mqttTopicAcUnitCommand,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.mqttTopicAcUnitCommand);
				} catch (MqttException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.mqttTopicAcUnitCommand);
					e.printStackTrace();
				}
			}

		});

		expression = "select * from Container" + gatewayID
				+ "AverageTemperature (averageTemperature < " + minTemperature
				+ ")";

		System.out.println("Registerd new statement in the CEP engine: "
				+ expression);

		statement = epService.getEPAdministrator().createEPL(expression);

		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				ACUnitEvent acUnitEvent = new ACUnitEvent();
				acUnitEvent.setAcUnitState(true);
				acUnitEvent.setAcUnitTarghetValue((new Double(targetvalueForMinTemperature)).intValue());
				acUnitEvent.setGatewayID(gatewayID);
				acUnitEvent.setSensorID(hvacActuatorID);
				acUnitEvent.setTimestamp(System.currentTimeMillis());
				

				String message = ACUnitEventConverter
						.convertToXML(acUnitEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.mqttTopicAcUnitCommand,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.mqttTopicAcUnitCommand);
				} catch (MqttException e) {
					System.out
							.println("Unable to publis mqtt message: "
									+ message
									+ " on topic "
									+ MqttTopics.mqttTopicAcUnitCommand);
					e.printStackTrace();
				}
			}

		});

	}

}
