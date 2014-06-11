/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;


/**
 * @author Lucian Sasu
 *
 */
public class Measurements extends ArrayList<MeasurementData>{

	private static final long serialVersionUID = -7083769849546002164L;
	
	public Measurements()
	{
		super();
	}
	
	public Measurements(int initialCapacity)
	{
		super(initialCapacity);
	}
}
