/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.basic.MostAccessedScenariosService;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.SwingUtil;

/**
 * Verify the most access scenarios
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleMostAcessedScenarios {
	
	public final static int QTD = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Starting ... ");
		
		long start = System.currentTimeMillis();
		
		String systemVersion = SwingUtil.readSystemVersion();
		boolean executeMining = SwingUtil.readTypeExecution();
		
		Map<String, Double> mapRange_3_21 = new ConsoleMostAcessedScenarios().getScenariosMostAccessed(systemVersion, executeMining);
		
		GraphicPlot plot = new GraphicPlot();
		
		//plot.drawColumnChart(mapRange_3_20);
		
		plot.drawColumnChart(mapRange_3_21, "Amount of Access", "Scenario", "Times");
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

	}
	
	public Map<String, Double> getScenariosMostAccessed(String systemVersion, boolean executeMining){
		 
		MostAccessedScenariosService service = new MostAccessedScenariosService();
		
		Map<String, Double> mapRange_3_21 = service.findMostAccessedScenarios(systemVersion, executeMining, false);

		mapRange_3_21 = MapUtil.cutOff(mapRange_3_21, QTD);
		
		return mapRange_3_21;
	}

}
