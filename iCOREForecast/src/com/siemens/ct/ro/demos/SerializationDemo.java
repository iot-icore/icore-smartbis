/**
 * 
 */
package com.siemens.ct.ro.demos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.siemens.ct.ro.forecast.ICOREForecaster;

/**
 * @author Lucian Sasu
 * Serialization/deserialization of an object with file and memory stream 
 */
public class SerializationDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		serializeForecasterWithFile();
		deserializeFrecasterWithFile();
		ByteArrayOutputStream memoryStream = serializeForecasterWithMemoryStream();
		deserializeForecasterFromMemoryStream(memoryStream);
	}

	private static void deserializeForecasterFromMemoryStream(
			ByteArrayOutputStream memoryStream) {
		
		ICOREForecaster icoreForecaster = null;
	      try
	      {
	    	  ByteArrayInputStream byteArrayInputStream = new   ByteArrayInputStream(memoryStream.toByteArray());
	    	  ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
	    	  icoreForecaster = (ICOREForecaster) in.readObject();
	    	  in.close();
	    	  System.out.println(icoreForecaster.getFieldNamesToBePredicted());
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("ICOREForecaster class not found");
	         c.printStackTrace();
	         return;
	      }
	}

	private static ByteArrayOutputStream serializeForecasterWithMemoryStream() throws Exception {
		ICOREForecaster icoreForecaster = new ICOREForecaster("80:00:00:00:01:01", null,  "aaaaa", "aaaaaaaa");
		ByteArrayOutputStream memory = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(memory);
		out.writeObject(icoreForecaster);
		out.close();
		memory.close();
		return memory;
	}

	private static void deserializeFrecasterWithFile() {
		ICOREForecaster icoreForecaster;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("serialized/icoreForecaster.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         icoreForecaster = (ICOREForecaster) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("ICOREForecaster class not found");
	         c.printStackTrace();
	         return;
	      }
	}

	private static void serializeForecasterWithFile() throws Exception {
		//ICOREForecaster icoreForecaster = new ICOREForecaster(null,  "aaaaa", "aaaaaaaa");
		ICOREForecaster icoreForecaster = new ICOREForecaster("80:00:00:00:01:01");
		FileOutputStream fileOut = new FileOutputStream("serialized/icoreForecaster.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(icoreForecaster);
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in serialized/icoreForecaster.ser"); 
	}

}
