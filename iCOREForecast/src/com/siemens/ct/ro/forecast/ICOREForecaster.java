package com.siemens.ct.ro.forecast;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.siemens.ct.ro.forecastUtils.Constants;
import com.siemens.ct.ro.forecastUtils.WekaHelper;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;

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
			//defaultModelAndOptions = new ModelAndOptions(new LWL(), "-U 0 -K 10 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\" -W weka.classifiers.functions.LinearRegression -- -S 0 -R 1.0E-8");
//			defaultModelAndOptions = new ModelAndOptions(new MultilayerPerceptron(), "-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 5");
		} catch (Exception e) {
			//a given option is not supported
			e.printStackTrace();
			// TODO logging
		}
	}
	
	/**
	 * @throws Exception if a field(s) can't be found, or if multiple fields are specified and this forecaster can't predict multiple fields.
	 */
	public ICOREForecaster() throws Exception {
		this(defaultModelAndOptions, Constants.TIME_FIELDNAME, Constants.FIELD_NAMES_TO_PREDICT);
	}
	
	/**
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
		workingWekaForecaster.getTSLagMaker().setMaxLag(100);
	}
	
	/**
	 * adds a new measurement to the predictor
	 * @param data
	 */
	public synchronized void recordNewMeasurement(TruckSensorJoinedType data)
	{
		if (measurements == null)
		{
			measurements = new ArrayList<TruckSensorJoinedType>();
		}
		measurements.add(data);
		if (workingWekaForecaster == null)
		{
			System.out.println("!!!!!!!!!!!!!!!!!!!!workingWekaForecaster IS NULL");
		}
		if ( workingWekaForecaster.getTSLagMaker() == null)
		{
			System.out.println("!!!!!!!!!!!!!!!!!!!!workingWekaForecaster.getTSLagMaker() IS NULL");
		}
		int lagData = workingWekaForecaster.getTSLagMaker().getMaxLag();
		//remove too old data
		if (measurements.size() > lagData)
		{
			measurements = measurements.subList(measurements.size() - lagData, measurements.size() );
		}
	}
	
	/**
	 * 
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
		primeData.sort(primeData.attribute("time"));
		try {
			workingWekaForecaster.primeForecaster(primeData);
		} catch (Exception e) {
			//the prime data cannot be used
			e.printStackTrace();
			// TODO logging
		}
		
		int toBeForecasted = 2 * 60 / 10 ;//number of 10-minutes chunk in 2 hours; index 0 = prediction for the next 10 mins
		
		List<List<NumericPrediction>> predictions;
		try {
			predictions = forecastNumericValues(toBeForecasted);
			prediction.setTimestamp(getLastTemperature());
			prediction.setPedictedTempTenMin(""+predictions.get(0).get(0).predicted());//the first prediction = the prediction for 10 minutes onwards
			prediction.setPredictedTempOneHour(""+predictions.get(5).get(0).predicted());//the prediction for 1 hour onwards
			prediction.setPredictedTempTwoHours(""+predictions.get(11).get(0).predicted());//the prediction for 2 hours onwards
		} catch (Exception e) {
			e.printStackTrace();
			// TODO logging
		}
		
		return prediction;
	}

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

	/****************************************************************/
	/********************boilerplate code****************************/
	/****************************************************************/
	
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
	 * @return the modelAndOptions
	 */
	public ModelAndOptions getModelAndOptions() {
		return modelAndOptions;
	}

	/**
	 * @return the tickFieldName
	 */
	public String getTickFieldName() {
		return tickFieldName;
	}

//	/**
//	 * @param fieldNameTick the fieldNameTick to set
//	 */
//	public void setTickFieldName(String tickFieldName) {
//		this.tickFieldName = tickFieldName;
//	}

	/**
	 * @return the fieldNamesToBePredicted, CSV
	 */
	public String getFieldNamesToBePredicted() {
		return fieldNamesToBePredicted;
	}

	/**
	 * @return the forecaster
	 */
	public WekaForecaster getForecaster() {
		return workingWekaForecaster;
	}

	public void trainForecaster(List<TruckSensorJoinedType> trainData,
			PrintStream out) throws Exception {
		
		Instances trainSet = WekaHelper.createInstances(trainData);
		
//		workingWekaForecaster.setOverlayFields(overlayFields);//TODO: ce se intampla daca se executa? unde setez overlay?
		
		workingWekaForecaster.buildForecaster(trainSet, out);
	}
	
}
