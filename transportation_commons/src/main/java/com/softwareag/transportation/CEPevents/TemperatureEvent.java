package com.softwareag.transportation.CEPevents;

public class TemperatureEvent {

	private long id;
	private Double temperature;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;
	
	

	public TemperatureEvent() {
		super();
	}

	public TemperatureEvent(long id, Double temperature, Long timestamp,
			String sensorID, String gatewayID) {
		super();
		this.id = id;
		this.temperature = temperature;
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

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
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
