package com.softwareag.transportation.CEPevents;

public class HumidityOutOfRangeEvent {

	private long id;
	private Double humidity;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;
	private Double minimumRangeValue;
	private Double maximumRangeValue;
	private String packageID;

	public HumidityOutOfRangeEvent() {
		super();
	}

	public HumidityOutOfRangeEvent(long id, Double humidity, Long timestamp,
			String sensorID, String gatewayID, Double minimumRangeValue,
			Double maximumRangeValue, String packageID) {
		super();
		this.id = id;
		this.humidity = humidity;
		this.timestamp = timestamp;
		this.sensorID = sensorID;
		this.gatewayID = gatewayID;
		this.minimumRangeValue = minimumRangeValue;
		this.maximumRangeValue = maximumRangeValue;
		this.packageID = packageID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
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

	public Double getMinimumRangeValue() {
		return minimumRangeValue;
	}

	public void setMinimumRangeValue(Double minimumRangeValue) {
		this.minimumRangeValue = minimumRangeValue;
	}

	public Double getMaximumRangeValue() {
		return maximumRangeValue;
	}

	public void setMaximumRangeValue(Double maximumRangeValue) {
		this.maximumRangeValue = maximumRangeValue;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

}
