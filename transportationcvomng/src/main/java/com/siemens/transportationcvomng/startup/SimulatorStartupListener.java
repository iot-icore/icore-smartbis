package com.siemens.transportationcvomng.startup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.siemens.transportationcvomng.GlobalVariables;
import com.siemens.transportationcvomng.cvoengine.CEPEngineNotDefinedException;
import com.siemens.transportationcvomng.cvoengine.CvoExecutionEngine;
import com.siemens.transportationcvomng.cvoengine.CvoExecutionEngineFactory;


public class SimulatorStartupListener implements ServletContextListener {

	Logger logger = Logger.getLogger(SimulatorStartupListener.class);
	private static ServletContext servletContext;
	public static CvoExecutionEngine cvoExecutionEngine = null;
	public final static String FORECAST_PATH = "serialized/icoreForecaster.ser";
	public static double reportingPeriod = 10;	
	public static double reportingWindowLength = 150;
	/**
	 * Called when the context is destroyed. Stops all the running simulations.
	 */
	public void contextDestroyed(ServletContextEvent contextEvent) {

	}

	/**
	 * This method is called once, when the server is started.
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {

		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(contextEvent.getServletContext());

		servletContext = applicationContext.getServletContext();

		contextEvent.getServletContext().setAttribute(
				GlobalVariables.InternalVariables.STATE.toString(),
				GlobalVariables.States.FREE);

		CvoExecutionEngineFactory cvoExecutionEngineFactory = new CvoExecutionEngineFactory();

		try {

			String realPath = getRealPath(GlobalVariables.CEP_ENGINE_CONFIG_FILE_PATH);

			cvoExecutionEngine = cvoExecutionEngineFactory
					.getCVOExecutionEngine(realPath);
		} catch (CEPEngineNotDefinedException e) {
			e.printStackTrace();
		}

		cvoExecutionEngine.startEngine();

		// adding the cep input adapters

		// temperature sensors inside the truck
		cvoExecutionEngine
				.registerNewTemperatureDataSource(GlobalVariables.temperatureSensorID1);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(GlobalVariables.temperatureSensorID2);
		cvoExecutionEngine
				.registerNewTemperatureDataSource(GlobalVariables.temperatureSensorID3);

		// external temperature sensor
		cvoExecutionEngine
				.registerNewTemperatureDataSource(GlobalVariables.temperatureSensorID16);

		// humidity sensor
		cvoExecutionEngine
				.registerNewHumidityDataSource(GlobalVariables.humiditySensorID1);

		// currentclamp sensor
		cvoExecutionEngine
				.registerNewcurrentClampDataSource(GlobalVariables.currentClampSensorID1);
		// AcUnit sensor
		cvoExecutionEngine
				.registerNewACUnitDataSource(GlobalVariables.ACUnitID1);
			
		cvoExecutionEngine.loadTimeSeriesPredictionModel(getRealPath(FORECAST_PATH));
	}
	
	public static String getRealPath(String fileName) {
		return servletContext.getRealPath(fileName);
	}
}
