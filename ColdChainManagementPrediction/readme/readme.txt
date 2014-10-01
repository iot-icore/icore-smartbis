Cold Chain Management (cep project)
	
ADAPTATIONS
- EventGenerator needs some files , unclear where referenced
Adding a tmp-Folder renaming has no effect. -> Conclusion folder not needed.

- Adoptions to the corresponding event type store at the bottom of the event generator configuration file
/ColdChainManagement/.evtgen/conf/DeviceMonitoringConfigTemperature.xml 


EXECUTION
You have the opinion to use the following event generator:
	1) .evtgenHumidity (executable)
	2) .evtgenTemperature (executable)
	3) .evtgenACunit (executable)
	4. .evtgenADC (executable) 
	
The event generator produces events defined by the event type
	1) /ColdChainManagement/Event Types/SmartBusiness/S02_Humidity.xsd
	2) /ColdChainManagement/Event Types/SmartBusiness/S01_Temperature.xsd
	3) /ColdChainManagement/Event Types/SmartBusiness/S03_CurrentClamp.xsd
	4) /ColdChainManagement/Event Types/SmartBusiness/S05_HVACActuatorStatus.xsd
	
	
More description below:
/ColdChainManagementPrediction/readme/Instruction for ColdChainManagementPrediction.docx