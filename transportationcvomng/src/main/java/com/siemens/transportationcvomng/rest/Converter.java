package com.siemens.transportationcvomng.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;
import com.siemens.ct.ro.exceptions.SensorNotFoundException;
import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.sparql.SPARQLAPI;
import com.siemens.ct.ro.transportation.commons.Request;
import com.siemens.ct.ro.transportation.commons.Request.Parcel.PredictiveModels.Candidate;
import com.siemens.transportationcvomng.GlobalVariables;
import com.siemens.transportationcvomng.GlobalVariables.States;
import com.siemens.transportationcvomng.startup.SimulatorStartupListener;

/**
 * Receives requests from LogisticEmployee application that specifies the
 * configuration of the new transport or a request to finish the running
 * transport.
 * 
 * @author Anca Petrescu
 * 
 */
@Path("/xml")
public class Converter {

	private Random generator = new Random();
	private static List<String> addedStatementsIds = new ArrayList<String>();;
	private static List<String> bindedSensors = new ArrayList<String>();

	@POST
	@Path("/send/")
	public String consumeXML(String requestObject,
			@Context HttpServletRequest request) {

		try {
			requestObject = java.net.URLDecoder.decode(requestObject, "UTF-8");
			int index = requestObject.indexOf("=");
			requestObject = requestObject.substring(index + 1,
					requestObject.length());

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();
		Request requestObj = gson.fromJson(requestObject, Request.class);

		ServletContext context = request.getSession().getServletContext();
		States state = (States) context
				.getAttribute(GlobalVariables.InternalVariables.STATE
						.toString());
		if (state.equals(States.FREE)) {
			startTransportation(requestObj, request);
			context.setAttribute(
					GlobalVariables.InternalVariables.STATE.toString(),
					GlobalVariables.States.TRANSPORT_IN_PROGRESS);
		} else {
			System.err
					.println("There already is a transportation in progress !");
		}

		return requestObject;
	}

	@POST
	@Path("/finish")
	public String finish(@Context HttpServletRequest request) {

		ServletContext context = request.getSession().getServletContext();
		States state = (States) context
				.getAttribute(GlobalVariables.InternalVariables.STATE
						.toString());
		if (state.equals(States.TRANSPORT_IN_PROGRESS)) {
			context.setAttribute(
					GlobalVariables.InternalVariables.STATE.toString(),
					GlobalVariables.States.FREE);

			// stop simulation
			/*
			 * String url = GlobalVariables.core_path + "resetSimulator";
			 * makeRequest("GET", url);
			 */

			// unbind packages
			for (int i = 0; i < bindedSensors.size(); i++) {
				try {
					System.out
							.println("Unbind sensor: " + bindedSensors.get(i));
					new SPARQLAPI().unbindSensor(bindedSensors.get(i));
				} catch (SensorNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// remove statements
			for (int i = 0; i < addedStatementsIds.size(); i++) {
				SimulatorStartupListener.cvoExecutionEngine
						.deleteEpn(addedStatementsIds.get(i));
			}
			// TODO remove adapters
			return "Transportation finished succesfully!";
		}
		return "There is no transportation in progress !";
	}

	/**
	 * Read the data from the request and start the transportation
	 * 
	 * @param requestObj
	 */
	private void startTransportation(Request requestObj,
			HttpServletRequest request) {

		String url;

		// initialize the simulator

		url = GlobalVariables.core_path + "resetSimulator";
		makeRequest("GET", url);
		/**
		 * Get the id of the packages that are going to be transported.
		 */
		String[] ids = requestObj.getParcel().getIds().split(",");
		if (ids.length > 12) {
			System.err
					.println("The maximum number of parcels that cand be added are 12! You are tring to add "
							+ ids.length + " packages.");
			return;
		}
		for (int i = 1; i < ids.length; i++) {

			// add package to simulator
			url = GlobalVariables.core_path + "iot/parcelactuator/add/"
					+ ids[i] + "/Parcel" + i + "GroupID/"
					+ generateRandomInitialTemperature() + "/"
					+ generateRandomInitialHumidityCoef();
			makeRequest("POST", url);

			// get the sensors bound to this package
			List<String> attachedSensors = new SPARQLAPI()
					.getAllIDsBoundToBox(ids[i]);
			for (int j = 0; j < attachedSensors.size(); j++) {
				// add sensors to transportationsim
				url = GlobalVariables.core_path + "iot/temperaturesensor/add/"
						+ ids[i] + "/" + attachedSensors.get(j);
				makeRequest("POST", url);
				bindedSensors.add(attachedSensors.get(j));
				String sensorId = attachedSensors.get(j).replace(":", "_");

				// add adaptor
				SimulatorStartupListener.cvoExecutionEngine
						.registerNewTemperatureDataSource(sensorId);

				// bind package to truck (truckId is a constant)
				// new SPARQLAPI().

				String packageID = new SPARQLAPI()
						.getPackageBoundToSensor(sensorId);

				// deploy CEP statements
				List<Candidate> statements = requestObj.getParcel()
						.getPredictiveModels().getCandidate();

				SimulatorStartupListener.cvoExecutionEngine
						.registerNewTemperatureDataSource(sensorId);

				String timeSeriesPredictionEPNID = "time series" + sensorId;
				addedStatementsIds.add(timeSeriesPredictionEPNID);
				SimulatorStartupListener.cvoExecutionEngine
						.addNewTimeSeriesPredictionEPN(
								timeSeriesPredictionEPNID, packageID,
								GlobalVariables.truckID,
								GlobalVariables.temperatureSensorID1,
								GlobalVariables.temperatureSensorID2,
								GlobalVariables.temperatureSensorID3,
								GlobalVariables.humiditySensorID1,
								GlobalVariables.ACUnitID1,
								GlobalVariables.currentClampSensorID1,
								GlobalVariables.temperatureSensorID16,
								sensorId,
								SimulatorStartupListener.reportingPeriod,
								SimulatorStartupListener.reportingWindowLength);
			}

		}

		// start transportationsim
		url = GlobalVariables.core_path + "startAutoAdvance";
		makeRequest("GET", url);

		ServletContext context = request.getSession().getServletContext();

		context.setAttribute(
				GlobalVariables.InternalVariables.STATE.toString(),
				GlobalVariables.States.TRANSPORT_IN_PROGRESS);
	}

	/**
	 * 
	 * @param method
	 *            POST or GET
	 * @param urlString
	 *            the url
	 */
	public void makeRequest(String method, String urlString) {
		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			InputStream xml = conn.getInputStream();

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int generateRandomInitialTemperature() {

		return generator.nextInt(40) + 1;

	}

	public double generateRandomInitialHumidityCoef() {

		return generator.nextDouble();

	}

	private String getVOType(String sensorID) {
		String voType = null;
		try {
			voType = new SPARQLAPI().getSensorType(sensorID);
		} catch (SensorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voType;
	}
	
	@GET
	@Path("/modelchanged/")
	public String modelChanged(@Context HttpServletRequest request) {

		String status;
		
		String filePath = request.getSession().getServletContext().getInitParameter("icoreforecasterlocation");
		status = "Read from " + filePath;
		
		System.out.println(status);
		//TODO: change the icoreforecaster instance by deserializing from filePath
		//ICOREForecaster icoreForecaster = ICORESerializer.deserialize(filePath);

		return status;
	}
}
