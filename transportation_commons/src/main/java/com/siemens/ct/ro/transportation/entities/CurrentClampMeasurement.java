package com.siemens.ct.ro.transportation.entities;

import java.math.BigDecimal;

public class CurrentClampMeasurement {

	private long id;

	private long headerID;

	private long timestamp;

	private BigDecimal currentclamp;

	private Short currentclamp_range;

	private Short currentclamp_change;

	private Short calibrated;

	private Short range_alarm;

	private Short change_alarm;

	private Short driver_version;

	private Short request_id;

	private String currentclamp_sensor_id;

	public CurrentClampMeasurement() {
		super();
	}

	public CurrentClampMeasurement(long id, long headerID, long timestamp,
			BigDecimal currentClamp, Short currentClamp_range, Short currentClamp_change,
			Short calibrated, Short range_alarm, Short change_alarm,
			Short driver_version, Short request_id, String currentClamp_sensor_id) {
		super();
		this.id = id;
		this.headerID = headerID;
		this.timestamp = timestamp;
		this.currentclamp = currentClamp;
		this.currentclamp_range = currentClamp_range;
		this.currentclamp_change = currentClamp_change;
		this.calibrated = calibrated;
		this.range_alarm = range_alarm;
		this.change_alarm = change_alarm;
		this.driver_version = driver_version;
		this.request_id = request_id;
		this.currentclamp_sensor_id = currentClamp_sensor_id;
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

	public BigDecimal getCurrentClamp() {
		return currentclamp;
	}

	public void setCurrentClamp(BigDecimal currentClamp) {
		this.currentclamp = currentClamp;
	}

	public Short getCurrentClamp_range() {
		return currentclamp_range;
	}

	public void setCurrentClamp_range(Short currentClamp_range) {
		this.currentclamp_range = currentClamp_range;
	}

	public Short getCurrentClamp_change() {
		return currentclamp_change;
	}

	public void setCurrentClamp_change(Short currentClamp_change) {
		this.currentclamp_change = currentClamp_change;
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

	public String getCurrentClamp_sensor_id() {
		return currentclamp_sensor_id;
	}

	public void setCurrentClamp_sensor_id(String currentClamp_sensor_id) {
		this.currentclamp_sensor_id = currentClamp_sensor_id;
	}

}
