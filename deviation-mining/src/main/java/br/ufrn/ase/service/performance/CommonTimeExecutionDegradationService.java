/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.degradation.Interval;
import br.ufrn.ase.util.DateUtil;

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
		
		final int INTERVAL = 60; // 1 hour
		
		final double PERCENTAGE_DEGRADATION_SAME_PERIOD = 0.7; // at least x% degrade in the periodo 
		
		List<LogOperacao> topScenariosAboveAverage = getExecutionTimeAboveAverageBySencarios(topScenarios);
		
		List<Interval> overloadTimes = verifyCommonTime(topScenariosAboveAverage, INTERVAL, PERCENTAGE_DEGRADATION_SAME_PERIOD);
		
		for (Interval interval : overloadTimes) {
			System.out.println("All degradate in interval: "+interval);
		}
		
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
	private List<LogOperacao> getExecutionTimeAboveAverageBySencarios(Map<String, Double> topScenarios) {
		
		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);

		List<LogOperacao> logs = dao.findAllOperationAboveAverage(topScenarios);
		
		return logs;
	}
	
	/**
	 * I have a list of scenarios and the time 01/05/2016 16:55.  Here I try to find if have some 
	 * common time when all scenario execute above the average
	 * 
	 * 
	 * @param topScenariosAboveAverage
	 * @return
	 */
	private List<Interval> verifyCommonTime(List<LogOperacao> topScenariosAboveAverage, final int INTERVAL, final double PERCENTAGE_DEGRADATION_SAME_PERIOD) {
		
		List<Interval> commonTime = new ArrayList<>();
		
		List<Date> allTimes = new ArrayList<>();
		Set<String> allScenarios = new HashSet<>();
		
		for (LogOperacao logOperacao : topScenariosAboveAverage) {
			allTimes.add( logOperacao.getHorario() );
			allScenarios.add( logOperacao.getAction() );
		}
		
		Collections.sort(allTimes);
		
		LocalDateTime initialTime =  DateUtil.toLocalDataTime(allTimes.get(0)); 
		LocalDateTime finalTime =    DateUtil.toLocalDataTime(allTimes.get(allTimes.size()-1)); 
		
		LocalDateTime currentTime = initialTime;
		LocalDateTime nextTime = getNextInterval(INTERVAL, initialTime, finalTime);
		
		/* O(n2) 
		 * 
		 * for each interval, verify each log have happen in this interval
		 * 
		 * IF All scenario degraded in the same interval, return this interval
		 */
		while(nextTime.isBefore(finalTime) ){
			
			Set<String> scenariosAtPeriod = new HashSet<>();
			
			for (LogOperacao logOperacao : topScenariosAboveAverage) {
				
				LocalDateTime timeTemp = DateUtil.toLocalDataTime(logOperacao.getHorario());
	
				if( DateUtil.isBetweenPeriod(timeTemp, currentTime, nextTime) ){ // log happen in this period of time
					scenariosAtPeriod.add(logOperacao.getAction());
				}
				
			}
			
			// x%  degraded in the interval, save it, it is the interval that we want
			if( scenariosAtPeriod.size() >= ( allScenarios.size() * PERCENTAGE_DEGRADATION_SAME_PERIOD ) ){ 
				commonTime.add( new Interval(currentTime, nextTime) );
			}
			
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = getNextInterval(INTERVAL, nextTime, finalTime);
		}
		return null;
	}


	/**
	 * return the next interval until the limit
	 * 
	 * @param INTERVAL
	 * @param initialTime
	 * @param finalTime
	 * @return
	 */
	private LocalDateTime getNextInterval(final int INTERVAL, LocalDateTime time, LocalDateTime limit) {
		return time.plusMinutes(INTERVAL).compareTo(limit) < 0 ?  time.plusMinutes(INTERVAL) : limit;
	}




}
