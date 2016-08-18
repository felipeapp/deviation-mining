/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.basic;

import java.sql.SQLException;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.result.ResultDataAnalysisDAO;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class HighestAverageService {


	/**
	 * @param mapRange_3_21
	 */
	public void saveResults(String systemVersion, Map<String, Double> mapRange) {
		
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertAverageTime(mapRange, systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}finally{
			DAOFactory.closeResultConnection();
		}
	}
	
	
	/**
	 * @param systemVersion
	 * @return
	 */
	public Map<String, Double> readResults(String systemVersion) {
		
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			return dao.readAverageTime(systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Read Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
			return null;
		}finally{
			DAOFactory.closeResultConnection();
		}
	}

}
