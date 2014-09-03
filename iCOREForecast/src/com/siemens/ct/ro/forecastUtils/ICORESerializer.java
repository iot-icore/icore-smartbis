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
 * Class to perform (de)serialization of an ICOREForecaster object
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class ICORESerializer {

	/**
	 * Serializes an ICOREForecaster object into a file
	 * @param object the ICOREForecaster instance to be serialized
	 * @param filename the path to the binary file holoding the serialized object
	 * @throws IOException I/O exception
	 */
	public static void serialize(ICOREForecaster object, String filename)
			throws IOException {
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(object);
		out.close();
		fileOut.close();
	}

	/**
	 * Deserializes from a given file
	 * @param filename the path to the binary file containing a serialized object
	 * @return the deserialized object; null if the deserialization fails
	 */
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
