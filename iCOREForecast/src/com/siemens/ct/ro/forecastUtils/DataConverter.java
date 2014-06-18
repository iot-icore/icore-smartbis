package com.siemens.ct.ro.forecastUtils;

import java.math.BigDecimal;

public class DataConverter {
	public static BigDecimal fromStringToBigDecimal(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return new BigDecimal(Double.valueOf(value));
		}
	}

	public static Double fromStringToDouble(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return Double.valueOf(value);
		}
	}
	
	public static double fromStringTo_double(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return Constants.IMPOSSIBLE_VALUE;
		} else {
			return Double.valueOf(value).doubleValue();
		}
	}

	public static Boolean fromStringToBoolean(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			return null;
		} else {
			return value.trim() == "0" ? Boolean.FALSE : Boolean.TRUE;
		}
	}

	public static long fromStringToLong(String value) {
		if (value == null || value.length() == 0
				|| "null".equalsIgnoreCase(value)) {
			throw new IllegalArgumentException("Converting from string to long: illegal value");
		} else {
			return Long.parseLong(value);
		}
	}
}
