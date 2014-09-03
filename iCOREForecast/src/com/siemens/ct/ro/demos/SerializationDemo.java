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
 * Serialization/deserialization demo code for an ICOREForecaster object with file and memory stream 
 * @author Lucian Sasu, lucian.sasu@siemens.com
 */
public class SerializationDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		serializeForecasterInFile();
		deserializeForecasterWithFile();
		ByteArrayOutputStream memoryStream = serializeForecasterIntoMemoryStream();
		deserializeForecasterFromMemoryStream(memoryStream);
	}

	/**
	 * Deserialization demo, using a memory stream object
	 * @param memoryStream the object holding serialized form of an ICOREForecaster instance
	 */
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

	/**
	 * Demo for serializing an ICOREForecaster instance into a memory stream object
	 * @return the memory stream instance
	 * @throws Exception if serialization exceptions occurs
	 */
	private static ByteArrayOutputStream serializeForecasterIntoMemoryStream() throws Exception{
		ICOREForecaster icoreForecaster = new ICOREForecaster(null,  "aaaaa", "aaaaaaaa");
		ByteArrayOutputStream memory = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(memory);
		out.writeObject(icoreForecaster);
		out.close();
		memory.close();
		return memory;
	}

	/**
	 * Demo for deserialization of an ICOREForecaster instance from a binary file
	 */
	private static void deserializeForecasterWithFile() {
		@SuppressWarnings("unused")
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

	/**
	 * Demo fro serializing an ICOREForecaster instance into a binary file
	 * @throws Exception if serialization exceptions occur
	 */
	private static void serializeForecasterInFile() throws Exception {
		ICOREForecaster icoreForecaster = new ICOREForecaster();
		FileOutputStream fileOut = new FileOutputStream("serialized/icoreForecaster.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(icoreForecaster);
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in serialized/icoreForecaster.ser"); 
	}

}
