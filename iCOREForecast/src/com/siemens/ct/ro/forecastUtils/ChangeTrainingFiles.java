/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Lucian Sasu
 * Converts the datetime stamp from "yyyy-MM-dd hh:mm:ss" to long (timestamp)
 */
public class ChangeTrainingFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputDataFile = "data/logs/server.log.unique.time_changed.2014-10-17_old.csv";
		String outputDataFile = "data/logs/server.log.unique.time_changed.2014-10-17.csv";
		
		try {
			changeData(inputDataFile, outputDataFile);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void changeData(String inputDataFile, String outputDataFile) throws IOException, ParseException {
		try(
				Scanner inFile = new Scanner(new FileReader(inputDataFile));
				BufferedWriter out = new BufferedWriter(new FileWriter(outputDataFile))
			)
		{
			String header = inFile.nextLine();//read header
			out.write(header + "\n");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			while(inFile.hasNextLine())
			{
				String inputLine = inFile.nextLine();
				if (inputLine == null || inputLine.trim().length() == 0)
				{
					continue;
				}
				inputLine = inputLine.trim();
				String[] tokens = inputLine.split(",");
				String dateTimeToken = tokens[tokens.length-1];
				if (dateTimeToken.startsWith("\""))
				{
					dateTimeToken = dateTimeToken.substring(1, dateTimeToken.length()-1);
				}
				Date date = df.parse(dateTimeToken);
				String outputLine = join(tokens, date);
				out.write(outputLine + "\n");
			}
		}
	}

	private static String join(String[] tokens, Date date) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<tokens.length - 1; i++)
		{
			sb.append(tokens[i] + ",");
		}
		sb.append(date.getTime());
		return sb.toString();
	}

}
