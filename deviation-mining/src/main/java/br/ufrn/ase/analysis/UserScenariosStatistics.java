/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.primitives.Doubles;

import br.ufrn.ase.service.performance.UserScenariosPerformanceService;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StatisticsUtil;

/**
 * Class to calculate statistic of the data return by mining.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class UserScenariosStatistics {

	
	/**
	 * Calculate the quantity of access the some scenario
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionAmount(String key, List<Double> mapScenarioExecutionTime) {
		
		Map<String, Double> mapCVScenario = new HashMap<String, Double>();

		// converts the List<Double> to double[] and calculate the CV
		mapCVScenario.put(key, new Double( mapScenarioExecutionTime.size() ));		

		return MapUtil.sortByValue(mapCVScenario);
	}
	
	
	/***
	 * Calculate the median of the times of a user
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMedian(String key, List<Double> mapScenarioExecutionTime) {
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();

		// converts the List<Double> to double[] and calculate the mean
		mapExecutionMeanScenario.put(key, StatisticsUtil.median( Doubles.toArray(mapScenarioExecutionTime)) );

		return MapUtil.sortByValue(mapExecutionMeanScenario);
	}
	
	
	
	/***
	 * Calculate the average of the times of a user
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMean(String key, List<Double> mapScenarioExecutionTime) {
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();

		// converts the List<Double> to double[] and calculate the mean
		mapExecutionMeanScenario.put(key, StatisticsUtil.mean( Doubles.toArray(mapScenarioExecutionTime)) );
		
		return MapUtil.sortByValue(mapExecutionMeanScenario);
	}
	
	
	/**
	 * 
	 * @param key
	 * @param mapScenarioExecutionTime
	 * @param ordered
	 * @return
	 */
	public Map<String, Double> calculateCoefficientOfVariation(String key, List<Double> mapScenarioExecutionTime,boolean ordered) {
		Map<String, Double> mapCVScenario = new HashMap<String, Double>();

		// converts the List<Double> to double[] and calculate the CV
		mapCVScenario.put(key, StatisticsUtil.coefficientOfVariation(Doubles.toArray(mapScenarioExecutionTime)));

		return ordered ? MapUtil.sortByValue(mapCVScenario) : mapCVScenario;
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	/***
	 * Calculate the average of the times of a user
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMeanScenario(Map<String, List<Double>> mapScenarioExecutionTime) {
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();

		for (String key : mapScenarioExecutionTime.keySet()) {
			// converts the List<Double> to double[] and calculate the mean
			mapExecutionMeanScenario.put(key, StatisticsUtil.mean(Doubles.toArray(mapScenarioExecutionTime.get(key))));
		}

		return MapUtil.sortByValue(mapExecutionMeanScenario);
	}
	
	

	
	

	
	
	/***
	 * Calculate the median of the times of a user
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMedianScenario(Map<String, List<Double>> mapScenarioExecutionTime) {
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();

		for (String key : mapScenarioExecutionTime.keySet()) {
			// converts the List<Double> to double[] and calculate the mean
			mapExecutionMeanScenario.put(key, StatisticsUtil.median(Doubles.toArray(mapScenarioExecutionTime.get(key))));
		}

		return MapUtil.sortByValue(mapExecutionMeanScenario);
	}
	
	

	public Map<String, Double> calculateCoefficientOfVariation(Map<String, List<Double>> mapScenarioExecutionTime,
			boolean ordered) {
		Map<String, Double> mapCVScenario = new HashMap<String, Double>();

		for (String key : mapScenarioExecutionTime.keySet()) {
			// converts the List<Double> to double[] and calculate the CV
			mapCVScenario.put(key, StatisticsUtil.coefficientOfVariation(Doubles.toArray(mapScenarioExecutionTime.get(key))));
		}

		return ordered ? MapUtil.sortByValue(mapCVScenario) : mapCVScenario;
	}
	


	/**
	 * Calculate the quantity of access the some scenario
	 * 
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionAmountScenario(Map<String, List<Double>> mapScenarioExecutionTime) {
		
		Map<String, Double> mapCVScenario = new HashMap<String, Double>();

		for (String key : mapScenarioExecutionTime.keySet()) {
			// converts the List<Double> to double[] and calculate the CV
			mapCVScenario.put(key, new Double( mapScenarioExecutionTime.get(key).size() ));
		}

		return MapUtil.sortByValue(mapCVScenario);
	}
	
	
	
	public Map<String, Double> calculateExecutionMeanScenario(String system_version) {
		Map<String, List<Double>> map = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios(system_version, true);
	
		Map<String, Double> mapExecutionMeanScenario = new UserScenariosStatistics()
				.calculateExecutionMeanScenario(map);
	
		return mapExecutionMeanScenario;
	}
	
	
	
	public Map<String, Double> calculateCoefficientOfVariation(String system_version) {
		Map<String, List<Double>> map = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios(system_version, true);
	
		Map<String, Double> mapCVScenario = new UserScenariosStatistics().calculateCoefficientOfVariation(map, true);
	
		return mapCVScenario;
	}
	
}
