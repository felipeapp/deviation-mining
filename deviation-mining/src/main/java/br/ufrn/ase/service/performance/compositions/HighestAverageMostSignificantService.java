/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.compositions;

import java.util.Map;

import br.ufrn.ase.service.performance.basic.HighestAverageService;
import br.ufrn.ase.service.performance.basic.MostAccessedScenariosService;
import br.ufrn.ase.util.MapUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class HighestAverageMostSignificantService {

	
	public Map<String, Double> findAverageMostSignificantScenarios(String systemVersion, final int QTD){
		
		
		Map<String, Double> mostAccesss = new MostAccessedScenariosService().readResults(systemVersion);
		
		Map<String, Double> highestAverage = new HighestAverageService().readResults(systemVersion);
		
	
		return MapUtil.crossMaps(highestAverage, mostAccesss, QTD);
	}
	
}
