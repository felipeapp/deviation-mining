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

import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StatisticsUtil;

/**
 * Class to calculate statistic of the data return by mining.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class UserScenariosStatistics {

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

		return mapExecutionMeanScenario;
	}

	public Map<String, Double> calculateCoefficientOfVariation(Map<String, List<Double>> mapScenarioExecutionTime,
			boolean ordered) {
		Map<String, Double> mapCVScenario = new HashMap<String, Double>();

		for (String key : mapScenarioExecutionTime.keySet()) {
			// converts the List<Double> to double[] and calculate the CV
			mapCVScenario.put(key, StatisticsUtil.coefficientOfVariation(Doubles.toArray(mapScenarioExecutionTime.get(key))));
		}

		return ordered ? MapUtil.sortByComparator(mapCVScenario) : mapCVScenario;
	}

}
