/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.compositions;

import java.util.Map;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.compositions.HighestVariationMostSignificantService;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.SwingUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestVariationMostSignificant {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Starting  "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		
		long start = System.currentTimeMillis();
	
		Map<String, Double> mapTemp = new ConsoleHighestVariationMostSignificant().getHighestVariationMostSignificantScenario(systemVersion);
		
		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapTemp, "Variation Most Significant", "Scenario", "Times");
		plot.drawBoxPlotChart(mapTemp);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

	}
	
	
	public Map<String, Double> getHighestVariationMostSignificantScenario(String systemVersion){
		
		HighestVariationMostSignificantService service = new HighestVariationMostSignificantService();
		
		Map<String, Double> map =  service.findVariationMostSignificantScenarios(systemVersion, QTD);
		
		map = MapUtil.cutOff(map, QTD);
		
		return map;
	}
}
