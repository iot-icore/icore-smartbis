/**
 * 
 */
package com.siemens.ct.ro.demos;

import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;

/**
 * @author Lucian Sasu
 *
 */
public class ObjectInspector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ICOREForecaster forecaster = ICORESerializer.deserialize("D:\\proiecte\\iCORE\\svn\\Developement\\iCOREForecast\\serialized\\icoreForecaster.ser");
		System.out.println("forecaster.getForecaster() is null" + forecaster.getForecaster()== null);
	}

}
