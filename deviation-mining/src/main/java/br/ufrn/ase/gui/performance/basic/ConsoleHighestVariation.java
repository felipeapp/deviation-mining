/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.basic.HighestVariationService;
import br.ufrn.ase.util.MapUtil;

/**
 * This class calculate the highest scenarios variations of a specific system version 
 * using Coefficient of variation
 * 
 * https://en.wikipedia.org/wiki/Coefficient_of_variation
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestVariation {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Starting ... ");
		
		long start = System.currentTimeMillis();
		
		String systemVersion = JOptionPane.showInputDialog(null, "Enter System Version ");
		boolean executeMining = Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Execute Mining? true or false ? "));
		
		Map<String, Double> mapRange_3_21 = new ConsoleHighestVariation().getScenariosHighestVariation(systemVersion, executeMining);

		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapRange_3_21, "Variation", "Scenario", "Times");
		plot.drawBoxPlotChart(mapRange_3_21);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

	}
	
	public Map<String, Double> getScenariosHighestVariation(String systemVersion, boolean executeMining){
		 
		HighestVariationService service = new HighestVariationService();
		
		Map<String, Double> mapRange_3_21 = service.findVariationScenarios(systemVersion, executeMining, false);

		mapRange_3_21 = MapUtil.cutOff(mapRange_3_21, QTD);
		
		return mapRange_3_21;
	}

}
