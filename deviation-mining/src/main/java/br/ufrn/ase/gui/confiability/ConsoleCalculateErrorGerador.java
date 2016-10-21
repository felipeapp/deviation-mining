/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.confiability;

import br.ufrn.ase.service.confiability.CalculateErrorGeradorService;
import br.ufrn.ase.util.SwingUtil;

/**
 * 
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
		
		long start = System.currentTimeMillis();
	
		new ConsoleCalculateErrorGerador().calculateErroGerador(systemVersion, true);
		
		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");


	}

	
	public void calculateErroGerador(String systemVersion, boolean executeMining){
		 
		CalculateErrorGeradorService service = new CalculateErrorGeradorService();
		
		service.calculateErrorGerador(systemVersion, executeMining);

	}

}
