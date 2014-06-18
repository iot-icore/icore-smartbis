//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.17 at 11:25:19 AM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for S02_TruckPositionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="S02_TruckPositionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timestamp_" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="utc_time" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="utc_date" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="lat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lat_deg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lat_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lat_sec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lon_deg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lon_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lon_sec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="speed" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *       &lt;attribute name="gps_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="gateway_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "S02_TruckPositionType", namespace = "http://namespaces.softwareag.com/SmartBusiness", propOrder = {
    "timestamp",
    "utcTime",
    "utcDate",
    "lat",
    "lon",
    "latDeg",
    "latMin",
    "latSec",
    "lonDeg",
    "lonMin",
    "lonSec",
    "speed"
})
public class S02TruckPositionType {

    @XmlElement(name = "timestamp_", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "utc_time", required = true)
    protected BigInteger utcTime;
    @XmlElement(name = "utc_date", required = true)
    protected BigInteger utcDate;
    protected double lat;
    protected double lon;
    @XmlElement(name = "lat_deg")
    protected double latDeg;
    @XmlElement(name = "lat_min")
    protected double latMin;
    @XmlElement(name = "lat_sec")
    protected double latSec;
    @XmlElement(name = "lon_deg")
    protected double lonDeg;
    @XmlElement(name = "lon_min")
    protected double lonMin;
    @XmlElement(name = "lon_sec")
    protected double lonSec;
    protected double speed;
    @XmlAttribute(name = "gps_sensor_id")
    protected String gpsSensorId;
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
     * Gets the value of the utcTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUtcTime() {
        return utcTime;
    }

    /**
     * Sets the value of the utcTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUtcTime(BigInteger value) {
        this.utcTime = value;
    }

    /**
     * Gets the value of the utcDate property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUtcDate() {
        return utcDate;
    }

    /**
     * Sets the value of the utcDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUtcDate(BigInteger value) {
        this.utcDate = value;
    }

    /**
     * Gets the value of the lat property.
     * 
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     * 
     */
    public void setLat(double value) {
        this.lat = value;
    }

    /**
     * Gets the value of the lon property.
     * 
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets the value of the lon property.
     * 
     */
    public void setLon(double value) {
        this.lon = value;
    }

    /**
     * Gets the value of the latDeg property.
     * 
     */
    public double getLatDeg() {
        return latDeg;
    }

    /**
     * Sets the value of the latDeg property.
     * 
     */
    public void setLatDeg(double value) {
        this.latDeg = value;
    }

    /**
     * Gets the value of the latMin property.
     * 
     */
    public double getLatMin() {
        return latMin;
    }

    /**
     * Sets the value of the latMin property.
     * 
     */
    public void setLatMin(double value) {
        this.latMin = value;
    }

    /**
     * Gets the value of the latSec property.
     * 
     */
    public double getLatSec() {
        return latSec;
    }

    /**
     * Sets the value of the latSec property.
     * 
     */
    public void setLatSec(double value) {
        this.latSec = value;
    }

    /**
     * Gets the value of the lonDeg property.
     * 
     */
    public double getLonDeg() {
        return lonDeg;
    }

    /**
     * Sets the value of the lonDeg property.
     * 
     */
    public void setLonDeg(double value) {
        this.lonDeg = value;
    }

    /**
     * Gets the value of the lonMin property.
     * 
     */
    public double getLonMin() {
        return lonMin;
    }

    /**
     * Sets the value of the lonMin property.
     * 
     */
    public void setLonMin(double value) {
        this.lonMin = value;
    }

    /**
     * Gets the value of the lonSec property.
     * 
     */
    public double getLonSec() {
        return lonSec;
    }

    /**
     * Sets the value of the lonSec property.
     * 
     */
    public void setLonSec(double value) {
        this.lonSec = value;
    }

    /**
     * Gets the value of the speed property.
     * 
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the value of the speed property.
     * 
     */
    public void setSpeed(double value) {
        this.speed = value;
    }

    /**
     * Gets the value of the gpsSensorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGpsSensorId() {
        return gpsSensorId;
    }

    /**
     * Sets the value of the gpsSensorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGpsSensorId(String value) {
        this.gpsSensorId = value;
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
