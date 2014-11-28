package com.siemens.logisticclient.webdata;

/**
 * @author Lucian Sasu
 *
 */

import java.util.LinkedList;
import java.util.List;

public class SingleSeries {
	private String color;
	private List<Point> data = new LinkedList<Point>();;
	private String name;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<Point> getData() {
		return data;
	}
	public void setData(List<Point> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addPoint(Point p)
	{
		data.add(p);
	}
}