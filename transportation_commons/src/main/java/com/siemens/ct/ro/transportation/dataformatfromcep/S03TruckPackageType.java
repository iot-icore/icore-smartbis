//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.07 at 03:39:28 PM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for S03_TruckPackageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="S03_TruckPackageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="truck_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="package_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="load" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "S03_TruckPackageType", namespace = "http://icore.com/SmartBusiness", propOrder = {
    "truckId",
    "packageId",
    "load"
})
public class S03TruckPackageType {

    @XmlElement(name = "truck_id", required = true)
    protected String truckId;
    @XmlElement(name = "package_id", required = true)
    protected String packageId;
    protected boolean load;

    /**
     * Gets the value of the truckId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTruckId() {
        return truckId;
    }

    /**
     * Sets the value of the truckId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTruckId(String value) {
        this.truckId = value;
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

    /**
     * Gets the value of the load property.
     * 
     */
    public boolean isLoad() {
        return load;
    }

    /**
     * Sets the value of the load property.
     * 
     */
    public void setLoad(boolean value) {
        this.load = value;
    }

}
