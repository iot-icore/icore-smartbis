package com.siemens.ct.ro.transportation.entities;

import java.math.BigDecimal;

public class ACUnitMeasurement {

	private long id;

	private long headerID;

	private long timestamp;

	private BigDecimal acunit_target;

	private Boolean acunit_active;

	private Short acunit_range;

	private Short acunit_change;

	private Short calibrated;

	private Short range_alarm;

	private Short change_alarm;

	private Short driver_version;

	private Short request_id;

	private String acunit_id;

	public ACUnitMeasurement() {
		super();

	}

	public ACUnitMeasurement(long id, long headerID, long timestamp,
			BigDecimal acunit_target, Boolean acunit_active,
			Short acunit_range, Short acunit_change, Short calibrated,
			Short range_alarm, Short change_alarm, Short driver_version,
			Short request_id, String acunit_id) {
		super();
		this.id = id;
		this.headerID = headerID;
		this.timestamp = timestamp;
		this.acunit_target = acunit_target;
		this.acunit_active = acunit_active;
		this.acunit_range = acunit_range;
		this.acunit_change = acunit_change;
		this.calibrated = calibrated;
		this.range_alarm = range_alarm;
		this.change_alarm = change_alarm;
		this.driver_version = driver_version;
		this.request_id = request_id;
		this.acunit_id = acunit_id;
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

	public BigDecimal getAcunit_target() {
		return acunit_target;
	}

	public void setAcunit_target(BigDecimal acunit_target) {
		this.acunit_target = acunit_target;
	}

	public Boolean getAcunit_active() {
		return acunit_active;
	}

	public void setAcunit_active(Boolean acunit_active) {
		this.acunit_active = acunit_active;
	}

	public Short getAcunit_range() {
		return acunit_range;
	}

	public void setAcunit_range(Short acunit_range) {
		this.acunit_range = acunit_range;
	}

	public Short getAcunit_change() {
		return acunit_change;
	}

	public void setAcunit_change(Short acunit_change) {
		this.acunit_change = acunit_change;
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

	public String getAcunit_id() {
		return acunit_id;
	}

	public void setAcunit_id(String acunit_id) {
		this.acunit_id = acunit_id;
	}

}
