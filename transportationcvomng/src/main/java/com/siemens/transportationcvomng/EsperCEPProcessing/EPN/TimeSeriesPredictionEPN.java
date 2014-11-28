package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.siemens.ct.ro.transportation.CEPEventsConverters.TemperaturePredictionEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.TemperaturePredictionOneHourValidationWithACUnitFilteringEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.TemperaturePredictionTwoHoursValidationWithACUnitFilteringEventConverter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.TruckSensorJointEventConverter;
import com.siemens.ct.ro.transportation.commons.MqttTopics;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionOneHourValidationWithACUnitFilteringEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

public class TimeSeriesPredictionEPN extends EsperEPN {

	private final int TRUCK_SENSOR_JOINED_TYPE_OUTPUT_RATE = 15;

	private final int PREDICTION_10_MIN_INT = 20;
	private final int PREDICTION_60_MIN_INT = PREDICTION_10_MIN_INT;// * 6;
	private final int PREDICTION_120_MIN_INT = PREDICTION_10_MIN_INT;// * 12;

	/**
	 * this value is used by the temperature measurement validation statements.
	 * 
	 * every time a temperature value is generated the CEP engine checks if a
	 * prediction event had been generated 10/60/120 minutes ago +/-
	 * PREDICTION_VALIDATION_RANGE
	 */
	public final static int PREDICTION_VALIDATION_RANGE = 5;

	private final static int MQTT_QoS = 0;
	private final static boolean MQTT_MESSAGE_PERSISTENCE = false;

	private EPServiceProvider epService;

	private String containerTemperatureSensorID1;
	private String containerTemperatureSensorID2;
	private String containerTemperatureSensorID3;
	private String humiditySensorID;
	private String hvacActuatorID;
	private String currentClampSensorID;
	private String externalContainerTemperatureSensorID;
	private String parcelTemperatureSensorID;
	private double reportingPeriod;
	private double reportingWindowLength;
	private String packageID;
	private String gatewayID;

	private MqttClient mqttAdaptersContainerForEsperOA;

	public TimeSeriesPredictionEPN(EPServiceProvider epService,
			String packageID, String gatewayID,
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			String parcelTemperatureSensorID, double reportingPeriod,
			double reportingWindowLength,
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
		this.parcelTemperatureSensorID = parcelTemperatureSensorID;
		this.mqttAdaptersContainerForEsperOA = mqttAdaptersContainerForEsperOA;
		this.reportingPeriod = reportingPeriod;
		this.reportingWindowLength = reportingWindowLength;
		this.packageID = packageID;
		this.gatewayID = gatewayID;
	}

	public void addEPN() {
		addTimeSeriesPredictionSampleAttributeSelectionEPN();
		addTimeSeriesPredictionEPN();
		addTimeSeriesPredictionValidationEPN();

	}

	private void addTimeSeriesPredictionValidationEPN() {

		String expression = "create window TemperaturePredictionWindow"
				+ packageID + ".win:time ("
				+ (PREDICTION_120_MIN_INT + PREDICTION_VALIDATION_RANGE)
				+ " sec) as select * from TemperaturePredictions";

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);

		registerStatement(statement);

		expression = "insert into TemperaturePredictionWindow"
				+ packageID
				+ " select * from TemperaturePredictions";

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		// The below functionality is used for creating the prediction
		// validation messages without AC filtering

		//
		// expression = "insert into TemperaturePrediction10MinutesValidation "
		// + " select"
		// + " currentEvent.gatewayID as gatewayID"
		// + ", currentEvent.temperature as currentTemperature"
		// + ", currentEvent.timestamp as timestamp"
		// + ", currentEvent.sensorID as sensorID"
		// +
		// ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempTenMin"
		// +
		// ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
		// + " from TemperatureEvent"
		// + parcelTemperatureSensorID
		// + ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
		// + parcelTemperatureSensorID
		// + " as prediction"
		// +
		// " where (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
		// + PREDICTION_10_MIN_INT
		// + " +"
		// + PREDICTION_VALIDATION_RANGE
		// +
		// ")*1000)) and (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
		// + PREDICTION_10_MIN_INT + " -" + PREDICTION_VALIDATION_RANGE
		// + ")*1000)) ";
		// /*
		// * System.out.println("Registered new statement in the CEP engine: " +
		// * expression);
		// */
		//
		// statement = epService.getEPAdministrator().createEPL(expression);
		//
		// // statement.addListener(new UpdateListener() {
		// //
		// // @Override
		// // public void update(EventBean[] arg0, EventBean[] arg1) {
		// // EventBean event = arg0[0];
		// // // System.out
		// // //
		// .println("TemperaturePrediction10MinutesValidation: gatewayID = "
		// // // + event.get("gatewayID")
		// // // + ", currentTemperature = "
		// // // + event.get("currentTemperature")
		// // // + ", pedictedTempTenMin = "
		// // // + event.get("pedictedTempTenMin")
		// // // + ", timestamp = "
		// // // + event.get("timestamp")
		// // // + ", predictedTimestamp "
		// // // + event.get("predictedTimestamp")
		// // // + ", timestam diference "
		// // // + (Long.parseLong(event.get(
		// // // "predictedTimestamp").toString()) - Long
		// // // .parseLong(event.get("timestamp")
		// // // .toString())));
		// //
		// // // CEPTemperaturePredictionTenMinutesValidation cValidation =
		// // // new CEPTemperaturePredictionTenMinutesValidation();
		// // // cValidation.setCurrentTemperature(Double.parseDouble(event.get(
		// // // "currentTemperature").toString()));
		// // // cValidation
		// // //
		// .setDriverVersion(CEPMessagesConstants.CEP_MESSAGE_DRIVER_VERSION);
		// // // cValidation.setGateway(event.get("gatewayID").toString());
		// // // cValidation
		// // // .setKindType(CEPMessagesConstants.CEP_EVENT_KIND_TYPE);
		// // // cValidation.setPedictedTempTenMin(Double.parseDouble(event.get(
		// // // "pedictedTempTenMin").toString()));
		// // // cValidation.setPredictedTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("predictedTimestamp")
		// // // .toString())));
		// // // cValidation
		// // // .setRequestID(CEPMessagesConstants.CEP_MESSAGE_REQUEST_ID);
		// // // cValidation.setTemperatureSensorId(event.get(
		// // // "sensorID").toString());
		// // // cValidation.setTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("timestamp").toString())));
		// // // cValidation.setType(null);
		// // //
		// // // String message = cValidation.generateMessage();
		// // //
		// // // try {
		// // // mqttAdaptersContainerForEsperOA
		// // // .publish(
		// // //
		// MqttTopics.TEMERATURE_PREDICTION_TEN_MINUTES_VALIDATION_MQTT_TOPIC,
		// // // message.getBytes(), MQTT_QoS,
		// // // MQTT_MESSAGE_PERSISTENCE);
		// // // } catch (MqttPersistenceException e) {
		// // // // TODO Auto-generated catch block
		// // // e.printStackTrace();
		// // // } catch (MqttException e) {
		// // // System.out.println("Unable to publis mqtt message: "
		// // // + message);
		// // // e.printStackTrace();
		// // // }
		// //
		// // }
		// // });
		//
		// expression = "insert into TemperaturePrediction60MinutesValidation "
		// + " select"
		// + " currentEvent.gatewayID as gatewayID"
		// + ", currentEvent.temperature as currentTemperature"
		// + ", currentEvent.timestamp as timestamp"
		// + ", currentEvent.sensorID as sensorID"
		// +
		// ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempTenMin"
		// +
		// ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
		// + " from TemperatureEvent"
		// + parcelTemperatureSensorID
		// + ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
		// + parcelTemperatureSensorID
		// + " as prediction"
		// +
		// " where (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
		// + PREDICTION_60_MIN_INT
		// + " +"
		// + PREDICTION_VALIDATION_RANGE
		// +
		// ")*1000)) and (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
		// + PREDICTION_60_MIN_INT + " -" + PREDICTION_VALIDATION_RANGE
		// + ")*1000)) ";
		// /*
		// * System.out.println("Registered new statement in the CEP engine: " +
		// * expression);
		// */
		//
		// statement = epService.getEPAdministrator().createEPL(expression);
		//
		// // statement.addListener(new UpdateListener() {
		// //
		// // @Override
		// // public void update(EventBean[] arg0, EventBean[] arg1) {
		// // EventBean event = arg0[0];
		// // // System.out
		// // //
		// .println("TemperaturePrediction60MinutesValidation: gatewayID = "
		// // // + event.get("gatewayID")
		// // // + ", currentTemperature = "
		// // // + event.get("currentTemperature")
		// // // + ", pedictedTempTenMin = "
		// // // + event.get("pedictedTempTenMin")
		// // // + ", timestamp = "
		// // // + event.get("timestamp")
		// // // + ", predictedTimestamp "
		// // // + event.get("predictedTimestamp")
		// // // + ", timestam diference "
		// // // + (Long.parseLong(event.get(
		// // // "predictedTimestamp").toString()) - Long
		// // // .parseLong(event.get("timestamp")
		// // // .toString())));
		// //
		// // // CEPTemperaturePredictionOneHourValidation cValidation = new
		// // // CEPTemperaturePredictionOneHourValidation();
		// // // cValidation.setCurrentTemperature(Double.parseDouble(event.get(
		// // // "currentTemperature").toString()));
		// // // cValidation
		// // //
		// .setDriverVersion(CEPMessagesConstants.CEP_MESSAGE_DRIVER_VERSION);
		// // // cValidation.setGateway_ID(event.get("gatewayID").toString());
		// // // cValidation
		// // // .setKindType(CEPMessagesConstants.CEP_EVENT_KIND_TYPE);
		// // // cValidation.setPredictedTempOneHour(Double.parseDouble(event
		// // // .get("pedictedTempTenMin").toString()));
		// // // cValidation.setPredictedTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("predictedTimestamp")
		// // // .toString())));
		// // // cValidation
		// // // .setRequestID(CEPMessagesConstants.CEP_MESSAGE_REQUEST_ID);
		// // // cValidation.setTemperatureSensorId(event.get(
		// // // "sensorID").toString());
		// // // cValidation.setTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("timestamp").toString())));
		// // // cValidation.setType(null);
		// // //
		// // // String message = cValidation.generateMessage();
		// // //
		// // // try {
		// // // mqttAdaptersContainerForEsperOA
		// // // .publish(
		// // //
		// MqttTopics.TEMERATURE_PREDICTION_ONE_HOUR_VALIDATION_MQTT_TOPIC,
		// // // message.getBytes(), MQTT_QoS,
		// // // MQTT_MESSAGE_PERSISTENCE);
		// // // } catch (MqttPersistenceException e) {
		// // // // TODO Auto-generated catch block
		// // // e.printStackTrace();
		// // // } catch (MqttException e) {
		// // // System.out.println("Unable to publis mqtt message: "
		// // // + message);
		// // // e.printStackTrace();
		// // // }
		// //
		// // }
		// // });
		//
		// expression = "insert into TemperaturePrediction120MinutesValidation "
		// + " select"
		// + " currentEvent.gatewayID as gatewayID"
		// + ", currentEvent.temperature as currentTemperature"
		// + ", currentEvent.timestamp as timestamp"
		// + ", currentEvent.sensorID as sensorID"
		// +
		// ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempTenMin"
		// +
		// ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
		// + " from TemperatureEvent"
		// + parcelTemperatureSensorID
		// + ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
		// + parcelTemperatureSensorID
		// + " as prediction"
		// +
		// " where (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
		// + PREDICTION_120_MIN_INT
		// + " +"
		// + PREDICTION_VALIDATION_RANGE
		// +
		// ")*1000)) and (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
		// + PREDICTION_120_MIN_INT + " -" + PREDICTION_VALIDATION_RANGE
		// + ")*1000)) ";
		// /*
		// * System.out.println("Registered new statement in the CEP engine: " +
		// * expression);
		// */
		//
		// statement = epService.getEPAdministrator().createEPL(expression);
		//
		// // statement.addListener(new UpdateListener() {
		// //
		// // @Override
		// // public void update(EventBean[] arg0, EventBean[] arg1) {
		// // EventBean event = arg0[0];
		// // // System.out
		// // //
		// .println("TemperaturePrediction120MinutesValidation: gatewayID = "
		// // // + event.get("gatewayID")
		// // // + ", currentTemperature = "
		// // // + event.get("currentTemperature")
		// // // + ", pedictedTempTenMin = "
		// // // + event.get("pedictedTempTenMin")
		// // // + ", timestamp = "
		// // // + event.get("timestamp")
		// // // + ", predictedTimestamp "
		// // // + event.get("predictedTimestamp")
		// // // + ", timestam diference "
		// // // + (Long.parseLong(event.get(
		// // // "predictedTimestamp").toString()) - Long
		// // // .parseLong(event.get("timestamp")
		// // // .toString())));
		// //
		// // // CEPTemperaturePredictionTwoHoursValidation cValidation = new
		// // // CEPTemperaturePredictionTwoHoursValidation();
		// // // cValidation.setCurrentTemperature(Double.parseDouble(event.get(
		// // // "currentTemperature").toString()));
		// // // cValidation
		// // //
		// .setDriverVersion(CEPMessagesConstants.CEP_MESSAGE_DRIVER_VERSION);
		// // // cValidation.setGateway_ID(event.get("gatewayID").toString());
		// // // cValidation
		// // // .setKindType(CEPMessagesConstants.CEP_EVENT_KIND_TYPE);
		// // // cValidation.setPredictedTempTwoHours(Double.parseDouble(event
		// // // .get("pedictedTempTenMin").toString()));
		// // // cValidation.setPredictedTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("predictedTimestamp")
		// // // .toString())));
		// // // cValidation
		// // // .setRequestID(CEPMessagesConstants.CEP_MESSAGE_REQUEST_ID);
		// // // cValidation.setTemperatureSensorId(event.get(
		// // // "sensorID").toString());
		// // // cValidation.setTimestamp(XMLGregorianCalendarConverter
		// // // .convertTimestampToXMLGregorialCalendar(Long
		// // // .parseLong(event.get("timestamp").toString())));
		// // // cValidation.setType(null);
		// // //
		// // // String message = cValidation.generateMessage();
		// // //
		// // // try {
		// // // mqttAdaptersContainerForEsperOA
		// // // .publish(
		// // //
		// MqttTopics.TEMERATURE_PREDICTION_TWO_HOURS_VALIDATION_MQTT_TOPIC,
		// // // message.getBytes(), MQTT_QoS,
		// // // MQTT_MESSAGE_PERSISTENCE);
		// // // } catch (MqttPersistenceException e) {
		// // // // TODO Auto-generated catch block
		// // // e.printStackTrace();
		// // // } catch (MqttException e) {
		// // // System.out.println("Unable to publis mqtt message: "
		// // // + message);
		// // // e.printStackTrace();
		// // // }
		// //
		// // }
		// // });

		expression = "insert into ACUnitStateChanged" + hvacActuatorID
				+ packageID + " select * from ACUnitEvent"
				+ hvacActuatorID + parcelTemperatureSensorID
				+ " where (prior(1,acUnitState) != acUnitState)"
				+ " or (prior(1,acUnitTarghetValue) != acUnitTarghetValue)";

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		//
		// System.out.println("ACUnit state changed");
		//
		// }
		// });

		expression = "create window ACUnitStateChanged" + hvacActuatorID
				+ packageID + "last10minutes.win:time("
				+ (PREDICTION_10_MIN_INT + PREDICTION_VALIDATION_RANGE)
				+ " sec)" + " select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into ACUnitStateChanged" + hvacActuatorID
				+ packageID
				+ "last10minutes select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into TemperaturePrediction10MinutesValidationWithACUnitFiltering "
				+ " select"
				+ " currentEvent.gatewayID as gatewayID"
				+ ", currentEvent.temperature as currentTemperature"
				+ ", currentEvent.timestamp as timestamp"
				+ ", currentEvent.sensorID as sensorID"
				+ ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempTenMin"
				+ ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
				+ " from TemperatureEvent"
				+ parcelTemperatureSensorID
				+ ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
				+ packageID
				+ " as prediction"
				+ " where"
				+ " (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
				+ PREDICTION_10_MIN_INT
				+ " +"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000))"
				+ " and"
				+ " (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
				+ PREDICTION_10_MIN_INT
				+ " -"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000))"
				+ " and "
				+ "((select count(*) from ACUnitStateChanged"
				+ hvacActuatorID
				+ packageID + "last10minutes) = 0) ";

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
				System.out
						.println("TemperaturePrediction10MinutesValidationWithACUnitFiltering: gatewayID = "
								+ event.get("gatewayID")
								+ ", currentTemperature = "
								+ event.get("currentTemperature")
								+ ", pedictedTempTenMin = "
								+ event.get("pedictedTempTenMin")
								+ ", timestamp = "
								+ event.get("timestamp")
								+ ", predictedTimestamp "
								+ event.get("predictedTimestamp")
								+ ", timestam diference "
								+ (Long.parseLong(event.get(
										"predictedTimestamp").toString()) - Long
										.parseLong(event.get("timestamp")
												.toString())));

				TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent = new TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent();
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setGatewayId(gatewayID);
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setPredictedTempTenMinutes(Double.parseDouble(event
								.get("pedictedTempTenMin").toString()));
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setPredictedTempTenMinutesTimestamp(Long
								.parseLong(event.get("predictedTimestamp")
										.toString()));
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setTemperatureEventTimestamp(Long.parseLong(event.get(
								"timestamp").toString()));
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setTemperatureSensorID(event.get("sensorID")
								.toString());
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setTemperatureValue(Double.parseDouble(event.get(
								"currentTemperature").toString()));
				temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent
						.setPackageID(packageID);

				String message = TemperaturePredictionTenMinutesValidationWithACUnitFilteringEventConverter
						.convertToXML(temperaturePredictionTenMinutesValidationWithACUnitFilteringEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.TEMERATURE_PREDICTION_TEN_MINUTES_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MqttException e) {
					System.out.println("Unable to publis mqtt message: "
							+ message);
					e.printStackTrace();
				}

			}
		});

		expression = "create window ACUnitStateChanged" + hvacActuatorID
				+ packageID + "last60minutes.win:time(("
				+ PREDICTION_60_MIN_INT + " + " + PREDICTION_VALIDATION_RANGE
				+ ") sec)" + " select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into ACUnitStateChanged" + hvacActuatorID
				+ packageID
				+ "last60minutes select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into TemperaturePrediction60MinutesValidationWithACUnitFiltering "
				+ " select"
				+ " currentEvent.gatewayID as gatewayID"
				+ ", currentEvent.temperature as currentTemperature"
				+ ", currentEvent.timestamp as timestamp"
				+ ", currentEvent.sensorID as sensorID"
				+ ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempOneHour"
				+ ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
				+ " from TemperatureEvent"
				+ parcelTemperatureSensorID
				+ ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
				+ packageID
				+ " as prediction"
				+ " where (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
				+ PREDICTION_60_MIN_INT
				+ " +"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000)) and (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
				+ PREDICTION_60_MIN_INT
				+ " -"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000))"
				+ " and ((select count(*) from ACUnitStateChanged"
				+ hvacActuatorID
				+ packageID
				+ "last60minutes) = 0) ";
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
				System.out
						.println("TemperaturePrediction60MinutesValidationWithACUnitFiltering: gatewayID = "
								+ event.get("gatewayID")
								+ ", currentTemperature = "
								+ event.get("currentTemperature")
								+ ", pedictedTempTenMin = "
								+ event.get("pedictedTempOneHour")
								+ ", timestamp = "
								+ event.get("timestamp")
								+ ", predictedTimestamp "
								+ event.get("predictedTimestamp")
								+ ", timestam diference "
								+ (Long.parseLong(event.get(
										"predictedTimestamp").toString()) - Long
										.parseLong(event.get("timestamp")
												.toString())));

				TemperaturePredictionOneHourValidationWithACUnitFilteringEvent temperaturePredictionOneHourValidationWithACUnitFilteringEvent = new TemperaturePredictionOneHourValidationWithACUnitFilteringEvent();
				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setGatewayId(gatewayID);
				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setPredictedTempOneHour(Double.parseDouble(event.get(
								"pedictedTempOneHour").toString()));
				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setPredictedTempOneHourTimestamp(Long.parseLong(event
								.get("predictedTimestamp").toString()));

				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setTemperatureSensorID(event.get("sensorID")
								.toString());
				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setTemperatureValue(Double.parseDouble(event.get(
								"currentTemperature").toString()));
				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setPackageID(packageID);

				temperaturePredictionOneHourValidationWithACUnitFilteringEvent
						.setTemperatureEventTimestamp(Long.parseLong(event.get(
								"timestamp").toString()));

				String message = TemperaturePredictionOneHourValidationWithACUnitFilteringEventConverter
						.convertToXML(temperaturePredictionOneHourValidationWithACUnitFilteringEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.TEMERATURE_PREDICTION_ONE_HOUR_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MqttException e) {
					System.out.println("Unable to publis mqtt message: "
							+ message);
					e.printStackTrace();
				}

			}
		});

		expression = "create window ACUnitStateChanged" + hvacActuatorID
				+ packageID + "last120minutes.win:time(("
				+ PREDICTION_120_MIN_INT + " + " + PREDICTION_VALIDATION_RANGE
				+ ") sec)" + " select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into ACUnitStateChanged" + hvacActuatorID
				+ packageID
				+ "last120minutes select * from ACUnitStateChanged"
				+ hvacActuatorID + packageID;
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		expression = "insert into TemperaturePrediction120MinutesValidationWithACUnitFiltering "
				+ " select"
				+ " currentEvent.gatewayID as gatewayID"
				+ ", currentEvent.temperature as currentTemperature"
				+ ", currentEvent.timestamp as timestamp"
				+ ", currentEvent.sensorID as sensorID"
				+ ", prediction.temperaturePrediction.pedictedTempTenMin as pedictedTempTwoHours"
				+ ", prediction.temperaturePrediction.timestamp as predictedTimestamp"
				+ " from TemperatureEvent"
				+ parcelTemperatureSensorID
				+ ".std:lastevent() as currentEvent, TemperaturePredictionWindow"
				+ packageID
				+ " as prediction"
				+ " where (prediction.temperaturePrediction.timestamp > (current_timestamp() - ("
				+ PREDICTION_120_MIN_INT
				+ " +"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000)) and (prediction.temperaturePrediction.timestamp < (current_timestamp() - ("
				+ PREDICTION_120_MIN_INT
				+ " -"
				+ PREDICTION_VALIDATION_RANGE
				+ ")*1000))"
				+ " and ((select count(*) from ACUnitStateChanged"
				+ hvacActuatorID
				+ packageID
				+ "last120minutes) = 0) ";
		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];
				System.out
						.println("TemperaturePrediction120MinutesValidationWithACUnitFiltering: gatewayID = "
								+ event.get("gatewayID")
								+ ", currentTemperature = "
								+ event.get("currentTemperature")
								+ ", pedictedTempTenMin = "
								+ event.get("pedictedTempTwoHours")
								+ ", timestamp = "
								+ event.get("timestamp")
								+ ", predictedTimestamp "
								+ event.get("predictedTimestamp")
								+ ", timestam diference "
								+ (Long.parseLong(event.get(
										"predictedTimestamp").toString()) - Long
										.parseLong(event.get("timestamp")
												.toString())));

				TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent = new TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent();

				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setGatewayId(gatewayID);
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setPredictedTempTwoHours(Double.parseDouble(event.get(
								"pedictedTempTwoHours").toString()));
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setPredictedTempTwoHourTimestamp(Long.parseLong(event
								.get("predictedTimestamp").toString()));
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setTemperatureEventTimestamp(Long.parseLong(event.get(
								"timestamp").toString()));
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setTemperatureSensorID(event.get("sensorID")
								.toString());
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setTemperatureValue(Double.parseDouble(event.get(
								"currentTemperature").toString()));
				temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent
						.setPackageID(packageID);

				String message = TemperaturePredictionTwoHoursValidationWithACUnitFilteringEventConverter
						.convertToXML(temperaturePredictionTwoHoursValidationWithACUnitFilteringEvent);

				try {
					mqttAdaptersContainerForEsperOA
							.publish(
									MqttTopics.TEMERATURE_PREDICTION_TWO_HOURS_VALIDATION_WITH_AC_UNIT_FILTERING_MQTT_TOPIC,
									message.getBytes(), MQTT_QoS,
									MQTT_MESSAGE_PERSISTENCE);
				} catch (MqttPersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MqttException e) {
					System.out.println("Unable to publis mqtt message: "
							+ message);
					e.printStackTrace();
				}

			}
		});

	}

	private void addTimeSeriesPredictionSampleAttributeSelectionEPN() {

		String expression = "insert into TemperatureEvent"
				+ containerTemperatureSensorID1
				+ " select * from TemperatureEvent (sensorID = \""
				+ containerTemperatureSensorID1 + "\", gatewayID = \""
				+ gatewayID + "\")";

		// System.out.println("Registered new statement in the CEP engine: " +
		// expression);
		//

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);
		registerStatement(statement);

		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("TemperatureEvent"
		// + containerTemperatureSensorID1 + ": temperature = "
		// + event.get("temperature") + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID") + ", timestamp = "
		// + event.get("timestamp"));
		//
		// }
		// });

		expression = "insert into TemperatureEvent"
				+ containerTemperatureSensorID2
				+ " select * from TemperatureEvent (sensorID = \""
				+ containerTemperatureSensorID2 + "\", gatewayID = \""
				+ gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("TemperatureEvent"
		// + containerTemperatureSensorID2 + ": temperature = "
		// + event.get("temperature") + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into TemperatureEvent"
				+ containerTemperatureSensorID3
				+ " select * from TemperatureEvent (sensorID = \""
				+ containerTemperatureSensorID3 + "\", gatewayID = \""
				+ gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("TemperatureEvent"
		// + containerTemperatureSensorID3 + ": temperature = "
		// + event.get("temperature") + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		// }
		// });

		expression = "insert into TemperatureEvent"
				+ externalContainerTemperatureSensorID
				+ " select * from TemperatureEvent (sensorID = \""
				+ externalContainerTemperatureSensorID + "\", gatewayID = \""
				+ gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("TemperatureEvent"
		// + externalContainerTemperatureSensorID
		// + ": temperature = " + event.get("temperature")
		// + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into TemperatureEvent" + parcelTemperatureSensorID
				+ " select * from TemperatureEvent (sensorID = \""
				+ parcelTemperatureSensorID + "\", gatewayID = \"" + gatewayID
				+ "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("TemperatureEvent"
		// + parcelTemperatureSensorID + ": temperature = "
		// + event.get("temperature") + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into HumidityEvent" + humiditySensorID
				+ " select * from HumidityEvent (sensorID = \""
				+ humiditySensorID + "\", gatewayID = \"" + gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("HumidityEvent" + humiditySensorID
		// + ": humidity = " + event.get("humidity")
		// + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into ACUnitEvent" + hvacActuatorID
				+ parcelTemperatureSensorID
				+ " select * from ACUnitEvent (sensorID = \"" + hvacActuatorID
				+ "\", gatewayID = \"" + gatewayID + "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("ACUnitEvent" + hvacActuatorID
		// + ":  state = " + event.get("acUnitState")
		// + ", target value = " + event.get("acUnitTarghetValue")
		// + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into CurrentClampEvent" + currentClampSensorID
				+ " select * from CurrentClampEvent (sensorID = \""
				+ currentClampSensorID + "\", gatewayID = \"" + gatewayID
				+ "\")";

		/*
		 * System.out.println("Registered new statement in the CEP engine: " +
		 * expression);
		 */

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		// System.out.println("CurrentClampEvent"
		// + currentClampSensorID + ": currentclamp = "
		// + event.get("currentClampValue") + ", sensor_ID = "
		// + event.get("sensorID") + ", truck_ID = "
		// + event.get("gatewayID"));
		//
		// }
		// });

		expression = "insert into TruckSensorJointEvent"
				+ " select "
				+ " container_temperature_stream_1.temperature as container_temperature_1,"
				+ " container_temperature_stream_2.temperature as container_temperature_2,"
				+ " container_temperature_stream_3.temperature as container_temperature_3,"
				+ " external_temperature_stream.temperature as external_temperature,"
				+ " parcel_temperature_stream.temperature as parcel_temperature,'"
				+ parcelTemperatureSensorID
				+ "' as parcel_temperature_id,"
				+ " humidity_value_stream.humidity as humidity_value,"
				+ " hvac_stream.acUnitState as hvacStateOn,"
				+ " hvac_stream.acUnitTarghetValue as hvacStateValue,"
				+ " current_clamp_stream.currentClampValue as currentClampValue,"
				+ " current_timestamp() as timestamp"
				+ " from "
				+ " TemperatureEvent"
				+ parcelTemperatureSensorID
				+ ".std:lastevent () as container_temperature_stream_1"
				+ ", TemperatureEvent"
				+ containerTemperatureSensorID2
				+ ".std:lastevent () as container_temperature_stream_2"
				+ ", TemperatureEvent"
				+ containerTemperatureSensorID3
				+ ".std:lastevent () as container_temperature_stream_3"
				+ ", TemperatureEvent"
				+ externalContainerTemperatureSensorID
				+ ".std:lastevent () as external_temperature_stream"
				+ ", TemperatureEvent"
				+ parcelTemperatureSensorID
				+ ".std:lastevent () as parcel_temperature_stream"
				+ ", HumidityEvent"
				+ humiditySensorID
				+ ".std:lastevent () as humidity_value_stream"
				+ ", ACUnitEvent"
				+ hvacActuatorID
				+ parcelTemperatureSensorID
				+ ".std:lastevent () as hvac_stream"
				+ ", CurrentClampEvent"
				+ currentClampSensorID
				+ ".std:lastevent () as current_clamp_stream"
				+ " output every "
				+ TRUCK_SENSOR_JOINED_TYPE_OUTPUT_RATE
				+ " sec";

		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {

				if (arg0 != null) {
					EventBean event = arg0[0];

					// System.out.println("TruckSensorJoinedType: "
					// + "container_temperature_1 = "
					// + event.get("container_temperature_1")
					// + ", container_temperature_2 = "
					// + event.get("container_temperature_2")
					// + ", container_temperature_3 = "
					// + event.get("container_temperature_3")
					// + ", external_temperature = "
					// + event.get("external_temperature")
					// + ", parcel_temperature = "
					// + event.get("parcel_temperature")
					// + ", humidity_value = "
					// + event.get("humidity_value") + ", hvacStateOn = "
					// + event.get("hvacStateOn") + ", hvacStateValue = "
					// + event.get("hvacStateValue")
					// + ", currentClampValue = "
					// + event.get("currentClampValue") + ", timestamp = "
					// + event.get("timestamp"));

					TruckSensorJointEvent truckSensorJoinedType = new TruckSensorJointEvent();
					truckSensorJoinedType.setContainerTemperature1Value(Double
							.parseDouble(event.get("container_temperature_1")
									.toString()));
					truckSensorJoinedType.setContainerTemperature2Value(Double
							.parseDouble(event.get("container_temperature_2")
									.toString()));
					truckSensorJoinedType.setContainerTemperature3Value(Double
							.parseDouble(event.get("container_temperature_3")
									.toString()));
					truckSensorJoinedType
							.setContainerTemperatureSensorId1(containerTemperatureSensorID1);
					truckSensorJoinedType
							.setContainerTemperatureSensorId2(containerTemperatureSensorID2);
					truckSensorJoinedType
							.setContainerTemperatureSensorId3(containerTemperatureSensorID3);
					truckSensorJoinedType.setCurrentClampValue(Double
							.parseDouble(event.get("currentClampValue")
									.toString()));
					truckSensorJoinedType
							.setCurrentClampSensorId(currentClampSensorID);
					truckSensorJoinedType
							.setExternalTemperatureId(externalContainerTemperatureSensorID);
					truckSensorJoinedType.setExternalTemperatureValue(Double
							.parseDouble(event.get("external_temperature")
									.toString()));
					truckSensorJoinedType.setGatewayId(gatewayID);
					truckSensorJoinedType.setHumiditySensorId(humiditySensorID);
					truckSensorJoinedType.setHumidityValue(Double
							.parseDouble(event.get("humidity_value").toString()));
					truckSensorJoinedType.setAcUnitActuatorId(hvacActuatorID);
					truckSensorJoinedType.setAcUnitState(Boolean
							.parseBoolean(event.get("hvacStateOn").toString()));
					truckSensorJoinedType.setAcUnitTarghetValue(Double
							.parseDouble(event.get("hvacStateValue").toString()));
					truckSensorJoinedType
							.setParcelTemperatureSensorId(parcelTemperatureSensorID);
					truckSensorJoinedType.setParcelTemperatureValue(Double
							.parseDouble(event.get("parcel_temperature")
									.toString()));
					truckSensorJoinedType.setTimestamp(Long.parseLong(event
							.get("timestamp").toString()));

					String message = TruckSensorJointEventConverter
							.convertToXML(truckSensorJoinedType);

					try {
						mqttAdaptersContainerForEsperOA.publish(
								MqttTopics.TRUCK_SENSOR_JOINED_TYPE,
								message.getBytes(), MQTT_QoS,
								MQTT_MESSAGE_PERSISTENCE);
					} catch (MqttPersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttException e) {
						System.out.println("Unable to publis mqtt message: "
								+ message);
						e.printStackTrace();
					}

				}
			}
		});

		expression = "insert into TimeSeriesPredictionsGroupSamples select timeSeriesPredictionsGroupSamples(*) as temperaturePredictionGroupSample"
				+ " from TruckSensorJointEvent.win:time("
				+ reportingWindowLength + " sec)";
		// System.out.println("Registered new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);

		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// @Override
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		// EventBean event = arg0[0];
		//
		//
		// // TimeSeriesPredictionSample timeSeriesPredictionSample =
		// // (TimeSeriesPredictionSample) event
		// // .get("temperaturePredictionGroupSample");
		//
		// // System.out
		// // .println("new time series perdiction sample (unfiltere) "
		// // + timeSeriesPredictionSample.getEvents());
		//
		// }
		// });

		expression = "insert into TemperaturePredictionSample "
				+ "select last(temperaturePredictionGroupSample) as temperaturePredictionGroupSampleFiltered "
				+ "from TimeSeriesPredictionsGroupSamples output snapshot every "
				+ reportingPeriod + " second";

		// System.out.println("Registerd new statement in the CEP engine: "
		// + expression);

		statement = epService.getEPAdministrator().createEPL(expression);
		this.registerStatement(statement);

		// statement.addListener(new UpdateListener() {
		//
		// public void update(EventBean[] arg0, EventBean[] arg1) {
		//
		// System.out.print(System.currentTimeMillis() + ": Test este: ");
		//
		// for (int i = 0; i < arg0.length; i++)
		// System.out.print(arg0[i]
		// .get("temperaturePredictionGroupSampleFiltered")
		// + " ");
		//
		// System.out.println();
		//
		// }
		// });

	}

	private void addTimeSeriesPredictionEPN() {

		String expression = "insert into TemperaturePredictions "
				+ "select predictParcelTemperature(temperaturePredictionGroupSampleFiltered) as temperaturePrediction"
				+ " from TemperaturePredictionSample";

		// System.out.println("Registerd new statement in the CEP engine: "
		// + expression);

		EPStatement statement = epService.getEPAdministrator().createEPL(
				expression);

		this.registerStatement(statement);

		statement.addListener(new UpdateListener() {

			@Override
			public void update(EventBean[] arg0, EventBean[] arg1) {
				EventBean event = arg0[0];

				if (event != null) {

					TemperaturePredictionEvent temperaturePredictionEvent = (TemperaturePredictionEvent) event
							.get("temperaturePrediction");

					temperaturePredictionEvent.setGatewayId(gatewayID);
					temperaturePredictionEvent
							.setTemperatureSensorID(parcelTemperatureSensorID);
					temperaturePredictionEvent.setPackageID(packageID);

					String message = TemperaturePredictionEventConverter
							.convertToXML(temperaturePredictionEvent);

					try {
						mqttAdaptersContainerForEsperOA.publish(
								MqttTopics.TEMPERATURE_PREDICTION_MQTT_TOPIC,
								message.getBytes(), MQTT_QoS,
								MQTT_MESSAGE_PERSISTENCE);
					} catch (MqttPersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttException e) {
						System.out.println("Unable to publis mqtt message: "
								+ message);
						e.printStackTrace();
					}
				}

			}
		});

	}

}
