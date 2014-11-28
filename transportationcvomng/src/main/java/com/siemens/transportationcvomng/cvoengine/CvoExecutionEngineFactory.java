package com.siemens.transportationcvomng.cvoengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CvoExecutionEngineFactory {

	Logger logger = Logger.getLogger(CvoExecutionEngineFactory.class);

	public static String ESPER_CONFIGURATION = "Esper";
	public static String WEB_METHODS_CONFIGURATION = "WebMethods";
	

	public CvoExecutionEngine getCVOExecutionEngine(String cepEngineConfigPath)
			throws CEPEngineNotDefinedException {

		// read the properties file to identify the CEP engine

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(cepEngineConfigPath);
		} catch (FileNotFoundException e) {
			logger.error("The configuration file "
					+ cepEngineConfigPath + " is missing!!!", e);
		}

		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			logger.error("Error while reading the congiguration file "
					+ cepEngineConfigPath, e);
		}
		
		String engineName = prop.getProperty("CEPEngine");

		
		// return the appropriate CEP engine
		if (engineName.equals(ESPER_CONFIGURATION)) {
			return new EsperCVOExecutionEngine();
		}

		if (engineName.equals(WEB_METHODS_CONFIGURATION)) {
			return new WebMethodsCVOExecutionEngine();
		}

		throw new CEPEngineNotDefinedException("The CEP engine name "
				+ engineName
				+ " is not a valid one. Check the configuration file "
				+ cepEngineConfigPath);

	}
}
