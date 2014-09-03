package com.siemens.ct.ro.forecast;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.siemens.ct.ro.forecastUtils.Constants;
import com.siemens.ct.ro.forecastUtils.Logger;
import com.siemens.ct.ro.forecastUtils.WekaHelper;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;
import com.siemens.ct.ro.transportation.entities.TemperaturePrediction;
import com.siemens.ct.ro.transportation.utils.XMLGregorianCalendarConverter;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.LWL;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.trees.M5P;
import weka.core.Instances;

/**
 * Used for forecasting in ICORE
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 2.0
 */
public class ICOREForecaster implements Serializable{
	
	private static final long serialVersionUID = 2999481471264147857L;
	private ModelAndOptions modelAndOptions;
	private String tickFieldName;
	private String fieldNamesToBePredicted;
	private WekaForecaster workingWekaForecaster;
	
	//store the recent measurements
	private transient List<TruckSensorJoinedType> measurements = new ArrayList<TruckSensorJoinedType>();
	
	private static ModelAndOptions defaultModelAndOptions = null;
	
	static
	{
		try {
			defaultModelAndOptions = new ModelAndOptions(new LinearRegression(), "-S 0 -R 1.0E-8");
//			defaultModelAndOptions = new ModelAndOptions(new LWL(), "-U 0 -K 10 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\" -W weka.classifiers.functions.LinearRegression -- -S 0 -R 1.0E-8");
//			defaultModelAndOptions = new ModelAndOptions(new MultilayerPerceptron(), "-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 5");
//			defaultModelAndOptions = new ModelAndOptions(new GaussianProcesses(), "");
//			defaultModelAndOptions = new ModelAndOptions(new M5P(), "-M 4.0");
		} catch (Exception e) {
			//a given option is not supported
			e.printStackTrace();
			// TODO logging
		}
	}
	
	/**
	 * Parameterless constructor, creates an instance with default prediction models and its corresponding associated options
	 * @throws Exception if a field(s) can't be found, or if multiple fields are specified and this forecaster can't predict multiple fields.
	 */
	public ICOREForecaster() throws Exception {
		this(defaultModelAndOptions, Constants.TIME_FIELDNAME, Constants.FIELD_NAMES_TO_PREDICT);
	}
	
	/**
	 * Creates an instance based on the passed model/options pair, the field name of the attribute containing the timestamp, and the CSV list of the attributes to be predicted
	 * @param sensorID the id of the sensor 
	 * @param modelAndOptions pair of models and corresponding options
	 * @param tickFieldName the name of the tick/timestamp field
	 * @param fieldNamesToBePredicted 
	 * @throws Exception if a field(s) can't be found, or if multiple fields are specified and this forecaster can't predict multiple fields.
	 */
	public ICOREForecaster(ModelAndOptions modelAndOptions, String tickFieldName,
			String fieldNamesToBePredicted) throws Exception {
		
		this.modelAndOptions = modelAndOptions;
		this.tickFieldName = tickFieldName;
		
		if (fieldNamesToBePredicted == null)
		{
			throw new IllegalArgumentException("You must specify the field(s) to be predicted");
		}
		else
		{
			// set the targets we want to forecast.
			this.fieldNamesToBePredicted = fieldNamesToBePredicted;
		}
		workingWekaForecaster = new WekaForecaster();
		workingWekaForecaster.setFieldsToForecast(fieldNamesToBePredicted);
		workingWekaForecaster.setBaseForecaster(modelAndOptions.getInnerModel());
		workingWekaForecaster.getTSLagMaker().setTimeStampField("time");
		workingWekaForecaster.getTSLagMaker().setAdjustForVariance(true);
		workingWekaForecaster.getTSLagMaker().setMinLag(1);
		workingWekaForecaster.getTSLagMaker().setMaxLag(4);
	}
	
	/**
	 * adds a new measurement to the predictor
	 * @param data which is added to the predictor 
	 */
	public synchronized void recordNewMeasurement(TruckSensorJoinedType data)
	{
		allocateMeasurementsIfNull();
		measurements.add(data);
//		if (workingWekaForecaster == null)
//		{
//			System.out.println("!!!!!!!!!!!!!!!!!!!!workingWekaForecaster IS NULL");
//		}
//		if ( workingWekaForecaster.getTSLagMaker() == null)
//		{
//			System.out.println("!!!!!!!!!!!!!!!!!!!!workingWekaForecaster.getTSLagMaker() IS NULL");
//		}
		//remove too old data
//		int lagData = workingWekaForecaster.getTSLagMaker().getMaxLag();
//		if (measurements.size() > lagData)
//		{
//			measurements = measurements.subList(measurements.size() - lagData, measurements.size() );
//		}
	}
	
	/**
	 * Takes a collection of TruckSensorJoinedType,uses it as prime data for the forecasting model and gets the forecasted values
	 * @param data collection of recent TruckSensorJoinedType items
	 * @return forecasted values
	 */
	public synchronized TemperaturePrediction feedWithDataAndForecast(HashMap<String, TruckSensorJoinedType> data)
	{
		
		List<TruckSensorJoinedType> orderedList = new ArrayList<TruckSensorJoinedType>(data.size());
		for(Map.Entry<String, TruckSensorJoinedType> item : data.entrySet())
		{
			orderedList.add(item.getValue());
		}
		WekaHelper.orderList(orderedList);
		allocateMeasurementsIfNull();
		measurements.clear();
		for(TruckSensorJoinedType dataItem : orderedList)
		{
			recordNewMeasurement(dataItem);
		}
		Logger.log("after sorting: ", orderedList);
		TemperaturePredictionType temperaturePredictionType = forecast();
		
//		WekaHelper.log(temperaturePredictionType);
		
		TemperaturePrediction temperaturePrediction = new TemperaturePrediction();
		
		temperaturePrediction.setGatewayId(temperaturePredictionType.getGatewayId());
		temperaturePrediction.setPedictedTempTenMin(temperaturePredictionType.getPedictedTempTenMin());
		temperaturePrediction.setPredictedTempOneHour(temperaturePredictionType.getPredictedTempOneHour());
		temperaturePrediction.setPredictedTempTwoHours(temperaturePredictionType.getPredictedTempTwoHours());
		temperaturePrediction.setTemperatureSensorID(temperaturePredictionType.getTemperatureSensorID());
		temperaturePrediction.setTimestamp(XMLGregorianCalendarConverter.convertXMLGregorialCalendarToTimestampt(temperaturePredictionType.getTimestamp()));//de luat din xml gregorian si convertit inlong, in commons
		
		return temperaturePrediction;
	}
	
	private void allocateMeasurementsIfNull() {
		if (measurements == null)
		{
			measurements = new ArrayList<TruckSensorJoinedType>();
		}
	}

	/**
	 * Extracts the forecasted vales from the Weka forecaster
	 * @param size the forecasting horizon
	 * @return the forecasted values
	 * @throws Exception if the forecast can't be produced for some reason.
	 */
	private List<List<NumericPrediction>> forecastNumericValues(int size) throws Exception {
		return workingWekaForecaster.forecast(size);
	}
	
	/**
	 * Computes and returns a predicted value.
	 * @return the prediction
	 */
	public TemperaturePredictionType forecast()
	{
		TemperaturePredictionType prediction = new TemperaturePredictionType();
		prediction.setTemperatureSensorID(getSensorID());
		
		Instances primeData = WekaHelper.createInstances(this.measurements);
		//the prime data needs to be sorted by time in order to be pumped into the weka forecaster object
		primeData.sort(primeData.attribute("time"));
		try {
			workingWekaForecaster.primeForecaster(primeData);
		} catch (Exception e) {
			//the prime data cannot be used
			e.printStackTrace();
			// TODO logging
		}
		
		int toBeForecasted = 2 * 60 / 10 ;//number of 10-minute chunks in 2 hours; index 0 = prediction for the next 10 minutes
		
		List<List<NumericPrediction>> predictions;
		try {
			predictions = forecastNumericValues(toBeForecasted);
			prediction.setTimestamp(getLastTemperature());
			prediction.setPedictedTempTenMin(predictions.get(0).get(0).predicted());//the first prediction = the prediction for 10 minutes onwards
			prediction.setPredictedTempOneHour(predictions.get(5).get(0).predicted());//the prediction for 1 hour onwards
			prediction.setPredictedTempTwoHours(predictions.get(11).get(0).predicted());//the prediction for 2 hours onwards
		} catch (Exception e) {
			e.printStackTrace();
			// TODO logging
		}
		
		return prediction;
	}

	/**
	 * returns the temperature of the the last item in the (ordered) list of prime data measurements
	 * @return
	 */
	private XMLGregorianCalendar getLastTemperature() {
		if (measurements == null || measurements.size() == 0)
		{
			return null;
		}
		else
		{
			return measurements.get(measurements.size() - 1).getTimestamp();
		}
	}
	
	/**
	 * Trains the Weka forecaster model
	 * @param trainData the data to be used as training instances
	 * @param out the (possible empty) list of Streams which receives the stratus messages
	 * @throws Exception
	 */
	public void trainForecaster(List<TruckSensorJoinedType> trainData,
			PrintStream... out) throws Exception {
		
		Instances trainSet = WekaHelper.createInstances(trainData);
		
//		workingWekaForecaster.setOverlayFields(overlayFields);//TODO: de testat
		
		workingWekaForecaster.buildForecaster(trainSet, out);
	}

	/****************************************************************/
	/********************boilerplate code****************************/
	/****************************************************************/
	
	/**
	 * @return the id of the sensor for which the forecasting is made
	 */
	private String getSensorID() {
		if (measurements == null || measurements.size() == 0)
		{
			return "no sensor id";
			// TODO log
		}
		else
		{
			return measurements.get(0).getParcelTemperatureSensorId();
		}
	}

	/**
	 * @return the modelAndOptions used to instantiate the Weka forecaster model
	 */
	public ModelAndOptions getModelAndOptions() {
		return modelAndOptions;
	}

	/**
	 * @return the tickFieldName, the name of the attribute containing the timestamp of the data
	 */
	public String getTickFieldName() {
		return tickFieldName;
	}

	/**
	 * @return the fieldNamesToBePredicted, CSV string
	 */
	public String getFieldNamesToBePredicted() {
		return fieldNamesToBePredicted;
	}

	/**
	 * @return the Weka forecaster object, aka the workhorse
	 */
	public WekaForecaster getForecaster() {
		return workingWekaForecaster;
	}

}
