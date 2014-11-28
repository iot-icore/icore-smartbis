package com.softwareag.transportation.CEPevents;

public class TimeSeriesTemperaturePredictionEvent {

	private String temperatureSensorId;
	private String gatewayID;
	private Double predictedTempOneHour;
	private Double predictedTempTenMin;
	private Double predictedTempTwoHours;
	private Long timstamp;

	public String getTemperatureSensorId() {
		return temperatureSensorId;
	}

	public void setTemperatureSensorId(String temperatureSensorId) {
		this.temperatureSensorId = temperatureSensorId;
	}

	public String getGatewayID() {
		return gatewayID;
	}

	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}

	public Double getPredictedTempOneHour() {
		return predictedTempOneHour;
	}

	public void setPredictedTempOneHour(Double predictedTempOneHour) {
		this.predictedTempOneHour = predictedTempOneHour;
	}

	public Double getPredictedTempTenMin() {
		return predictedTempTenMin;
	}

	public void setPredictedTempTenMin(Double predictedTempTenMin) {
		this.predictedTempTenMin = predictedTempTenMin;
	}

	public Double getPredictedTempTwoHours() {
		return predictedTempTwoHours;
	}

	public void setPredictedTempTwoHours(Double predictedTempTwoHours) {
		this.predictedTempTwoHours = predictedTempTwoHours;
	}

	public Long getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(Long timstamp) {
		this.timstamp = timstamp;
	}

}
