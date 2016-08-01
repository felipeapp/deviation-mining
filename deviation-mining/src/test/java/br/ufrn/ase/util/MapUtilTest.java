/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class MapUtilTest {
	
	@Before
	public void setup(){
		File file = new File(MapUtil.TEMP_FILE_PATH);
		file.delete();
	}

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
	
	/**
	 * Test method for {@link br.ufrn.ase.util.MapUtil#crossMaps(java.util.Map, java.util.Map, int)}.
	 */
	@Test
	public void testCrossMap(){
		final int QTD = 1;
		Map<String, Double> firstMap = new HashMap<>();
		Map<String, Double> secondMap = new HashMap<>();
		
		firstMap.put("scenario1", 10d);
		firstMap.put("scenario2", 5d);
		
		secondMap.put("scenario0", 200000d);
		secondMap.put("scenario1", 500000d);
		secondMap.put("scenario2", 100000d);
		secondMap.put("scenario3", 90000d);
		secondMap.put("scenario4", 80000d);
		secondMap.put("scenario5", 70000d);
		secondMap.put("scenario6", 60000d);
		secondMap.put("scenario7", 50000d);
		secondMap.put("scenario8", 40000d);
		secondMap.put("scenario9", 30000d);
		secondMap.put("scenario10", 20000d);
		secondMap.put("scenario11", 10000d); // out of qtd
		secondMap.put("scenario12", 9000d);
		secondMap.put("scenario13", 8000d);
		secondMap.put("scenario14", 7000d);
		secondMap.put("scenario15", 6000d);
		secondMap.put("scenario16", 5000d);
		secondMap.put("scenario17", 4000d);
		secondMap.put("scenario18", 3000d);
		secondMap.put("scenario19", 2000d);
		secondMap.put("scenario20", 1000d);
		
		Map<String, Double> temp = MapUtil.crossMaps(firstMap, secondMap, QTD);
		
		String firstKey = new ArrayList<>(temp.keySet()).get(0);
		Assert.assertTrue(firstKey.equals("scenario1"));
		Assert.assertEquals((Double) 10d, temp.get(firstKey));
		
	}
	
	/**
	 * Test method for {@link br.ufrn.ase.util.MapUtil#crossMaps(java.util.Map, java.util.Map, int)}.
	 */
	@Test
	public void testCrossMap2(){
		final int QTD = 1;
		Map<String, Double> firstMap = new HashMap<>();
		Map<String, Double> secondMap = new HashMap<>();
		
		firstMap.put("scenario1", 10d);
		firstMap.put("scenario2", 5d);
		
		secondMap.put("scenario0", 200000d);
		secondMap.put("scenario2", 100000d);
		secondMap.put("scenario3", 90000d);
		secondMap.put("scenario4", 80000d);
		secondMap.put("scenario5", 70000d);
		secondMap.put("scenario6", 60000d);
		secondMap.put("scenario7", 50000d);
		secondMap.put("scenario8", 40000d);
		secondMap.put("scenario9", 30000d);
		secondMap.put("scenario10", 20000d);
		secondMap.put("scenario11", 10000d); // out of qtd
		secondMap.put("scenario12", 9000d);
		secondMap.put("scenario13", 8000d);
		secondMap.put("scenario14", 7000d);
		secondMap.put("scenario15", 6000d);
		secondMap.put("scenario16", 5000d);
		secondMap.put("scenario17", 4000d);
		secondMap.put("scenario18", 3000d);
		secondMap.put("scenario19", 2000d);
		secondMap.put("scenario20", 1000d);
		secondMap.put("scenario1", 500d);
		
		Map<String, Double> temp = MapUtil.crossMaps(firstMap, secondMap, QTD);
		
		String firstKey = new ArrayList<>(temp.keySet()).get(0);
		Assert.assertTrue(firstKey.equals("scenario2"));
		Assert.assertEquals((Double) 5d, temp.get(firstKey));
		
	}
	
	/**
	 * Test method for {@link br.ufrn.ase.util.MapUtil#crossMaps(java.util.Map, java.util.Map, int)}.
	 */
	@Test
	public void testCrossMap3(){
		final int QTD = 1;
		Map<String, Double> firstMap = new HashMap<>();
		Map<String, Double> secondMap = new HashMap<>();
		
		firstMap.put("scenario1", 10d);
		firstMap.put("scenario2", 5d);
		
		secondMap.put("scenario0", 200000d);
		secondMap.put("scenario1", 500d);
		secondMap.put("scenario2", 100000d);
		secondMap.put("scenario3", 90000d);
		secondMap.put("scenario4", 80000d);
		secondMap.put("scenario5", 70000d);
		secondMap.put("scenario6", 60000d);
		secondMap.put("scenario7", 50000d);
		secondMap.put("scenario8", 40000d);
		secondMap.put("scenario9", 30000d);
		secondMap.put("scenario10", 20000d);
		secondMap.put("scenario11", 10000d); // out of qtd
		secondMap.put("scenario12", 9000d);
		secondMap.put("scenario13", 8000d);
		secondMap.put("scenario14", 7000d);
		secondMap.put("scenario15", 6000d);
		secondMap.put("scenario16", 5000d);
		secondMap.put("scenario17", 4000d);
		secondMap.put("scenario18", 3000d);
		secondMap.put("scenario19", 2000d);
		secondMap.put("scenario20", 1000d);
		
		
		Map<String, Double> temp = MapUtil.crossMaps(firstMap, secondMap, QTD);
		
		String firstKey = new ArrayList<>(temp.keySet()).get(0);
		Assert.assertTrue(firstKey.equals("scenario2"));
		Assert.assertEquals((Double) 5d, temp.get(firstKey));
		
	}
	
	
	
	/**
	 * Teste Append information on propertie file
	 * 
	 * Test method for {@link br.ufrn.ase.util.MapUtil#writePropertie(String , List<Double>, String )}.
	 * Test method for {@link br.ufrn.ase.util.MapUtil#readPropertie(String , String)}.
	 */
	@Test
	public void testAppendInformationOnPropertieFile() {
		List<Double> list = new ArrayList<>();
		list.add(1d);
		list.add(10d);
		list.add(5d);
		list.add(20d);
		
		MapUtil.appendPropertie("portal.jsp", list);
		
		List<Double> list2 = MapUtil.readPropertiesValues("portal.jsp");
		
		Assert.assertTrue(list2.size() == 4 && ! list2.contains(3d));
		
		MapUtil.appendPropertie("portal.jsp",  Arrays.asList( new Double[]{3d}));
		
		
		List<Double> list3 = MapUtil.readPropertiesValues("portal.jsp");
		

		Assert.assertTrue(list3.size() == 5 && list3.contains(3d));
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testStoreMapInFile() {
		
		List<Double> list1 = new ArrayList<>();
		list1.add(1d);
		list1.add(10d);
		list1.add(5d);
		list1.add(20d);
		
		Map<String, List<Double>> dataInMemory = new HashMap<>();
		
		dataInMemory.put("tela1.jsp", list1);
		
		MapUtil.storeMapInFile(dataInMemory);
		
		List<String> keys1 = MapUtil.readAllPropertiesKeys();
		
		Assert.assertTrue(keys1.size() == 1 && keys1.contains("tela1.jsp"));
		
		List<Double> values1 = MapUtil.readPropertiesValues(keys1.get(0));
		
		Assert.assertTrue(values1.size() == 4 && values1.contains(10d));
		
		// try to save more information without lose the first one //
		
		dataInMemory = new HashMap<>();
		
		List<Double> list2 = new ArrayList<>();
		list2.add(1d);
		list2.add(3d);
		list2.add(7d);
		list2.add(6d);
		
		dataInMemory.put("tela1.jsp", new ArrayList<>( Arrays.asList( new Double[]{50d} ) ) );
		
		dataInMemory.put("tela2.jsp", list2);
		
		MapUtil.storeMapInFile(dataInMemory);
		
		List<String> keys2 = MapUtil.readAllPropertiesKeys();
		
		Assert.assertTrue(keys2.size() == 2 && keys2.contains("tela1.jsp") && keys2.contains("tela2.jsp"));
		
		List<Double> values2 = MapUtil.readPropertiesValues("tela1.jsp");
		
		Assert.assertTrue(values2.size() == 5 && values2.contains(10d));
		
	}
	
	
	

}
