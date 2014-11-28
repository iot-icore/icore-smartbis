package com.softwareag.transportation.CEPevents;

public class TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent {

	private long id;
	private String gatewayId;
	private Double predictedTempTwoHours;
	private Long predictedTempTwoHourTimestamp;
	private Double temperatureValue;
	private Long temperatureEventTimestamp;
	private String temperatureSensorID;
	private String packageID;

	public TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent() {
		super();
	}

	public TemperaturePredictionTwoHoursValidationWithACUnitFilteringEvent(
			long id, String gatewayId, Double predictedTempTwoHours,
			Long predictedTempTwoHourTimestamp, Double temperatureValue,
			Long temperatureEventTimestamp, String temperatureSensorID,
			String packageID) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.predictedTempTwoHours = predictedTempTwoHours;
		this.predictedTempTwoHourTimestamp = predictedTempTwoHourTimestamp;
		this.temperatureValue = temperatureValue;
		this.temperatureEventTimestamp = temperatureEventTimestamp;
		this.temperatureSensorID = temperatureSensorID;
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

	public Double getPredictedTempTwoHours() {
		return predictedTempTwoHours;
	}

	public void setPredictedTempTwoHours(Double predictedTempTwoHours) {
		this.predictedTempTwoHours = predictedTempTwoHours;
	}

	public Long getPredictedTempTwoHourTimestamp() {
		return predictedTempTwoHourTimestamp;
	}

	public void setPredictedTempTwoHourTimestamp(
			Long predictedTempTwoHourTimestamp) {
		this.predictedTempTwoHourTimestamp = predictedTempTwoHourTimestamp;
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

}
