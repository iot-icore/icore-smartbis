package com.siemens.logisticclient.webdata;

public class WebColorsCollection {
	private static final String[] webColors = {"#65A1CC", "#CC9065", "#1ADB1E", "#E1F27E", "#E1F27E", "#7D13E8", "#E813C1", "#B3224B", "#0BE382"};
	
	public static final String RED = "#FF0000";
	
	private int currentIndex = -1;
	
	public String next()
	{
		currentIndex = (currentIndex + 1) % webColors.length;
		return webColors[currentIndex];
	}
}
