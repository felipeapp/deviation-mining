/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import br.ufrn.ase.analysis.VariationTimeRangeStatistics;
import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.UserScenariosPerformanceService;
import br.ufrn.ase.service.performance.VariationTimeRangeService;

/**
 * Console to see the information temporarily
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class ConsoleVariationRange {

	public static void main(String[] args) {
		
		System.out.println("Starting ... ");
		
		long start = System.currentTimeMillis();
		
		UserScenariosPerformanceService userScenariosService = new UserScenariosPerformanceService();
		VariationTimeRangeStatistics variationTimeRangeStatistics = new VariationTimeRangeStatistics();

		VariationTimeRangeService service = new VariationTimeRangeService(null, userScenariosService, variationTimeRangeStatistics, false);
		Map<String, Double> mapRange = service.calculateTimeRange("SIGAA-3.21.0");

		GraphicPlot plot = new GraphicPlot();
		plot.drawColumnChart(mapRange, "Range", "Scenario", "Times");
		plot.drawBoxPlotChart(mapRange);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");
	}

}