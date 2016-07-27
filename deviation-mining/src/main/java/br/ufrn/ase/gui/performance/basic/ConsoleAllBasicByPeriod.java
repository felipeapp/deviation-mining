/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance.basic;

import br.ufrn.ase.service.performance.basic.AllBasicByPeriodService;
import br.ufrn.ase.util.SwingUtil;

/**
 * <p>Starts the execution of all basic information dividing this information by period of time 
 * and save it on a temporary file to reduce the quantity of memory and execute for big amount of data.</p>
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleAllBasicByPeriod {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		
		long start = System.currentTimeMillis();
	
		new ConsoleAllBasicByPeriod().calculateBasicScenarios(systemVersion, true);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}

	
	public void calculateBasicScenarios(String systemVersion, boolean executeMining){
		 
		AllBasicByPeriodService service = new AllBasicByPeriodService();
		
		service.calculateAllBasicScenarios(systemVersion, executeMining);

	}

}
