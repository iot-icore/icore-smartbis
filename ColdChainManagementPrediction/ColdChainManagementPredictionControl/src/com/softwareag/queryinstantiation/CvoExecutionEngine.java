package com.softwareag.queryinstantiation;

import java.net.UnknownHostException;
import java.util.List;

import com.softwareag.wep.api.client.DirectConnection;

public interface CvoExecutionEngine {

	/**
	 * instantiates the CEP engine or the connection to the CEP engine if it is
	 * running on a server
	 * 
	 * @param user
	 * 			car-file name
	 * @param password
	 * 			default empty ("")
	 * @param eventServerHost
	 * 			Host-address of the Event Server
	 * @param eventServerPort
	 * 			Port number of the Event Server
	 * @return DirectConnection
	 * 			A DirectConnection is the central point to configure high-speed 
	 * 			direct connections to the Event Server. These direct connections can be used 
	 * 			either to send relational events, so-called records, into an input stream or 
	 * 			to receive the result stream of a query. These input streams and queries must be 
	 * 			already registered at the system, i.e., the corresponding CEP project must have 
	 * 			been successfully deployed. Input as well as output connections require the 
	 * 			implementation of corresponding callbacks, which determine the application-specific 
	 * 			processing of the input and output events.
	 * @throws UnknownHostException 
	 */
	public DirectConnection startEngine(String user, String password,
			String eventServerHost, int eventServerPort) throws UnknownHostException;

	/**
	 * stops the CEP engine or the connection to the CEP engine if it is running
	 * on a server
	 * @throws UnknownHostException 
	 */
	public void stopEngine(DirectConnection conn);

	/**
	 * registers a new temperature data source
	 * 
	 * @param temperatureSensorID
	 *            the ID of the temperature sensor which will provide the
	 *            measurements
	 */
	public void registerNewTemperatureDataSource(String temperatureSensorID);

	/**
	 * registers a new humidity data source
	 * 
	 * @param humiditySensorID
	 *            the ID of the humidity sensor which will provide the
	 *            measurements
	 */
	public void registerNewHumidityDataSource(String humiditySensorID);

	/**
	 * registers a new currentClamp data source
	 * 
	 * @param currentClampSensorID
	 *            the ID of the currentClamp sensor which will provide the
	 *            measurements
	 */
	public void registerNewcurrentClampDataSource(String currentClampSensorID);

	/**
	 * registers a new ac unit data source
	 * 
	 * @param acUnitID
	 *            the ID of the AC unit which will provide the measurements
	 */
	public void registerNewACUnitDataSource(String acUnitID);

	/**
	 * registers in the CEP engine a new EPN for monitoring the temperature and
	 * the humidity of a parcel with non perishable goods.
	 * 
	 * @param epnID
	 *            the unique ID of this EPN
	 * @param temperatureSensorID
	 *            the ID of the temperature sensor attached to the package
	 * @param humiditySensorID
	 *            the ID of the container humidity sensor
	 * @param gatewayID
	 *            the ID of the truck (the gateway ID)
	 * @param hardMinTemperature
	 *            the lower temperature value recommended for storing the
	 *            product
	 * @param hardMaxTemperature
	 *            the upper temperature value recommended for storing the
	 *            product
	 * @param hardMinHumdity
	 *            the lower humidity value recommended for storing the product
	 * @param hardMaxHumdity
	 *            the upper humidity value recommended for storing the product
	 * @throws UnknownHostException 
	 */
	public void addNewNonPerishableGoodsMonitoringEPN(DirectConnection conn,
			String user,String epnID,
			String temperatureSensorID, String humiditySensorID,
			String gatewayID, double hardMinTemperature,
			double hardMaxTemperature, double hardMinHumdity,
			double hardMaxHumdity) throws UnknownHostException;

	/**
	 * registers in the CEP engine a new EPN for monitoring the temperature and
	 * the humidity of a parcel with perishable goods.
	 * 
	 * @param epnID
	 *            the ID of the EPN
	 * @param temperatureSensorID
	 *            the ID of the temperature sensor attached to the package
	 * @param humiditySensorID
	 *            the ID of the container humidity sensor
	 * @param truckID
	 *            the ID of the truck (the gateway ID)
	 * @param hardMinTemperature
	 *            the hard lower temperature value recommended for storing the
	 *            product (it is not recommended to break this value)
	 * @param hardMaxTemperature
	 *            the upper temperature value recommended for storing the
	 *            product (it is not recommended to break this value)
	 * @param softMinTemperature
	 *            the soft lower temperature value recommended for storing the
	 *            product (the temperature of the product can be lower than this
	 *            value but in a period of time shorter than
	 *            softTemperatureMonitoringPeriod)
	 * @param softMaxTemperature
	 *            the soft upper temperature value recommended for storing the
	 *            product (the temperature of the product can be bigger than
	 *            this value but in a period of time shorter than
	 *            softTemperatureMonitoringPeriod)
	 * @param softTemperatureMonitoringPeriod
	 *            the period of time when the temperature of the product can be
	 *            outside the range specified by soft max/min temperature [in seconds]
	 * @param hardMinHumdity
	 *            the lower humidity value recommended for storing the product
	 * @param hardMaxHumdity
	 *            the upper humidity value recommended for storing the product
	 * @throws UnknownHostException 
	 */

	public void addNewPerishableGoodsMonitoringEPN(DirectConnection conn,
			String user,String epnID,
			String temperatureSensorID, String humiditySensorID,
			String truckID, double hardMinTemperature,
			double hardMaxTemperature, double softMinTemperature,
			double softMaxTemperature, int softTemperatureMonitoringPeriod,
			double hardMinHumdity, double hardMaxHumdity) throws UnknownHostException;

	/**
	 * indicates the location of the serialized prediction model. It can be used
	 * for instantiating the prediction models or for changing its parameters.
	 * 
	 * @param pathToSerialisedPredictionModel
	 */
	public void loadTimeSeriesPredictionModel(
			String pathToSerialisedPredictionModel);

	/**
	 * registers in the CEP engine a new EPN for forecasting the temperature of
	 * the parcel using the time series prediction model. This EPN is
	 * responsible for the following actions:
	 * 
	 * 1) Performs the sample attribute selection for the prediction model
	 * (generates the events of type: TruckSensorJoneType);
	 * 
	 * 2) Sends the list of events to the prediction model and requests the
	 * prediction
	 * 
	 * 3) Generates the validation messages
	 * 
	 * 4) Returns a list of all produced epnIDs in descending sequence
	 * 
	 * @param epnID
	 *            the ID of the EPN
	 * @param truckID
	 *            the ID of the truck (the gateway ID)
	 * @param containerTemperatureSensorID1
	 *            the ID of the temperature sensor #1 located in the container
	 * @param containerTemperatureSensorID2
	 *            the ID of the temperature sensor #2 located in the container
	 * @param containerTemperatureSensorID3
	 *            the ID of the temperature sensor #3 located in the container
	 * @param humiditySensorID
	 *            the ID of the humidity sensor located in the container
	 * @param hvacActuatorID
	 *            the ID of the hvac unit located in the container
	 * @param currentClampSensorID
	 *            the ID of the current clamp sensor located in the container
	 * @param externalContainerTemperatureSensorID
	 *            the ID of the temperature sensor located outside the container
	 * @param parcelTemperatureSensorID
	 *            the ID of the temperature sensor attached to the parcel
	 * @param reportingPeriod
	 *            specifies the triggering period of the prediction model (every
	 *            reportingPeriod minutes the prediction model has to be
	 *            triggered to make a prediction)
	 * @param reportingWindowLength
	 *            before the CEP engine triggers a prediction it has to send to
	 *            the prediction model all the TruckSensorJoined events
	 *            registered in the last reportingWindowLength minutes
	 *            and has to be bigger than reportingPeriod
	 * @throws UnknownHostException 
	 */
	public void addNewTimeSeriesPredictionEPN(DirectConnection conn, String user,
			String epnID, String truckID, 
			String containerTemperatureSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String humiditySensorID,
			String hvacActuatorID, String currentClampSensorID,
			String externalContainerTemperatureSensorID,
			String parcelTemperatureSensorID, int reportingPeriod,
			int reportingWindowLength) throws UnknownHostException ;

	/**
	 * 
	 * @param epnID
	 *            the ID of the EPN
	 * @param truckID
	 *            the ID of the truck (the gateway ID)
	 * @param containerTemperatureSensorID1
	 *            the ID of the temperature sensor #1 located in the container
	 * @param containerTemperatureSensorID2
	 *            the ID of the temperature sensor #2 located in the container
	 * @param containerTemperatureSensorID3
	 *            the ID of the temperature sensor #3 located in the container
	 * @param hvacActuatorID
	 *            the ID of the hvac unit located in the container
	 * @param maxTemperature
	 *            when the temperature inside container is bigger than the
	 *            maxTemperature, the AC unit target value has to be set to
	 *            targetvalueForMaxTemperature
	 * @param minTemperature
	 *            when the temperature inside container is smaller than the
	 *            minTemperature, the AC unit target value has to be set to
	 *            targetvalueForMinTemperature
	 * @param targetvalueForMaxTemperature
	 *            check maxTemperature
	 * @param targetvalueForMinTemperature
	 *            check minTemperature
	 */

	public void addNewACUnitControlEPN(String epnID, String truckID,
			String containerTemperaturRaeSensorID1,
			String containerTemperatureSensorID2,
			String containerTemperatureSensorID3, String hvacActuatorID,
			double maxTemperature, double minTemperature,
			double targetvalueForMaxTemperature,
			double targetvalueForMinTemperature);

	/**
	 * deletes a registerd epns
	 *
	 * @param listEpn
	 * 			  list from generated epnIDs returned by 
	 * 			  addNewTimeSeriesPredictionEPN method
	 **/
	public void deleteEpn(DirectConnection conn,
			String user, String epnID);

}
