package com.siemens.ct.ro.forecast;

import java.io.Serializable;

import weka.classifiers.AbstractClassifier;
import weka.core.Utils;

/**
 * Container for prediction model and its associated option, internally used by the forecasting model. 
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class ModelAndOptions implements Serializable {

	private static final long serialVersionUID = 2999481471264147857L;
	private final AbstractClassifier innerModel;
	private final String options;
	
	/**
	 * @param innerModel the model to be used for regression
	 * @param options String tokens separated by spaces
	 * @throws Exception if a given option is not supported
	 */
	public ModelAndOptions(AbstractClassifier innerModel, String options) throws Exception {
		this.innerModel = innerModel;
		this.options = options;
		this.innerModel.setOptions(Utils.splitOptions(getOptions()));
	}
	
	/**
	 * @return the innerModel
	 */
	public AbstractClassifier getInnerModel() {
		return innerModel;
	}
	
	/**
	 * @return the options
	 */
	public String getOptions() {
		return options;
	}
	
}
