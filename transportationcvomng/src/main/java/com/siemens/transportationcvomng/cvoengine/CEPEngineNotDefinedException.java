package com.siemens.transportationcvomng.cvoengine;

public class CEPEngineNotDefinedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public CEPEngineNotDefinedException(String message)
	{
		super(message);
	}
	
	public CEPEngineNotDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

}
