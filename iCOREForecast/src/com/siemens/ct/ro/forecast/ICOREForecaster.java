package com.siemens.ct.ro.forecast;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.siemens.ct.ro.forecastUtils.Constants;
import com.siemens.ct.ro.forecastUtils.Utils;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.trees.M5P;
import weka.core.Instances;

/**
 * Used for forecasting in ICORE
 * 
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 2.0
 */
public class ICOREForecaster implements Serializable {

	private static final long serialVersionUID = 103L;
	private ModelAndOptions modelAndOptions;
	private String tickFieldName;
	private String fieldNamesToBePredicted;
	private WekaForecaster workingWekaForecaster;

	private final int secondsBetweenMeasurements = 15;

	// store the recent measurements
	private transient List<TruckSensorJointEvent> measurements = new ArrayList<TruckSensorJointEvent>();
	private long lastTimeInTrainSet;

	private static ModelAndOptions defaultModelAndOptions = null;

	static {
		try {
			defaultModelAndOptions = new ModelAndOptions(
					new MultilayerPerceptron(),
					"-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 5");
		} catch (Exception e) {
			// a given option is not supported
			e.printStackTrace();
			// TODO logging
		}
	}

	/**
	 * Parameterless constructor, creates an instance with default prediction
	 * models and its corresponding associated options
	 * 
	 * @throws Exception
	 *             if a field(s) can't be found, or if multiple fields are
	 *             specified and this forecaster can't predict multiple fields.
	 */
	public ICOREForecaster() throws Exception {
		this(defaultModelAndOptions, Constants.TIME_FIELDNAME,
				Constants.FIELD_NAMES_TO_PREDICT);
	}

	/**
	 * Creates an instance based on the passed model/options pair, the field
	 * name of the attribute containing the timestamp, and the CSV list of the
	 * attributes to be predicted
	 * 
	 * @param sensorID
	 *            the id of the sensor
	 * @param modelAndOptions
	 *            pair of models and corresponding options
	 * @param tickFieldName
	 *            the name of the tick/timestamp field
	 * @param fieldNamesToBePredicted
	 * @throws Exception
	 *             if a field(s) can't be found, or if multiple fields are
	 *             specified and this forecaster can't predict multiple fields.
	 */
	protected ICOREForecaster(ModelAndOptions modelAndOptions,
			String tickFieldName, String fieldNamesToBePredicted)
			throws Exception {

		this.modelAndOptions = modelAndOptions;
		this.tickFieldName = tickFieldName;

		if (fieldNamesToBePredicted == null) {
			throw new IllegalArgumentException(
					"You must specify the field(s) to be predicted");
		} else {
			// set the targets we want to forecast.
			this.fieldNamesToBePredicted = fieldNamesToBePredicted;
		}
		workingWekaForecaster = new WekaForecaster();
		workingWekaForecaster.setFieldsToForecast(fieldNamesToBePredicted);
		workingWekaForecaster
				.setBaseForecaster(modelAndOptions.getInnerModel());
		workingWekaForecaster.getTSLagMaker().setTimeStampField(
				Constants.TIME_FIELDNAME);
		workingWekaForecaster.getTSLagMaker().setAdjustForVariance(true);
		workingWekaForecaster.getTSLagMaker().setMinLag(1);
		workingWekaForecaster.getTSLagMaker().setMaxLag(20);
	}

	public ICOREForecaster(ModelAndOptions modelAndOptions) throws Exception {
		this(modelAndOptions, Constants.TIME_FIELDNAME,
				Constants.PARCEL_TEMPERATURE);
	}

	/**
	 * adds a new measurement to the predictor
	 * 
	 * @param data
	 *            which is added to the predictor
	 */
	private synchronized void recordNewMeasurement(TruckSensorJointEvent data) {
		measurements.add(data);
		// remove too old data
	}

	/**
	 * Takes a collection of TruckSensorJointEvent,uses it as prime data for the
	 * forecasting model and gets the forecasted values
	 * 
	 * @param data
	 *            collection of recent TruckSensorJointEvent items
	 * @return forecasted values
	 */
	public synchronized TemperaturePredictionEvent feedWithDataAndForecast(
			HashMap<String, TruckSensorJointEvent> data) {
		List<TruckSensorJointEvent> dataAsList = Utils.createInstances(data);
		Utils.orderList(dataAsList);
		// Logger.log("orderedList: ", dataAsList);

		TruckSensorJointEvent lastDataGiven = dataAsList
				.get(dataAsList.size() - 1);

		primeData(dataAsList);
		TemperaturePredictionEvent temperaturePredictionEvent = forecast();

		TemperaturePredictionEvent temperaturePrediction = new TemperaturePredictionEvent();

		temperaturePrediction.setGatewayId(temperaturePredictionEvent
				.getGatewayId());
		temperaturePrediction.setPedictedTempTenMin(temperaturePredictionEvent
				.getPedictedTempTenMin());
		temperaturePrediction
				.setPredictedTempOneHour(temperaturePredictionEvent
						.getPredictedTempOneHour());
		temperaturePrediction
				.setPredictedTempTwoHours(temperaturePredictionEvent
						.getPredictedTempTwoHours());
		temperaturePrediction.setTemperatureSensorID(temperaturePredictionEvent
				.getTemperatureSensorID());
		temperaturePrediction.setTimestamp(lastDataGiven.getTimestamp());

		// System.out.println(Utils.toString(temperaturePrediction));

		return temperaturePrediction;
	}

	public synchronized void primeData(List<TruckSensorJointEvent> primeData) {
		allocateMeasurementsIfNull();
		measurements.clear();
		List<TruckSensorJointEvent> clonedData = Utils.alignData(primeData,
				this.lastTimeInTrainSet, this.secondsBetweenMeasurements);
		for (TruckSensorJointEvent dataItem : clonedData) {
			recordNewMeasurement(dataItem);
		}
		try {
			Instances primeDataInstances = Utils.createInstances(clonedData);
			workingWekaForecaster.primeForecaster(primeDataInstances);
		} catch (Exception exception) {
			System.out.println("exception thrown at primeData call: "
					+ exception.getMessage());
		}
	}

	private void allocateMeasurementsIfNull() {
		if (measurements == null) {
			measurements = new ArrayList<TruckSensorJointEvent>();
		}
	}

	/**
	 * Extracts the forecasted vales from the Weka forecaster
	 * 
	 * @param size
	 *            the forecasting horizon
	 * @return the forecasted values; by default, weka can provide multiple
	 *         output values for a single time moment
	 * @throws Exception
	 *             if the forecast can't be produced for some reason.
	 */
	public List<List<NumericPrediction>> forecastNumericValues(int size)
			throws Exception {
		return workingWekaForecaster.forecast(size);
	}

	/**
	 * Extracts a list of double values - as provided by method
	 * forecastNumericValues, but only the single forecasted value
	 * @param size how many data to forecast
	 * @return either an empty list, if the prediction cannot be made, or a list containing size Double values
	 */
	public List<Double> forecast(int size) {
		List<List<NumericPrediction>> forecasted;
		LinkedList<Double> result = new LinkedList<Double>();
		try {
			forecasted = forecastNumericValues(size);

			if (forecasted == null || forecasted.size() == 0) {
				//that's it, the result is already created
			} else {
				for (List<NumericPrediction> multipleValuesAtAPoint : forecasted) {
					result.add(multipleValuesAtAPoint.get(0).predicted());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//the result is already created
		}
		return result;
	}

	/**
	 * Computes and returns a predicted value.
	 * 
	 * @return the prediction
	 */
	private synchronized TemperaturePredictionEvent forecast() {
		TemperaturePredictionEvent prediction = new TemperaturePredictionEvent();
		prediction.setTemperatureSensorID(getSensorID());

		if (measurements.size() != 0) {
			// the prime data needs to be sorted by time in order to be pumped
			// into the weka forecaster object
			Instances primeData = Utils.createInstances(this.measurements);
			try {
				workingWekaForecaster.primeForecaster(primeData);
			} catch (Exception e) {
				// the prime data cannot be used
				e.printStackTrace();
			}
		}

		// number of 15-second chunks in 2 hours; index 0 = prediction for the
		// next 10 minutes
		int toBeForecasted = 2 * 60 * 60 / secondsBetweenMeasurements;

		List<List<NumericPrediction>> predictions;
		try {
			predictions = forecastNumericValues(toBeForecasted);
			prediction.setTimestamp(getTimeForLastTemperature());
			prediction.setPedictedTempTenMin(predictions.get(0).get(0)
					.predicted());// the first prediction = the prediction for
									// 15 seconds onwards
			prediction.setPredictedTempOneHour(predictions
					.get(-1 + 1 * 60 * 60 / secondsBetweenMeasurements).get(0)
					.predicted());// the prediction for 1 hour onwards
			prediction.setPredictedTempTwoHours(predictions
					.get(-1 + 2 * 60 * 60 / secondsBetweenMeasurements).get(0)
					.predicted());// the prediction for 2 hours onwards
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prediction;
	}

	public double computeRMSE(List<Double> actual, List<Double> predicted) {
		return 0;
	}

	/**
	 * returns the temperature of the the last item in the (ordered) list of
	 * prime data measurements
	 * 
	 * @return
	 */
	private long getTimeForLastTemperature() {
		if (measurements == null || measurements.size() == 0) {
			return Long.MIN_VALUE;
		} else {
			return measurements.get(measurements.size() - 1).getTimestamp();
		}
	}

	/**
	 * Trains the Weka forecaster model
	 * 
	 * @param trainData
	 *            the data to be used as training instances
	 * @param out
	 *            the (possible empty) list of Streams which receives the
	 *            stratus messages
	 * @throws Exception
	 */
	public void trainForecaster(List<TruckSensorJointEvent> trainData,
			PrintStream... out) throws Exception {

		Instances trainSet = Utils.createInstances(trainData);

		workingWekaForecaster.buildForecaster(trainSet, out);

		Utils.orderList(trainData);
		TruckSensorJointEvent lastTrainItem = trainData
				.get(trainData.size() - 1);

		// this.lastTimeInTrainSet =
		// XMLGregorianCalendarConverter.convertXMLGregorialCalendarToTimestampt(lastTrainItem.getTimestamp());
		this.lastTimeInTrainSet = lastTrainItem.getTimestamp();
	}

	/****************************************************************/
	/******************** boilerplate code ****************************/
	/****************************************************************/

	/**
	 * @return the id of the sensor for which the forecasting is made
	 */
	private String getSensorID() {
		if (measurements == null || measurements.size() == 0) {
			return "no sensor id";
			// TODO log
		} else {
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
	 * @return the tickFieldName, the name of the attribute containing the
	 *         timestamp of the data
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

	/****************************** end boilerplate code ***********************/

	/********************* Creational methods ***********************/

	public static ICOREForecaster createLinearModel(String linear_options) {
		ModelAndOptions modelAndOptions;
		try {
			System.out
					.println("ICOREForecaster createLinearModel: linear_options= "
							+ linear_options);
			String wekaOptions;
			switch (linear_options) {
			default:
			case "m5_method":
				wekaOptions = "-S 0 -R 1.0E-8";
				break;
			case "greedy_method":
				wekaOptions = "-S 2 -R 1.0E-8";
				break;
			case "no_attribute":
				wekaOptions = "-S 1 -R 1.0E-8";
				break;
			}
			modelAndOptions = new ModelAndOptions(new LinearRegression(),
					wekaOptions);
			return new ICOREForecaster(modelAndOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ICOREForecaster createLinearModel(int hiddenNeurons,
			double learningRate, double momentumCoefficient) {
		ModelAndOptions modelAndOptions;
		try {
			String wekaOptions = "-L " + learningRate + " -M "
					+ momentumCoefficient + " -N 500 -V 0 -S 0 -E 20 -H "
					+ hiddenNeurons;
			modelAndOptions = new ModelAndOptions(new MultilayerPerceptron(),
					wekaOptions);
			return new ICOREForecaster(modelAndOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ICOREForecaster createM5P(int minimumNumber) {
		ModelAndOptions modelAndOptions;
		try {
			String wekaOptions = "-M " + minimumNumber;
			modelAndOptions = new ModelAndOptions(new M5P(), wekaOptions);
			return new ICOREForecaster(modelAndOptions);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// public static ICOREForecaster createRBF(int numberOfClusters,
	// double standardDeviation) {
	// ModelAndOptions modelAndOptions;
	// try {
	// String wekaOptions = "-B " + numberOfClusters +
	// " -S 1 -R 1.0E-8 -M -1 -W " + standardDeviation;
	// System.out.println("wekaOptions: " + wekaOptions);
	// modelAndOptions = new ModelAndOptions(new Rbf, wekaOptions);
	// // return new ICOREForecaster(modelAndOptions);
	// return null;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
}
