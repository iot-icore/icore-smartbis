Required wars:
	transportationsim.war
	PersistDataFromMqtt.war
	eebicore.war


Script location:
	jboss-as-7.1.1.Final\standalone\deployments\transportationsim.war\WEB-INF\configurations\icoreConfigFolder\
	
	
Script commands:

Add package:
	tick	ADD_PACKAGE	Initial_temperature	HumidityCoeff	PackId	GroupId (Locatie)
		tick: integer (4 ticks ~ 1 second)
		Initial_temperature: integer 
		HumidityCoeff: double (in range: 0.0->1.0) represents the humidity factor of the product (0.0 no humidity generation; 1.0 max humidity generation)
		PackID: String can contain any name
		GroupID: String represents the location where the package will be placed. May contain one of the following values: 
				Parcel1GroupID
				Parcel2GroupID
				Parcel3GroupID
				...
				Parcel12GroupID
				
	example: 5,ADD_PACKAGE,10,0.5,P_001,Parcel6GroupID

Remove package:
tick	REMOVE_PACKAGE	PackId	
	tick: integer (4 ticks ~ 1 second)
	PackID: String - The id of the package to be removed
	
	example: 10,REMOVE_PACKAGE,P_001
	
Add sensor:
	tick	ADD_SENSOR	sensorId	PackID	
		tick: integer (4 ticks ~ 1 second)
		sensorID: String May contain one of the following values:
				80_00_00_00_01_01
				80_00_00_00_01_02
				...
				80_00_00_00_01_12
		PackID: String The ID of the package; check add package command (The package must be added before!!!!)
		
		
	example: 5,ADD_SENSOR,80_00_00_00_01_01,P_001

Remove sensor:
tick	REMOVE_SENSOR	sensorId	PackID
	tick: integer (4 ticks ~ 1 second)
	sensorID: String check add sensor command
	PackID: String check add sensor command
	
	example: 10,REMOVE_SENSOR,80_00_00_00_01_01,P_001
	
Change outside temperature:
tick	CHANGE_OUTSIDE_TEMPERATURE	targetValue
	tick: integer (4 ticks ~ 1 second)
	targetValue: Integer
	
	example: 23,CHANGE_OUTSIDE_TEMPERATURE,12

Start HVAC: (can be used for changing the temperature of the HVAC even if the HVAC is already started)
tick	START_HVAC	targetValue
	tick: integer (4 ticks ~ 1 second)
	targetValue: integer the temperature of the air getting out of the HVAC
	
	example: 20,START_HVAC,5
	
stop HVAC
tick	STOP_HVAC
	tick: integer (4 ticks ~ 1 second)

	example:  200,STOP_HVAC
	
	
	
	
	
