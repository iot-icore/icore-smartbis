package com.siemens.ct.ro.transportation.entities;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TemperatureMeasurement {

	private long id;

	private long headerID;

	private long timestamp;

	private BigDecimal temperature;

	private BigInteger temperature_range;

	private BigInteger temperature_change;

	private Short calibrated;

	private Short range_alarm;

	private Short change_alarm;

	private Short driver_version;

	private Short request_id;

	private String temperature_sensor_id;
	
	private String gateway;

	public TemperatureMeasurement() {
		super();
	}

	public TemperatureMeasurement(long id, long headerID, int timestamp_,
			BigDecimal temperature, BigInteger temperature_range,
			BigInteger temperature_change, Short calibrated, Short range_alarm,
			Short change_alarm, Short driver_version, Short request_id,
			String temperature_sensor_id) {
		super();
		this.id = id;
		this.headerID = headerID;
		this.timestamp = timestamp_;
		this.temperature = temperature;
		this.temperature_range = temperature_range;
		this.temperature_change = temperature_change;
		this.calibrated = calibrated;
		this.range_alarm = range_alarm;
		this.change_alarm = change_alarm;
		this.driver_version = driver_version;
		this.request_id = request_id;
		this.temperature_sensor_id = temperature_sensor_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHeaderID() {
		return headerID;
	}

	public void setHeaderID(long headerID) {
		this.headerID = headerID;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long time) {
		this.timestamp = time;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public BigInteger getTemperature_range() {
		return temperature_range;
	}

	public void setTemperature_range(BigInteger temperature_range) {
		this.temperature_range = temperature_range;
	}

	public BigInteger getTemperature_change() {
		return temperature_change;
	}

	public void setTemperature_change(BigInteger temperature_change) {
		this.temperature_change = temperature_change;
	}

	public Short getCalibrated() {
		return calibrated;
	}

	public void setCalibrated(Short calibrated) {
		this.calibrated = calibrated;
	}

	public Short getRange_alarm() {
		return range_alarm;
	}

	public void setRange_alarm(Short range_alarm) {
		this.range_alarm = range_alarm;
	}

	public Short getChange_alarm() {
		return change_alarm;
	}

	public void setChange_alarm(Short change_alarm) {
		this.change_alarm = change_alarm;
	}

	public Short getDriver_version() {
		return driver_version;
	}

	public void setDriver_version(Short driver_version) {
		this.driver_version = driver_version;
	}

	public Short getRequest_id() {
		return request_id;
	}

	public void setRequest_id(Short request_id) {
		this.request_id = request_id;
	}

	public String getTemperature_sensor_id() {
		return temperature_sensor_id;
	}

	public void setTemperature_sensor_id(String temperature_sensor_id) {
		this.temperature_sensor_id = temperature_sensor_id;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

}
