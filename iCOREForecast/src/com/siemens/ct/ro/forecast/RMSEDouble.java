/**
 * 
 */
package com.siemens.ct.ro.forecast;

/**
 * @author Lucian Sasu
 *
 */
public class RMSEDouble implements IMetric {

	private double[] predicted;
	private double[] actual;

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
