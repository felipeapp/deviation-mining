/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui;

import java.util.Map;

import br.ufrn.ase.service.performance.VariationTimeRangeService;

/**
 * Console to see the information temporarily
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleVariationRange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		System.out.println("Inicando execução... ");

		Map<String, Double> mapRange = new VariationTimeRangeService().calculateTimeRange("SIGAA-3.21.0");

		for (String scenario : mapRange.keySet()) {
			System.out.println("scenario: "+scenario+" -> variation: "+mapRange.get(scenario) );
		}
		
		System.out.println("Tempo: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");

	}

}
