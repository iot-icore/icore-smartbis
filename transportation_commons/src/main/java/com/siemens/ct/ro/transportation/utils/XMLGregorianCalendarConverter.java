/**
 * Utility class used for converting XMLGregorian Calendar in other formats and reverse
 * 
 * @author dan.puiu
 */

package com.siemens.ct.ro.transportation.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarConverter {
	
	public static  XMLGregorianCalendar convertTimestampToXMLGregorialCalendar(long timestamp){
		Date date = new Date(timestamp);
				
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		return xmlGregorianCalendar;
	}

	public static long convertXMLGregorialCalendarToTimestampt(XMLGregorianCalendar date){
		return date.toGregorianCalendar().getTimeInMillis();
	}
}
