package br.ufrn.ase.gui;

import java.util.Map;

import br.ufrn.ase.service.performance.UserScenariosService;

public class Console {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		Map<String, Double> map = new UserScenariosService().calculateExecutionMeanScenario("SIGAA-3.21.0");

		System.out.println("Tempo: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");

		printInformationMap(map);
	}

	private static void printInformationMap(Map<String, Double> map) {
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("Mean of the execution time ");
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		for (String key : map.keySet()) {
			System.out.println(key + " - " + map.get(key));
		}
	}

}
