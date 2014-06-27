package com.siemens.ct.ro.transportation.entities;

public class TemperaturePrediction {
	private long id;
	private String gatewayId;
	private String temperatureSensorIdContainerTemp;
	private String pedictedTempTenMin;
	private String predictedTempOneHour;
	private String predictedTempTwoHours;
	private long timestamp;

	public TemperaturePrediction() {

	}

	public TemperaturePrediction(long id, String gatewayId,
			String temperatureSensorIdContainerTemp, String pedictedTempTenMin,
			String predictedTempOneHour, String predictedTempTwoHours,
			long timestamp) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.temperatureSensorIdContainerTemp = temperatureSensorIdContainerTemp;
		this.pedictedTempTenMin = pedictedTempTenMin;
		this.predictedTempOneHour = predictedTempOneHour;
		this.predictedTempTwoHours = predictedTempTwoHours;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getTemperatureSensorIdContainerTemp() {
		return temperatureSensorIdContainerTemp;
	}

	public void setTemperatureSensorIdContainerTemp(
			String temperatureSensorIdContainerTemp) {
		this.temperatureSensorIdContainerTemp = temperatureSensorIdContainerTemp;
	}

	public String getPedictedTempTenMin() {
		return pedictedTempTenMin;
	}

	public void setPedictedTempTenMin(String pedictedTempTenMin) {
		this.pedictedTempTenMin = pedictedTempTenMin;
	}

	public String getPredictedTempOneHour() {
		return predictedTempOneHour;
	}

	public void setPredictedTempOneHour(String predictedTempOneHour) {
		this.predictedTempOneHour = predictedTempOneHour;
	}

	public String getPredictedTempTwoHours() {
		return predictedTempTwoHours;
	}

	public void setPredictedTempTwoHours(String predictedTempTwoHours) {
		this.predictedTempTwoHours = predictedTempTwoHours;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
