/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.confiability;

import br.ufrn.ase.service.confiability.CalculateErrorGeradorService;
import br.ufrn.ase.util.SwingUtil;

/**
 * This class calculate the line of code the generate the error
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleCalculateErrorGerador {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Starting "+Thread.currentThread().getStackTrace()[1].getClassName()+" ... ");
		
		String systemVersion = SwingUtil.readSystemVersion();
		
		boolean executeMining = SwingUtil.readTypeExecution();
		
		long start = System.currentTimeMillis();
	
		new ConsoleCalculateErrorGerador().calculateErroGerador(systemVersion, executeMining);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}

	
	public void calculateErroGerador(String systemVersion, boolean executeMining){
		 
		CalculateErrorGeradorService service = new CalculateErrorGeradorService();
		
		service.calculateErrorGerador(systemVersion, executeMining);

	}

}
