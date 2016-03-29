/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.VariationTimeRangeStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.UserScenariosDAO;

/**
 * This service calculate scenarios of larger and smaller variation at runtime
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class VariationTimeRangeService {

	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public Map<String, Double> calculateTimeRange(String system_version) {

		Map<String, List<Double>> map = DAOFactory.getDAO(UserScenariosDAO.class).findUserScenario(system_version);

		Map<String, Double> mapRange = new VariationTimeRangeStatistics().calculateVariationTimeRange(map);
		
		return mapRange;

	}
	
}
