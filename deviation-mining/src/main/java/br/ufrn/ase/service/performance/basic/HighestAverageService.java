/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.result.ResultDataAnalysisDAO;
import br.ufrn.ase.service.performance.UserScenariosPerformanceService;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class HighestAverageService {
	
	public Map<String, Double> findTopAvaregeScenarios(String systemVersion, boolean isUserEnabled){
		
		Map<String, List<Double>> retorno = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios(systemVersion, false);
	
		Map<String, Double> mapRange = new UserScenariosStatistics().calculateExecutionMeanScenario(retorno);
		
		saveResults(systemVersion, mapRange);
		
		return mapRange;
	}
	
	
	/**
	 * @param mapRange_3_21
	 */
	private void saveResults(String systemVersion, Map<String, Double> mapRange) {
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertAverageTime(mapRange, systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
		}
	}

}
