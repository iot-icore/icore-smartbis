/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.siemens.ct.ro.forecast.ICOREForecaster;

/**
 * @author Lucian Sasu
 * 
 */
public class ICORESerializer {

	public static void serialize(ICOREForecaster object, String filename)
			throws IOException {
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(object);
		out.close();
		fileOut.close();
	}

	public static ICOREForecaster deserialize(String filename) {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		ICOREForecaster icoreForecaster = null;
		try {
			fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			icoreForecaster = (ICOREForecaster) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("ICOREForecaster class not found");
			c.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
				}
			}
		}
		return icoreForecaster;
	}
}
