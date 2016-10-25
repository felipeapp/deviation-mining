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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	/**
	 * READ All scenarios after execute the mining
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<String> readAllScenariosError() {
		
		String sql = " SELECT scenario FROM temporary.scenarios_error as scenarios ";
		
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

	
	/***
	 * Read a specific scenario values
	 * 
	 * @param scenario
	 * @return
	 */
	public List<String> readScenariosErrorValues(String scenario){
		
		String sql = " SELECT trace FROM temporary.scenarios_error_trace scenarios WHERE scenario = ?";
		
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario  );
			
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
	public List<String> readScenariosValuesForError(String scenario){
		
		String sql = " SELECT v.trace "
				+ " FROM temporary.scenarios_error scenarios "
				+ " INNER JOIN temporary.scenarios_error_trace v on scenario = scenarios.scenario  "
				+ " WHERE scenarios.scenario = ? "
				;
		
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario  );
			
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
	

	/**
	 * @param scenario
	 * @return
	 */
	public boolean contaisScenarios(String scenario) {

		String sql = " SELECT COUNT(*) FROM temporary.scenarios_executions scenarios WHERE scenario = ? ";
		
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
	 * @return
	 */
	public boolean contaisScenariosError(String scenario) {

		String sql = " SELECT COUNT(*) FROM temporary.scenarios_error scenarios WHERE scenario = ? ";
		
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
	
	
	/**
	 * @param scenario
	 * @param list
	 */
	public void insertNewScenarioError(String scenario) {
		
		String sql = " INSERT INTO temporary.scenarios_error ( scenario) values (?) ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, scenario);
			
			
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
	public void insertScenarioErrorValues(String scenario, String trace) {

		String sql = " INSERT INTO temporary.scenarios_error_trace (trace, scenario) VALUES (?, ?) ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
				
			stmt.setString(1, trace);
			stmt.setString(2, scenario);
			
			stmt .executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	///  ErrorGerador queries ///
	
	/**
	 * @param scenario
	 * @return
	 */
	public boolean containsScenarioLineOfCode(String scenario) {
		String sql = " SELECT COUNT(*) FROM temporary.infra_error_scenario_line_of_code WHERE scenario_line_of_code = ? ";
		
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
	 * @param qtdError
	 */
	public void updateQtdScenarioLineOfCode(String scenario, Integer qtdError) {
		
		int qtdCurrent = 0;
		
		String sqlSelect = " SELECT qtd FROM temporary.infra_error_scenario_line_of_code WHERE scenario_line_of_code = ? ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sqlSelect);
			stmt.setString(1, scenario );
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				qtdCurrent = rs.getInt(1);
				
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		String sql = " UPDATE temporary.infra_error_scenario_line_of_code set qtd = ? WHERE scenario_line_of_code = ? ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, qtdCurrent + qtdError);
			stmt.setString(2, scenario);
			
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * @param scenario
	 * @param qtdError
	 */
	public void insertNewQtdScenarioLineOfCode(String scenario, Integer qtdError) {
		String sql = " INSERT INTO temporary.infra_error_scenario_line_of_code (qtd, scenario_line_of_code) VALUES (?, ?) ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
				
			stmt.setInt(1, qtdError);
			stmt.setString(2, scenario);
			
			stmt .executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Clear always before execute a new mining.
	 */
	public void clearErrorGeradorTable() {
		String sql = " DELETE FROM temporary.infra_error_scenario_line_of_code ";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt .executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * READ All scenarios after execute the mining
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public Map<String, Integer> readAllErrorGerador() {
		
		String sql = " SELECT scenario_line_of_code, qtd FROM temporary.infra_error_scenario_line_of_code ";
		
		Map<String, Integer> retorno = new HashMap<String, Integer>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				retorno.put(rs.getString(1), rs.getInt(2));
			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retorno;
	}
	
	
	
	
	

}
