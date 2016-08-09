/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational.performance.temporary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;

/**
 * Uses a temporary database to calculate informatio about mining before write in the resulta data base.
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class TemporaryDataAnalysisDAO extends AbstractBasicRelationalDAO{

	/**
	 * @param connection
	 */
	public TemporaryDataAnalysisDAO(Connection connection) {
		super(connection);
	}
	
	
	/**
	 * READ All scenarios after execute the mining
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<String> readAllScenarios() {
		
		String sql = " SELECT scenario FROM temporary.scenarios_executions scenarios ";
		
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/***
	 * Read a specific scenario values
	 * 
	 * @param scenario
	 * @return
	 */
	public List<Double> readScenariosValues(String scenario){
		
		String sql = " SELECT values FROM temporary.scenarios_executions scenarios WHERE scenario = ?";
		
		List<Double> list = new ArrayList<Double>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario  );
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String valuesAsString = rs.getString(1);
				
				if(valuesAsString != null && valuesAsString.length() > 0 ){
	            	
		            String[] valuesArray = valuesAsString.split(",");
		           
		            for (String value : valuesArray) {
						list.add(Double.valueOf(value));
					}
	            }
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}


	/**
	 * @param scenario
	 * @return
	 */
	public boolean contaisScenarios(String scenario) {

		String sql = " SELECT COUNT(*) FROM temporary.scenarios_executions scenarios WHERE scenario = :scenario ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario );
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) 
					return true;
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	
		return false;
	}


	/**
	 * @param scenario
	 * @param list
	 */
	public void updateScenarioValues(String scenario, List<Double> list) {

		String sql = " UPDATE temporary.scenarios_executions set values = ? WHERE scenario = ? ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, list.toString().replace("[", "").replace("]", ""));
			stmt.setString(2, scenario);
			
			stmt .executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * @param scenario
	 * @param list
	 */
	public void insertNewScenario(String scenario, List<Double> list) {
		
		String sql = " INSERT INTO temporary.scenarios_executions ( scenario, values) values (?, ?) ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario);
			stmt.setString(2, list.toString().replace("[", "").replace("]", ""));
			
			stmt .executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
