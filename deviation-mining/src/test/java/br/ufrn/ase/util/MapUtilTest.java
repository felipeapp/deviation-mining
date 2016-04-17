/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class MapUtilTest {

	/**
	 * Test method for {@link br.ufrn.ase.util.MapUtil#sortByValue(java.util.Map)}.
	 */
	@Test
	public void testSortByValue() {
		Map<String, Double> temp = new HashMap<>();
		temp.put("a", 10d);
		temp.put("b", 20d);
		temp.put("c", 50d);
		temp.put("d", 100d);
		temp = MapUtil.sortByValue(temp);
		
		List<Double> c = new ArrayList<>(temp.values());
		Assert.assertEquals((Double) 100d, c.get(0));
		Assert.assertEquals((Double) 50d, c.get(1));
		Assert.assertEquals((Double) 20d, c.get(2));
		Assert.assertEquals((Double) 10d, c.get(3));
	}

	/**
	 * Test method for {@link br.ufrn.ase.util.MapUtil#cutOff(java.util.Map, int)}.
	 */
	@Test
	public void testCutOff() {
		Map<String, Double> temp = new HashMap<>();
		temp.put("a", 10d);
		temp.put("b", 20d);
		temp.put("c", 50d);
		temp.put("d", 100d);
		temp.put("e", 200d);
		temp.put("f", 300d);
		temp.put("g", 400d);
		temp.put("h", 500d);
		temp.put("i", 600d);
		temp.put("j", 700d);
		temp = MapUtil.cutOff(temp, 3);
		
		List<Double> c = new ArrayList<>(temp.values());
		Assert.assertEquals((Double) 700d, c.get(0));
		Assert.assertEquals((Double) 600d, c.get(1));
		Assert.assertEquals((Double) 500d, c.get(2));
	}

}
