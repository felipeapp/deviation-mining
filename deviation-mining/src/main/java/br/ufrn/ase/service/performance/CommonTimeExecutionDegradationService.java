/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
import br.ufrn.ase.domain.LogOperacao;

/**
 * 
 *  Implementacao:
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
public class CommonTimeExecutionDegradationService {
	
	
	public Map<String, Double> calculateCommonTimeExecutionDegradation(String systemVersion, Map<String, Double> topScenarios) {
		
		
		Map<String, List<Date>> topScenariosAboveAverage = getExecutionTimeAboveAverageBySencarios(topScenarios);
		
//		List<Date> overloadTimes = verifyCommonTime(topScenariosAboveAverage);
//		
//		List<String> scenariosExecuteOverloadTimes = findScenariosByTime(overloadTimes);
//		
//		Map<String, Double> scenariosAvarage = calculateAvarage(scenariosExecuteOverloadTimes);
//		
//		Map<String, List<Double>> scenariosOverAvarage = getExecutionsOverAvarage(scenariosAvarage.keySet());
		
		return null;
	}

	/**
	 * @param topScenarios
	 * @return
	 */
	private Map<String, List<Date>> getExecutionTimeAboveAverageBySencarios(Map<String, Double> topScenarios) {
		
		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);

		List<LogOperacao> logs = dao.findAllOperationAboveAverage(topScenarios);
		
		return null;
	}

}
