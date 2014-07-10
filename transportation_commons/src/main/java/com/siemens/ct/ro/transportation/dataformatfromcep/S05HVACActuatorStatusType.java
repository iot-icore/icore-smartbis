//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.07 at 03:39:28 PM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for S05_HVACActuatorStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="S05_HVACActuatorStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hvacStateValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="minAllowed" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="maxAllowed" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="resolution" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="targetValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *       &lt;attribute name="hvacActuatorID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="gateway_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "S05_HVACActuatorStatusType", namespace = "http://namespaces.softwareag.com/SmartBusiness", propOrder = {
    "active",
    "hvacStateValue",
    "condition",
    "minAllowed",
    "maxAllowed",
    "resolution",
    "targetValue"
})
public class S05HVACActuatorStatusType {

    protected boolean active;
    protected double hvacStateValue;
    @XmlElement(required = true)
    protected String condition;
    protected double minAllowed;
    protected double maxAllowed;
    protected double resolution;
    protected double targetValue;
    @XmlAttribute(name = "hvacActuatorID")
    protected String hvacActuatorID;
    @XmlAttribute(name = "gateway_id")
    protected String gatewayId;

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
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
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

    /**
     * Gets the value of the minAllowed property.
     * 
     */
    public double getMinAllowed() {
        return minAllowed;
    }

    /**
     * Sets the value of the minAllowed property.
     * 
     */
    public void setMinAllowed(double value) {
        this.minAllowed = value;
    }

    /**
     * Gets the value of the maxAllowed property.
     * 
     */
    public double getMaxAllowed() {
        return maxAllowed;
    }

    /**
     * Sets the value of the maxAllowed property.
     * 
     */
    public void setMaxAllowed(double value) {
        this.maxAllowed = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     */
    public double getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     */
    public void setResolution(double value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the targetValue property.
     * 
     */
    public double getTargetValue() {
        return targetValue;
    }

    /**
     * Sets the value of the targetValue property.
     * 
     */
    public void setTargetValue(double value) {
        this.targetValue = value;
    }

    /**
     * Gets the value of the hvacActuatorID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHvacActuatorID() {
        return hvacActuatorID;
    }

    /**
     * Sets the value of the hvacActuatorID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHvacActuatorID(String value) {
        this.hvacActuatorID = value;
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
