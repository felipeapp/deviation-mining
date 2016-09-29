/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.confiability;

import br.ufrn.ase.service.confiability.CalculatePercentageErrorService;
import br.ufrn.ase.util.SwingUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleCalculatePercentageError {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		
		long start = System.currentTimeMillis();
	
		new ConsoleCalculatePercentageError().countAmountOfErrorByScenario(systemVersion, true);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}

	
	public void countAmountOfErrorByScenario(String systemVersion, boolean executeMining){
		 
		CalculatePercentageErrorService service = new CalculatePercentageErrorService();
		
		service.countAmountOfErrorByScenario(systemVersion, executeMining);

	}
	
}
