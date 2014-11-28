/**
 * 
 * The HumiditySensorAdapter is used for persisting the data coming from and mqtt topic
 * to the database.
 * 
 * This class implements the MqttSubscribeAdapter (have a look at the
 * mqtt-adapters project).
 * 
 * The method messageReceived is called when a new message is received on the
 * mqtt topic (in string format). It converts the string in a
 * S02HumidityType and using HumidityMeasurementDAO stores the
 * contend in the database.
 * 
 * @author dan.puiu
 */
package com.siemens.transportationcvomng.EsperCEPProcessing.ICoreResources;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.siemens.ct.mqttadapter.MqttSubscribeAdapter;
import com.siemens.ct.ro.transportation.CEPEventsConverters.HumidityEventConverter;
import com.softwareag.transportation.CEPevents.HumidityEvent;

/**
 * @author dan.puiu
 * 
 */

public class HumiditySensorAdapter extends MqttSubscribeAdapter {

	Logger logger = Logger.getLogger(HumiditySensorAdapter.class);

	private EPServiceProvider epService;



	public HumiditySensorAdapter(String topic, int qos,
			EPServiceProvider epService) {
		super(topic, qos);
		this.epService = epService;
	}

	
	@Override
	public void messageReceived(String message) {

		HumidityEvent humidityEvent = HumidityEventConverter.convertToObject(message);

			epService.getEPRuntime().sendEvent(humidityEvent);
	}
}
