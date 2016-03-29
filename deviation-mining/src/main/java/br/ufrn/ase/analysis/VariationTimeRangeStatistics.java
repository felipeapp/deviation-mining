/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.analysis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.util.MapUtil;

/**
 * Calculate the variation of a scenario
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class VariationTimeRangeStatistics {

	/***
	 * Calculate the range of the times
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateVariationTimeRange(Map<String, List<Double>> mapScenarioExecutionTime){
		
		Map<String, Double> mapVarationTime = new HashMap<String, Double>();
		
		
		// forEach scenario
		for (String scenario : mapScenarioExecutionTime.keySet()) {
			List<Double> sortedTime =  mapScenarioExecutionTime.get(scenario);
			Collections.sort(sortedTime); // sort the list
			mapVarationTime.put(scenario,  sortedTime.get(sortedTime.size()-1) - sortedTime.get(0)  );  // calculate the variation
		}
	
		return MapUtil.sortByValue(mapVarationTime);
	}
	
}
