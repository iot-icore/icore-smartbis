package com.softwareag.transportation.CEPevents;

public class PerishableGoodTemperatureOutOfRangeSoftEvent {

	private long id;
	private Long timestamp;
	private String sensorID;
	private String gatewayID;
	private Double softMinimumRangeValue;
	private Double softMaximumRangeValue;
	private String packageID;
	private Double softTemperatureMonitoringPeriod;

	public PerishableGoodTemperatureOutOfRangeSoftEvent() {
		super();
	}

	public PerishableGoodTemperatureOutOfRangeSoftEvent(long id,
			Long timestamp, String sensorID, String gatewayID,
			Double softMinimumRangeValue, Double softMaximumRangeValue,
			String packageID, Double softTemperatureMonitoringPeriod) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.sensorID = sensorID;
		this.gatewayID = gatewayID;
		this.softMinimumRangeValue = softMinimumRangeValue;
		this.softMaximumRangeValue = softMaximumRangeValue;
		this.packageID = packageID;
		this.softTemperatureMonitoringPeriod = softTemperatureMonitoringPeriod;
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

	public Double getSoftMinimumRangeValue() {
		return softMinimumRangeValue;
	}

	public void setSoftMinimumRangeValue(Double softMinimumRangeValue) {
		this.softMinimumRangeValue = softMinimumRangeValue;
	}

	public Double getSoftMaximumRangeValue() {
		return softMaximumRangeValue;
	}

	public void setSoftMaximumRangeValue(Double softMaximumRangeValue) {
		this.softMaximumRangeValue = softMaximumRangeValue;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	public Double getSoftTemperatureMonitoringPeriod() {
		return softTemperatureMonitoringPeriod;
	}

	public void setSoftTemperatureMonitoringPeriod(
			Double softTemperatureMonitoringPeriod) {
		this.softTemperatureMonitoringPeriod = softTemperatureMonitoringPeriod;
	}

}
