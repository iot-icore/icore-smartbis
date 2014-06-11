package com.siemens.ct.ro.forecastUtils;

/**
 * 
 * @author Lucian Sasu
 * The measurement data provided by the sensors
 */

public class MeasurementData {
	private double temperatureInfrastructureSensor1;
	private double temperatureInfrastructureSensor2;
	private double temperatureInfrastructureSensor3;
	private double humidityInfrastructureSensor;
	private boolean hvacStateOn; 
	private double hvacStateValue;
	private double currentClampValue;
	private double externalTemperature;
	private double temperatureParcel;//to be forecasted
	private long time;
	/**
	 * @return the temperatureInfrastructureSensor1
	 */
	public double getTemperatureInfrastructureSensor1() {
		return temperatureInfrastructureSensor1;
	}
	/**
	 * @param temperatureInfrastructureSensor1 the temperatureInfrastructureSensor1 to set
	 */
	public void setTemperatureInfrastructureSensor1(
			double temperatureInfrastructureSensor1) {
		this.temperatureInfrastructureSensor1 = temperatureInfrastructureSensor1;
	}
	/**
	 * @return the temperatureInfrastructureSensor2
	 */
	public double getTemperatureInfrastructureSensor2() {
		return temperatureInfrastructureSensor2;
	}
	/**
	 * @param temperatureInfrastructureSensor2 the temperatureInfrastructureSensor2 to set
	 */
	public void setTemperatureInfrastructureSensor2(
			double temperatureInfrastructureSensor2) {
		this.temperatureInfrastructureSensor2 = temperatureInfrastructureSensor2;
	}
	/**
	 * @return the temperatureInfrastructureSensor3
	 */
	public double getTemperatureInfrastructureSensor3() {
		return temperatureInfrastructureSensor3;
	}
	/**
	 * @param temperatureInfrastructureSensor3 the temperatureInfrastructureSensor3 to set
	 */
	public void setTemperatureInfrastructureSensor3(
			double temperatureInfrastructureSensor3) {
		this.temperatureInfrastructureSensor3 = temperatureInfrastructureSensor3;
	}
	/**
	 * @return the humidityInfrastructureSensor
	 */
	public double getHumidityInfrastructureSensor() {
		return humidityInfrastructureSensor;
	}
	/**
	 * @param humidityInfrastructureSensor the humidityInfrastructureSensor to set
	 */
	public void setHumidityInfrastructureSensor(double humidityInfrastructureSensor) {
		this.humidityInfrastructureSensor = humidityInfrastructureSensor;
	}
	/**
	 * @return the hvacStateOn
	 */
	public boolean isHvacStateOn() {
		return hvacStateOn;
	}
	/**
	 * @param aCStateOn the aCStateOn to set
	 */
	public void setHvacStateOn(boolean hvacStateOn) {
		this.hvacStateOn = hvacStateOn;
	}
	/**
	 * @return the hvacStateValue
	 */
	public double getHvacStateValue() {
		return hvacStateValue;
	}
	/**
	 * @param hvacStateValue the hvacStateValue to set
	 */
	public void setHvacStateValue(double hvacStateValue) {
		this.hvacStateValue = hvacStateValue;
	}
	/**
	 * @return the currentClampValue
	 */
	public double getCurrentClampValue() {
		return currentClampValue;
	}
	/**
	 * @param currentClampValue the currentClampValue to set
	 */
	public void setCurrentClampValue(double currentClampValue) {
		this.currentClampValue = currentClampValue;
	}
	/**
	 * @return the externalTemperature
	 */
	public double getExternalTemperature() {
		return externalTemperature;
	}
	/**
	 * @param externalTemperature the externalTemperature to set
	 */
	public void setExternalTemperature(double externalTemperature) {
		this.externalTemperature = externalTemperature;
	}
	/**
	 * @return the temperatureParcel
	 */
	public double getTemperatureParcel() {
		return temperatureParcel;
	}
	/**
	 * @param temperatureParcel the temperatureParcel to set
	 */
	public void setTemperatureParcel(double temperatureParcel) {
		this.temperatureParcel = temperatureParcel;
	}
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
}
