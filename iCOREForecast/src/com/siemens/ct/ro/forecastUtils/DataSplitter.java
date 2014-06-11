/**
 * 
 */
package com.siemens.ct.ro.forecastUtils;

/**
 * @author Lucian Sasu
 *
 */
public class DataSplitter {

	public static Measurements[] splitMeasurements(
			Measurements allMeasurements, double... percents) {
		if (percents.length < 2)
		{
			throw new RuntimeException("Must have at least 2 percentage values");
		}
		percents = normalizePercents(percents);
		double[] fullPercents = getFullPercents(percents);
		Measurements[] result = getSplittedData(allMeasurements, fullPercents);
		return result;
	}

	private static Measurements[] getSplittedData(Measurements allMeasurements, double[] fullPercents) {
		Measurements[] result = new Measurements[fullPercents.length-1];
		for(int i=0; i<result.length; i++)
		{
			result[i] = getSplit(allMeasurements, fullPercents[i], fullPercents[i+1]);
		}
		return result;
	}

	private static Measurements getSplit(Measurements allMeasurements,
			double fromIncluding, double upToExcluding) {
		int iMin = (int)(allMeasurements.size() * fromIncluding);
		int iMax = (int)(allMeasurements.size() * upToExcluding);
		
		Measurements result = new Measurements();
		for(int i=iMin; i<iMax; i++)
		{
			result.add(allMeasurements.get(i));
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
