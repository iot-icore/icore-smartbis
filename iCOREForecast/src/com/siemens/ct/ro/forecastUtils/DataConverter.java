package com.siemens.ct.ro.forecastUtils;

import java.math.BigDecimal;

public class DataConverter {
	public static BigDecimal fromStringToBigDecimal(String value)
	{
		if (value == null || value.length() == 0 || "null".equalsIgnoreCase(value))
		{
			return null;
		}
		else
		{
			return new BigDecimal(Double.valueOf(value));
		}
	}
}
