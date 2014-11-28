package com.softwareag.transportation.CEPevents;

public class TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent {

	private long id;
	private String gatewayId;
	private Double predictedTempTenMinutes;
	private Long predictedTempTenMinutesTimestamp;
	private Double temperatureValue;
	private Long temperatureEventTimestamp;
	private String temperatureSensorID;
	private String packageID;

	public TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent() {
		super();
	}

	public TemperaturePredictionTenMinutesValidationWithACUnitFilteringEvent(
			long id, String gatewayId, Double predictedTempTenMinutes,
			Long predictedTempTenMinutesTimestamp, Double temperatureValue,
			Long temperatureEventTimestamp, String temperatureSensorID,
			String packageID) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.predictedTempTenMinutes = predictedTempTenMinutes;
		this.predictedTempTenMinutesTimestamp = predictedTempTenMinutesTimestamp;
		this.temperatureValue = temperatureValue;
		this.temperatureEventTimestamp = temperatureEventTimestamp;
		this.temperatureSensorID = temperatureSensorID;
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

	public Double getPredictedTempTenMinutes() {
		return predictedTempTenMinutes;
	}

	public void setPredictedTempTenMinutes(Double predictedTempTenMinutes) {
		this.predictedTempTenMinutes = predictedTempTenMinutes;
	}

	public Long getPredictedTempTenMinutesTimestamp() {
		return predictedTempTenMinutesTimestamp;
	}

	public void setPredictedTempTenMinutesTimestamp(
			Long predictedTempTenMinutesTimestamp) {
		this.predictedTempTenMinutesTimestamp = predictedTempTenMinutesTimestamp;
	}

	public Double getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(Double temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

	public Long getTemperatureEventTimestamp() {
		return temperatureEventTimestamp;
	}

	public void setTemperatureEventTimestamp(Long temperatureEventTimestamp) {
		this.temperatureEventTimestamp = temperatureEventTimestamp;
	}

	public String getTemperatureSensorID() {
		return temperatureSensorID;
	}

	public void setTemperatureSensorID(String temperatureSensorID) {
		this.temperatureSensorID = temperatureSensorID;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

}
