//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.17 at 11:25:19 AM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TruckSensorJoinedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TruckSensorJoinedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gateway_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="container_temperature_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="container_temperature_sensor_id_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="container_temperature_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="container_temperature_sensor_id_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="container_temperature_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="container_temperature_sensor_id_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="humidity_value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="humidity_temperature" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="humidity_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hvacStateOn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hvacStateValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hvacActuatorId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentClampValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="currentClamp_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="external_temperature" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="external_temperature_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parcel_temperature" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="parcel_temperature_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp_" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TruckSensorJoinedType", propOrder = {
    "gatewayId",
    "containerTemperature1",
    "containerTemperatureSensorId1",
    "containerTemperature2",
    "containerTemperatureSensorId2",
    "containerTemperature3",
    "containerTemperatureSensorId3",
    "humidityValue",
    "humidityTemperature",
    "humiditySensorId",
    "hvacStateOn",
    "hvacStateValue",
    "hvacActuatorId",
    "currentClampValue",
    "currentClampSensorId",
    "externalTemperature",
    "externalTemperatureId",
    "parcelTemperature",
    "parcelTemperatureSensorId",
    "timestamp"
})
public class TruckSensorJoinedType implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2819470859311889588L;
	
	@XmlElement(name = "gateway_id")
    protected String gatewayId;
    @XmlElement(name = "container_temperature_1")
    protected double containerTemperature1;
    @XmlElement(name = "container_temperature_sensor_id_1")
    protected String containerTemperatureSensorId1;
    @XmlElement(name = "container_temperature_2")
    protected double containerTemperature2;
    @XmlElement(name = "container_temperature_sensor_id_2")
    protected String containerTemperatureSensorId2;
    @XmlElement(name = "container_temperature_3")
    protected double containerTemperature3;
    @XmlElement(name = "container_temperature_sensor_id_3")
    protected String containerTemperatureSensorId3;
    @XmlElement(name = "humidity_value")
    protected double humidityValue;
    @XmlElement(name = "humidity_temperature")
    protected double humidityTemperature;
    @XmlElement(name = "humidity_sensor_id")
    protected String humiditySensorId;
    protected boolean hvacStateOn;
    protected double hvacStateValue;
    protected String hvacActuatorId;
    protected double currentClampValue;
    @XmlElement(name = "currentClamp_sensor_id")
    protected String currentClampSensorId;
    @XmlElement(name = "external_temperature")
    protected double externalTemperature;
    @XmlElement(name = "external_temperature_id")
    protected String externalTemperatureId;
    @XmlElement(name = "parcel_temperature")
    protected double parcelTemperature;
    @XmlElement(name = "parcel_temperature_sensor_id")
    protected String parcelTemperatureSensorId;
    @XmlElement(name = "timestamp_", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;

    /**
     * Gets the value of the gatewayId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayId() {
        return gatewayId;
    }

    /**
     * Sets the value of the gatewayId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayId(String value) {
        this.gatewayId = value;
    }

    /**
     * Gets the value of the containerTemperature1 property.
     * 
     */
    public double getContainerTemperature1() {
        return containerTemperature1;
    }

    /**
     * Sets the value of the containerTemperature1 property.
     * 
     */
    public void setContainerTemperature1(double value) {
        this.containerTemperature1 = value;
    }

    /**
     * Gets the value of the containerTemperatureSensorId1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerTemperatureSensorId1() {
        return containerTemperatureSensorId1;
    }

    /**
     * Sets the value of the containerTemperatureSensorId1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerTemperatureSensorId1(String value) {
        this.containerTemperatureSensorId1 = value;
    }

    /**
     * Gets the value of the containerTemperature2 property.
     * 
     */
    public double getContainerTemperature2() {
        return containerTemperature2;
    }

    /**
     * Sets the value of the containerTemperature2 property.
     * 
     */
    public void setContainerTemperature2(double value) {
        this.containerTemperature2 = value;
    }

    /**
     * Gets the value of the containerTemperatureSensorId2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerTemperatureSensorId2() {
        return containerTemperatureSensorId2;
    }

    /**
     * Sets the value of the containerTemperatureSensorId2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerTemperatureSensorId2(String value) {
        this.containerTemperatureSensorId2 = value;
    }

    /**
     * Gets the value of the containerTemperature3 property.
     * 
     */
    public double getContainerTemperature3() {
        return containerTemperature3;
    }

    /**
     * Sets the value of the containerTemperature3 property.
     * 
     */
    public void setContainerTemperature3(double value) {
        this.containerTemperature3 = value;
    }

    /**
     * Gets the value of the containerTemperatureSensorId3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerTemperatureSensorId3() {
        return containerTemperatureSensorId3;
    }

    /**
     * Sets the value of the containerTemperatureSensorId3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerTemperatureSensorId3(String value) {
        this.containerTemperatureSensorId3 = value;
    }

    /**
     * Gets the value of the humidityValue property.
     * 
     */
    public double getHumidityValue() {
        return humidityValue;
    }

    /**
     * Sets the value of the humidityValue property.
     * 
     */
    public void setHumidityValue(double value) {
        this.humidityValue = value;
    }

    /**
     * Gets the value of the humidityTemperature property.
     * 
     */
    public double getHumidityTemperature() {
        return humidityTemperature;
    }

    /**
     * Sets the value of the humidityTemperature property.
     * 
     */
    public void setHumidityTemperature(double value) {
        this.humidityTemperature = value;
    }

    /**
     * Gets the value of the humiditySensorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHumiditySensorId() {
        return humiditySensorId;
    }

    /**
     * Sets the value of the humiditySensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHumiditySensorId(String value) {
        this.humiditySensorId = value;
    }

    /**
     * Gets the value of the hvacStateOn property.
     * 
     */
    public boolean isHvacStateOn() {
        return hvacStateOn;
    }

    /**
     * Sets the value of the hvacStateOn property.
     * 
     */
    public void setHvacStateOn(boolean value) {
        this.hvacStateOn = value;
    }

    /**
     * Gets the value of the hvacStateValue property.
     * 
     */
    public double getHvacStateValue() {
        return hvacStateValue;
    }

    /**
     * Sets the value of the hvacStateValue property.
     * 
     */
    public void setHvacStateValue(double value) {
        this.hvacStateValue = value;
    }

    /**
     * Gets the value of the hvacActuatorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHvacActuatorId() {
        return hvacActuatorId;
    }

    /**
     * Sets the value of the hvacActuatorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHvacActuatorId(String value) {
        this.hvacActuatorId = value;
    }

    /**
     * Gets the value of the currentClampValue property.
     * 
     */
    public double getCurrentClampValue() {
        return currentClampValue;
    }

    /**
     * Sets the value of the currentClampValue property.
     * 
     */
    public void setCurrentClampValue(double value) {
        this.currentClampValue = value;
    }

    /**
     * Gets the value of the currentClampSensorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentClampSensorId() {
        return currentClampSensorId;
    }

    /**
     * Sets the value of the currentClampSensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentClampSensorId(String value) {
        this.currentClampSensorId = value;
    }

    /**
     * Gets the value of the externalTemperature property.
     * 
     */
    public double getExternalTemperature() {
        return externalTemperature;
    }

    /**
     * Sets the value of the externalTemperature property.
     * 
     */
    public void setExternalTemperature(double value) {
        this.externalTemperature = value;
    }

    /**
     * Gets the value of the externalTemperatureId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalTemperatureId() {
        return externalTemperatureId;
    }

    /**
     * Sets the value of the externalTemperatureId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalTemperatureId(String value) {
        this.externalTemperatureId = value;
    }

    /**
     * Gets the value of the parcelTemperature property.
     * 
     */
    public double getParcelTemperature() {
        return parcelTemperature;
    }

    /**
     * Sets the value of the parcelTemperature property.
     * 
     */
    public void setParcelTemperature(double value) {
        this.parcelTemperature = value;
    }

    /**
     * Gets the value of the parcelTemperatureSensorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParcelTemperatureSensorId() {
        return parcelTemperatureSensorId;
    }

    /**
     * Sets the value of the parcelTemperatureSensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParcelTemperatureSensorId(String value) {
        this.parcelTemperatureSensorId = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

}
