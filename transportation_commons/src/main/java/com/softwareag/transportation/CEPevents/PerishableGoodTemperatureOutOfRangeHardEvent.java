package com.softwareag.transportation.CEPevents;

public class PerishableGoodTemperatureOutOfRangeHardEvent {

	private long id;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;
	private Double hardMinimumRangeValue;
	private Double hardMaximumRangeValue;
	private String packageID;
	private Double temperature;

	public PerishableGoodTemperatureOutOfRangeHardEvent() {
		super();
	}

	public PerishableGoodTemperatureOutOfRangeHardEvent(long id,
			Long timestamp, String sensorID, String gatewayID,
			Double hardMinimumRangeValue, Double hardMaximumRangeValue,
			String packageID, Double temperature) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.sensorID = sensorID;
		this.gatewayID = gatewayID;
		this.hardMinimumRangeValue = hardMinimumRangeValue;
		this.hardMaximumRangeValue = hardMaximumRangeValue;
		this.packageID = packageID;
		this.temperature = temperature;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public String getGatewayID() {
		return gatewayID;
	}

	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}

	public Double getHardMinimumRangeValue() {
		return hardMinimumRangeValue;
	}

	public void setHardMinimumRangeValue(Double hardMinimumRangeValue) {
		this.hardMinimumRangeValue = hardMinimumRangeValue;
	}

	public Double getHardMaximumRangeValue() {
		return hardMaximumRangeValue;
	}

	public void setHardMaximumRangeValue(Double hardMaximumRangeValue) {
		this.hardMaximumRangeValue = hardMaximumRangeValue;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

}
