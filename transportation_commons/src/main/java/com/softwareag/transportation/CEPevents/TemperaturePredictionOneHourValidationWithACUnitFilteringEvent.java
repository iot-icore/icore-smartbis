package com.softwareag.transportation.CEPevents;

public class TemperaturePredictionOneHourValidationWithACUnitFilteringEvent {

	private long id;
	private String gatewayId;
	private Double predictedTempOneHour;
	private Long predictedTempOneHourTimestamp;
	private Double temperatureValue;
	private Long temperatureEventTimestamp;
	private String temperatureSensorID;
	private String packageID;

	public TemperaturePredictionOneHourValidationWithACUnitFilteringEvent() {
		super();
	}

	public TemperaturePredictionOneHourValidationWithACUnitFilteringEvent(
			long id, String gatewayId, Double predictedTempOneHour,
			Long predictedTempOneHourTimestamp, Double temperatureValue,
			Long temperatureEventTimestamp, String temperatureSensorID,
			String packageID) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.predictedTempOneHour = predictedTempOneHour;
		this.predictedTempOneHourTimestamp = predictedTempOneHourTimestamp;
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

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Double getPredictedTempOneHour() {
		return predictedTempOneHour;
	}

	public void setPredictedTempOneHour(Double predictedTempOneHour) {
		this.predictedTempOneHour = predictedTempOneHour;
	}

	public Long getPredictedTempOneHourTimestamp() {
		return predictedTempOneHourTimestamp;
	}

	public void setPredictedTempOneHourTimestamp(
			Long predictedTempOneHourTimestamp) {
		this.predictedTempOneHourTimestamp = predictedTempOneHourTimestamp;
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
