/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import java.util.Map;

import javax.swing.JOptionPane;

import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.basic.HighestMedianService;
import br.ufrn.ase.util.MapUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleHighestMedian {

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
		
		Map<String, Double> mapRange_3_21 = new ConsoleHighestMedian().getScenariosHighestMedian(systemVersion, executeMining);
		
		
		GraphicPlot plot = new GraphicPlot();
		
		plot.drawColumnChart(mapRange_3_21, "Average", "Scenario", "Times");
		plot.drawBoxPlotChart(mapRange_3_21);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}
	
	public Map<String, Double> getScenariosHighestMedian(String systemVersion, boolean executeMining){
		 
		HighestMedianService service = new HighestMedianService();
		
		Map<String, Double> mapRange_3_21 = service.findMedianScenarios(systemVersion, executeMining, false);

		mapRange_3_21 = MapUtil.cutOff(mapRange_3_21, QTD);
		
		return mapRange_3_21;
	}

}
