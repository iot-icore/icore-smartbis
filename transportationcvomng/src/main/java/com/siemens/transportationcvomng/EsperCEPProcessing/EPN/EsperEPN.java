package com.siemens.transportationcvomng.EsperCEPProcessing.EPN;

/**
 * Base class for transportation sim EPNs. It provides the mechanism of storing the 
 * list of statements added to the CEP engine. By calling the method remove EPN all 
 * the statements will be removed from CEP engine.
 * 
 * @author dan.puiu
 */

import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPStatement;

public abstract class EsperEPN {

	private List<EPStatement> epnStatements = new ArrayList<EPStatement>();

	/**
	 * Method used for creating the EPN
	 */
	public abstract void addEPN();

	/**
	 * Every time a CEP statement is created by the method addEPN it has to be
	 * registered using this method,
	 * 
	 * @param statement
	 */

	void registerStatement(EPStatement statement) {
		epnStatements.add(statement);
	}

	/**
	 * Method used for removing the EPN. All the registered statements will be
	 * undeployed from the CEP engine
	 */
	public void removeEPN() {

		for (EPStatement statement : epnStatements) {
			statement.destroy();
		}

	}

}
