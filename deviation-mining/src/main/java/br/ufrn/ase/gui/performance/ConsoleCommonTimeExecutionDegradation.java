/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance;

import java.util.Map;

import br.ufrn.ase.gui.basic.ConsoleHighestAverageMostSignificant;
import br.ufrn.ase.service.performance.CommonTimeExecutionDegradationService;

/**
 * 
 * (1) Identificar horarios de carga no sistema durante a semana e quais
 * cenarios sao relacionados a tais horarios de carga;
 *      
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleCommonTimeExecutionDegradation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String systemVersion ="SIGAA-3.21.0";
		
		long start = System.currentTimeMillis();
		
		System.out.println("Starting ... ");
		
		Map<String, Double> topScenarios = new ConsoleHighestAverageMostSignificant().getHighestAverageMostSignificantScenario(systemVersion, 10);
		
		new CommonTimeExecutionDegradationService().calculateCommonTimeExecutionDegradation(systemVersion, topScenarios);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");
//		
//		GraphicPlot plot = new GraphicPlot();
//		
//		plot.drawColumnChart(scenariosOverAvarage, "Average Most Significant", "Scenario", "Times");
	}

}
