package com.siemens.logisticemployee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siemens.ct.ro.exceptions.SensorNotFoundException;
import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.GenericDataSplitter;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.sparql.SPARQLAPI;
import com.siemens.ct.ro.transportation.commons.Constants;
import com.siemens.ct.ro.transportation.commons.Request;
import com.siemens.ct.ro.transportation.commons.Request.Parcel;
import com.siemens.ct.ro.transportation.commons.Request.Parcel.AcceptanceCriteria;
import com.siemens.ct.ro.transportation.commons.Request.Parcel.DeliveryTime;
import com.siemens.ct.ro.transportation.commons.Request.Parcel.PredictiveModels;
import com.siemens.ct.ro.transportation.commons.Request.Parcel.PredictiveModels.Candidate;
import com.siemens.ct.ro.transportation.dao.OrderDao;
import com.siemens.ct.ro.transportation.dao.PackageDao;
import com.siemens.ct.ro.transportation.dao.ProductTypesDao;
import com.siemens.ct.ro.transportation.dao.SensorDao;
import com.siemens.ct.ro.transportation.dao.TemperatureEventDAO;
import com.siemens.ct.ro.transportation.dao.TemperaturePredictionEventDAO;
import com.siemens.ct.ro.transportation.dao.TruckSensorJointEventDao;
import com.siemens.ct.ro.transportation.entities.Order;
import com.siemens.ct.ro.transportation.entities.PackageUnit;
import com.siemens.ct.ro.transportation.entities.ProductType;
import com.siemens.ct.ro.transportation.entities.Sensor;
import com.softwareag.transportation.CEPevents.TemperatureEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

/**
 * Used to access data from the database and to redirect to a specific view.
 * 
 * @author Anca Petrescu
 * 
 */
@Controller
public class DataAccesorServlet {
	
	private static ICOREForecaster icoreForecaster = null;

	Logger logger = Logger.getLogger(DataAccesorServlet.class);
	Gson gson;
	@Autowired
	SensorDao sensorDao;
	@Autowired
	PackageDao packageDao;
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	ProductTypesDao productTypesDao;
	
	@Autowired
	TemperaturePredictionEventDAO temperaturePredictionEventDAO;
	
	@Autowired
	TemperatureEventDAO temperatureEventDAO;
	
	@Autowired
	TruckSensorJointEventDao truckSensorJointEventDao;

	@RequestMapping(value = "/addSensor", method = RequestMethod.GET)
	public String redirectToSensor(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "addSensor";
	}

	@RequestMapping(value = "/unbinding", method = RequestMethod.GET)
	public String redirectToUnbinding(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "unbinding";
	}

	@RequestMapping(value = "/binding", method = RequestMethod.GET)
	public String redirectToBinding(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "binding";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String redirectToIndex(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "menu";
	}

	@RequestMapping(value = "/managepm", method = RequestMethod.GET)
	public String redirectToPredictionModel(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "managepm";
	}
	
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		return "home";
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String redirectToStart(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		return "start";
	}

	@RequestMapping(value = "/getSensors")
	@Transactional
	public void getSensors(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<Sensor> sensors = sensorDao.getSensors();
			List<String> sensorsNames = new ArrayList<String>();
			for (int i = 0; i < sensors.size(); i++)
				sensorsNames.add(sensors.get(i).getSensorID());
			String json = gson.toJson(sensorsNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** return all the packages */
	@RequestMapping(value = "/getAllPackages",  method = RequestMethod.GET)
	@Transactional
	public void getPackages(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<PackageUnit> packages = packageDao.getPackages();
			List<String> packagesNames = new ArrayList<String>();
			for (int i = 0; i < packages.size(); i++)
					packagesNames.add(packages.get(i).getPackageId());
			String json = gson.toJson(packagesNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/getSensors")
	@Transactional
    public ResponseEntity<String> fooBar2(HttpServletResponse response,
			HttpServletRequest request , @RequestBody String pack) {

            
			Gson gson = new Gson();			
			int index = pack.indexOf("=");
			pack = pack.substring(index + 1,
					pack.length());
			
			SPARQLAPI sparqlApi = new SPARQLAPI();
			List<String> sensorIDsBoundToBox = sparqlApi.getAllIDsBoundToBox(pack);
			
			
			String sensorNames = "";
			for (int i = 0; i < sensorIDsBoundToBox.size(); i++)
				sensorNames=sensorNames+","+sensorIDsBoundToBox.get(i);
			
			
			String json = gson.toJson(sensorIDsBoundToBox);

			HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		    return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		
    }
	
	
	@RequestMapping(value = "/getOrders")
	@Transactional
	public void getOrders(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<Order> orders = orderDao.getAll();
			List<String> ordersNames = new ArrayList<String>();
			for (int i = 0; i < orders.size(); i++)
				ordersNames.add(orders.get(i).getOrderId());
			String json = gson.toJson(ordersNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/addSingleSensor", method = RequestMethod.POST, consumes = { "application/json" })
	public void addSensor(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap,
			@RequestBody Sensor sensor) {

		// TODO connect to VORegistry
		String sensorType = getVOType(sensor.getSensorID());

		sensor.setSensorType(sensorType);
		Sensor newAddedSensr = sensorDao.addSensor(sensor);
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String json = gson.toJson(newAddedSensr);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@Transactional
	public void bind(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			String packID = request.getParameter("pack");
			PackageUnit pack = packageDao.getPackage(packID);
			pack.setBound(true);
			pack.setStartTime(System.currentTimeMillis());
			packageDao.update(pack);
			String sensors = request.getParameter("selectedSensors");
			String[] sensorList = sensors.split(",");

			for (int i = 0; i < sensorList.length; i++) {

				new SPARQLAPI().bindSensorToBox(sensorList[i],
						"" + pack.getPackageId());

				logger.info("Done binding sensor " + sensorList[i] + " to box "
						+ pack.getPackageId());
				sensorDao.deleteSensor(sensorList[i]);
			}

			Gson gson = new Gson();

			PrintWriter out = response.getWriter();
			String json = gson.toJson(pack.getId());
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SensorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	public void unbind(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			String packID = request.getParameter("pack");
			PackageUnit pack = packageDao.getPackage(packID);
			packageDao.updateBinding(pack.getId(), false);

			// TODO get all the sensors that are bound to the selected package

			SPARQLAPI sparqlApi = new SPARQLAPI();
			List<String> sensorIDsBoundToBox = sparqlApi.getAllIDsBoundToBox(""
					+ pack.getId());
			for (String sensorID : sensorIDsBoundToBox) {
				try {
					sparqlApi.unbindSensor(sensorID);
				} catch (SensorNotFoundException e) {
					e.printStackTrace();
				}
			}

			Gson gson = new Gson();

			PrintWriter out = response.getWriter();
			String json = gson.toJson(packID);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** return the unbound packages */
	@RequestMapping(value = "/getPackages")
	@Transactional
	public void getUnboundPackages(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<PackageUnit> packages = packageDao.getPackages();
			List<String> packagesNames = new ArrayList<String>();
			for (int i = 0; i < packages.size(); i++)
				if (!packages.get(i).isBound())
					packagesNames.add(packages.get(i).getPackageId());
			String json = gson.toJson(packagesNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// return bounded packages
	@RequestMapping(value = "/getBoundPackages")
	@Transactional
	public void getAllPackages(HttpServletResponse response,
			HttpServletRequest request, String orderId) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<PackageUnit> packages = packageDao.getPackages();
			List<String> packagesNames = new ArrayList<String>();
			for (int i = 0; i < packages.size(); i++) {
				if ((packages.get(i).isBound())
						&& (packages.get(i).getOrderId()
								.equalsIgnoreCase(orderId))) {
					packagesNames.add(packages.get(i).getPackageId());
				}
			}
			String json = gson.toJson(packagesNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// return bounded packages
	@RequestMapping(value = "/getRequest")
	@Transactional
	public @ResponseBody Request getRequest(HttpServletResponse response,
			HttpServletRequest request, String orderId) {
	

		Gson gson = new Gson();
		List<PackageUnit> packages = packageDao.getPackages();

		PackageUnit samplePackage = null;
		String packagesNames = "";

		for (int i = 0; i < packages.size(); i++) {
			if ((packages.get(i).isBound())
					&& (packages.get(i).getOrderId().equalsIgnoreCase(orderId))) {
				samplePackage = packages.get(i);
				packagesNames = packagesNames + "," +packages.get(i).getPackageId();
			}
		}

		if (packagesNames.equals("")) {
			return null;
		}

		Parcel parcel = new Parcel();
		parcel.setIds(packagesNames);
		parcel.setReceiver(samplePackage.getReceiver());
		parcel.setSender(samplePackage.getSender());
		parcel.setWithTransportationType(Constants.JobType.PERISHABLE.toString());
		DeliveryTime delivaryTime = new Request.Parcel.DeliveryTime();
		delivaryTime.setEnd("" + samplePackage.getArrivalTime());
		delivaryTime.setStart("" + samplePackage.getStartTime());
		parcel.setDeliveryTime(delivaryTime);

		ProductType productType = productTypesDao.getProductType(samplePackage
				.getProductType());
		AcceptanceCriteria acceptance = new AcceptanceCriteria();

		acceptance.setHardMaxHumidity("" + productType.getHardMaxHumidity());
		acceptance.setHardMinHumidity("" + productType.getHardMinHumidity());

		acceptance.setSoftMinTemperature(""
				+ productType.getSoftMinTemperature());
		acceptance.setSoftMaxTemperature(""
				+ productType.getSoftMaxTemperature());

		acceptance.setHardMaxTemperature(""
				+ productType.getHardMaxTemperature());
		acceptance.setHardMinTemperature(""
				+ productType.getHardMinTemperature());
		acceptance.setMaximumTimeOutOfTemperature(""+productType.getMaximumTimeOutOfTemperature());

		parcel.setAcceptanceCriteria(acceptance);
		parcel.setJob("JOB");
		PredictiveModels predictiveModels = new PredictiveModels();
		
		Candidate candidate = new Candidate();
		candidate.setId(1);
		predictiveModels.getCandidate().add(candidate);

		parcel.setPredictiveModels(predictiveModels);

		Request xmlRequest = new Request();
		xmlRequest.setParcel(parcel);

		String json = gson.toJson(xmlRequest);

		return xmlRequest;

	}

	private String getVOType(String sensorID) {
		String voType = null;
		try {
			voType = new SPARQLAPI().getSensorType(sensorID);
			logger.info("vo type: " + voType);
		} catch (SensorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voType;
	}
	
	
	/**
	 * Provides the current value for humidity, current clamp and mean
	 * temperature. Returns a string that contains the three value split by ","
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getChartData")
	public void getChartData(HttpServletRequest request,
			HttpServletResponse response, String sensorId) {
		try {
			PrintWriter out = response.getWriter();
			ServletContext context = request.getSession().getServletContext();
			sensorId=sensorId.replace(":", "_");
			
			double realValue = temperatureEventDAO.getLastEvent(sensorId, System.currentTimeMillis()- 24*60*60*1000).getTemperature().doubleValue();
			double pred10min = 0;
			double pred1h = 0;
			double pred2h = 0;
		
			List<TemperaturePredictionEvent> predictions = temperaturePredictionEventDAO.getTemperaturePredictionEvents(sensorId);
			if (predictions != null) {
				TemperaturePredictionEvent latestPrediction = predictions.get(predictions.size()-1);
				pred10min = latestPrediction.getPedictedTempTenMin();
				pred1h = latestPrediction.getPredictedTempOneHour();
				pred2h = latestPrediction.getPredictedTempTwoHours();
			}

			String result =realValue+ "," + pred10min + "," + pred1h + ","+ pred2h;
			out.print(result);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Provides the historical data for the specified sensor
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getChartHistory")
	public void getChartHistory(HttpServletRequest request,
			HttpServletResponse response, String sensorId) {
		try {
			PrintWriter out = response.getWriter();
			sensorId = sensorId.replace(":", "_");

			List<TemperatureEvent> measurements = temperatureEventDAO
					.getTemperatureEvents(sensorId, new Date().getTime()
							- 12 * 60 * 60 * 1000, new Date().getTime());
			List<TemperaturePredictionEvent> predictions = temperaturePredictionEventDAO
					.getTemperaturePredictionEvents(sensorId);

			//get the minimum length
			
			int minimum = measurements.size()>predictions.size() ? predictions.size() : measurements.size();
			
			
			String result = "";
			String realValues = "";
			String resultpred10min = "";
			String resultpred1h = "";
			String resultpred2h = "";

			if (predictions != null) {
				for (int i = 1; i < minimum; i++) {
					
					realValues += measurements.get(i).getTemperature().doubleValue() + ",";
					
					TemperaturePredictionEvent latestPrediction = predictions.get(i);
					resultpred10min += latestPrediction.getPedictedTempTenMin() + ",";
					resultpred1h += latestPrediction.getPredictedTempOneHour() + ",";
					resultpred2h += latestPrediction.getPredictedTempTwoHours() + ",";
				}

				result = realValues + ";" + resultpred10min + ";" + resultpred1h + ";"
						+ resultpred2h;
				out.print(result);
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@RequestMapping(method=RequestMethod.POST, value = "/saveLinear")
	@Transactional
    public void saveLinearModel(HttpServletResponse response,
			HttpServletRequest request , @RequestParam String linear_options) {
            
		logger.info("SaveLinear OPTION: "+linear_options);
			try {
				logger.info("Before calling createLinearModel");
				icoreForecaster = ICOREForecaster.createLinearModel(linear_options);
//				PackageUnit pack = packageDao.getPackage(packID);
				logger.info("icoreForecaster is null: " + (icoreForecaster == null));
				PrintWriter out = response.getWriter();
				out.print("0");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    }
	
	@RequestMapping(method=RequestMethod.POST, value = "/saveMultilayer")
	@Transactional
    public void saveMultilayer(HttpServletResponse response,
			HttpServletRequest request , @RequestParam String hidden_neurons, @RequestParam String learning_rate, @RequestParam String momentum_coefficient) {
         
		logger.info("saveMultilayer OPTION: "+hidden_neurons+ ","+learning_rate+","+momentum_coefficient);
			try {
				int hiddenNeurons = Integer.parseInt(hidden_neurons);
				double learningRate = Double.parseDouble(learning_rate);
				double momentumCoefficient = Double.parseDouble(momentum_coefficient);
				icoreForecaster = ICOREForecaster.createLinearModel(hiddenNeurons, learningRate, momentumCoefficient);
				logger.info("icoreForecaster is null: " + (icoreForecaster == null));
				PrintWriter out = response.getWriter();
				out.print("0");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
    }
	
	@RequestMapping(method=RequestMethod.POST, value = "/saveM5P")
	@Transactional
    public void saveM5P(HttpServletResponse response,
			HttpServletRequest request , @RequestParam String minimum_number) {
            
		logger.info("saveM5P OPTION: "+minimum_number);
			try {
				int minimumNumber = Integer.parseInt(minimum_number);
				icoreForecaster = ICOREForecaster.createM5P(minimumNumber);
				System.out.println("icoreForecaster is null: " + icoreForecaster == null);
				PrintWriter out = response.getWriter();
				out.print("0");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    }
	
	/**
	 * @author Lucian Sasu
	 * @param response 
	 * @param request
	 * @param minimum_number
	 */
	@RequestMapping(method=RequestMethod.POST, value = "/startTraining")
	@Transactional
	public void startTraining(HttpServletResponse response,
			HttpServletRequest request , @RequestParam String packID, @RequestParam String sensorID) {
        String status = "";
		sensorID = sensorID.replace(":", "_");   
		logger.info("packID: "+packID);
		logger.info("sensorID: "+sensorID);
			try {
				PackageUnit packageUnit = packageDao.getPackage(packID);
				List<TruckSensorJointEvent> allData = truckSensorJointEventDao.getAllTruckSensorJointEvent(sensorID, packageUnit.getStartTime(), packageUnit.getArrivalTime());
				if (icoreForecaster != null)
				{
					try {
						List<List<TruckSensorJointEvent>> splits = new GenericDataSplitter<TruckSensorJointEvent>().splitData(allData, 0.7, 0.3);
						
						List<TruckSensorJointEvent> trainData = splits.get(0);
						List<TruckSensorJointEvent> testData = splits.get(1);
						
						icoreForecaster.trainForecaster(trainData, System.out);
						icoreForecaster.primeData(trainData);
						
//						int itemsToPredict = testData.size();
//						List<Double> predicted = icoreForecaster.forecast(itemsToPredict);
						
						String serializationPath = request.getSession().getServletContext().getInitParameter("pathForSerializedModel");
						
						ICORESerializer.serialize(icoreForecaster, serializationPath);
						
						String signalModelChangeURL = request.getSession().getServletContext().getInitParameter("signalModelChangeURL");
						
						boolean result = callURL(signalModelChangeURL);
						
						System.out.println("Change of the model was understood: " + result);
						
						status = "Training done successfully";
					} catch (Exception e) {
						logger.error(e.getMessage());
						logger.error(e.getStackTrace());
						status = "Error while training the model: " + e.getMessage();
					}
				}
				else
				{
					status = "Choose training model first!";
				}
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
			    response.setCharacterEncoding("UTF-8");
				out.print(status);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    }

	private boolean callURL(String url) {
		try {
		    URL myURL = new URL(url);
		    URLConnection myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
		    return true;
		} 
		catch (MalformedURLException e) { 
		    e.printStackTrace();
		    return false;
		} 
		catch (IOException e) {   
			e.printStackTrace();
			return false;
		}
	}
	
//	@RequestMapping(method=RequestMethod.POST, value = "/saveRBF")
//	@Transactional
//    public void saveRBF(HttpServletResponse response,
//			HttpServletRequest request , @RequestParam String number_clusters, @RequestParam String standard_deviation) {
//          
//		logger.info("saveRBF OPTION: "+ number_clusters+","+standard_deviation);
//			try {
//				System.out.println("number_clusters= " +number_clusters);
//				System.out.println("standard_deviation= " + standard_deviation);
//				int numberOfClusters = Integer.parseInt(number_clusters);
//				double standardDeviation = Double.parseDouble(standard_deviation);
//				System.out.println("before calling ICOREForecaster.createRBF");
//				ICOREForecaster icoreForecaster = ICOREForecaster.createRBF(numberOfClusters, standardDeviation);
//				System.out.println("icoreForecaster is null: " + icoreForecaster == null);
//				//TODO: check if icoreForecaster is null, get data and train
//				PrintWriter out = response.getWriter();
//				out.print("0");
//				out.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}		
//    }	
	
}
