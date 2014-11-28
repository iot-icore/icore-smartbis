package com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.siemens.ct.mqttadapter.MqttSubscribeAdapter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.ACUnitEventConverter;
import com.softwareag.transportation.CEPevents.ACUnitEvent;

/**
 * 
 * The ACUnit adapter is used for persisting the data coming from and mqtt topic
 * to the database.
 * 
 * This class implements the MqttSubscribeAdapter (have a look at the
 * mqtt-adapters project).
 * 
 * The method messageReceived is called when a new message is received on the
 * mqtt topic (in string format). It converts the string in a
 * S05HVACActuatorStatusType and using {@link ACUnitMeasurementDAO} stores the
 * contend in the database.
 * 
 * @author dan.puiu
 */
public class ACUnitAdapter extends MqttSubscribeAdapter {

	Logger logger = Logger.getLogger(ACUnitAdapter.class);

	private EPServiceProvider epService;

	public ACUnitAdapter(String topic, int qos, EPServiceProvider epService) {
		super(topic, qos);
		this.epService = epService;

	}

	@Override
	public void messageReceived(String message) {

		ACUnitEvent acUnitEvent = ACUnitEventConverter.convertToObject(message);

		epService.getEPRuntime().sendEvent(acUnitEvent);

	}

}
