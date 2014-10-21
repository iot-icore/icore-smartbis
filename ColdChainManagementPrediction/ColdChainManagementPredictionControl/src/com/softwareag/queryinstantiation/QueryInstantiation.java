
/*
 * Copyright 2011 by
 *
 * Software AG, Uhlandstrasse 12, D-64297 Darmstadt, GERMANY
 *
 * All rights reserved
 *
 * This software is the confidential and proprietary
 * information of Software AG ('Confidential Information').
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Software AG or its distributors.
 */

package com.softwareag.queryinstantiation;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.softwareag.wep.api.client.DirectConnection;
import com.softwareag.wep.api.client.DirectConnectionProvider;
import com.softwareag.wep.api.client.DirectDeploymentConnection;
import com.softwareag.wep.api.client.QueryDefinition;
import com.softwareag.wep.api.client.TcpIpEventServerAddress;

public class QueryInstantiation {

	private static final String EVENT_SERVER_HOST = "localhost";
	private static final String COLD_CHAIN_MANAGEMENT = "ColdChainManagementPrediction";
	
	/**
	 * 
	 * @param list   	Unique Identifier for the started query. Necessary for stopping or adopting parameter like outputChannel
	 * @param queryContent  transformed query {@link prepareCVO_Template(String, ArrayList<String>)}
	 * @param outputChannel UM topic_name the started query will published the result
	 * @param outputSchema  XML Schema of the query output 
	 * @throws UnknownHostException 
	 */
	
	public void cancelQuery(List<String> list) throws UnknownHostException{
		
		DirectConnection conn = DirectConnectionProvider.connect(
				COLD_CHAIN_MANAGEMENT, "", new TcpIpEventServerAddress(
						EVENT_SERVER_HOST, 7867));
		
		DirectDeploymentConnection deployConn = conn
				.connectDeployment(COLD_CHAIN_MANAGEMENT);
		
		Set<String> queryNames = deployConn.getQueryNames();
		for(int i=0; i<list.size(); i++){
			if (queryNames.contains(COLD_CHAIN_MANAGEMENT + "." + list.get(i))) {
			// ---------------------------------------------------------------------------------------
			// Un-deploy query
			// ---------------------------------------------------------------------------------------
			deployConn.removeQuery(COLD_CHAIN_MANAGEMENT + "." + list.get(i));	
		}
			//deployConn.close();
			//conn.close();
		System.out.println("Registered queries: " + deployConn.getQueryNames());
		System.out.println("Count registered queries: " + deployConn.getQueryNames().size());
		}
		
	}
	
	public void initAndStart(String queryID, String queryContent, String outputChannel,
			String outputSchema) throws UnknownHostException {
		DirectConnection conn = DirectConnectionProvider.connect(
				COLD_CHAIN_MANAGEMENT, "", new TcpIpEventServerAddress(
						EVENT_SERVER_HOST, 7867));

		// ---------------------------------------------------------------------------------------
		// Create connection for deployment operations on top of already
		// deployed
		// "HelloCEPWorldDemo" demo project
		// ---------------------------------------------------------------------------------------
		DirectDeploymentConnection deployConn = conn
				.connectDeployment(COLD_CHAIN_MANAGEMENT);
		Set<String> queryNames = deployConn.getQueryNames();
		if (queryNames.contains(COLD_CHAIN_MANAGEMENT + "." + queryID)) {
			// ---------------------------------------------------------------------------------------
			// Un-deploy query
			// ---------------------------------------------------------------------------------------
			deployConn.removeQuery(COLD_CHAIN_MANAGEMENT + "." + queryID);	
		}

		QueryDefinition queryDefinition = defineDeployQuery(deployConn,
				queryID, queryContent, outputChannel, outputSchema);

		if(outputChannel.contains("iCore::Temperature::Predictions::")){
			outputChannel = outputChannel.replace("iCore::Temperature::Predictions::", "");
		}
		

		// The following list of deployed queries no longer contains the
		// dynamically deployed query
		System.out.println("Registered queries: " + deployConn.getQueryNames());
		System.out.println("Count registered queries: " + deployConn.getQueryNames().size());

		// ---------------------------------------------------------------------------------------
		// Close connections
		// ---------------------------------------------------------------------------------------
		deployConn.close();
		conn.close();
	}

	public QueryDefinition defineDeployQuery(
			DirectDeploymentConnection deployConn, String queryID,
			String queryContent, String outputChannel, String outputSchema) {
		// ---------------------------------------------------------------------------------------
		// Define and dynamically deploy additional query
		// ---------------------------------------------------------------------------------------
		QueryDefinition queryDefinition = new QueryDefinition(queryID,
				queryContent);
		queryDefinition.setOutputEventType(outputSchema);
		queryDefinition.setOutputConnectionAlias("EventBus");
		queryDefinition.setOutputChannel(outputChannel);
		queryDefinition.setHeartBeatProcessing(false);
		deployConn.addQuery(queryDefinition);

		// Now the query is deployed and will process input events

		// The following list of deployed queries contains the dynamically
		// deployed query in addition
		// to the deployed queries of the "HelloCEPWorldInput" base project
		System.out.println("Registered queries: " + deployConn.getQueryNames());
		return queryDefinition;
	}

	
	/**
	 * 
	 * @param queryTemplate  query template with parameter starting with $00 as first substitution parameter
	 * @param queryValues replacing parameter from $00 up to $99
	 * @return queryContent transformed query with substituted parameters
	 */
	public String prepareCVO_Template(String queryTemplate, ArrayList<String> queryValues){
		
		// Placeholder in the query template(e.g. $00, $01) are replaced with the queryValues from the list.
		
		String queryContent = queryTemplate;
		for(int i = 0; i < queryValues.size(); i++){
			if( i < 10){
				queryContent = queryContent.replace("$0"+ i, queryValues.get(i));
			}
			else{
				queryContent = queryContent.replace("$" + i, queryValues.get(i));
			}
		}
		System.out.println(queryContent);
		
		// Now you will receive a complete query
		return queryContent;
	}
	
	public void deleteQuery(){
		
	}
}