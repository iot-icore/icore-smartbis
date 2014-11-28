package com.softwareag.transportation.CEPevents;

public class HumidityEvent {

	private long id;
	private Double humidity;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;

	public HumidityEvent() {
		super();
	}

	public HumidityEvent(long id, Double humidity, Long timestamp,
			String sensorID, String gatewayID) {
		super();
		this.id = id;
		this.humidity = humidity;
		this.timestamp = timestamp;
		this.sensorID = sensorID;
		this.gatewayID = gatewayID;
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

}
