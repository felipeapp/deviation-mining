/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class VariationTimeRangeStatisticsTest {

	/**
	 * Test method for {@link br.ufrn.ase.analysis.VariationTimeRangeStatistics#calculateVariationTimeRange(java.util.Map)}.
	 */
	@Test
	public void testCalculateVariationTimeRange() {
		
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		
		map.put("1.jsp", Arrays.asList( new Double[]{2.0d, 1.0d, 3.0d}));
		map.put("2.jsp", Arrays.asList( new Double[]{0.10d, 10.15d, 3.1d}));
		map.put("3.jsp", Arrays.asList( new Double[]{20.0d, 10.0d, 30.0d}));
		
		Map<String, Double> _return = new VariationTimeRangeStatistics().calculateVariationTimeRange(map);
		
		Assert.assertEquals(2.0d, new Double(_return.get("1.jsp")), 0.0d);
		Assert.assertEquals(10.05d, new Double(_return.get("2.jsp")), 0.0d);
		Assert.assertEquals(20.0d, new Double(_return.get("3.jsp")), 0.0d);
		
	}

}
