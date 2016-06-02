/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.compositions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.UserScenariosPerformanceService;
import br.ufrn.ase.util.MapUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestAverageMostSignificant {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		System.out.println("Starting ... ");
	
		Map<String, Double> mapTemp = new ConsoleHighestAverageMostSignificant().getHighestAverageMostSignificantScenario("SIGAA-3.21.0", 10);
		
		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapTemp, "Average Most Significant", "Scenario", "Times");
		plot.drawBoxPlotChart(mapTemp);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

	}
	
	
	public Map<String, Double> getHighestAverageMostSignificantScenario(String systemVersion, int qtd){
			
		UserScenariosPerformanceService userScenariosService = new UserScenariosPerformanceService();
		
		// We want to sort the 2 criteria entirely distinct
		// How I do it?
		
		// Fist calculate the most variation and the most access
		Map<String, List<Double>> retorno_3_21 = userScenariosService.findTimesExecutionOfUserScenarios(systemVersion, false);
		Map<String, Double> mapRange_3_21Mean = new UserScenariosStatistics().calculateExecutionMeanScenario(retorno_3_21);
		Map<String, Double> mapRange_3_21MostAccess = new UserScenariosStatistics().calculateExecutionAmountScenario(retorno_3_21);
		
		// That we first select 2 times the quantity of result, and take the variation just for this sub sample
		List<String> mostAccessKeys = new ArrayList<String>(mapRange_3_21MostAccess.keySet());
		
		Map<String, Double> mapTemp = new HashMap<>();
		
		
		for (int i = 0; i < ( qtd * 2 < mostAccessKeys.size() ? qtd * 2: mostAccessKeys.size() ); i++) {
			String scenarioMostAccess = mostAccessKeys.get(i);
			
			mapTemp.put(scenarioMostAccess, mapRange_3_21Mean.get(scenarioMostAccess));
		}
		
		mapTemp = MapUtil.cutOff(mapTemp, qtd);
		
		
		return mapTemp;
	}

}
