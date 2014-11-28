package com.siemens.transportationcvomng.EsperCEPProcessing.UDF;

import com.espertech.esper.client.hook.AggregationFunctionFactory;
import com.espertech.esper.epl.agg.aggregator.AggregationMethod;
import com.espertech.esper.epl.agg.service.AggregationValidationContext;
import com.softwareag.transportation.CEPevents.TimeSeriesPredictionSample;

public class TimeSeriesPredictionSampleAtributeGroupFactory implements
		AggregationFunctionFactory {

	@Override
	public Class getValueType() {
		return TimeSeriesPredictionSample.class;

	}

	@Override
	public AggregationMethod newAggregator() {
		return new TimeSeriesPredictionSampleAtributeGroupFunction();
	}

	@Override
	public void setFunctionName(String arg0) {

	}

	@Override
	public void validate(AggregationValidationContext arg0) {

		if (arg0.getParameterTypes().length != 1) {
			throw new IllegalArgumentException(
					"TTimeSeriesPredictionSampleAtributeGroupFunction requires only one parameter.");
		}

	}

}
