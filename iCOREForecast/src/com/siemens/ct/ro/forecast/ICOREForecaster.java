package com.siemens.ct.ro.forecast;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.ro.forecastUtils.WekaHelper;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class ICOREForecaster implements Serializable{
	
	private static final long serialVersionUID = 2999481471264147857L;
	private ModelAndOptions modelAndOptions;
	private String tickFieldName;
	private String fieldNamesToBePredicted;
	private WekaForecaster wekaForecaster;
	
	private List<TruckSensorJoinedType> measurements = new ArrayList<TruckSensorJoinedType>();
	
	private static ModelAndOptions defaultModelAndOptions = null;
	private static final String defaultTickFieldName = "tick";
	private static final String defaultFieldNamesToBePredicted = "temperatureParcel";
	
	static
	{
		try {
			defaultModelAndOptions = new ModelAndOptions(new LinearRegression(), "-S 0 -R 1.0E-8");
		} catch (Exception e) {
			// TODO logging
		}
	}
	
	/**
	 * default constructor
	 * @throws Exception if a field(s) can't be found, or if multiple fields are specified and this forecaster can't predict multiple fields.
	 */
	public ICOREForecaster() throws Exception {
		this(defaultModelAndOptions, defaultTickFieldName, defaultFieldNamesToBePredicted);
	}
	
	/**
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
		wekaForecaster = new WekaForecaster();
		wekaForecaster.setFieldsToForecast(fieldNamesToBePredicted);
		wekaForecaster.setBaseForecaster(modelAndOptions.getInnerModel());
		wekaForecaster.getTSLagMaker().setTimeStampField("time");
		wekaForecaster.getTSLagMaker().setAdjustForVariance(true);
		wekaForecaster.getTSLagMaker().setMinLag(1);
		wekaForecaster.getTSLagMaker().setMaxLag(100);
	}
	
	public void recordNewMeasurement(TruckSensorJoinedType data)
	{
		measurements.add(data);
	}
	
	private List<List<NumericPrediction>> forecastNumericValues(int size, Instances overlayData) throws Exception {
			overlayData = setUnknownValues(overlayData, getFieldNamesToBePredicted());
			return wekaForecaster.forecast(size, overlayData);
	}
	
	/**
	 * 
	 * @param size the forecasting horizon
	 * @return the forecasted values
	 * @throws Exception if the forecast can't be produced for some reason.
	 */
	private List<List<NumericPrediction>> forecastNumericValues(int size) throws Exception {
		return wekaForecaster.forecast(size);
	}
	
	/**
	 * 
	 * @param size the forecast horizon
	 * @return forecasted double in an array of length 'size'
	 * @throws Exception
	 */
	public double[] forecast(int size) throws Exception {
		if (size <= 0)
		{
			throw new IllegalArgumentException("The forecast must be required for at least one value");
		}
		double[] result = new double[size];
		Instances primeData = WekaHelper.createInstances(this.measurements);
		wekaForecaster.primeForecaster(primeData);
		List<List<NumericPrediction>> predictions = forecastNumericValues(size);
		for(int i=0; i<predictions.size(); i++)
		{
			result[i] = predictions.get(i).get(0).predicted();
		}
		return result;
	}	
	
	public TemperaturePredictionType forecast()
	{
		TemperaturePredictionType prediction = new TemperaturePredictionType();
		
		Instances primeData = WekaHelper.createInstances(this.measurements);
		try {
			wekaForecaster.primeForecaster(primeData);
		} catch (Exception e) {
			//the prime data cannot be used
		}
		
		int toBeForecasted = 2 * 60 / 10 ;//number of 10-minutes chunk in 2 hours; index 0 = prediction for the next 10 mins
		
		List<List<NumericPrediction>> predictions;
		try {
			predictions = forecastNumericValues(toBeForecasted);
			double[] predictionsDouble = new double[toBeForecasted];
			for(int i=0; i<predictions.size(); i++)
			{
				predictionsDouble[i] = predictions.get(i).get(0).predicted();
			}
			prediction.setPedictedTempTenMin(""+predictionsDouble[0]);
			prediction.setPredictedTempOneHour(""+predictionsDouble[5]);
			prediction.setPredictedTempTwoHours(""+predictionsDouble[11]);
		} catch (Exception e) {
		}
		
		return prediction;
	}

	/**
	 * If needed, removes the values of the data to be forecasted
	 * @param dataset the dataset containing recent measurements
	 * @param fieldNamesToSetAsUnknown comma separated names of the 
	 * @return
	 */
	private static Instances setUnknownValues(Instances dataset, String fieldNamesToSetAsUnknown) {
		String[] tokensFieldNamesToBeSetUnknown = fieldNamesToSetAsUnknown.split(",");
		Attribute[] attributesToBePredicted = new Attribute[tokensFieldNamesToBeSetUnknown.length];
		Instances result = new Instances(dataset);
		for(int i = 0; i<attributesToBePredicted.length; i++)
		{
			attributesToBePredicted[i] = result.attribute(tokensFieldNamesToBeSetUnknown[i]);
		}
		for(Instance instance : result)
		{
			for(Attribute attribute: attributesToBePredicted)
			{
				instance.setMissing(attribute);
			}
		}
		return result;
	}
	
	/****************************************************************/
	/********************boilerplate code****************************/
	/**

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

	/**
	 * @param fieldNameTick the fieldNameTick to set
	 */
	public void setTickFieldName(String tickFieldName) {
		this.tickFieldName = tickFieldName;
	}

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
		return wekaForecaster;
	}

	public void trainForecaster(List<TruckSensorJoinedType> trainData,
			PrintStream out) throws Exception {
		
		Instances trainSet = WekaHelper.createInstances(trainData);
		
//		System.out.println(trainSet);
		
//		wekaForecaster.setOverlayFields(overlayFields);//TODO: ce se intampla daca se executa? unde setez overlay?
		
//		System.out.println("Core regression algorithm: " + wekaForecaster.getAlgorithmName());
		
		wekaForecaster.buildForecaster(trainSet, out);
		// prime the forecaster with enough recent historical data
	    // to cover up to the maximum lag. 
//		wekaForecaster.primeForecaster(trainSet);
	}
	
}
