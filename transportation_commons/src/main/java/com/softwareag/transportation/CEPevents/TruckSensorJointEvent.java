package com.softwareag.transportation.CEPevents;

public class TruckSensorJointEvent {

	private long id;
	private String gatewayId;
	private Double containerTemperature1Value;
	private String containerTemperatureSensorId1;
	private Double containerTemperature2Value;
	private String containerTemperatureSensorId2;
	private Double containerTemperature3Value;
	private String containerTemperatureSensorId3;
	private Double humidityValue;
	private String humiditySensorId;
	private boolean acUnitState;
	private double acUnitTarghetValue;
	private String acUnitActuatorId;
	private double currentClampValue;
	private String currentClampSensorId;
	private double externalTemperatureValue;
	private String externalTemperatureId;
	private double parcelTemperatureValue;
	private String parcelTemperatureSensorId;
	private Long timestamp;

	public TruckSensorJointEvent() {
		super();
	}

	public TruckSensorJointEvent(long id, String gatewayId,
			Double containerTemperature1Value,
			String containerTemperatureSensorId1,
			Double containerTemperature2Value,
			String containerTemperatureSensorId2,
			Double containerTemperature3Value,
			String containerTemperatureSensorId3, Double humidityValue,
			String humiditySensorId, boolean acUnitState, double acUnitTarghetValue,
			String acUnitActuatorId, double currentClampValue,
			String currentClampSensorId, double externalTemperatureValue,
			String externalTemperatureId, double parcelTemperatureValue,
			String parcelTemperatureSensorId, Long timestamp) {
		super();
		this.id = id;
		this.gatewayId = gatewayId;
		this.containerTemperature1Value = containerTemperature1Value;
		this.containerTemperatureSensorId1 = containerTemperatureSensorId1;
		this.containerTemperature2Value = containerTemperature2Value;
		this.containerTemperatureSensorId2 = containerTemperatureSensorId2;
		this.containerTemperature3Value = containerTemperature3Value;
		this.containerTemperatureSensorId3 = containerTemperatureSensorId3;
		this.humidityValue = humidityValue;
		this.humiditySensorId = humiditySensorId;
		this.acUnitState = acUnitState;
		this.acUnitTarghetValue = acUnitTarghetValue;
		this.acUnitActuatorId = acUnitActuatorId;
		this.currentClampValue = currentClampValue;
		this.currentClampSensorId = currentClampSensorId;
		this.externalTemperatureValue = externalTemperatureValue;
		this.externalTemperatureId = externalTemperatureId;
		this.parcelTemperatureValue = parcelTemperatureValue;
		this.parcelTemperatureSensorId = parcelTemperatureSensorId;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Double getContainerTemperature1Value() {
		return containerTemperature1Value;
	}

	public void setContainerTemperature1Value(Double containerTemperature1Value) {
		this.containerTemperature1Value = containerTemperature1Value;
	}

	public String getContainerTemperatureSensorId1() {
		return containerTemperatureSensorId1;
	}

	public void setContainerTemperatureSensorId1(
			String containerTemperatureSensorId1) {
		this.containerTemperatureSensorId1 = containerTemperatureSensorId1;
	}

	public Double getContainerTemperature2Value() {
		return containerTemperature2Value;
	}

	public void setContainerTemperature2Value(Double containerTemperature2Value) {
		this.containerTemperature2Value = containerTemperature2Value;
	}

	public String getContainerTemperatureSensorId2() {
		return containerTemperatureSensorId2;
	}

	public void setContainerTemperatureSensorId2(
			String containerTemperatureSensorId2) {
		this.containerTemperatureSensorId2 = containerTemperatureSensorId2;
	}

	public Double getContainerTemperature3Value() {
		return containerTemperature3Value;
	}

	public void setContainerTemperature3Value(Double containerTemperature3Value) {
		this.containerTemperature3Value = containerTemperature3Value;
	}

	public String getContainerTemperatureSensorId3() {
		return containerTemperatureSensorId3;
	}

	public void setContainerTemperatureSensorId3(
			String containerTemperatureSensorId3) {
		this.containerTemperatureSensorId3 = containerTemperatureSensorId3;
	}

	public Double getHumidityValue() {
		return humidityValue;
	}

	public void setHumidityValue(Double humidityValue) {
		this.humidityValue = humidityValue;
	}

	public String getHumiditySensorId() {
		return humiditySensorId;
	}

	public void setHumiditySensorId(String humiditySensorId) {
		this.humiditySensorId = humiditySensorId;
	}

	public boolean isAcUnitState() {
		return acUnitState;
	}

	public void setAcUnitState(boolean acUnitState) {
		this.acUnitState = acUnitState;
	}

	public double getAcUnitTarghetValue() {
		return acUnitTarghetValue;
	}

	public void setAcUnitTarghetValue(double acUnitTarghetValue) {
		this.acUnitTarghetValue = acUnitTarghetValue;
	}

	public String getAcUnitActuatorId() {
		return acUnitActuatorId;
	}

	public void setAcUnitActuatorId(String acUnitActuatorId) {
		this.acUnitActuatorId = acUnitActuatorId;
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

	public double getExternalTemperatureValue() {
		return externalTemperatureValue;
	}

	public void setExternalTemperatureValue(double externalTemperatureValue) {
		this.externalTemperatureValue = externalTemperatureValue;
	}

	public String getExternalTemperatureId() {
		return externalTemperatureId;
	}

	public void setExternalTemperatureId(String externalTemperatureId) {
		this.externalTemperatureId = externalTemperatureId;
	}

	public double getParcelTemperatureValue() {
		return parcelTemperatureValue;
	}

	public void setParcelTemperatureValue(double parcelTemperatureValue) {
		this.parcelTemperatureValue = parcelTemperatureValue;
	}

	public String getParcelTemperatureSensorId() {
		return parcelTemperatureSensorId;
	}

	public void setParcelTemperatureSensorId(String parcelTemperatureSensorId) {
		this.parcelTemperatureSensorId = parcelTemperatureSensorId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
