package com.siemens.ct.ro.quickdemo;

import java.util.List;

import com.siemens.ct.ro.forecastUtils.CSVUtils;
import com.siemens.ct.ro.forecastUtils.GenericDataSplitter;
import com.siemens.ct.ro.forecastUtils.WekaHelper;
import com.siemens.ct.ro.transportation.dataformatfromcep.TruckSensorJoinedType;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;

public class QuickCode {
	public static void main(String[] args) throws Exception {
		
		WekaForecaster wekaForecaster = new WekaForecaster();
		AbstractClassifier model = new LinearRegression();
		String options = "-S 0 -R 1.0E-8";
		model.setOptions(weka.core.Utils.splitOptions(options));

		wekaForecaster.setBaseForecaster(model);
		wekaForecaster.setFieldsToForecast("temperatureParcel");
		wekaForecaster.getTSLagMaker().setTimeStampField("time");
		wekaForecaster.getTSLagMaker().setAdjustForVariance(true);
		wekaForecaster.getTSLagMaker().setMinLag(1);
		wekaForecaster.getTSLagMaker().setMaxLag(10);

		List<TruckSensorJoinedType> allData = CSVUtils.readData(
				"data/data2_2.csv", true);
		List<List<TruckSensorJoinedType>> splits = new GenericDataSplitter<TruckSensorJoinedType>()
				.splitData(allData, 0.6, 0.2, 0.2);
		List<TruckSensorJoinedType> trainData = splits.get(0);
		List<TruckSensorJoinedType> primeData = splits.get(1);
		
		Instances trainInstances = WekaHelper.createInstances(trainData);
		
		System.out.println(trainInstances);

		wekaForecaster.buildForecaster(trainInstances, System.out);
		
		System.out.println(wekaForecaster);
		
		System.out.println("main done");
		
	}
}
