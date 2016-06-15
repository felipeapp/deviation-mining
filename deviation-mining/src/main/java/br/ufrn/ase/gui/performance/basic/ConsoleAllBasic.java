/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import br.ufrn.ase.service.performance.basic.HighestAverageService;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.SwingUtil;

/**
 * This class execute all basic mining and save the results in the cache database
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleAllBasic {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		boolean executeMining = SwingUtil.readTypeExecution();
		
		long start = System.currentTimeMillis();
		
		Map<String, Double> mapRange = new ConsoleAllBasic().getBasicScenarios(systemVersion, executeMining);
		
		printResults(mapRange);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}


	public Map<String, Double> getBasicScenarios(String systemVersion, boolean executeMining){
		 
		HighestAverageService service = new HighestAverageService();
		
		Map<String, Double> mapRange_3_21 = service.findAvaregeScenarios(systemVersion, executeMining, false);

		mapRange_3_21 = MapUtil.cutOff(mapRange_3_21, QTD);
		
		return mapRange_3_21;
	}
	
	
	/**
	 * @param mapRange
	 */
	private static void printResults(Map<String, Double> mapRange) {
		for (String scenario : mapRange.keySet()) {
			System.out.println("scenario: "+scenario+" -> "+mapRange.get(scenario));
		}
		
	}
	
}
