package com.softwareag.transportation.CEPevents;

public class TemperaturePredictionEvent {
	private long id;
	private String gatewayId;
	private String temperatureSensorID;
	private Double pedictedTempTenMin;
	private Double predictedTempOneHour;
	private Double predictedTempTwoHours;
	private long timestamp;
	String packageID;

	public TemperaturePredictionEvent() {

	}

	public TemperaturePredictionEvent(long id, String packageID, String gatewayId,
			String temperatureSensorID, Double pedictedTempTenMin,
			Double predictedTempOneHour, Double predictedTempTwoHours,
			long timestamp) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.temperatureSensorID = temperatureSensorID;
		this.pedictedTempTenMin = pedictedTempTenMin;
		this.predictedTempOneHour = predictedTempOneHour;
		this.predictedTempTwoHours = predictedTempTwoHours;
		this.timestamp = timestamp;
		this.packageID = packageID;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
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

	public String getTemperatureSensorID() {
		return temperatureSensorID;
	}

	public void setTemperatureSensorID(String temperatureSensorID) {
		this.temperatureSensorID = temperatureSensorID;
	}

	public Double getPedictedTempTenMin() {
		return pedictedTempTenMin;
	}

	public void setPedictedTempTenMin(Double pedictedTempTenMin) {
		this.pedictedTempTenMin = pedictedTempTenMin;
	}

	public Double getPredictedTempOneHour() {
		return predictedTempOneHour;
	}

	public void setPredictedTempOneHour(Double predictedTempOneHour) {
		this.predictedTempOneHour = predictedTempOneHour;
	}

	public Double getPredictedTempTwoHours() {
		return predictedTempTwoHours;
	}

	public void setPredictedTempTwoHours(Double predictedTempTwoHours) {
		this.predictedTempTwoHours = predictedTempTwoHours;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
