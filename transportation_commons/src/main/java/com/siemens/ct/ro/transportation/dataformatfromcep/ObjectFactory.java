//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.17 at 11:25:19 AM FET 
//


package com.siemens.ct.ro.transportation.dataformatfromcep;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.siemens.ct.ro.transportation.dataformatfromcep package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _S03CurrentClamp_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S03_CurrentClamp");
    private final static QName _S04TemperaturePackage_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "S04_TemperaturePackage");
    private final static QName _AggregateTemperature_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "AggregateTemperature");
    private final static QName _TruckSensorJoined_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "TruckSensorJoined");
    private final static QName _OutputEventGenerator_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "OutputEventGenerator");
    private final static QName _S02TruckPosition_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S02_TruckPosition");
    private final static QName _S02Humidity_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S02_Humidity");
    private final static QName _TruckSensorJoinDoc_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "TruckSensorJoinDoc");
    private final static QName _CEPTemperatureGPS_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "CEP_Temperature_GPS");
    private final static QName _S03TruckPackage_QNAME = new QName("http://icore.com/SmartBusiness", "S03_TruckPackage");
    private final static QName _S04CO2_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S04_CO2");
    private final static QName _Q05TruckPackageTemperature_QNAME = new QName("http://icore.com/SmartBusiness", "Q05_TruckPackageTemperature");
    private final static QName _Payload_QNAME = new QName("http://namespaces.softwareag.com/EDA/Event", "Payload");
    private final static QName _S05HVACActuatorStatus_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S05_HVACActuatorStatus");
    private final static QName _S01Temperature_QNAME = new QName("http://namespaces.softwareag.com/SmartBusiness", "S01_Temperature");
    private final static QName _TruckSensorFlat_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "TruckSensorFlat");
    private final static QName _QueryOutput_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "QueryOutput");
    private final static QName _CEPGPSToVectorLength_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "CEP_GPS_toVectorLength");
    private final static QName _TemperatureLowRangeWarning_QNAME = new QName("http://namespaces.softwareag.com/EDA/SmartBusiness", "TemperatureLowRangeWarning");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.siemens.ct.ro.transportation.dataformatfromcep
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link S04TemperaturePackageType }
     * 
     */
    public S04TemperaturePackageType createS04TemperaturePackageType() {
        return new S04TemperaturePackageType();
    }

    /**
     * Create an instance of {@link AggregateTemperatureType }
     * 
     */
    public AggregateTemperatureType createAggregateTemperatureType() {
        return new AggregateTemperatureType();
    }

    /**
     * Create an instance of {@link TruckSensorFlatType }
     * 
     */
    public TruckSensorFlatType createTruckSensorFlatType() {
        return new TruckSensorFlatType();
    }

    /**
     * Create an instance of {@link OutputEventGeneratorType }
     * 
     */
    public OutputEventGeneratorType createOutputEventGeneratorType() {
        return new OutputEventGeneratorType();
    }

    /**
     * Create an instance of {@link TruckSensorJoinedType }
     * 
     */
    public TruckSensorJoinedType createTruckSensorJoinedType() {
        return new TruckSensorJoinedType();
    }

    /**
     * Create an instance of {@link CEPGPSToVectorLengthType }
     * 
     */
    public CEPGPSToVectorLengthType createCEPGPSToVectorLengthType() {
        return new CEPGPSToVectorLengthType();
    }

    /**
     * Create an instance of {@link QueryOutputType }
     * 
     */
    public QueryOutputType createQueryOutputType() {
        return new QueryOutputType();
    }

    /**
     * Create an instance of {@link TemperatureLowRangeWarningType }
     * 
     */
    public TemperatureLowRangeWarningType createTemperatureLowRangeWarningType() {
        return new TemperatureLowRangeWarningType();
    }

    /**
     * Create an instance of {@link CEPTemperatureGPSType }
     * 
     */
    public CEPTemperatureGPSType createCEPTemperatureGPSType() {
        return new CEPTemperatureGPSType();
    }

    /**
     * Create an instance of {@link TruckSensorJoinDocType }
     * 
     */
    public TruckSensorJoinDocType createTruckSensorJoinDocType() {
        return new TruckSensorJoinDocType();
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link Event.Body }
     * 
     */
    public Event.Body createEventBody() {
        return new Event.Body();
    }

    /**
     * Create an instance of {@link CustomHeaderType }
     * 
     */
    public CustomHeaderType createCustomHeaderType() {
        return new CustomHeaderType();
    }

    /**
     * Create an instance of {@link S03TruckPackageType }
     * 
     */
    public S03TruckPackageType createS03TruckPackageType() {
        return new S03TruckPackageType();
    }

    /**
     * Create an instance of {@link Q05TruckPackageTemperatureType }
     * 
     */
    public Q05TruckPackageTemperatureType createQ05TruckPackageTemperatureType() {
        return new Q05TruckPackageTemperatureType();
    }

    /**
     * Create an instance of {@link S02TruckPositionType }
     * 
     */
    public S02TruckPositionType createS02TruckPositionType() {
        return new S02TruckPositionType();
    }

    /**
     * Create an instance of {@link S02HumidityType }
     * 
     */
    public S02HumidityType createS02HumidityType() {
        return new S02HumidityType();
    }

    /**
     * Create an instance of {@link S03CurrentClampType }
     * 
     */
    public S03CurrentClampType createS03CurrentClampType() {
        return new S03CurrentClampType();
    }

    /**
     * Create an instance of {@link S01TemperatureType }
     * 
     */
    public S01TemperatureType createS01TemperatureType() {
        return new S01TemperatureType();
    }

    /**
     * Create an instance of {@link S05HVACActuatorStatusType }
     * 
     */
    public S05HVACActuatorStatusType createS05HVACActuatorStatusType() {
        return new S05HVACActuatorStatusType();
    }

    /**
     * Create an instance of {@link S04CO2Type }
     * 
     */
    public S04CO2Type createS04CO2Type() {
        return new S04CO2Type();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S03CurrentClampType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S03_CurrentClamp", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S03CurrentClampType> createS03CurrentClamp(S03CurrentClampType value) {
        return new JAXBElement<S03CurrentClampType>(_S03CurrentClamp_QNAME, S03CurrentClampType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S04TemperaturePackageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "S04_TemperaturePackage", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S04TemperaturePackageType> createS04TemperaturePackage(S04TemperaturePackageType value) {
        return new JAXBElement<S04TemperaturePackageType>(_S04TemperaturePackage_QNAME, S04TemperaturePackageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggregateTemperatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "AggregateTemperature", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<AggregateTemperatureType> createAggregateTemperature(AggregateTemperatureType value) {
        return new JAXBElement<AggregateTemperatureType>(_AggregateTemperature_QNAME, AggregateTemperatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TruckSensorJoinedType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "TruckSensorJoined", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<TruckSensorJoinedType> createTruckSensorJoined(TruckSensorJoinedType value) {
        return new JAXBElement<TruckSensorJoinedType>(_TruckSensorJoined_QNAME, TruckSensorJoinedType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutputEventGeneratorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "OutputEventGenerator", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<OutputEventGeneratorType> createOutputEventGenerator(OutputEventGeneratorType value) {
        return new JAXBElement<OutputEventGeneratorType>(_OutputEventGenerator_QNAME, OutputEventGeneratorType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S02TruckPositionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S02_TruckPosition", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S02TruckPositionType> createS02TruckPosition(S02TruckPositionType value) {
        return new JAXBElement<S02TruckPositionType>(_S02TruckPosition_QNAME, S02TruckPositionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S02HumidityType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S02_Humidity", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S02HumidityType> createS02Humidity(S02HumidityType value) {
        return new JAXBElement<S02HumidityType>(_S02Humidity_QNAME, S02HumidityType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TruckSensorJoinDocType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "TruckSensorJoinDoc", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<TruckSensorJoinDocType> createTruckSensorJoinDoc(TruckSensorJoinDocType value) {
        return new JAXBElement<TruckSensorJoinDocType>(_TruckSensorJoinDoc_QNAME, TruckSensorJoinDocType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CEPTemperatureGPSType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "CEP_Temperature_GPS", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<CEPTemperatureGPSType> createCEPTemperatureGPS(CEPTemperatureGPSType value) {
        return new JAXBElement<CEPTemperatureGPSType>(_CEPTemperatureGPS_QNAME, CEPTemperatureGPSType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S03TruckPackageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icore.com/SmartBusiness", name = "S03_TruckPackage", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S03TruckPackageType> createS03TruckPackage(S03TruckPackageType value) {
        return new JAXBElement<S03TruckPackageType>(_S03TruckPackage_QNAME, S03TruckPackageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S04CO2Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S04_CO2", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S04CO2Type> createS04CO2(S04CO2Type value) {
        return new JAXBElement<S04CO2Type>(_S04CO2_QNAME, S04CO2Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Q05TruckPackageTemperatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icore.com/SmartBusiness", name = "Q05_TruckPackageTemperature", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<Q05TruckPackageTemperatureType> createQ05TruckPackageTemperature(Q05TruckPackageTemperatureType value) {
        return new JAXBElement<Q05TruckPackageTemperatureType>(_Q05TruckPackageTemperature_QNAME, Q05TruckPackageTemperatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/Event", name = "Payload")
    public JAXBElement<Object> createPayload(Object value) {
        return new JAXBElement<Object>(_Payload_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S05HVACActuatorStatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S05_HVACActuatorStatus", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S05HVACActuatorStatusType> createS05HVACActuatorStatus(S05HVACActuatorStatusType value) {
        return new JAXBElement<S05HVACActuatorStatusType>(_S05HVACActuatorStatus_QNAME, S05HVACActuatorStatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link S01TemperatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/SmartBusiness", name = "S01_Temperature", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<S01TemperatureType> createS01Temperature(S01TemperatureType value) {
        return new JAXBElement<S01TemperatureType>(_S01Temperature_QNAME, S01TemperatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TruckSensorFlatType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "TruckSensorFlat")
    public JAXBElement<TruckSensorFlatType> createTruckSensorFlat(TruckSensorFlatType value) {
        return new JAXBElement<TruckSensorFlatType>(_TruckSensorFlat_QNAME, TruckSensorFlatType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryOutputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "QueryOutput", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<QueryOutputType> createQueryOutput(QueryOutputType value) {
        return new JAXBElement<QueryOutputType>(_QueryOutput_QNAME, QueryOutputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CEPGPSToVectorLengthType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "CEP_GPS_toVectorLength", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<CEPGPSToVectorLengthType> createCEPGPSToVectorLength(CEPGPSToVectorLengthType value) {
        return new JAXBElement<CEPGPSToVectorLengthType>(_CEPGPSToVectorLength_QNAME, CEPGPSToVectorLengthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemperatureLowRangeWarningType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://namespaces.softwareag.com/EDA/SmartBusiness", name = "TemperatureLowRangeWarning", substitutionHeadNamespace = "http://namespaces.softwareag.com/EDA/Event", substitutionHeadName = "Payload")
    public JAXBElement<TemperatureLowRangeWarningType> createTemperatureLowRangeWarning(TemperatureLowRangeWarningType value) {
        return new JAXBElement<TemperatureLowRangeWarningType>(_TemperatureLowRangeWarning_QNAME, TemperatureLowRangeWarningType.class, null, value);
    }

}
