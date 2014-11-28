/**
 * 
 */
package com.siemens.ct.ro.forecast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import weka.classifiers.evaluation.NumericPrediction;

import com.siemens.ct.ro.forecastUtils.CSVUtils;
import com.siemens.ct.ro.forecastUtils.GenericDataSplitter;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;
import com.siemens.ct.ro.forecastUtils.Utils;
import com.softwareag.transportation.CEPevents.TemperaturePredictionEvent;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

/**
 * @author Lucian Sasu
 * 
 */
@SuppressWarnings("unused")
public class MainClassICOREForecaster {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		workflowTrainSerialize();

		// deserialize();

		// createNew();

		// makePredictions();
		
//		workflowTrainTest();

		System.out.println("main done");
	}

	private static void makePredictions() throws FileNotFoundException {
		String serialiationFilename = "serialized/icoreForecaster.ser";
		ICOREForecaster icoreForecaster = ICORESerializer
				.deserialize(serialiationFilename);
		throw new RuntimeException("makePredictions methot not implemented");
	}

	private static void createNew() throws Exception {
		ICOREForecaster icoreForecaster = new ICOREForecaster();
		System.out.println(icoreForecaster.getModelAndOptions().getInnerModel()
				.toString());
		System.out.println(icoreForecaster.getModelAndOptions().getOptions()
				.toString());
	}

	private static void deserialize() {

		String serialiationFilename = "serialized/icoreForecaster.ser";
		ICOREForecaster icoreForecaster = ICORESerializer
				.deserialize(serialiationFilename);
		System.out.println("getInnerModel= "
				+ icoreForecaster.getModelAndOptions().getInnerModel()
						.toString());
		System.out.println("getOptions= "
				+ icoreForecaster.getModelAndOptions().getOptions().toString());
	}

	private static void workflowTrainTest() throws IOException,
			FileNotFoundException, Exception {

		List<TruckSensorJointEvent> allData = CSVUtils.readData(
				"data/logs/server.log.unique.time_changed.2014-10-17.csv", true);
		
		List<List<TruckSensorJointEvent>> splits = new GenericDataSplitter<TruckSensorJointEvent>().splitData(allData, 0.6, 0.2, 0.2);
		List<TruckSensorJointEvent> trainData = splits.get(0);

		ICOREForecaster icoreForecaster = new ICOREForecaster();
		System.out.println("Start training the forecaster");
		icoreForecaster.trainForecaster(trainData, System.out);
		
		List<TruckSensorJointEvent> primeData = splits.get(1);
		System.out.println("Add prime data");
//		TemperaturePrediction temperaturePrediction = icoreForecaster.feedWithDataAndForecast(Utils.createHash(trainData));
		icoreForecaster.primeData(primeData);
//		icoreForecaster.primeData(allData);
		
		List<List<NumericPrediction>> forecasts = icoreForecaster.forecastNumericValues(10 * 60 / 15);
		
//		List<TruckSensorJoinedType> testData = splits.get(2);
		for(List<NumericPrediction> list : forecasts)
		{
			System.out.println(list.get(0).toString());
		}
		

//		List<TruckSensorJoinedType> actualData = CSVUtils.readData("data/logs/server.2014_10_20.unique.log.csv", false);
		
//		List<TruckSensorJoinedType> primeData = actualData.subList(0,  5);
		
		TemperaturePredictionEvent prediction = icoreForecaster.feedWithDataAndForecast(Utils.createHash(allData));

	}

	private static void workflowTrainSerialize() throws IOException,
			FileNotFoundException, Exception {

//		List<TruckSensorJoinedType> allData = CSVUtils.readData(
//				"data/2014_09_15_test.csv", true);
		// List<TruckSensorJoinedType> allData = CSVUtils.readData(
		// "data/buffer_2014-08-18.csv", true);
		List<TruckSensorJointEvent> allData = CSVUtils.readData("data/logs/server.log.unique.time_changed.2014-10-17.csv", true);
		
//		List<List<TruckSensorJoinedType>> splits = new GenericDataSplitter<TruckSensorJoinedType>()
//				.splitData(allData, 0.6, 0.2, 0.2);
//		List<TruckSensorJoinedType> trainData = splits.get(0);
//		List<TruckSensorJoinedType> primeData = splits.get(1);
//		List<TruckSensorJoinedType> testData = splits.get(2);

		ICOREForecaster icoreForecaster = new ICOREForecaster();
		
		System.out.println("inner model: " + icoreForecaster.getModelAndOptions().getInnerModel().getClass().toString());
		System.out.println("options: " + icoreForecaster.getModelAndOptions().getOptions().toString());
		
		System.out.println("Start training the forecaster");
		icoreForecaster.trainForecaster(allData, System.out);

		String serializationFilename = "serialized/icoreForecaster.ser";
//
		ICORESerializer.serialize(icoreForecaster, serializationFilename);
		System.out.println("Serialization made in file " + serializationFilename);
//
//		for (TruckSensorJoinedType truckSensorJoinedType : primeData) {
//			icoreForecaster.recordNewMeasurement(truckSensorJoinedType);
//		}
//		
//		System.out.println(testData.get(0).getTimestamp().toString());
//
//		TemperaturePredictionType prediction = icoreForecaster.forecast();
//
//		System.out.println("prediction: in ten minutes: "
//				+ prediction.getPedictedTempTenMin());
//		System.out.println("prediction: in one hour: "
//				+ prediction.getPredictedTempOneHour());
//		System.out.println("prediction: in two hours: "
//				+ prediction.getPredictedTempTwoHours());
//		System.out.println(prediction.getTimestamp().toString());
//		// System.out.println("timestamp for predicted data: " +
//		// prediction.getTimestamp());
	}
	
	// /**
	// * @return
	// * @throws IOException
	// * @throws FileNotFoundException
	// */
	// private static Instances getTSDataSet(String timeAttributeName)
	// throws IOException, FileNotFoundException {
	// Instances dataset = new Instances(new BufferedReader(new FileReader(
	// "data/measurements_TSIM1397134595325.arff")));
	// Attribute timeAttribute = dataset.attribute(timeAttributeName);
	// dataset.sort(timeAttribute);
	// return dataset;
	// }

}
