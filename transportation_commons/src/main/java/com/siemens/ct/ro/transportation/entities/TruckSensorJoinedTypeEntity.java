package com.siemens.ct.ro.transportation.entities;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Entity used to store TruckSensorJoinedType.
 * 
 * @author Anca Petrescu
 * 
 */
public class TruckSensorJoinedTypeEntity {

	protected long id;
	protected String gatewayId;
	protected double containerTemperature1;
	protected String containerTemperatureSensorId1;
	protected double containerTemperature2;
	protected String containerTemperatureSensorId2;
	protected double containerTemperature3;
	protected String containerTemperatureSensorId3;
	protected double humidityValue;
	protected double humidityTemperature;
	protected String humiditySensorId;
	protected boolean hvacStateOn;
	protected double hvacStateValue;
	protected String hvacActuatorId;
	protected double currentClampValue;
	protected String currentClampSensorId;
	protected double externalTemperature;
	protected String externalTemperatureId;
	protected double parcelTemperature;
	protected String parcelTemperatureSensorId;
	protected Date timestamp;

	public TruckSensorJoinedTypeEntity() {
		super();
	}

	public TruckSensorJoinedTypeEntity(long id, String gatewayId,
			double containerTemperature1, String containerTemperatureSensorId1,
			double containerTemperature2, String containerTemperatureSensorId2,
			double containerTemperature3, String containerTemperatureSensorId3,
			double humidityValue, double humidityTemperature,
			String humiditySensorId, boolean hvacStateOn,
			double hvacStateValue, String hvacActuatorId,
			double currentClampValue, String currentClampSensorId,
			double externalTemperature, String externalTemperatureId,
			double parcelTemperature, String parcelTemperatureSensorId,
			Date timestamp) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.containerTemperature1 = containerTemperature1;
		this.containerTemperatureSensorId1 = containerTemperatureSensorId1;
		this.containerTemperature2 = containerTemperature2;
		this.containerTemperatureSensorId2 = containerTemperatureSensorId2;
		this.containerTemperature3 = containerTemperature3;
		this.containerTemperatureSensorId3 = containerTemperatureSensorId3;
		this.humidityValue = humidityValue;
		this.humidityTemperature = humidityTemperature;
		this.humiditySensorId = humiditySensorId;
		this.hvacStateOn = hvacStateOn;
		this.hvacStateValue = hvacStateValue;
		this.hvacActuatorId = hvacActuatorId;
		this.currentClampValue = currentClampValue;
		this.currentClampSensorId = currentClampSensorId;
		this.externalTemperature = externalTemperature;
		this.externalTemperatureId = externalTemperatureId;
		this.parcelTemperature = parcelTemperature;
		this.parcelTemperatureSensorId = parcelTemperatureSensorId;
		this.timestamp = timestamp;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public double getContainerTemperature1() {
		return containerTemperature1;
	}

	public void setContainerTemperature1(double containerTemperature1) {
		this.containerTemperature1 = containerTemperature1;
	}

	public String getContainerTemperatureSensorId1() {
		return containerTemperatureSensorId1;
	}

	public void setContainerTemperatureSensorId1(
			String containerTemperatureSensorId1) {
		this.containerTemperatureSensorId1 = containerTemperatureSensorId1;
	}

	public double getContainerTemperature2() {
		return containerTemperature2;
	}

	public void setContainerTemperature2(double containerTemperature2) {
		this.containerTemperature2 = containerTemperature2;
	}

	public String getContainerTemperatureSensorId2() {
		return containerTemperatureSensorId2;
	}

	public void setContainerTemperatureSensorId2(
			String containerTemperatureSensorId2) {
		this.containerTemperatureSensorId2 = containerTemperatureSensorId2;
	}

	public double getContainerTemperature3() {
		return containerTemperature3;
	}

	public void setContainerTemperature3(double containerTemperature3) {
		this.containerTemperature3 = containerTemperature3;
	}

	public String getContainerTemperatureSensorId3() {
		return containerTemperatureSensorId3;
	}

	public void setContainerTemperatureSensorId3(
			String containerTemperatureSensorId3) {
		this.containerTemperatureSensorId3 = containerTemperatureSensorId3;
	}

	public double getHumidityValue() {
		return humidityValue;
	}

	public void setHumidityValue(double humidityValue) {
		this.humidityValue = humidityValue;
	}

	public double getHumidityTemperature() {
		return humidityTemperature;
	}

	public void setHumidityTemperature(double humidityTemperature) {
		this.humidityTemperature = humidityTemperature;
	}

	public String getHumiditySensorId() {
		return humiditySensorId;
	}

	public void setHumiditySensorId(String humiditySensorId) {
		this.humiditySensorId = humiditySensorId;
	}

	public boolean isHvacStateOn() {
		return hvacStateOn;
	}

	public void setHvacStateOn(boolean hvacStateOn) {
		this.hvacStateOn = hvacStateOn;
	}

	public double getHvacStateValue() {
		return hvacStateValue;
	}

	public void setHvacStateValue(double hvacStateValue) {
		this.hvacStateValue = hvacStateValue;
	}

	public String getHvacActuatorId() {
		return hvacActuatorId;
	}

	public void setHvacActuatorId(String hvacActuatorId) {
		this.hvacActuatorId = hvacActuatorId;
	}

	public double getCurrentClampValue() {
		return currentClampValue;
	}

	public void setCurrentClampValue(double currentClampValue) {
		this.currentClampValue = currentClampValue;
	}

	public String getCurrentClampSensorId() {
		return currentClampSensorId;
	}

	public void setCurrentClampSensorId(String currentClampSensorId) {
		this.currentClampSensorId = currentClampSensorId;
	}

	public double getExternalTemperature() {
		return externalTemperature;
	}

	public void setExternalTemperature(double externalTemperature) {
		this.externalTemperature = externalTemperature;
	}

	public String getExternalTemperatureId() {
		return externalTemperatureId;
	}

	public void setExternalTemperatureId(String externalTemperatureId) {
		this.externalTemperatureId = externalTemperatureId;
	}

	public double getParcelTemperature() {
		return parcelTemperature;
	}

	public void setParcelTemperature(double parcelTemperature) {
		this.parcelTemperature = parcelTemperature;
	}

	public String getParcelTemperatureSensorId() {
		return parcelTemperatureSensorId;
	}

	public void setParcelTemperatureSensorId(String parcelTemperatureSensorId) {
		this.parcelTemperatureSensorId = parcelTemperatureSensorId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
