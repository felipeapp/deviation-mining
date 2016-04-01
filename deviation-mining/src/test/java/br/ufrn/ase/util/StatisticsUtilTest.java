/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import org.junit.Assert;
import org.junit.Test;

import br.ufrn.ase.util.StatisticsUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class StatisticsUtilTest {

	double[] s1 = { 1.83, 0.50, 1.62, 2.48, 1.68, 1.88, 1.55, 3.06, 1.30 };
	double[] s2 = { 0.878, 0.647, 0.598, 2.05, 1.06, 1.29, 1.06, 3.14, 1.29 };
	
	/**
	 * Test method for {@link br.ufrn.ase.util.StatisticsUtil#tTest(double[], double[])}.
	 */
	@Test
	public void testTTest() {
		Assert.assertEquals( 0.24588283853317128, StatisticsUtil.tTest(s1, s2), 0.0d);
	}

	/**
	 * Test method for {@link br.ufrn.ase.util.StatisticsUtil#mannWhitneyUTest(double[], double[])}.
	 */
	@Test
	public void testMannWhitneyUTest() {
		Assert.assertEquals( 0.12227667721471358, StatisticsUtil.mannWhitneyUTest(s1, s2), 0.0d);
	}

	/**
	 * Test method for {@link br.ufrn.ase.util.StatisticsUtil#wilcoxonSignedRankTest(double[], double[])}.
	 */
	@Test
	public void testWilcoxonSignedRankTest() {
		Assert.assertEquals( 0.0390625, StatisticsUtil.wilcoxonSignedRankTest(s1, s2), 0.0d); 
	}

	/**
	 * Test method for {@link br.ufrn.ase.util.StatisticsUtil#mean(double[])}.
	 */
	@Test
	public void testMean() {
		Assert.assertEquals( 1.7666666666666666, StatisticsUtil.mean(s1), 0.0d);
	}

	/**
	 * Test method for {@link br.ufrn.ase.util.StatisticsUtil#meanDifference(double[], double[], boolean)}.
	 */
	@Test
	public void testMeanDifference() {
		Assert.assertEquals( 0.43188888888888893, StatisticsUtil.meanDifference(s1, s2, true), 0.0d);
	}

}
