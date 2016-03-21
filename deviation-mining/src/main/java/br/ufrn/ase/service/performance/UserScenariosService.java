/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.UserScenariosDAO;

/**
 * Execute the User Scenario Mining
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class UserScenariosService {

	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public Map<String, Double> calculateExecutionMeanScenario(String system_version) {

		Map<String, List<Double>> map = DAOFactory.getDAO(UserScenariosDAO.class).findUserScenario(system_version);

		Map<String, Double> mapExecutionMeanScenario = new UserScenariosStatistics().calculateExecutionMeanScenario(map);

		return mapExecutionMeanScenario;

	}

}
