package com.siemens.ct.ro.quickCode;

import java.util.List;

import com.siemens.ct.ro.forecastUtils.CSVUtils;
import com.siemens.ct.ro.forecastUtils.Constants;
import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import weka.core.Utils;

public class WekaForecasterDemo {
	public static void main(String[] args) {
	    try {
	        // path to the Australian wine data included with the time series forecasting
	        // package
//	        String pathToWineData = "C:\\Users\\ro1v0393\\Documents\\_timeseries_data\\wine.arff";
	        
	        List<TruckSensorJointEvent> allData = CSVUtils.readData("data/logs/server.log.unique.time_changed.2014-10-17.csv", true);
	        Instances trainData = com.siemens.ct.ro.forecastUtils.Utils.createInstances(allData);

	        // new forecaster
	        WekaForecaster forecaster = new WekaForecaster();

	        // set the targets we want to forecast. This method calls
	        // setFieldsToLag() on the lag maker object for us
	        forecaster.setFieldsToForecast(Constants.PARCEL_TEMPERATURE);

	        // default underlying classifier is SMOreg (SVM) - we'll use
	        // gaussian processes for regression instead
//	        forecaster.setBaseForecaster(new GaussianProcesses());
	        
	        AbstractClassifier model = new MultilayerPerceptron();
	        String options = "-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 5";
	        model.setOptions(Utils.splitOptions(options));
	        forecaster.setBaseForecaster(model);
	        
//	        AbstractClassifier model = new LinearRegression();
//	        String options = "-S 0 -R 1.0E-8";
//	        model.setOptions(Utils.splitOptions(options));
//	        forecaster.setBaseForecaster(model);
	        
	        forecaster.getTSLagMaker().setTimeStampField(Constants.TIME_FIELDNAME); // date time stamp
	        forecaster.getTSLagMaker().setMinLag(1);
	        forecaster.getTSLagMaker().setMaxLag(12); // monthly data

	        // add a month of the year indicator field
//	        forecaster.getTSLagMaker().setAddMonthOfYear(true);
	        
	        // add a quarter of the year indicator field
//	        forecaster.getTSLagMaker().setAddQuarterOfYear(true);

	        // build the model
	        forecaster.buildForecaster(trainData, System.out);

	        // prime the forecaster with enough recent historical data
	        // to cover up to the maximum lag. In our case, we could just supply
	        // the 12 most recent historical instances, as this covers our maximum
	        // lag period
//	        forecaster.primeForecaster(trainData);
	        
	        List<TruckSensorJointEvent> primeData1 = CSVUtils.readData(
					"data/logs/prime1.csv", false);
	        allData.addAll(primeData1);
//	        forecaster.primeForecaster(com.siemens.ct.ro.forecastUtils.Utils.createInstances(allData));
	        
//	        List<TruckSensorJoinedType> primeData2 = CSVUtils.readData("data/logs/prime2.csv", false);
//	        forecaster.primeForecaster(com.siemens.ct.ro.forecastUtils.Utils.createInstances(primeData2));

	        // forecast for 12 units (months) beyond the end of the
	        // training data
	        List<List<NumericPrediction>> forecast = forecaster.forecast(2*60*60/15, System.out);

	        // output the predictions. Outer list is over the steps; inner list is over
	        // the targets
	        for (int i = 0; i < forecast.size(); i++) {
	          List<NumericPrediction> predsAtStep = forecast.get(i);
//	          for (int j = 0; j < 2; j++) {
//	            NumericPrediction predForTarget = predsAtStep.get(j);
//	            System.out.print("" + predForTarget.predicted() + " ");
//	          }
	          System.out.println(predsAtStep.get(0));
//	          System.out.println();
	        }
	        
	        // we can continue to use the trained forecaster for further forecasting
	        // by priming with the most recent historical data (as it becomes available).
	        // At some stage it becomes prudent to re-build the model using current
	        // historical data.
	        
//	        System.out.println(forecaster.toString());

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	    }
}
