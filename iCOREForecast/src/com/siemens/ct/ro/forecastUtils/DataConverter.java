package com.siemens.ct.ro.forecastUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.softwareag.transportation.CEPevents.TruckSensorJointEvent;

/**
 * Helper methods for data conversions
 * 
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class DataConverter {

	/**
	 * Returns a (possible null) BigDecimal object based on the passed argument
	 * 
	 * @param value
	 *            a String object containing the value
	 * @return the BigDecimal object obtained from the given argument
	 */
	public static BigDecimal fromStringToBigDecimal(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return new BigDecimal(Double.valueOf(value));
		}
	}

	/**
	 * Returns a (possible null) Double object based on the passed value
	 * 
	 * @param value
	 *            the String object to be parsed
	 * @return a Double instance
	 */
	public static Double fromStringToDouble(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return Double.valueOf(value);
		}
	}

	/**
	 * Returns a double value based on the passed String; if the argument cannot
	 * be parsed, then Constants.NULL_OR_MISSING_VALUE
	 * 
	 * @param value
	 *            the String to be parsed
	 * @return parsed value as double, or Constants.NULL_OR_MISSING_VALUE if the
	 *         given argument cannot be parsed
	 */
	public static double fromStringTo_double(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return Constants.NULL_OR_MISSING_VALUE;
		} else {
			return Double.valueOf(value).doubleValue();
		}
	}

	/**
	 * returns a (possible null) Boolean value based on the passed argument
	 * 
	 * @param value
	 *            a String object to be parsed
	 * @return the Boolean value resulted from parsing the argument
	 */
	public static Boolean fromStringToBoolean(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return value.trim() == "0" ? Boolean.FALSE : Boolean.TRUE;
		}
	}

	/**
	 * Parses a String and returns a (possible null) Long value
	 * 
	 * @param value
	 *            he String object to be parsed
	 * @return a Long value
	 */
	public static long fromStringToLong(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			throw new IllegalArgumentException(
					"Converting from string to long: illegal value");
		} else {
			return Long.parseLong(value);
		}
	}

	/**
	 * Parses the passed String value and returns a (possible null)
	 * XMLGregorianCalendar object
	 * 
	 * @param value
	 *            the String to be parsed
	 * @return an XMLGregorianCalendar value
	 */
	public static XMLGregorianCalendar fromStringToXMLGregorianCalendar(
			String value) {
		if (value == null) {
			return null;
		}
		value = value.replace("\"", "");
		XMLGregorianCalendar result = null;
		Date date;
		SimpleDateFormat simpleDateFormat;
		GregorianCalendar gregorianCalendar;
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = simpleDateFormat.parse(value);
			gregorianCalendar = (GregorianCalendar) GregorianCalendar
					.getInstance();
			gregorianCalendar.setTime(date);
			result = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					gregorianCalendar);
			return result;
		} catch (ParseException | DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, TruckSensorJointEvent> fromListToHash(
			List<TruckSensorJointEvent> list) {
		HashMap<String, TruckSensorJointEvent> result = new HashMap<String, TruckSensorJointEvent>();
		int i = 0;
		for (TruckSensorJointEvent item : list) {
			result.put("" + i, item);
			++i;
		}
		return result;
	}
}
