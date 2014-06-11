package com.siemens.ct.ro.forecast;

import java.io.Serializable;

import weka.classifiers.AbstractClassifier;
import weka.core.Utils;

/**
 * @author Lucian Sasu
 * Container for prediction model and corresponding options for the forecasting model. 
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
		super();
		this.innerModel = innerModel;
		this.options = options;
		this.innerModel.setOptions(Utils.splitOptions(options));
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
