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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Start" type="{http://namespaces.softwareag.com/EDA/Event}SAGDateTimeType"/>
 *         &lt;element name="End" type="{http://namespaces.softwareag.com/EDA/Event}SAGDateTimeType" minOccurs="0"/>
 *         &lt;element name="Kind" type="{http://namespaces.softwareag.com/EDA/Event}SAGKindType" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://namespaces.softwareag.com/EDA/Event}EventTypeVersionType" minOccurs="0"/>
 *         &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://namespaces.softwareag.com/EDA/Event}EventPriorityType" minOccurs="0"/>
 *         &lt;element name="ProducerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FormatVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomHeaders" type="{http://namespaces.softwareag.com/EDA/Event}CustomHeaderType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", namespace = "http://namespaces.softwareag.com/EDA/Event", propOrder = {

})
public class HeaderType {

    @XmlElement(name = "Start", required = true)
    protected XMLGregorianCalendar start;
    @XmlElement(name = "End")
    protected XMLGregorianCalendar end;
    @XmlElement(name = "Kind", defaultValue = "Event")
    protected SAGKindType kind;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "CorrelationID")
    protected String correlationID;
    @XmlElement(name = "EventID")
    protected String eventID;
    @XmlElement(name = "Priority", defaultValue = "Normal")
    protected EventPriorityType priority;
    @XmlElement(name = "ProducerID")
    protected String producerID;
    @XmlElement(name = "UserID")
    protected String userID;
    @XmlElement(name = "FormatVersion")
    protected String formatVersion;
    @XmlElement(name = "CustomHeaders")
    protected CustomHeaderType customHeaders;

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStart(XMLGregorianCalendar value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnd(XMLGregorianCalendar value) {
        this.end = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link SAGKindType }
     *     
     */
    public SAGKindType getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAGKindType }
     *     
     */
    public void setKind(SAGKindType value) {
        this.kind = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the correlationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrelationID() {
        return correlationID;
    }

    /**
     * Sets the value of the correlationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationID(String value) {
        this.correlationID = value;
    }

    /**
     * Gets the value of the eventID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Sets the value of the eventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventID(String value) {
        this.eventID = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link EventPriorityType }
     *     
     */
    public EventPriorityType getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventPriorityType }
     *     
     */
    public void setPriority(EventPriorityType value) {
        this.priority = value;
    }

    /**
     * Gets the value of the producerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProducerID() {
        return producerID;
    }

    /**
     * Sets the value of the producerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProducerID(String value) {
        this.producerID = value;
    }

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the formatVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatVersion() {
        return formatVersion;
    }

    /**
     * Sets the value of the formatVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatVersion(String value) {
        this.formatVersion = value;
    }

    /**
     * Gets the value of the customHeaders property.
     * 
     * @return
     *     possible object is
     *     {@link CustomHeaderType }
     *     
     */
    public CustomHeaderType getCustomHeaders() {
        return customHeaders;
    }

    /**
     * Sets the value of the customHeaders property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomHeaderType }
     *     
     */
    public void setCustomHeaders(CustomHeaderType value) {
        this.customHeaders = value;
    }

}
