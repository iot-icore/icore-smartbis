/**
 * 
 */
package com.siemens.ct.ro.forecast;

/**
 * Contains an implementation for RMSE metric (http://en.wikipedia.org/wiki/RMSE), with predicted and actual values stored into arrays of double
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class RMSEDouble implements IMetric {

	private double[] predicted;
	private double[] actual;

	/**
	 * Creates an instance with predicted and actual array values
	 * @param predicted values produced by a prediction model
	 * @param actual the actual values
	 */
	public RMSEDouble(double[] predicted, double[] actual)
	{
		this.predicted = predicted;
		this.actual = actual;
	}
	
	/* (non-Javadoc)
	 * @see com.siemens.ct.ro.forecast.IMetric#getMetric()
	 */
	@Override
	public double getMetric() {
		double sum = 0.0;
		for(int i=0; i<predicted.length; i++)
		{
			sum += (predicted[i]  - actual[i]) * (predicted[i]  - actual[i]);
			
		}
		return Math.sqrt(sum/predicted.length);
	}

	/* (non-Javadoc)
	 * @see com.siemens.ct.ro.forecast.IMetric#getMetricName()
	 */
	@Override
	public String getMetricName() {
		return "RMSE";
	}

}
