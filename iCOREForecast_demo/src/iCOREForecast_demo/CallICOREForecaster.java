/**
 * 
 */
package iCOREForecast_demo;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

/**
 * @author Lucian Sasu
 *
 */
public class CallICOREForecaster {

	/**
	 * @param args
	 * @throws DatatypeConfigurationException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws DatatypeConfigurationException, InterruptedException {
		
		//put the path here
		//String pathToSerializedFile = "D:\\proiecte\\iCORE\\svn\\Developement\\iCOREForecast\\serialized\\icoreForecaster.ser";
		String pathToSerializedFile = "serialized/icoreForecaster.ser";
		
		//use the serialized form from the harddisk
		ICOREForecaster forecaster = ICORESerializer.deserialize(pathToSerializedFile);
		
		//prepare at least one TruckSensorJoinedType object before asking for forecasting
		TruckSensorJoinedType truckSensorJoinedType1 = new TruckSensorJoinedType();
		
		truckSensorJoinedType1.setContainerTemperature1(25.0);
		truckSensorJoinedType1.setContainerTemperature2(25);
		truckSensorJoinedType1.setContainerTemperature3(25.0);
		truckSensorJoinedType1.setContainerTemperatureSensorId1("sensor1ID");
		truckSensorJoinedType1.setContainerTemperatureSensorId2("sensor2ID");
		truckSensorJoinedType1.setContainerTemperatureSensorId3("sensor3ID");
		truckSensorJoinedType1.setCurrentClampSensorId("currentClamsSensorID");
		truckSensorJoinedType1.setCurrentClampValue(0);
		truckSensorJoinedType1.setExternalTemperature(30.1);
		truckSensorJoinedType1.setExternalTemperatureId("externalTempSensID");
		truckSensorJoinedType1.setGatewayId("gatewayID");
		truckSensorJoinedType1.setHumiditySensorId("humSensorID");
		truckSensorJoinedType1.setHumidityTemperature(0.7);
		truckSensorJoinedType1.setHvacActuatorId("hvacActuatorID");
		truckSensorJoinedType1.setHvacStateOn(false);
		
		truckSensorJoinedType1.setParcelTemperature(19);
		GregorianCalendar gregorianCalendar1 = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar1);
		truckSensorJoinedType1.setTimestamp(now);
		
		forecaster.recordNewMeasurement(truckSensorJoinedType1);
		//you may ask for forecaster.forecast() right now, if you want
		//forecaster.forecast();
		
		//sleep 2 seconds
		System.out.println("Sleeping 2 seconds to make sure the two timestamps are different");
		Thread.sleep(2000);
		
		//another measurement is prepared; this is optional, though
		TruckSensorJoinedType truckSensorJoinedType2 = new TruckSensorJoinedType();
		
		truckSensorJoinedType2.setContainerTemperature1(25.0);
		truckSensorJoinedType2.setContainerTemperature2(25);
		truckSensorJoinedType2.setContainerTemperature3(25.0);
		truckSensorJoinedType2.setContainerTemperatureSensorId1("sensor1ID");
		truckSensorJoinedType2.setContainerTemperatureSensorId2("sensor2ID");
		truckSensorJoinedType2.setContainerTemperatureSensorId3("sensor3ID");
		truckSensorJoinedType2.setCurrentClampSensorId("currentClamsSensorID");
		truckSensorJoinedType2.setCurrentClampValue(0);
		truckSensorJoinedType2.setExternalTemperature(30.1);
		truckSensorJoinedType2.setExternalTemperatureId("externalTempSensID");
		truckSensorJoinedType2.setGatewayId("gatewayID");//mandatory: the same gateway ID as before!
		//that is: a series of measurements comes from the same group of sensors (SAG agreed this in the prev telco), and then you may call for a forecast to be done
		truckSensorJoinedType2.setHumiditySensorId("humSensorID");
		truckSensorJoinedType2.setHumidityTemperature(0.7);
		truckSensorJoinedType2.setHvacActuatorId("hvacActuatorID");
		truckSensorJoinedType2.setHvacStateOn(false);
		truckSensorJoinedType2.setParcelTemperature(19);
		GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now2 = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar2);
		truckSensorJoinedType2.setTimestamp(now2);
		forecaster.recordNewMeasurement(truckSensorJoinedType2);
		
		forecaster.forecast();
		
		System.out.println("Done");
	}

}
