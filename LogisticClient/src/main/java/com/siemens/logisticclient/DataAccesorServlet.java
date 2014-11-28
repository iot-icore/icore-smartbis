package com.siemens.logisticclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

import com.google.gson.Gson;
import com.siemens.ct.ro.sparql.SPARQLAPI;
import com.siemens.ct.ro.transportation.dao.OrderDao;
import com.siemens.ct.ro.transportation.dao.PackageDao;
import com.siemens.ct.ro.transportation.dao.ProductTypesDao;
import com.siemens.ct.ro.transportation.dao.TemperatureEventDAO;
import com.siemens.ct.ro.transportation.dao.TemperaturePredictionEventDAO;
import com.siemens.ct.ro.transportation.entities.Order;
import com.siemens.ct.ro.transportation.entities.PackageUnit;
import com.siemens.ct.ro.transportation.entities.ProductType;
import com.siemens.logisticclient.utlis.AddOrderRequest;
import com.siemens.logisticclient.webdata.DataMapper;
import com.siemens.logisticclient.webdata.Point;
import com.siemens.logisticclient.webdata.SingleSeries;
import com.siemens.logisticclient.webdata.WebColorsCollection;
import com.softwareag.transportation.CEPevents.TemperatureEvent;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;

/**
 * Used to access data from the database and to redirect to a specific view.
 * 
 * @author Anca Petrescu
 * 
 */
@Controller
public class DataAccesorServlet {

	Logger logger = Logger.getLogger(DataAccesorServlet.class);
	Gson gson;

	@Autowired
	PackageDao packageDao;
	@Autowired
	ProductTypesDao productTypesDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	TemperatureEventDAO temperatureEventDAO;
	@Autowired
	TemperaturePredictionEventDAO temperaturePredictionEventDAO;
	
	private static final Comparator<TemperaturePredictionEvent> comparator = new Comparator<TemperaturePredictionEvent>() {
		
		@Override
		public int compare(TemperaturePredictionEvent o1, TemperaturePredictionEvent o2) {
			return o1.getTimestamp() < o2.getTimestamp() ? -1 : o1.getTimestamp() == o2.getTimestamp() ? 0 : +1;
		}
	};

	/**
	 * redirect order
	 * 
	 * @param session
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String redirectToOrder(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "logistic";
	}
	
	@RequestMapping(value = "/viewDashboard", method = RequestMethod.GET)
	public String redirectToDashBoard(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "viewDashboard";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String redirectToMenu(HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		return "menu";
	}
	
	/**
	 * Return the product type list.
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/getProductTypes")
	@Transactional
	public void getProductTypes(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<ProductType> productTypes = productTypesDao
					.getAllProductTypes();
			List<String> productTypesNames = new ArrayList<String>();
			for (int i = 0; i < productTypes.size(); i++)
				productTypesNames.add(productTypes.get(i).getTypeName());
			String json = gson.toJson(productTypesNames);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** return the unbound packages */
	@RequestMapping(value = "/getPackages",  method = RequestMethod.POST)
	@Transactional
	public void getSensors(HttpServletResponse response,
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
	/**
	 * Add a order unit in the database.
	 * 
	 * @param session
	 * @param response
	 * @param request
	 * @param modelMap
	 * @param pack
	 */
	@Transactional
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST, consumes = { "application/json" })
	public void addOrder(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap,
			@RequestBody AddOrderRequest addOrderRequest) {

		
		
		/* create the order object and add it in the database.*/
		Order order = new Order(addOrderRequest.getOrderId());
		orderDao.add(order);
		
		/* create the package objects, add them in the database, link them to the order.*/
		for (int i = 0; i < addOrderRequest.getNr_pack(); i++) {
			PackageUnit pack = new PackageUnit(addOrderRequest.getOrderId()
					+ "_" + (i + 1), addOrderRequest.getProductType(),
					addOrderRequest.getSender(), addOrderRequest.getReceiver(),
					addOrderRequest.getArrivalTime(),
					System.currentTimeMillis(), false, order.getOrderId());
			packageDao.addPackage(pack);
		}
		
	}

	// return bounded  packages
	@RequestMapping(value = "/getBoundPackages")
	@Transactional
	public void getAllPackages(HttpServletResponse response,
			HttpServletRequest request) {

		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			List<PackageUnit> packages = packageDao.getPackages();
			List<String> packagesNames = new ArrayList<String>();
			for (int i = 0; i < packages.size(); i++)	
			{
				if (packages.get(i).isBound())
				{
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
	
	/**
	 * Redirect to the home (login) page.
	 * 
	 * @param session
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		return "home";
	}

	/**
	 * Returns the next available orderId. The id is composed using the string
	 * "Order_" near a number, representing the size +1 of the registered
	 * orders.
	 * 
	 * @param session
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/getOrderId", method = RequestMethod.GET)
	public void getOrderId(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {

		/* the number that will be part of the order id */
		int order_id_number = orderDao.getAll().size() + 1;
		String id = "Order_" + order_id_number;

		try {
			PrintWriter out = response.getWriter();
			out.print(id);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

	
	@RequestMapping(value = "/viewDashboardMethod", method = RequestMethod.POST)
	@Transactional
	public void viewDashboard(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			String sensorId = request.getParameter("sensorId");
			
			String packId = request.getParameter("packId");
			
			logger.info("sensorId= " + sensorId);
			logger.info("packId= "  + packId);
			
			PackageUnit pack = packageDao.getPackage(packId);
			
			String productTypePack = pack.getProductType();
			ProductType productType = productTypesDao.getProductType(productTypePack);
			double minTempBound = productType.getHardMinTemperature();
			double maxTempBound = productType.getHardMaxTemperature();
			logger.info("minTempBound= " + minTempBound);
			logger.info("maxTempBound= " + maxTempBound);
			
			List<SingleSeries> list = getListOfData(pack);
			
			logger.info("list size in viewDashboardMethod: "+ list.size());
			
			List<SingleSeries> listTemperatures = getTemperatureBounds(list, minTempBound, maxTempBound);
			list.addAll(listTemperatures);
			
			String json = gson.toJson(list);
			logger.info(json);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/viewPredict10Mins", method = RequestMethod.POST)
	@Transactional
	public void viewPredict10Mins(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			String sensorId_withDescription = request.getParameter("sensorId");
			
			String sensorId = sensorId_withDescription.substring(0, sensorId_withDescription.indexOf("_"));
			
			String packId = request.getParameter("packId");
			
			System.out.println("packId= " + packId);
			
			PackageUnit pack = packageDao.getPackage(packId);
			
			String productTypePack = pack.getProductType();
			ProductType productType = productTypesDao.getProductType(productTypePack);
			double minTempBound = productType.getHardMinTemperature();
			double maxTempBound = productType.getHardMaxTemperature();
			
			List<SingleSeries> list = getListOfData10mins(sensorId, pack);
			
			logger.info("list size in viewPredict10Mins: "+ list.size());
			
			List<SingleSeries> listTemperatures = getTemperatureBounds(list, minTempBound, maxTempBound);
			list.addAll(listTemperatures);
			
			String json = gson.toJson(list);
			logger.info(json);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/viewPredict1Hour", method = RequestMethod.POST)
	@Transactional
	public void viewPredict1Hour(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			String sensorId_withDescription = request.getParameter("sensorId");
			
			String sensorId = sensorId_withDescription.substring(0, sensorId_withDescription.indexOf("_"));
			
			System.out.println("sensorId= " + sensorId);
			
			String packId = request.getParameter("packId");
			
			PackageUnit pack = packageDao.getPackage(packId);
			
			String productTypePack = pack.getProductType();
			ProductType productType = productTypesDao.getProductType(productTypePack);
			double minTempBound = productType.getHardMinTemperature();
			double maxTempBound = productType.getHardMaxTemperature();
			
			List<SingleSeries> list = getListOfData1Hour(sensorId, pack);
			
			logger.info("list size in viewPredict1Hour: "+ list.size());
			
			List<SingleSeries> listTemperatures = getTemperatureBounds(list, minTempBound, maxTempBound);
			list.addAll(listTemperatures);
			
			String json = gson.toJson(list);
			logger.info(json);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@RequestMapping(value = "/viewPredict2Hours", method = RequestMethod.POST)
	@Transactional
	public void viewPredict2Hours(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			String sensorId_withDescription = request.getParameter("sensorId");
			
			String sensorId = sensorId_withDescription.substring(0, sensorId_withDescription.indexOf("_"));
			
			String packId = request.getParameter("packId");
			
			PackageUnit pack = packageDao.getPackage(packId);
			
			String productTypePack = pack.getProductType();
			ProductType productType = productTypesDao.getProductType(productTypePack);
			double minTempBound = productType.getHardMinTemperature();
			double maxTempBound = productType.getHardMaxTemperature();
			
			List<SingleSeries> list = getListOfData2Hours(sensorId, pack);
			
			logger.info("list size in viewPredict2Hours: "+ list.size());
			
			List<SingleSeries> listTemperatures = getTemperatureBounds(list, minTempBound, maxTempBound);
			list.addAll(listTemperatures);
			
			String json = gson.toJson(list);
			logger.info(json);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private List<SingleSeries> getTemperatureBounds(List<SingleSeries> list,
			double minTempBound, double maxTempBound) {
		if (list == null || list.size() == 0)
		{
			return new LinkedList<SingleSeries>();
		}
		
		long tMin = Long.MAX_VALUE;
		long tMax = Long.MIN_VALUE;
		
		for(SingleSeries series : list)
		{
			tMin = Math.min(tMin, series.getData().get(0).getX());
			tMax = Math.max(tMax, series.getData().get(series.getData().size()-1).getX());
		}
		Point pointMinTempMinTime = new Point(tMin, minTempBound);
		Point pointMinTempMaxTime = new Point(tMax, minTempBound);
		Point pointMaxTempMinTime = new Point(tMin, maxTempBound);
		Point pointMaxTempMaxTime = new Point(tMax, maxTempBound);
		
		List<SingleSeries> result = new LinkedList<SingleSeries>();
		SingleSeries minTempSeries = new SingleSeries();
		SingleSeries maxTempSeries = new SingleSeries();
		
		result.add(minTempSeries);
		result.add(maxTempSeries);
		
		minTempSeries.setName("Min allowed temp.");
		maxTempSeries.setName("Max allowed temp.");
		
		minTempSeries.setColor(WebColorsCollection.RED);
		maxTempSeries.setColor(WebColorsCollection.RED);
		
		minTempSeries.addPoint(pointMinTempMinTime);
		minTempSeries.addPoint(pointMinTempMaxTime);
		maxTempSeries.addPoint(pointMaxTempMinTime);
		maxTempSeries.addPoint(pointMaxTempMaxTime);
		
		return result;
	}

	@RequestMapping(value = "/viewOutOfBoundsTemperatures", method = RequestMethod.POST)
	@Transactional
	public void viewOutOfBoundsTemperatures(HttpSession session, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {

		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			
			String packID = request.getParameter("pack");
			PackageUnit pack = packageDao.getPackage(packID);
			
			String productTypePack = pack.getProductType();
			ProductType productType = productTypesDao.getProductType(productTypePack);
			double minTempBound = productType.getHardMinTemperature();
			double maxTempBound = productType.getHardMaxTemperature();;
			logger.info("minTempBound" + minTempBound);
			logger.info("maxTempBound" + maxTempBound);
			
			List<SingleSeries> list = getOutboundValues(pack, minTempBound, maxTempBound);
			
//			List<SingleSeries> listTemperatures = getTemperatureBounds(list, minTempBound, maxTempBound);
//			list.addAll(listTemperatures);
			
			logger.info("list size in viewOutOfBoundsTemperatures: "+ list.size());
			
			String json = gson.toJson(list);
			logger.info(json);
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private List<SingleSeries> getListOfData(PackageUnit pack)
	{
		WebColorsCollection colors = new WebColorsCollection();
		List<SingleSeries> list = new LinkedList<SingleSeries>();
		
		List<String> allSensorsForPackage = getSensorsForPackage(pack.getPackageId() + "");
		logger.info("allSensorsForPackage.size= " + allSensorsForPackage.size());
		
		Long tStart = getTimeStart(pack);
		logger.info("getListOfData: tStart= " + tStart);
		Long tStop = getTimeStop(pack);
		logger.info("getListOfData: tStop= " + tStop);
		
		for(int i=0; i<allSensorsForPackage.size(); i++)
		{
			String sensorID = allSensorsForPackage.get(i);
			logger.info("sensor : " + sensorID);
			sensorID = sensorID.replace(':', '_');
			List<TemperatureEvent> sensorTemperatureList = temperatureEventDAO.getTemperatureEvents(sensorID, tStart, tStop);
			logger.info("getListOfData: sensorTemperatureList.size() = " + sensorTemperatureList.size());
			int start = Math.max(0,  sensorTemperatureList.size() - limit);
			sensorTemperatureList = sensorTemperatureList.subList(start, sensorTemperatureList.size());
			//for multiple sensors:
			//SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Sensor " + (i+1) , colors.next(), sensorTemperatureList);
			SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Pack. sensor temp.", colors.next(), sensorTemperatureList);
			list.add(singleSeriesSensor);
		}
		return list;
	}
	
	//how many point to represent
	private final static int forecastLength = 20;
	
	private List<SingleSeries> getListOfData10mins(String sensorID, PackageUnit pack)
	{
		WebColorsCollection colors = new WebColorsCollection();
		List<SingleSeries> list = new LinkedList<SingleSeries>();
		
		sensorID = sensorID.replace(':', '_');
		List<TemperaturePredictionEvent> predictions = temperaturePredictionEventDAO.getTemperaturePredictionEvents(sensorID);
		
		Collections.sort(predictions, comparator);
		
		int start = Math.max(0,  predictions.size() - forecastLength);
		predictions = predictions.subList(start, predictions.size());
		//for multiple sensors:
		//SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Sensor " + (i+1) , colors.next(), sensorTemperatureList);
		SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries10Minutes("10 min. predictions", predictions, colors.next());
		list.add(singleSeriesSensor);
		
		return list;
	}
	
	private List<SingleSeries> getListOfData1Hour(String sensorID, PackageUnit pack)
	{
		WebColorsCollection colors = new WebColorsCollection();
		List<SingleSeries> list = new LinkedList<SingleSeries>();
		
		sensorID = sensorID.replace(':', '_');
		List<TemperaturePredictionEvent> predictions = temperaturePredictionEventDAO.getTemperaturePredictionEvents(sensorID);
		
		Collections.sort(predictions, comparator);
		
		int start = Math.max(0,  predictions.size() - forecastLength);
		predictions = predictions.subList(start, predictions.size());
		//for multiple sensors:
		//SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Sensor " + (i+1) , colors.next(), sensorTemperatureList);
		SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries1Hour("1 hour predictions", predictions, colors.next());
		list.add(singleSeriesSensor);
		
		return list;
	}
	
	private List<SingleSeries> getListOfData2Hours(String sensorID, PackageUnit pack)
	{
		WebColorsCollection colors = new WebColorsCollection();
		List<SingleSeries> list = new LinkedList<SingleSeries>();
		
		sensorID = sensorID.replace(':', '_');
		List<TemperaturePredictionEvent> predictions = temperaturePredictionEventDAO.getTemperaturePredictionEvents(sensorID);
		
		Collections.sort(predictions, comparator);
		
		int start = Math.max(0,  predictions.size() - forecastLength);
		predictions = predictions.subList(start, predictions.size());
		//for multiple sensors:
		//SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Sensor " + (i+1) , colors.next(), sensorTemperatureList);
		SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries2Hours("2 hours predictions", predictions, colors.next());
		list.add(singleSeriesSensor);
		
		return list;
	}

	private List<String> getSensorsForPackage(String packID) {
		logger.info("in getSensorsForPackage: packID=  " + packID);
		List<String> allSensorsForPackage = new SPARQLAPI().getAllIDsBoundToBox(packID);
		logger.info("in getSensorsForPackage: allSensorsForPackage.size()=  " + allSensorsForPackage.size());
		return allSensorsForPackage;
	}
	
	private static final int limit = 120;
	
	private List<SingleSeries> getOutboundValues(PackageUnit pack, double minimumAllowableTemperature, double maximumAllowableTemperature) {
		WebColorsCollection colors = new WebColorsCollection();
		List<SingleSeries> list = new LinkedList<SingleSeries>();
		
		List<String> allSensorsForPackage = getSensorsForPackage(pack.getId() + "");
		
		long timeStart = getTimeStart(pack);
		long timeStop = getTimeStop(pack);
		
		for(int i=0; i<allSensorsForPackage.size(); i++)
		{
			String sensorID = allSensorsForPackage.get(i);
			List<TemperatureEvent> sensorList = temperatureEventDAO.getTemperatureEventsOutsideBounds(sensorID, timeStart, timeStop, minimumAllowableTemperature, maximumAllowableTemperature);
			sensorList = sensorList.subList(0, Math.min(limit, sensorList.size()));
			logger.info("sensorList.size() " + sensorList.size());
			SingleSeries singleSeriesSensor = DataMapper.fromSensorDataToSingleSeries("Sensor " + (i+1) , colors.next(), sensorList);
			list.add(singleSeriesSensor);
		}
		
		return list;
	}

	private long getTimeStop(PackageUnit pack) {
		return new Date().getTime();
	}

	private long getTimeStart(PackageUnit pack) {
		return pack.getStartTime();
	}
}
