/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui.performance;

import java.util.Map;

import br.ufrn.ase.gui.basic.ConsoleHighestAverageMostSignificant;

/**
 * 
 * (1) Identificar horarios de carga no sistema durante a semana e quais
 * cenarios sao relacionados a tais horarios de carga;
 *      Possivel implementacao:
 *            - observar para cada cenario das top 10 em que horarios
 * eles foram executados com tempo acima da media.
 *            - verificar se existem horarios comuns (necessario definir
 * um intervalo, por exemplo, 5 ou 10 minutos) nos quais as top 10
 * ficaram acima da media, que seriam entao os horarios de carga que
 * estamos procurando.
 *            - pesquisar no mesmo horario, cenarios que foram
 * executados, calcular a media de execucao de tais cenarios, e verificar
 * se eles tambem estao acima da media.
 *            - criar lista de cenarios comuns que mais aparecem nos
 * horarios de carga e para cada um desses cenarios, executar a abordagem
 * de Felipe para ver se existe sobrecarga em algum algoritmo, consulta
 * ao BD, etc
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleCommonTimeExecutionDegradation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, Double> topScenarios = new ConsoleHighestAverageMostSignificant().getHighestAverageMostSignificantScenario(10);
		
		
//		Map<String, List<Date>> topScenariosAboveAverage = userScenariosService.getSencariosAboveAverage(topScenarios);
//		
//		List<Date> overloadTimes = verifyCommonTime(topScenariosAboveAverage);
//		
//		List<String> scenariosExecuteOverloadTimes = findScenariosByTime(overloadTimes);
//		
//		Map<String, Double> scenariosAvarage = calculateAvarage(scenariosExecuteOverloadTimes);
//		
//		Map<String, List<Double>> scenariosOverAvarage = getExecutionsOverAvarage(scenariosAvarage.keySet());
//		
//		GraphicPlot plot = new GraphicPlot();
//		
//		plot.drawColumnChart(scenariosOverAvarage, "Average Most Significant", "Scenario", "Times");
	}

}
