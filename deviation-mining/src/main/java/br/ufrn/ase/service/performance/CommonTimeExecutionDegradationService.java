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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.primitives.Doubles;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.degradation.Interval;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StatisticsUtil;

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
	
	
	public List<String> calculateCommonTimeExecutionDegradation(String systemVersion, Map<String, Double> topScenarios) {
		
		final int INTERVAL = 60;                                    // x minutes for the interval
		final double PERCENTAGE_DEGRADATION_SAME_PERIOD = 0.7;      // at least x% degrade in the period 
		
		List<LogOperacao> allExecutionTopScenariosAboveAverage = getExecutionTimeAboveAverageBySencarios(topScenarios);
		
		System.out.println("Exectuion of top scenairios above average: "+allExecutionTopScenariosAboveAverage.size());
		
		List<Interval> commonTimesOverload = verifyCommonTimeOverloaded(allExecutionTopScenariosAboveAverage, INTERVAL, PERCENTAGE_DEGRADATION_SAME_PERIOD);
		
		System.out.println("Intervals overload executions: "+commonTimesOverload.size());
		
		// other scenarios same interval
		List<LogOperacao> scenariosExecutedOverloadTime = getSencariosExecutedInOverloadTime(commonTimesOverload, systemVersion);
		
		System.out.println("Other scenarios same interval: "+scenariosExecutedOverloadTime.size());
		
		List<LogOperacao> scenariosExecutedOverloadTimeAllExecutions =  getAllExecutionOfScenarios(scenariosExecutedOverloadTime);
		
		System.out.println("All execution of others scenarios: "+scenariosExecutedOverloadTimeAllExecutions.size());
		
		Map<String, Double> scenariosExecutedOverloadTimeMean = calculateExecutionMeanScenario(scenariosExecutedOverloadTimeAllExecutions);
		
		System.out.println("Mean of execution of others scenarios: "+scenariosExecutedOverloadTimeMean.size());
		
		List<String> allScenariosOverAverageSameInterval = getScenarioOverAverageSameInterval(scenariosExecutedOverloadTimeAllExecutions, scenariosExecutedOverloadTimeMean, commonTimesOverload);
		
		System.out.println("All scenarios Over Average Same Interval: "+allScenariosOverAverageSameInterval.size());
		
		return allScenariosOverAverageSameInterval;

	}


	


	




	/**
	 * Return All execution of the top scenarios above average.
	 * 
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
	private List<Interval> verifyCommonTimeOverloaded(List<LogOperacao> scenariosAboveAverage, final int INTERVAL, final double PERCENTAGE_DEGRADATION_SAME_PERIOD) {
		
		Set<Interval> commonTime = new HashSet<>();
		
		List<Date> allTimes = new ArrayList<>();
		Set<String> allScenarios = new HashSet<>();
		
		for (LogOperacao logOperacao : scenariosAboveAverage) {
			allTimes.add( logOperacao.getHorario() );
			allScenarios.add( logOperacao.getAction() );
		}
		
		Collections.sort(allTimes);
		
		LocalDateTime initialTime =  DateUtil.toLocalDateTime(allTimes.get(0)); 
		LocalDateTime finalTime =    DateUtil.toLocalDateTime(allTimes.get(allTimes.size()-1)); 
		
		LocalDateTime currentTime = initialTime;
		LocalDateTime nextTime = getNextInterval(INTERVAL, initialTime, finalTime);
		
		
		double percentageDegraded = allScenarios.size() * PERCENTAGE_DEGRADATION_SAME_PERIOD;
		
		int interval = 0;
		
		/* O(n2) 
		 * 
		 * for each interval, verify each log have happen in this interval
		 * 
		 * IF All scenario degraded in the same interval, return this interval
		 */
		while(nextTime.isBefore(finalTime) ){
			
			if(interval % 1000 == 0)
				System.out.println("-> Analyzing: "+interval+ " intervals");
			
			Set<String> scenariosAtPeriod = new HashSet<>();
			
			for (LogOperacao logOperacao : scenariosAboveAverage) {
				
				LocalDateTime timeTemp = DateUtil.toLocalDateTime(logOperacao.getHorario());
	
				if( DateUtil.isBetweenPeriod(timeTemp, currentTime, nextTime) ){ // log happen in this period of time
					scenariosAtPeriod.add(logOperacao.getAction());
				}
				
			}
			
			// x%  degraded in the interval, save it, it is the interval that we want
			if( scenariosAtPeriod.size() >= percentageDegraded  ){ 
				commonTime.add( new Interval(currentTime, nextTime) );
			}
			
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = getNextInterval(INTERVAL, nextTime, finalTime);
			
			interval++;
		}
		return new ArrayList<>(commonTime);
	}

	
	
	/**
	 * Return others scenarios the executer overloadTime in the same interval 
	 * 
	 * @param topScenarios
	 * @return
	 */
	private List<LogOperacao> getSencariosExecutedInOverloadTime(List<Interval> commonTimesOverload, String systemVersion) {
		
		String systemName = systemVersion.substring(0, systemVersion.indexOf('-')).trim().toUpperCase();
		
		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);

		List<LogOperacao> logs = dao.findAllOperationInTheInterval(commonTimesOverload, systemName);
		
		return logs;
	}
	
	
	/**
	 * Return all execution of the scenario passed. 
	 * 
	 * @param scenariosExecutedOverloadTime
	 * @return
	 */
	private List<LogOperacao>  getAllExecutionOfScenarios(List<LogOperacao> scenariosExecutedOverloadTime) {
		
		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);
		
		List<String> scenarios = new ArrayList<>(); 
		
		for (LogOperacao logOperacao : scenariosExecutedOverloadTime) {
			scenarios.add(logOperacao.getAction());
		}

		return dao.findAllByScenario(scenarios);
		
	}
	
	
	/***
	 * Calculate the average of the times of a user
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMeanScenario(List<LogOperacao> scenariosExecutions) {
		
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		
		for (LogOperacao log : scenariosExecutions) {
			String key =  log.getAction();

			List<Double> tempos = map.get(key);

			if (tempos == null) {
				tempos = new ArrayList<Double>();
				map.put(key, tempos);
			}

			tempos.add((double) log.getTempo());
		}
		
		// calculate the mean
		
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();

		for (String key : map.keySet()) {
			// converts the List<Double> to double[] and calculate the mean
			mapExecutionMeanScenario.put(key, StatisticsUtil.mean(Doubles.toArray(map.get(key))));
		}

		return MapUtil.sortByValue(mapExecutionMeanScenario);
	}
	
	
	/**
	 * This is the last method of this scenario.
	 * 
	 * This method find the scenario that execute over average in the same interval of the top 10.
	 * 
	 * @param scenariosExecutedOverloadTimeAllExecutions
	 * @param scenariosExecutedOverloadTimeMean
	 * @param commonTimesOverload
	 * @return
	 */
	private List<String> getScenarioOverAverageSameInterval(
			List<LogOperacao> scenariosExecutedOverloadTimeAllExecutions,
			Map<String, Double> scenariosExecutedOverloadTimeMean, List<Interval> commonTimesOverload) {
		
		Set<String> scenarios = new HashSet<>();
		
		// O(n2)
		for (LogOperacao log : scenariosExecutedOverloadTimeAllExecutions) {
			
			// if execute above the average
			if(log.getTempo() > scenariosExecutedOverloadTimeMean.get(log.getAction() ) ){ 
				
				
				// now we have to the if execute in the times of overload of the system (get using the top 10 scenarios)
				forIntervals:
				for (Interval interval : commonTimesOverload) {
					if(DateUtil.isBetweenPeriod(DateUtil.toLocalDateTime(log.getHorario()), interval.getInitialTime(), interval.getFinalTime())){
						scenarios.add(log.getAction());
						break forIntervals;
					}
				}
			} 
			
		}
		
		return new ArrayList<>(scenarios);
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
