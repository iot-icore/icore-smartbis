package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class TrainingSetGeneratorEPN extends EsperEPN {

	private EPServiceProvider epService;

	private final int TRUCK_SENSOR_JOINED_TYPE_OUTPUT_RATE = 15;

	private String containerTemperatureSensorID1;
	private String containerTemperatureSensorID2;
	private String containerTemperatureSensorID3;
	private String humiditySensorID;
	private String hvacActuatorID;
	private String currentClampSensorID;
	private String externalContainerTemperatureSensorID;
	private List<String> parcelTemperatureSensorIDs;
	private double desiredFortcastInterval;

	private MqttClient mqttAdaptersContainerForEsperOA;

	public TrainingSetGeneratorEPN(EPServiceProvider epService,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			List<String> parcelTemperatureSensorIDs,
			double desiredFortcastInterval,
			MqttClient mqttAdaptersContainerForEsperOA) {
		super();
		this.epService = epService;
		this.containerTemperatureSensorID1 = containerTemperatureSensorID1;
		this.containerTemperatureSensorID2 = containerTemperatureSensorID2;
		this.containerTemperatureSensorID3 = containerTemperatureSensorID3;
		this.humiditySensorID = humiditySensorID;
		this.hvacActuatorID = hvacActuatorID;
		this.currentClampSensorID = currentClampSensorID;
		this.externalContainerTemperatureSensorID = externalContainerTemperatureSensorID;
		this.parcelTemperatureSensorIDs = parcelTemperatureSensorIDs;
		this.mqttAdaptersContainerForEsperOA = mqttAdaptersContainerForEsperOA;
		this.desiredFortcastInterval = desiredFortcastInterval;

	}

	@Override
	public void addEPN() {
		addSampleAttributeSelectionEPN();

	}

	private void addSampleAttributeSelectionEPN() {

		String expression = "insert into SampleAttributeSelection_TemperatureMesurement"
				+ containerTemperatureSensorID1
				+ " select * from TemperatureMeasurement (temperature_sensor_id = \""
				+ containerTemperatureSensorID1 + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);

		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("TemperatureMesurement"
//						+ containerTemperatureSensorID1 + ": temperature = "
//						+ event.get("temperature") + ", sensor_ID = "
//						+ event.get("temperature_sensor_id") + ", truck_ID = "
//						+ event.get("truck_id") + ", timestamp = "
//						+ event.get("timestamp"));
//		
//		 }
//		 });

		expression = "insert into SampleAttributeSelection_TemperatureMesurement"
				+ containerTemperatureSensorID2
				+ " select * from TemperatureMeasurement (temperature_sensor_id = \""
				+ containerTemperatureSensorID2 + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("TemperatureMesurement"
//						+ containerTemperatureSensorID2 + ": temperature = "
//						+ event.get("temperature") + ", sensor_ID = "
//						+ event.get("temperature_sensor_id") + ", truck_ID = "
//						+ event.get("truck_id"));
//		
//		 }
//		 });

		expression = "insert into SampleAttributeSelection_TemperatureMesurement"
				+ containerTemperatureSensorID3
				+ " select * from TemperatureMeasurement (temperature_sensor_id = \""
				+ containerTemperatureSensorID3 + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("TemperatureMesurement"
//						+ containerTemperatureSensorID3 + ": temperature = "
//						+ event.get("temperature") + ", sensor_ID = "
//						+ event.get("temperature_sensor_id") + ", truck_ID = "
//						+ event.get("truck_id"));
//		
//		 }
//		 });

		expression = "insert into SampleAttributeSelection_TemperatureMesurement"
				+ externalContainerTemperatureSensorID
				+ " select * from TemperatureMeasurement (temperature_sensor_id = \""
				+ externalContainerTemperatureSensorID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("TemperatureMesurement"
//						+ externalContainerTemperatureSensorID
//						+ ": temperature = " + event.get("temperature")
//						+ ", sensor_ID = " + event.get("temperature_sensor_id")
//						+ ", truck_ID = " + event.get("truck_id"));
//		
//		 }
//		 });

		for (String parcelTemperatureSensorID : parcelTemperatureSensorIDs) {

			expression = "insert into SampleAttributeSelection_TemperatureMesurement"
					+ parcelTemperatureSensorID
					+ " select * from TemperatureMeasurement (temperature_sensor_id = \""
					+ parcelTemperatureSensorID + "\")";

			
			  System.out.println("Registered new statement in the CEP engine: "
			  + expression);
			 

			statement = epService.getEPAdministrator().createEPL(expression);
			this.registerStatement(statement);

//			 statement.addListener(new UpdateListener() {
//			
//			 @Override
//			 public void update(EventBean[] arg0, EventBean[] arg1) {
//			 EventBean event = arg0[0];
//					System.out.println("TemperatureMesurement"
//							+ ": temperature = "
//							+ event.get("temperature") + ", sensor_ID = "
//							+ event.get("temperature_sensor_id")
//							+ ", truck_ID = " + event.get("truck_id"));
//			
//			 }
//			 });
		}

		expression = "insert into SampleAttributeSelection_HumidityMeasurement"
				+ humiditySensorID
				+ " select * from HumidityMeasurement (humidity_sensor_id = \""
				+ humiditySensorID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("HumidityMeasurement" + humiditySensorID
//						+ ": humidity = " + event.get("humidity")
//						+ ", sensor_ID = " + event.get("humidity_sensor_id")
//						+ ", truck_ID = " + event.get("truck_id"));
//		
//		 }
//		 });

		expression = "insert into SampleAttributeSelection_ACUnitMeasurement"
				+ hvacActuatorID
				+ "_fortrainigSetGenerator select * from ACUnitMeasurement (acunit_id = \""
				+ hvacActuatorID + "\")";

		System.out.println("Registered new statement in the CEP engine: "
				+ expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		 statement.addListener(new UpdateListener() {
//		
//		 @Override
//		 public void update(EventBean[] arg0, EventBean[] arg1) {
//		 EventBean event = arg0[0];
//				System.out.println("ACUnitMeasurement" + hvacActuatorID
//						+ ":  state = " + event.get("acunit_active")
//						+ ", target value = " + event.get("acunit_target")
//						+ ", sensor_ID = " + event.get("acunit_id")
//						+ ", truck_ID = " + event.get("truck_id"));
//		
//		 }
//		 });

		expression = "insert into SampleAttributeSelection_CurrentClampMeasurement"
				+ currentClampSensorID
				+ " select * from CurrentClampMeasurement (currentclamp_sensor_id = \""
				+ currentClampSensorID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

//		statement.addListener(new UpdateListener() {
//
//			@Override
//			public void update(EventBean[] arg0, EventBean[] arg1) {
//				EventBean event = arg0[0];
//				System.out.println("CurrentClampMeasurement"
//						+ currentClampSensorID + ": currentclamp = "
//						+ event.get("currentclamp") + ", sensor_ID = "
//						+ event.get("currentclamp_sensor_id") + ", truck_ID = "
//						+ event.get("truck_id"));
//
//			}
//		});

		
		
		for (String parcelTemperatureSensorID : parcelTemperatureSensorIDs) {

			expression = "insert into SampleAttributeSelection_TruckSensorJoinedType"
					+ " select "
					+ " container_temperature_stream_1.temperature as container_temperature_1,"
					+ " container_temperature_stream_2.temperature as container_temperature_2,"
					+ " container_temperature_stream_3.temperature as container_temperature_3,"
					+ " external_temperature_stream.temperature as external_temperature,"
					+ " parcel_temperature_stream.temperature as parcel_temperature,'"
					+ parcelTemperatureSensorID
					+ "' as parcel_temperature_id,"
					+ " humidity_value_stream.humidity as humidity_value,"
					+ " hvac_stream.acunit_active as hvacStateOn,"
					+ " hvac_stream.acunit_target as hvacStateValue,"
					+ " current_clamp_stream.currentclamp as currentClampValue,"
					+ " current_timestamp() as timestamp"
					+ " from "
					+ " SampleAttributeSelection_TemperatureMesurement"
					+ parcelTemperatureSensorID
					+ ".std:lastevent () as container_temperature_stream_1"
					+ ", SampleAttributeSelection_TemperatureMesurement"
					+ containerTemperatureSensorID2
					+ ".std:lastevent () as container_temperature_stream_2"
					+ ", SampleAttributeSelection_TemperatureMesurement"
					+ containerTemperatureSensorID3
					+ ".std:lastevent () as container_temperature_stream_3"
					+ ", SampleAttributeSelection_TemperatureMesurement"
					+ externalContainerTemperatureSensorID
					+ ".std:lastevent () as external_temperature_stream"
					+ ", SampleAttributeSelection_TemperatureMesurement"
					+ parcelTemperatureSensorID
					+ ".std:lastevent () as parcel_temperature_stream"
					+ ", SampleAttributeSelection_HumidityMeasurement"
					+ humiditySensorID
					+ ".std:lastevent () as humidity_value_stream"
					+ ", SampleAttributeSelection_ACUnitMeasurement"
					+ hvacActuatorID
					+ "_fortrainigSetGenerator.std:lastevent () as hvac_stream"
					+ ", SampleAttributeSelection_CurrentClampMeasurement"
					+ currentClampSensorID
					+ ".std:lastevent () as current_clamp_stream"
					+ " output every "
					+ TRUCK_SENSOR_JOINED_TYPE_OUTPUT_RATE
					+ " seconds";

			/*
			 * System.out.println("Registered new statement in the CEP engine: "
			 * + expression);
			 */

			statement = epService.getEPAdministrator().createEPL(expression);
			this.registerStatement(statement);

			statement.addListener(new UpdateListener() {

				@Override
				public void update(EventBean[] arg0, EventBean[] arg1) {

					if (arg0 != null) {
						EventBean event = arg0[0];
						System.out.println("TruckSensorJoinedType: "
								+ "container_temperature_1 = "
								+ event.get("container_temperature_1")
								+ ", container_temperature_2 = "
								+ event.get("container_temperature_2")
								+ ", container_temperature_3 = "
								+ event.get("container_temperature_3")
								+ ", external_temperature = "
								+ event.get("external_temperature")
								+ ", parcel_temperature = "
								+ event.get("parcel_temperature")
								+ ", humidity_value = "
								+ event.get("humidity_value")
								+ ", hvacStateOn = " + event.get("hvacStateOn")
								+ ", hvacStateValue = "
								+ event.get("hvacStateValue")
								+ ", currentClampValue = "
								+ event.get("currentClampValue")
								+ ", timestamp = " + event.get("timestamp")
								+ ", parcelSensorID = " + event.get("parcel_temperature_id"));
					}
				}
			});

		}

	}

}
