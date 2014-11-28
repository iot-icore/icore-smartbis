package com.softwareag.transportation.CEPevents;

public class ACUnitEvent {

	private long id;
	private boolean acUnitState;
	private Integer acUnitTarghetValue;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;

	public ACUnitEvent() {
		super();
	}

	public ACUnitEvent(long id, boolean acUnitState,
			Integer acUnitTarghetValue, Long timestamp, String sensorID,
			String gatewayID) {
		super();
		this.id = id;
		this.acUnitState = acUnitState;
		this.acUnitTarghetValue = acUnitTarghetValue;
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

	public boolean isAcUnitState() {
		return acUnitState;
	}

	public void setAcUnitState(boolean acUnitState) {
		this.acUnitState = acUnitState;
	}

	public Integer getAcUnitTarghetValue() {
		return acUnitTarghetValue;
	}

	public void setAcUnitTarghetValue(Integer acUnitTarghetValue) {
		this.acUnitTarghetValue = acUnitTarghetValue;
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
