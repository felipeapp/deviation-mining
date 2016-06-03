/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.compositions;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.compositions.HighestAverageMostSignificantService;
import br.ufrn.ase.util.MapUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestAverageMostSignificant {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		System.out.println("Starting ... ");
	
		String systemVersion = JOptionPane.showInputDialog(null, "Enter System Version ");
		
		Map<String, Double> mapTemp = new ConsoleHighestAverageMostSignificant().getHighestAverageMostSignificantScenario(systemVersion);
		
		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapTemp, "Average Most Significant", "Scenario", "Times");
		plot.drawBoxPlotChart(mapTemp);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");

	}
	
	
	public Map<String, Double> getHighestAverageMostSignificantScenario(String systemVersion){
			
		HighestAverageMostSignificantService service = new HighestAverageMostSignificantService();
		
		Map<String, Double> map =  service.findAverageMostSignificantScenarios(systemVersion, QTD);
		
		map = MapUtil.cutOff(map, QTD);
		
		return map;
	}

}
