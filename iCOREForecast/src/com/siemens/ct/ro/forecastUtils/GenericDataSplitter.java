/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Various splitting methods; useful for split in train/test or trainvalidation/test subsets
 * @author Lucian Sasu, lucian.sasu@siemens.com
 * @version 1.0
 */
public class GenericDataSplitter<T> {
	
	/**
	 * Split a list of items into sublists
	 * @param data the dataset to be splitted
	 * @param percents the percentage of the splitting; the values should sum to 1, but this is actually checked and the values will be normalized if necessary
	 * @return a list of (data subsets)
	 */
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
	
	/**
	 * Helper method; Split a list of items into sublists
	 * @param data data the dataset to be splitted
	 * @param fullPercents; the percents passed here are guaranteed to sum to 1.
	 * @return a list of (data subsets)
	 */
	private List<List<T>> getSplittedData(List<T> data, double[] fullPercents) {
		List<List<T>> result = new ArrayList<List<T>>(fullPercents.length-1);
		for(int i=0; i<fullPercents.length - 1; i++)
		{
			result.add( getSplit(data, fullPercents[i], fullPercents[i+1]) );
		}
		return result;
	}

	/**
	 * Returns a sublist of a list, based on the start and stop-1 index
	 * @param data the list from which to extract a sublist
	 * @param fromIncluding start index
	 * @param upToExcluding stop index (excluding this position)
	 * @return
	 */
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

	/**
	 * Makes sure that the given value sum to 1
	 * @param percents the initial split ratios
	 * @return an array of double containing values between 0 and 1, summing to 1
	 */
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
