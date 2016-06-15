/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.basic;

import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.service.performance.UserScenariosPerformanceService;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class AllBasicService {
	
	
	public void findAllBasicScenarios(String systemVersion, boolean executeMining, boolean isUserEnabled){
		
		if( executeMining ){
			
			Map<String, List<Double>> retorno = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios(systemVersion, false);
			
			Map<String, Double> mapRangeMedian = new UserScenariosStatistics().calculateExecutionMedianScenario(retorno);
	
			Map<String, Double> mapRangeVariation = new UserScenariosStatistics().calculateCoefficientOfVariation(retorno, true);
			
			Map<String, Double> mapRangeAverage = new UserScenariosStatistics().calculateExecutionMeanScenario(retorno);
			
			Map<String, Double> mapRangeAccessed = new UserScenariosStatistics().calculateExecutionAmountScenario(retorno);
	
			new HighestMedianService().saveResults(systemVersion, mapRangeMedian);
			
			new HighestVariationService().saveResults(systemVersion, mapRangeVariation);
			
			new HighestAverageService().saveResults(systemVersion, mapRangeAverage);
			
			new MostAccessedScenariosService().saveResults(systemVersion, mapRangeAccessed);
			
		}else{
	
		}
		
	}
	


}
