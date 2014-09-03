/**
 * 
 */
package com.siemens.ct.ro.forecast;

/**
 * Interface for loss functions implementations (RMSE, MSE, etc)
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * version 1.0
 */
public interface IMetric {
	/**
	 * Gets the loss value quantifying the quality of the predictions
	 * @return the loss value
	 */
	public double getMetric();
	
	/**
	 * Gets the name of the current metric
	 * @return the name of the metric function
	 */
	public String getMetricName();
}
