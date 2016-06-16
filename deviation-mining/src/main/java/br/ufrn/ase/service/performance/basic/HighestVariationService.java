/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.basic;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class HighestVariationService {
	
	
	public Map<String, Double> findVariationScenarios(String systemVersion, boolean executeMining, boolean isUserEnabled){
		
		if( executeMining ){
			Map<String, List<Double>> retorno = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios(systemVersion, false);
	
			Map<String, Double> mapRange = new UserScenariosStatistics().calculateCoefficientOfVariation(retorno, true);
		
			saveResults(systemVersion, mapRange);
			
			return mapRange;
			
		}else{
			return readResults(systemVersion);
		}
		
	}


	/**
	 * @param mapRange_3_21
	 */
	public void saveResults(String systemVersion, Map<String, Double> mapRange) {
		
		System.out.println("savingResults of variation at: "+new SimpleDateFormat("DD/MM/yyyy HH:mm:ss").format(new Date() ) );
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertVariationTimeRanges(mapRange, systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}
	}
	
	
	/**
	 * @param systemVersion
	 * @return
	 */
	public Map<String, Double> readResults(String systemVersion) {
		
		System.out.println("readResults");
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			return dao.readVariationTimeRanges(systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Read Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
			return null;
		}
	}

}