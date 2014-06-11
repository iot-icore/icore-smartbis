package com.siemens.ct.ro.transportation.entities;

import com.siemens.ct.ro.transportation.commons.Constants.JobType;

/**
 * ProductType entity.
 * 
 * @author Anca Petrescu
 * 
 */
public class ProductType {
	private long id;
	private String typeName;
	private JobType jobType;
	private double hardMinTemperature;
	private double hardMaxTemperature;
	private double hardMinHumidity;
	private double hardMaxHumidity;
	private double softMinTemperature;
	private double softMaxTemperature;
	private double maximumTimeOutOfTemperature;
	private double maximumTimeOutOfHumidity;

	public ProductType() {
	}

	public ProductType(String typeName, JobType jobType,
			double hardMinTemperature, double hardMaxTemperature,
			double hardMinHumidity, double hardMaxHumidity,
			double softMinTemperature, double softMaxTemperature,
			double maximumTimeOutOfTemperature, double maximumTimeOutOfHumidity) {
		super();
		this.typeName = typeName;
		this.jobType = jobType;
		this.hardMinTemperature = hardMinTemperature;
		this.hardMaxTemperature = hardMaxTemperature;
		this.hardMinHumidity = hardMinHumidity;
		this.hardMaxHumidity = hardMaxHumidity;
		this.softMinTemperature = softMinTemperature;
		this.softMaxTemperature = softMaxTemperature;
		this.maximumTimeOutOfTemperature = maximumTimeOutOfTemperature;
		this.maximumTimeOutOfHumidity = maximumTimeOutOfHumidity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public double getHardMinTemperature() {
		return hardMinTemperature;
	}

	public void setHardMinTemperature(double hardMinTemperature) {
		this.hardMinTemperature = hardMinTemperature;
	}

	public double getHardMaxTemperature() {
		return hardMaxTemperature;
	}

	public void setHardMaxTemperature(double hardMaxTemperature) {
		this.hardMaxTemperature = hardMaxTemperature;
	}

	public double getHardMinHumidity() {
		return hardMinHumidity;
	}

	public void setHardMinHumidity(double hardMinHumidity) {
		this.hardMinHumidity = hardMinHumidity;
	}

	public double getHardMaxHumidity() {
		return hardMaxHumidity;
	}

	public void setHardMaxHumidity(double hardMaxHumidity) {
		this.hardMaxHumidity = hardMaxHumidity;
	}

	public double getSoftMinTemperature() {
		return softMinTemperature;
	}

	public void setSoftMinTemperature(double softMinTemperature) {
		this.softMinTemperature = softMinTemperature;
	}

	public double getSoftMaxTemperature() {
		return softMaxTemperature;
	}

	public void setSoftMaxTemperature(double softMaxTemperature) {
		this.softMaxTemperature = softMaxTemperature;
	}

	public double getMaximumTimeOutOfTemperature() {
		return maximumTimeOutOfTemperature;
	}

	public void setMaximumTimeOutOfTemperature(
			double maximumTimeOutOfTemperature) {
		this.maximumTimeOutOfTemperature = maximumTimeOutOfTemperature;
	}

	public double getMaximumTimeOutOfHumidity() {
		return maximumTimeOutOfHumidity;
	}

	public void setMaximumTimeOutOfHumidity(double maximumTimeOutOfHumidity) {
		this.maximumTimeOutOfHumidity = maximumTimeOutOfHumidity;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

}
