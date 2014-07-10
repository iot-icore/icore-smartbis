/**
 * 
 */
package com.siemens.ct.ro.forecast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;

import com.siemens.ct.ro.forecastUtils.CSVUtils;
import com.siemens.ct.ro.forecastUtils.GenericDataSplitter;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.transportation.dataformatfromcep.TemperaturePredictionType;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

/**
 * @author Lucian Sasu
 * 
 */
public class MainClass {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		workflowTrainSerialize();
		
//		deserialize();
		
//		createNew();

		System.out.println("main done");
	}

//	private static void demoWithoutOverlay() throws Exception {

		// ICOREForecaster icoreForecaster = new ICOREForecaster();
		//
		// String fieldNamesToBePredicted = icoreForecaster
		// .getFieldNamesToBePredicted();
		// String tickFieldName = icoreForecaster.getTickFieldName();
		//
		// Instances dataset = getTSDataSet(tickFieldName);
		// Measurements allMeasurements =
		// WekaHelper.createMeasurements(dataset);
		//
		// Measurements[] splits =
		// DataSplitter.splitMeasurements(allMeasurements,
		// 0.6, 0.2, 0.2);
		// Measurements trainData = splits[0];
		// Measurements primeData = splits[1];
		// Measurements testData = splits[2];
		//
		// icoreForecaster.trainForecaster(trainData, System.out);
		//
		// // here i get CEP data
		// for (MeasurementData data : primeData) {
		// icoreForecaster.recordNewMeasurement(data);
		// }
		//
		// double[] predictedValues = icoreForecaster.forecast(testData.size());
		// double[] actualValues = getActualValues(testData);
		//
		// IMetric metric = new RMSEDouble(predictedValues, actualValues);
		// System.out.println("RMSE: " + metric.getMetric());
		//
		// // System.out.println(metric.getMetric());
//	}

//	private static void demoWithOverlay() throws Exception {
//		AbstractClassifier model;
//		String options;
//
//		// Linear regression
//		model = new LinearRegression();
//		options = "-S 0 -R 1.0E-8";
//		model.setOptions(weka.core.Utils.splitOptions(options));
//		WekaForecaster wekaForecaster = new WekaForecaster();
//		wekaForecaster.setBaseForecaster(model);
//
//		String fieldNamesToBePredicted = "valueTS_002";
//		String tickFieldName = "tick";
//
//		Instances dataset = getTSDataSet(tickFieldName);
//
//		double percentageTrain = 0.6;
//		double percentagePrimeData = 0.2;
//		int trainSetFrom = 0;
//		int trainSetToCopy = (int) (dataset.size() * percentageTrain);
//		Instances trainSubset = new Instances(dataset, trainSetFrom,
//				trainSetToCopy);
//		int primeDataFrom = trainSetToCopy;
//		int primeDataToCopy = (int) (percentagePrimeData * dataset.size());
//		Instances primedataSubset = new Instances(dataset, primeDataFrom,
//				primeDataToCopy);
//		int testDataFrom = primeDataFrom + primeDataToCopy;
//		int testDataToCopy = dataset.size() - testDataFrom;
//		Instances testSubset = new Instances(dataset, testDataFrom,
//				testDataToCopy);
//
//		wekaForecaster
//				.setOverlayFields("hvac100on,hvac200on,hvac300on,valueTS100,valueTS101,valueTS102,valueTS103,valueTS104,hvac100Value,hvac200Value,hvac300Value");
//
//		wekaForecaster.setFieldsToForecast(fieldNamesToBePredicted);
//
//		wekaForecaster.buildForecaster(trainSubset, System.out);
//
//		wekaForecaster.primeForecaster(primedataSubset);
//
//		// Instances emptyInstances = new Instances(trainSubset, -1);
//		//
//		// wekaForecaster.primeForecaster(emptyInstances);
//
//		// Instances testAndPrime = new Instances(trainSubset);
//		// testAndPrime.addAll(primedataSubset);
//		// wekaForecaster.primeForecaster(testAndPrime);
//
//		Instances overlayData = new Instances(testSubset);
//		WekaHelper.setUnknownValues(overlayData, fieldNamesToBePredicted);
//
//		List<List<NumericPrediction>> forecastedValues = wekaForecaster
//				.forecast(testSubset.size(), overlayData, System.out);
//		// List<List<NumericPrediction>> forecastedValues =
//		// wekaForecaster.forecast(testSubset.size(), System.out);//crapa, s-a
//		// declarat ca se lucreaza cu overlay data
//		IMetric metric = new RMSE(forecastedValues, testSubset,
//				fieldNamesToBePredicted);
//		System.out.println(metric.getMetric());
//	}

//	private static double getRMSEForModelAndData(
//			ModelAndOptions modelAndOptions, Instances trainInstances,
//			Instances testInstances, String tickFieldName,
//			String fieldNamesToBePredicted) throws Exception {
//		// //ICOREForecaster icoreForecaster = new
//		// ICOREForecaster(modelAndOptions, tickFieldName,
//		// fieldNamesToBePredicted);
//		// ICOREForecaster icoreForecaster = new ICOREForecaster();
//		// icoreForecaster.buildForecaster(trainInstances, System.out);
//		//
//		// List<List<NumericPrediction>> estimatedValues =
//		// icoreForecaster.forecast(testInstances.size());
//		//
//		// IMetric metric = new RMSE(estimatedValues, testInstances,
//		// fieldNamesToBePredicted);
//		// double rmse = metric.getMetric();
//		// // System.out.println("Metric: " + metric.getMetricName() + "\n" +
//		// "score: " + metric.getMetric());
//		//
//		// return rmse;
//
//		return 0.0;
//	}

//	private static void demo2() throws Exception {
//		String fieldNameToBePredicted = "valueTS_002";
//		String tickFieldName = "tick";
//
//		Instances dataset = getTSDataSet(tickFieldName);
//
//		double percentageTrain = 0.8;
//		int trainSize = (int) (dataset.size() * percentageTrain);
//
//		Instances trainSubset = new Instances(dataset, 0, trainSize);
//		Instances testSubset = new Instances(dataset, trainSize, dataset.size()
//				- trainSize);
//
//		// Instances overlayData = prepareOverlayData(testSubset,
//		// fieldNameToBePredicted, )
//
//		List<ModelAndOptions> modelsAndOptions = getModelsAndOptions();
//
//		for (ModelAndOptions modelAndOptions : modelsAndOptions) {
//			double rmse = getRMSEForModelAndData(modelAndOptions, trainSubset,
//					testSubset, tickFieldName, fieldNameToBePredicted);
//			System.out.println("RMSE= " + rmse);
//			System.out.println("****************************************\n\n");
//		}
//
//		// WekaForecaster wekaForecaster = new WekaForecaster();
//		// wekaForecaster.setFieldsToForecast(fieldNameToBePredicted);
//		// wekaForecaster.setBaseForecaster(model);
//		// wekaForecaster.buildForecaster(trainSubset, System.out);
//		// wekaForecaster.primeForecaster(trainSubset);
//
//		// ICOREForecaster icoreForecaster = new
//		// ICOREForecaster(modelAndOptions, tickFieldName,
//		// fieldNameToBePredicted);
//		// icoreForecaster.setTrainSet(trainSubset);
//		// icoreForecaster.setTestSet(testSubset);
//		// icoreForecaster.buildForecaster(System.out);
//
//		// forecaster.getTSLagMaker().setTimeStampField("tick");
//		// forecaster.getTSLagMaker().setAdjustForVariance(true);
//		// wekaForecaster.getTSLagMaker().setMinLag(1);
//		// wekaForecaster.getTSLagMaker().setMaxLag(100);
//
//		// ErrorModule errorModule = evaluation.getPredictionsForTestData(1);
//		// evaluation.evaluateForecaster(forecaster, System.out);
//		// System.out.println(evaluation.toSummaryString());
//		// System.out.println(
//		// evaluation.printPredictionsForTestData("aaaaaaaaa", "valueTS100", 1)
//		// );
//
//		// List<List<NumericPrediction>> estimatedValues =
//		// icoreForecaster.forecast(testSubset.size());
//
//		// List<List<NumericPrediction>> forecastedValues =
//		// wekaForecaster.forecast(testSubset.size(), System.out);
//
//		// for(List<NumericPrediction> list : forecastedValues)
//		// {
//		// NumericPrediction value = list.get(0);
//		// System.out.println("actual value: " + value.actual() +
//		// " ; predicted: " + value.predicted());
//		// }
//
//		// IMetric metric = new RMSE(estimatedValues, testSubset,
//		// fieldNameToBePredicted);
//		// System.out.println("Metric: " + metric.getMetricName() + "\n" +
//		// "score: " + metric.getMetric());
//
//		// Attribute attribute = dataset.attribute(fieldNameToBePredicted);
//		// int index = attribute.index();
//
//		// for(int i=0; i<testSubset.size(); i++)
//		// {
//		// NumericPrediction value = estimatedValues.get(i).get(0);
//		// System.out.println("actual value: " + testSubset.get(i).value(index)
//		// + " ; predicted: " + value.predicted());
//		// }
//	}

//	private static List<ModelAndOptions> getModelsAndOptions() throws Exception {
//		List<ModelAndOptions> result = new LinkedList<ModelAndOptions>();
//
//		AbstractClassifier model;
//		String options;
//		ModelAndOptions modelAndOptions;
//
//		// Linear regression
//		model = new LinearRegression();
//		options = "-S 0 -R 1.0E-8";
//
//		modelAndOptions = new ModelAndOptions(model, options);
//		result.add(modelAndOptions);
//
//		// LWL
//		model = new LWL();
//		options = "-U 0 -K 10 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\" -W weka.classifiers.functions.LinearRegression -- -S 0 -R 1.0E-8";
//
//		modelAndOptions = new ModelAndOptions(model, options);
//		result.add(modelAndOptions);
//
//		// MLP
//		model = new MultilayerPerceptron();
//		options = "-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 5";
//
//		modelAndOptions = new ModelAndOptions(model, options);
//		result.add(modelAndOptions);
//
//		// M5P
//		model = new M5P();
//		options = "-M 4.0";
//
//		modelAndOptions = new ModelAndOptions(model, options);
//		result.add(modelAndOptions);
//
//		// Gaussian Processes
//		model = new GaussianProcesses();
//		options = "-L 1.0 -N 0 -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\"";
//
//		modelAndOptions = new ModelAndOptions(model, options);
//		result.add(modelAndOptions);
//
//		//
//		// modelAndOptions = new ModelAndOptions(model, options);
//		// result.add(modelAndOptions);
//
//		return result;
//	}

//	/**
//	 * @throws IOException
//	 * @throws FileNotFoundException
//	 * @throws Exception
//	 */
//	private static void demo1() throws IOException, FileNotFoundException,
//			Exception {
//		Instances dataset = getTSDataSet("tick");
//		WekaForecaster forecaster = new WekaForecaster();
//		forecaster.setFieldsToForecast("valueTS100");
//		// forecaster.setBaseForecaster(new GaussianProcesses());
//		AbstractClassifier model = new LinearRegression();
//		model.setOptions(weka.core.Utils.splitOptions("-S 0 -R 1.0E-8"));
//		forecaster.setBaseForecaster(model);
//		forecaster.getTSLagMaker().setTimeStampField("tick");
//		forecaster.getTSLagMaker().setAdjustForVariance(true);
//		forecaster.getTSLagMaker().setMinLag(1);
//		forecaster.getTSLagMaker().setMaxLag(100);
//
//		forecaster.buildForecaster(dataset, System.out);
//		forecaster.primeForecaster(dataset);
//
//		List<List<NumericPrediction>> forecastedValues = forecaster
//				.forecast(100);
//
//		for (List<NumericPrediction> list : forecastedValues) {
//			NumericPrediction value = list.get(0);
//			System.out.println("actual value: " + value.actual()
//					+ " ; predicted: " + value.predicted());
//		}
//	}

	private static void createNew() throws Exception {
		ICOREForecaster icoreForecaster = new ICOREForecaster();
		System.out.println(icoreForecaster.getModelAndOptions().getInnerModel().toString());
		System.out.println(icoreForecaster.getModelAndOptions().getOptions().toString());
		//java.lang.NullPointerException
//		LinearRegression linearRegression = (LinearRegression)icoreForecaster.getModelAndOptions().getInnerModel();
//		System.out.println(linearRegression.coefficients());
	}

	private static void deserialize() {
		
		String serialiationFilename = "serialized/icoreForecaster.ser";
		ICOREForecaster icoreForecaster = ICORESerializer.deserialize(serialiationFilename);
		System.out.println("getInnerModel= " + icoreForecaster.getModelAndOptions().getInnerModel().toString());
		System.out.println("getOptions= " + icoreForecaster.getModelAndOptions().getOptions().toString());
	}

	private static void workflowTrainSerialize() throws IOException, FileNotFoundException,
			Exception {

		List<TruckSensorJoinedType> allData = CSVUtils.readData(
				"data/data2_2.csv", true);
		List<List<TruckSensorJoinedType>> splits = new GenericDataSplitter<TruckSensorJoinedType>()
				.splitData(allData, 0.6, 0.2, 0.2);
		List<TruckSensorJoinedType> trainData = splits.get(0);
		List<TruckSensorJoinedType> primeData = splits.get(1);
		List<TruckSensorJoinedType> testData = splits.get(2);

		ICOREForecaster icoreForecaster = new ICOREForecaster();
		System.out.println("Start training the forecaster");
		icoreForecaster.trainForecaster(trainData, System.out);
		
//		System.out.println(icoreForecaster.getModelAndOptions().getInnerModel().toString());
//		System.out.println(icoreForecaster.getModelAndOptions().getOptions().toString());
		String serialiationFilename = "serialized/icoreForecaster.ser";
		
		ICORESerializer.serialize(icoreForecaster, serialiationFilename);
		System.out.println("Serialization made in file " + serialiationFilename);

		for (TruckSensorJoinedType truckSensorJoinedType : primeData) {
			icoreForecaster.recordNewMeasurement(truckSensorJoinedType);
		}
		
		TemperaturePredictionType prediction = icoreForecaster.forecast();
		
		System.out.println("prediction: in ten minutes: " + prediction.getPedictedTempTenMin());
		System.out.println("prediction: in one hour: " + prediction.getPredictedTempOneHour());
		System.out.println("prediction: in two hours: " + prediction.getPredictedTempTwoHours());
//		System.out.println("timestamp for predicted data: " + prediction.getTimestamp());

	}

//	/**
//	 * @return
//	 * @throws IOException
//	 * @throws FileNotFoundException
//	 */
//	private static Instances getTSDataSet(String timeAttributeName)
//			throws IOException, FileNotFoundException {
//		Instances dataset = new Instances(new BufferedReader(new FileReader(
//				"data/measurements_TSIM1397134595325.arff")));
//		Attribute timeAttribute = dataset.attribute(timeAttributeName);
//		dataset.sort(timeAttribute);
//		return dataset;
//	}

}
