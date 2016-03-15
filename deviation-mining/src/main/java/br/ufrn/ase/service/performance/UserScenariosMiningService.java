/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosMiningStatistics;
import br.ufrn.ase.dao.UserScenariosDAOFactory;
import br.ufrn.ase.dao.UserScenariosDAOFactory.DATABASE;

/**
 * Execute the User Scenario Mining
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class UserScenariosMiningService {

	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public Map<String, Double> calculateExecutionMeanScenario(String system_version) {

		Map<String, List<Double>> map = UserScenariosDAOFactory.getDAO(DATABASE.MONGODB).findUserScenario(system_version);

		Map<String, Double> mapExecutionMeanScenario = new UserScenariosMiningStatistics().calculateExecutionMeanScenario(map);

		return mapExecutionMeanScenario;

	}

}
