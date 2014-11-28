package com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources;

/**
 * 
 * The CurrentClampSensorAdapter adapter is used for persisting the data coming from and mqtt topic
 * to the database.
 * 
 * This class implements the MqttSubscribeAdapter (have a look at the
 * mqtt-adapters project).
 * 
 * The method messageReceived is called when a new message is received on the
 * mqtt topic (in string format). It converts the string in a
 * S03CurrentClampType  and using currentClampMeasurementDAO stores the
 * contend in the database.
 * 
 * @author dan.puiu
 */

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.siemens.ct.mqttadapter.MqttSubscribeAdapter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.CurrentClampEventConverter;
import com.softwareag.transportation.CEPevents.CurrentClampEvent;

public class CurrentClampSensorAdapter extends MqttSubscribeAdapter {

	Logger logger = Logger.getLogger(CurrentClampSensorAdapter.class);

	private EPServiceProvider epService;


	public CurrentClampSensorAdapter(String topic, int qos,
			EPServiceProvider epService) {
		super(topic, qos);
		this.epService = epService;

	}


	@Override
	public void messageReceived(String message) {


		CurrentClampEvent currentClampEvent = CurrentClampEventConverter.convertToObject(message);

		epService.getEPRuntime().sendEvent(currentClampEvent);

	}
}
