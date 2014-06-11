//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.06 at 01:11:08 PM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for S01_TemperatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="S01_TemperatureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timestamp_" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="temperature" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="temperature_range" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="temperature_change" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="calibrated" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Range_alarm" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Change_alarm" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *       &lt;attribute name="driver_version" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="request_id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="temperature_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="gateway_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "S01_TemperatureType", namespace = "http://namespaces.softwareag.com/SmartBusiness", propOrder = {
    "timestamp",
    "temperature",
    "temperatureRange",
    "temperatureChange",
    "calibrated",
    "rangeAlarm",
    "changeAlarm"
})
public class S01TemperatureType {

    @XmlElement(name = "timestamp_", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(required = true)
    protected BigDecimal temperature;
    @XmlElement(name = "temperature_range", required = true)
    protected BigInteger temperatureRange;
    @XmlElement(name = "temperature_change", required = true)
    protected BigInteger temperatureChange;
    @XmlElement(required = true)
    protected BigInteger calibrated;
    @XmlElement(name = "Range_alarm", required = true)
    protected BigInteger rangeAlarm;
    @XmlElement(name = "Change_alarm", required = true)
    protected BigInteger changeAlarm;
    @XmlAttribute(name = "driver_version")
    protected BigInteger driverVersion;
    @XmlAttribute(name = "request_id")
    protected BigInteger requestId;
    @XmlAttribute(name = "temperature_sensor_id")
    protected String temperatureSensorId;
    @XmlAttribute(name = "gateway_id")
    protected String gatewayId;

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

    /**
     * Gets the value of the temperature property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     * Sets the value of the temperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTemperature(BigDecimal value) {
        this.temperature = value;
    }

    /**
     * Gets the value of the temperatureRange property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTemperatureRange() {
        return temperatureRange;
    }

    /**
     * Sets the value of the temperatureRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTemperatureRange(BigInteger value) {
        this.temperatureRange = value;
    }

    /**
     * Gets the value of the temperatureChange property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTemperatureChange() {
        return temperatureChange;
    }

    /**
     * Sets the value of the temperatureChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTemperatureChange(BigInteger value) {
        this.temperatureChange = value;
    }

    /**
     * Gets the value of the calibrated property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCalibrated() {
        return calibrated;
    }

    /**
     * Sets the value of the calibrated property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCalibrated(BigInteger value) {
        this.calibrated = value;
    }

    /**
     * Gets the value of the rangeAlarm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRangeAlarm() {
        return rangeAlarm;
    }

    /**
     * Sets the value of the rangeAlarm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRangeAlarm(BigInteger value) {
        this.rangeAlarm = value;
    }

    /**
     * Gets the value of the changeAlarm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getChangeAlarm() {
        return changeAlarm;
    }

    /**
     * Sets the value of the changeAlarm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setChangeAlarm(BigInteger value) {
        this.changeAlarm = value;
    }

    /**
     * Gets the value of the driverVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDriverVersion() {
        return driverVersion;
    }

    /**
     * Sets the value of the driverVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDriverVersion(BigInteger value) {
        this.driverVersion = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequestId(BigInteger value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the temperatureSensorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemperatureSensorId() {
        return temperatureSensorId;
    }

    /**
     * Sets the value of the temperatureSensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemperatureSensorId(String value) {
        this.temperatureSensorId = value;
    }

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

}
