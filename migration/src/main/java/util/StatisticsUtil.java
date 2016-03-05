package util;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

public abstract class StatisticsUtil {

	/**
	 * This uses apache libs to calculate the observed significance level, or
	 * p-value, associated with the Student's T-test.
	 * 
	 * @param sample1
	 *            Values for sample one
	 * @param sample2
	 *            Values for sample two
	 * @return P-value associated with the Student's T-test
	 */
	public static double tTest(double[] sample1, double[] sample2) {
		return TestUtils.tTest(sample1, sample2);
	}

	/**
	 * This uses apache libs to calculate the asymptotic observed significance
	 * level, or p-value, associated with a Mann-Whitney U-Test, also called
	 * Wilcoxon rank-sum test.
	 * 
	 * @param sample1
	 *            Values for sample one
	 * @param sample2
	 *            Values for sample two
	 * @return T p-value associated with a Mann-Whitney U-Test
	 */
	public static double mannWhitneyUTest(double[] sample1, double[] sample2) {
		return new MannWhitneyUTest().mannWhitneyUTest(sample1, sample2);
	}

	/**
	 * This uses apache libs to calculate the asymptotic observed significance
	 * level, or p-value, associated with a Mann-Whitney U-Test, also called
	 * Wilcoxon rank-sum test.
	 * 
	 * @param sample1
	 *            Values for sample one
	 * @param sample2
	 *            Values for sample two
	 * @return T p-value associated with a Mann-Whitney U-Test
	 */
	public static double wilcoxonSignedRankTest(double[] sample1, double[] sample2) {
		return new WilcoxonSignedRankTest().wilcoxonSignedRankTest(sample1, sample2, true);
	}

	/**
	 * This uses apache libs to calculate the arithmetic mean of the entries in
	 * the input array.
	 * 
	 * @param values
	 *            Entries
	 * @return The arithmetic mean of the entries
	 */
	public static double mean(double[] values) {
		return StatUtils.mean(values);
	}

	public static void main(String[] args) {

		double[] s1 = { 1.83, 0.50, 1.62, 2.48, 1.68, 1.88, 1.55, 3.06, 1.30 };
		double[] s2 = { 0.878, 0.647, 0.598, 2.05, 1.06, 1.29, 1.06, 3.14, 1.29 };

		System.out.println("Mean: " + mean(s1));
		System.out.println("Wilconx Signed: " + wilcoxonSignedRankTest(s1, s2));
		System.out.println("T-Test: " + tTest(s1, s2));
		System.out.println("U-Test: " + mannWhitneyUTest(s1, s2));

	}

}
