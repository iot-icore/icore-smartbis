//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.06 at 01:11:08 PM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for S04_TemperaturePackageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="S04_TemperaturePackageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attached" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="temperature_sensor_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="package_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "S04_TemperaturePackageType", propOrder = {
    "attached",
    "temperatureSensorId",
    "packageId"
})
public class S04TemperaturePackageType {

    @XmlElement(required = true)
    protected BigInteger attached;
    @XmlElement(name = "temperature_sensor_id", required = true)
    protected String temperatureSensorId;
    @XmlElement(name = "package_id", required = true)
    protected String packageId;

    /**
     * Gets the value of the attached property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAttached() {
        return attached;
    }

    /**
     * Sets the value of the attached property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAttached(BigInteger value) {
        this.attached = value;
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
     * Gets the value of the packageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * Sets the value of the packageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageId(String value) {
        this.packageId = value;
    }

}
