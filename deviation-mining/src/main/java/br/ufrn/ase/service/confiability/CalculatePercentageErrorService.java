/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.confiability;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.result.ResultDataAnalysisDAO;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class CalculatePercentageErrorService {
	
	
	/***
	 * Calculate the basic information about the log
	 * 
	 * @param systemVersion
	 * @param executeMining
	 */
	public void countAmountOfErrorByScenario(String systemVersion, boolean executeMining){
		
		Map<String, Integer> qtdAccessMap = new HashMap<>();
		Map<String, Integer> qtdErrorMap = new HashMap<>();
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		try {
			System.out.println(">>>> Reading Error ");
			qtdErrorMap = dao.readScenariosWithError(systemVersion);
			System.out.println(">>>> Reading Qtd Access ");
			qtdAccessMap = dao.readQtdAccessScenarios(systemVersion);
			DAOFactory.closeResultConnection();
		} catch (SQLException sqlEx) {
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Reading Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}
		
		
		System.out.println(">>>> Saving Result ");
		/*
		 * For each scenario with error
		 */
		for (String scenario : qtdErrorMap.keySet()) {
			Integer qtdAccess = qtdAccessMap.get(scenario);
			
			saveResultsPercentageOfError(systemVersion, scenario, qtdAccess, qtdErrorMap.get(scenario));
		}
		
	}
	

	
	/**
	 * @param systemVersion
	 * @param scenario
	 * @param size
	 */
	private void saveResultsPercentageOfError(String systemVersion, String scenario, int qtdAccess, int qtdError) {

		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			float percentage = (float) ( (float) qtdError / (float) qtdAccess );
			dao.insertPercentageOfError(systemVersion, scenario, qtdAccess,  qtdError, percentage);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}finally{
			DAOFactory.closeResultConnection();
		}
		
	}
	

	
}
