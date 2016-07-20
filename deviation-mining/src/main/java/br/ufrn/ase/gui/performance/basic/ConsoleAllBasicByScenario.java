/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import br.ufrn.ase.service.performance.basic.AllBasicByScenarioService;
import br.ufrn.ase.util.SwingUtil;

/**
 * This class execute all basic mining and save the results in the cache database
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleAllBasicByScenario {

	/** QTD to plot in the graphic */
	public final static int QTD = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		
		long start = System.currentTimeMillis();
	
		new ConsoleAllBasicByScenario().calculateBasicScenarios(systemVersion);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}


	public void calculateBasicScenarios(String systemVersion){
		 
		AllBasicByScenarioService service = new AllBasicByScenarioService();
		
		service.calculateAllBasicScenarios(systemVersion);

	}
	
}
