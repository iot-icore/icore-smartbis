/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucian Sasu
 *
 */
public class GenericDataSplitter<T> {
	
	public List<List<T>> splitData(List<T> data, double...percents)
	{
		if (percents.length < 2)
		{
			throw new RuntimeException("Must have at least 2 percentage values");
		}
		percents = normalizePercents(percents);
		double[] fullPercents = getFullPercents(percents);
		List<List<T>> result = getSplittedData(data, fullPercents);
		return result;
	}
	
	private List<List<T>> getSplittedData(List<T> data, double[] fullPercents) {
		List<List<T>> result = new ArrayList<List<T>>(fullPercents.length-1);
		for(int i=0; i<fullPercents.length - 1; i++)
		{
			result.add( getSplit(data, fullPercents[i], fullPercents[i+1]) );
		}
		return result;
	}

	private List<T> getSplit(List<T> data,
			double fromIncluding, double upToExcluding) {
		int iMin = (int)(data.size() * fromIncluding);
		int iMax = (int)(data.size() * upToExcluding);
		
		List<T> result = new ArrayList<T>(iMax-iMin);
		for(int i=iMin; i<iMax; i++)
		{
			result.add(data.get(i));
		}
		return result;
	}

	private static double[] getFullPercents(double[] percents) {
		double[] result = new double[percents.length + 1];
		double sum = 0.0;
		for(int i=0; i<percents.length; i++)
		{
			result[i] = sum;
			sum += percents[i];
		}
		result[result.length-1] = sum;
		return result;
	}

	private static double[] normalizePercents(double[] percents) {
		double sum = 0.0;
		for(double d : percents)
		{
			sum += d;
		}
		double[] result = new double[percents.length];
		for(int i=0; i<result.length; i++)
		{
			result[i] = percents[i] / sum;
		}
		return result;
	}
}
