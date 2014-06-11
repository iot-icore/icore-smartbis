package com.siemens.ct.ro.transportation.entities;

import java.math.BigDecimal;

public class HumidityMeasurement {

	private long id;

	private long headerID;

	private long timestamp;

	private BigDecimal humidity;

	private Short humidity_range;

	private Short humidity_change;

	private Short calibrated;

	private Short range_alarm;

	private Short change_alarm;

	private Short driver_version;

	private Short request_id;

	private String humidity_sensor_id;

	public HumidityMeasurement() {
		super();
	}

	public HumidityMeasurement(long id, long headerID, long timestamp,
			BigDecimal humidity, Short humidity_range, Short humidity_change,
			Short calibrated, Short range_alarm, Short change_alarm,
			Short driver_version, Short request_id, String humidity_sensor_id) {
		super();
		this.id = id;
		this.headerID = headerID;
		this.timestamp = timestamp;
		this.humidity = humidity;
		this.humidity_range = humidity_range;
		this.humidity_change = humidity_change;
		this.calibrated = calibrated;
		this.range_alarm = range_alarm;
		this.change_alarm = change_alarm;
		this.driver_version = driver_version;
		this.request_id = request_id;
		this.humidity_sensor_id = humidity_sensor_id;
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

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getHumidity() {
		return humidity;
	}

	public void setHumidity(BigDecimal humidity) {
		this.humidity = humidity;
	}

	public Short getHumidity_range() {
		return humidity_range;
	}

	public void setHumidity_range(Short humidity_range) {
		this.humidity_range = humidity_range;
	}

	public Short getHumidity_change() {
		return humidity_change;
	}

	public void setHumidity_change(Short humidity_change) {
		this.humidity_change = humidity_change;
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

	public String getHumidity_sensor_id() {
		return humidity_sensor_id;
	}

	public void setHumidity_sensor_id(String humidity_sensor_id) {
		this.humidity_sensor_id = humidity_sensor_id;
	}

}
