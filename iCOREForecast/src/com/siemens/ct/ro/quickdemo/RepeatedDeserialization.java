/**
 * 
 */
package com.siemens.ct.ro.quickdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.siemens.ct.ro.forecast.ICOREForecaster;
import com.siemens.ct.ro.forecastUtils.ICORESerializer;

/**
 * Demo for repeated deserialization, used for memory profiling
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * 
 */
public class RepeatedDeserialization {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("How many objects to deserialize?");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		List<ICOREForecaster> list = new ArrayList<ICOREForecaster>(n);
		for(int i=0; i<n; i++)
		{
			list.add(ICORESerializer.deserialize("serialized/icoreForecaster.ser"));
			if (list.size() % 10 == 0)
			{
				System.out.println("list length: " + list.size());
				System.out.println("press any key");
				System.in.read();
			}
		}
		scanner.close();
	}

}
