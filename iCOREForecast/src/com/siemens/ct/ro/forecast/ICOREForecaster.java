package com.siemens.ct.ro.forecast;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import com.siemens.ct.ro.forecastUtils.MeasurementData;
import com.siemens.ct.ro.forecastUtils.Measurements;
import com.siemens.ct.ro.forecastUtils.WekaHelper;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredicitonType;
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
	
	private Measurements storedMeasurements = new Measurements();
	
	private static ModelAndOptions defaultModelAndOptions = null;
	private static final String defaultTickFieldName = "tick";
	private static final String defaultFieldNamesToBePredicted = "valueTS_002";
	
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
		super();
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
	}
	

	/**
	 * Trains the 
	 * @param out an optional varargs parameter supplying progress objects to report/log to
	 * @throws Exception if the model can't be constructed for some reason.
	 */
	public void trainForecaster(Measurements measurements, PrintStream... out) throws Exception {
		
		Instances trainSet = WekaHelper.createInstances(measurements);
		
		wekaForecaster = new WekaForecaster();
		wekaForecaster.setBaseForecaster(modelAndOptions.getInnerModel());
		wekaForecaster.setFieldsToForecast(fieldNamesToBePredicted);
		wekaForecaster.getTSLagMaker().setTimeStampField(tickFieldName);
		
//		wekaForecaster.setOverlayFields(overlayFields);//TODO: ce se intampla daca se executa? unde setez overlay?
		
		System.out.println(wekaForecaster.getAlgorithmName());
		//default values: 
		//wekaForecaster.getTSLagMaker().getMinLag() == 1
		//wekaForecaster.getTSLagMaker().getMaxLag() == 12
		
		wekaForecaster.buildForecaster(trainSet, out);
		
		// prime the forecaster with enough recent historical data
	    // to cover up to the maximum lag. 
//		wekaForecaster.primeForecaster(trainSet);
	}

	public void recordNewMeasurement(MeasurementData data)
	{
		storedMeasurements.add(data);
	}
	
	public void recordNewMeasurement(TruckSensorJoinedType data)
	{
		//not yet implemented
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
	
	//pump + forecast; store the data internally
//	public double[] forecast(int size, Measurements overlayData) throws Exception {
//		if (size <= 0)
//		{
//			throw new IllegalArgumentException("The forecast must be required for at least one value");
//		}
//		double[] result = new double[size];
//		Instances instancesOverlayData = WekaHelper.createInstances(overlayData);
//		return forecast(size, instancesOverlayData);
//	}	
	
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
		Instances primeData = WekaHelper.createInstances(this.storedMeasurements);
		wekaForecaster.primeForecaster(primeData);
		List<List<NumericPrediction>> predictions = forecastNumericValues(size);
		for(int i=0; i<predictions.size(); i++)
		{
			result[i] = predictions.get(i).get(0).predicted();
		}
		return result;
	}	
	
	public TemperaturePredicitonType forecast()
	{
		TemperaturePredicitonType prediction = new TemperaturePredicitonType();
		//TODO: create prediction
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
	
}
