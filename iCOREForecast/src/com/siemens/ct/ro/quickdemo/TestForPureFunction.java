/**
 * 
 */
package com.siemens.ct.ro.quickdemo;

import java.io.FileNotFoundException;
import java.util.List;

import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.CSVUtils;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;


/**
 * Test whether the prime data changes the internal state of the Wekaforecaster object
 * @author Lucian Sasu, lucian.sasu@siemens.com
 */
public class TestForPureFunction {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		List<TruckSensorJoinedType> buffer1 = getDataForBuffer("data/buffer1.csv");
		List<TruckSensorJoinedType> buffer2 = getDataForBuffer("data/buffer2.csv");
		
		testWithBuffers(buffer1);
		testWithBuffers(buffer1, buffer2);
	}

	@SafeVarargs
	private static void testWithBuffers(List<TruckSensorJoinedType>... buffers) {
		ICOREForecaster forecaster = ICORESerializer.deserialize("serialized/icoreForecaster.ser");
		for(List<TruckSensorJoinedType> buffer : buffers)
		{
			for(TruckSensorJoinedType data : buffer)
			{
				forecaster.recordNewMeasurement(data);
			}
		}
		TemperaturePredictionType temperaturePredictionType = forecaster.forecast();
		System.out.println("forecasting with number of buffers: " + buffers.length + ": ");
		System.out.println("temperaturePredictionType.getPedictedTempTenMin()= " + temperaturePredictionType.getPedictedTempTenMin() + "; temperaturePredictionType.getPredictedTempOneHour()= " + temperaturePredictionType.getPredictedTempOneHour() + "; temperaturePredictionType.getPredictedTempTwoHours()= " + temperaturePredictionType.getPredictedTempTwoHours());
		System.out.println("***********************");
	}

	private static List<TruckSensorJoinedType> getDataForBuffer(String filepath) throws FileNotFoundException {
		return CSVUtils.readData2(filepath, true);
	}

}
