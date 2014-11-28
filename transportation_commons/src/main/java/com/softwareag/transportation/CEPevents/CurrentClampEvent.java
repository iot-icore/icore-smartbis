package com.softwareag.transportation.CEPevents;

public class CurrentClampEvent {

	private long id;
	private Double currentClampValue;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;

	public CurrentClampEvent() {
		super();
	}

	public CurrentClampEvent(long id, Double currentClampValue, Long timestamp,
			String sensorID, String gatewayID) {
		super();
		this.id = id;
		this.currentClampValue = currentClampValue;
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

	public Double getCurrentClampValue() {
		return currentClampValue;
	}

	public void setCurrentClampValue(Double currentClampValue) {
		this.currentClampValue = currentClampValue;
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
