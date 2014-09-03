/**
 * 
 */
package com.siemens.ct.ro.forecast;

import java.util.List;

import weka.classifiers.evaluation.NumericPrediction;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Root mean squared error implementation
 * see http://en.wikipedia.org/wiki/RMSE
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class RMSE implements IMetric {

	private Instances testSubset;
	private List<List<NumericPrediction>> estimatedValues;
	private String fieldNameToBePredicted;

	/**
	 * Instantiates an object with estimated and actual values
	 * @param estimatedValues the values produced by a model
	 * @param testSubset the actual values
	 * @param fieldNameToBePredicted the name of the column in the testSubset collection holding actual values
	 */
	public RMSE(List<List<NumericPrediction>> estimatedValues,
			Instances testSubset, String fieldNameToBePredicted) {
		this.testSubset = testSubset;
		this.estimatedValues = estimatedValues;
		this.fieldNameToBePredicted = fieldNameToBePredicted;
	}

	/* (non-Javadoc)
	 * @see com.siemens.ct.ro.forecast.IMetric#getMetric()
	 */
	public double getMetric() {
		Attribute attribute = testSubset.attribute(fieldNameToBePredicted);
		int index = attribute.index();
		double sum = 0.0;
		
		for(int i=0; i<testSubset.size(); i++)
		{
			NumericPrediction value = estimatedValues.get(i).get(0);//a single value is predicted
			double difference = testSubset.get(i).value(index) - value.predicted();
			sum += difference * difference;
			
		}
		return Math.sqrt(sum/testSubset.size());
	}

	/* (non-Javadoc)
	 * @see com.siemens.ct.ro.forecast.IMetric#getMetricName()
	 */
	public String getMetricName() {
		return "RMSE";
	}

}
