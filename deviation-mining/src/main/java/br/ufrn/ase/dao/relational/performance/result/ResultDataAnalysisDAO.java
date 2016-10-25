/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational.performance.result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;
import br.ufrn.ase.util.MapUtil;

/**
 * <p>
 * This DAO connect with the database where we save the result of the data
 * analysis to show quickly the processed results
 * </p>
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ResultDataAnalysisDAO extends AbstractBasicRelationalDAO {

	public ResultDataAnalysisDAO(Connection connection){
		super(connection);
	}
	
	
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param map
	 * @throws SQLException
	 */
	public void insertVariationTimeRanges(Map<String, Double> mapRange, String system_version) throws SQLException {

		String sql = " INSERT INTO result.variation_time_range ( scenario, system_version, variation) VALUES (?, ?, ?) ";

		final int batchSize = 1000;
		int count = 0;
		int mapSize = mapRange.size();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, scenario);
				prepated.setString(2, system_version);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
				
				if(++count % batchSize == 0) {
					System.out.println("Inserting "+count+" of "+mapSize);
					prepated.executeBatch();
			    }
			}

			prepated.executeBatch();
		}
	}
	

	/**
	 * @param system_version
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Double> readVariationTimeRanges(String system_version) throws SQLException {

		String sql = " SELECT scenario, variation FROM result.variation_time_range WHERE system_version = ? ORDER BY variation DESC";

		Map<String, Double> map = new HashMap<>();

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, system_version);

			ResultSet rs = prepated.executeQuery();

			while (rs.next()) {
				String key = rs.getString(1);
				Double value = rs.getDouble(2);
				map.put(key, value);
			}
		}

		return MapUtil.sortByValue(map);
	}

	
	/////////////////////////////////////////////////////////////////////

	
	
	/////////////////////////////////////////////////////////////////////
	
	
	/**
	 * @param map
	 * @throws SQLException
	 */
	public void insertAverageTime(Map<String, Double> mapRange, String systemVersion) throws SQLException {

		String sql = " INSERT INTO result.average_time_execution ( system_version, scenario, average) VALUES (?, ?, ?) ";

		final int batchSize = 1000;
		int count = 0;
		int mapSize = mapRange.size();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
				
				if(++count % batchSize == 0) {
					System.out.println("Inserting "+count+" of "+mapSize);
					prepated.executeBatch();
			    }
			}

			prepated.executeBatch();
		}
	}
	
	/**
	 * @param system_version
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Double> readAverageTime(String system_version) throws SQLException {

		String sql = " SELECT scenario, average FROM result.average_time_execution WHERE system_version = ? ORDER BY average DESC";

		Map<String, Double> map = new HashMap<>();

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, system_version);

			ResultSet rs = prepated.executeQuery();

			while (rs.next()) {
				String key = rs.getString(1);
				Double value = rs.getDouble(2);
				map.put(key, value);
			}
		}

		return MapUtil.sortByValue(map);
	}
	
	
	/////////////////////////////////////////////////////////////////////
	
	
	
	/////////////////////////////////////////////////////////////////////

	/**
	 * @param map
	 * @throws SQLException
	 */
	public void insertMostAccess(Map<String, Double> mapRange, String system_version) throws SQLException {

		String sql = " INSERT INTO result.most_access_scenarios ( scenario, system_version, qtd_access) VALUES (?, ?, ?) ";

		final int batchSize = 1000;
		int count = 0;
		int mapSize = mapRange.size();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, scenario);
				prepated.setString(2, system_version);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
				
				if(++count % batchSize == 0) {
					System.out.println("Inserting "+count+" of "+mapSize);
					prepated.executeBatch();
			    }
			}

			prepated.executeBatch();
		}
	}

	/**
	 * @param system_version
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Double> readMostAccesss(String system_version) throws SQLException {

		String sql = " SELECT scenario, qtd_access FROM result.most_access_scenarios WHERE system_version = ? ORDER BY qtd_access DESC";

		Map<String, Double> map = new HashMap<>();

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, system_version);

			ResultSet rs = prepated.executeQuery();

			while (rs.next()) {
				String key = rs.getString(1);
				Double value = rs.getDouble(2);
				map.put(key, value);
			}
		}

		return MapUtil.sortByValue(map);
	}

	///////////////////////////////////////////////////////////////////////////
	
	
	
	/////////////////////////////////////////////////////////////////////

	/**
	 * @param map
	 * @throws SQLException
	 */
	public void insertMedianTime(Map<String, Double> mapRange, String systemVersion) throws SQLException {

		String sql = " INSERT INTO result.median_time_execution ( system_version, scenario, median) VALUES (?, ?, ?) ";

		final int batchSize = 1000;
		int count = 0;
		int mapSize = mapRange.size();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
				
				if(++count % batchSize == 0) {
					System.out.println("Inserting "+count+" of "+mapSize);
					prepated.executeBatch();
			    }
			}

			prepated.executeBatch();
		}
	}

	/**
	 * @param system_version
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Double> readMedianTime(String system_version) throws SQLException {

		String sql = " SELECT scenario, median FROM result.median_time_execution WHERE system_version = ? ORDER BY median DESC";

		Map<String, Double> map = new HashMap<>();

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, system_version);

			ResultSet rs = prepated.executeQuery();

			while (rs.next()) {
				String key = rs.getString(1);
				Double value = rs.getDouble(2);
				map.put(key, value);
			}
		}

		return MapUtil.sortByValue(map);
	}

	/////////////////////////////////////////////////////////////////////
	
	
	
	
/////////////////////////////////////////////////////////////////////

	/**
	 * @param map
	 * @throws SQLException
	 */
	public void insertTotalOfError(String scenario, int qtdError, String systemVersion) throws SQLException {

		String sql = " INSERT INTO result.total_of_error_by_scenario ( system_version, scenario, qtd_error) VALUES (?, ?, ?) ";

		//final int batchSize = 1000;
		//int count = 0;
		//int mapSize = mapRange.size();

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setInt(3, qtdError);
				//prepated.addBatch();

//				if (++count % batchSize == 0) {
//					System.out.println("Inserting " + count + " of " + mapSize);
//					prepated.execute();
//				}
//			}

			prepated.execute();
		}
	}



	/**
	 * @param systemVersion
	 * @param scenario
	 * @param result
	 * @throws SQLException 
	 */
	public void insertTotalOfErrorByScenario(String systemVersion, String scenario, Map<String, Integer> result) throws SQLException {
		
		String sql = " INSERT INTO result.total_of_error_by_trace ( system_version, scenario, trace, qtd_error) VALUES (?, ?, ?, ?) ";

		final int batchSize = 1000;
		int count = 0;
		int mapSize = result.size();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String trace : result.keySet()) {
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setString(3, trace);
				prepated.setInt(4, result.get(trace));
				prepated.addBatch();
				
				if(++count % batchSize == 0) {
					System.out.println("Inserting "+count+" of "+mapSize);
					prepated.executeBatch();
			    }
			}

			prepated.executeBatch();
		}
		
	}



	/**
	 * @param systemVersion
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Integer> readScenariosWithError(String systemVersion) throws SQLException {
		
		String sql = " SELECT scenario, qtd_error FROM result.total_of_error_by_scenario WHERE system_version = ?  ";
		
		Map<String, Integer> qtdError= new HashMap<>();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, systemVersion);
			
			ResultSet result = prepated.executeQuery();
			
			
			while(result.next()){
				qtdError.put(result.getString(1), result.getInt(2));
			}
		}
		
		return qtdError;
	}
	
	
	/**
	 * @param systemVersion
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Integer> readQtdAccessScenarios(String systemVersion) throws SQLException {
		
		String sql = " SELECT scenario, qtd_access FROM result.most_access_scenarios WHERE system_version = ?  ";
		
		Map<String, Integer> qtdAccess = new HashMap<>();
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, systemVersion);
			
			ResultSet result = prepated.executeQuery();
			
			while(result.next()){
				qtdAccess.put(result.getString(1), result.getInt(2));
			}
		}
		
		return qtdAccess;
	}



	/**
	 * @param systemVersion
	 * @param scenario
	 * @param qtdAccess
	 * @param qtdError
	 * @param i
	 * @throws SQLException 
	 */
	public void insertPercentageOfError(String systemVersion, String scenario, int qtdAccess, int qtdError, float percentage) throws SQLException {
		
		
		String sql = " INSERT INTO result.error_statistic ( system_version, scenario, qtd_access, qtd_error, percentage) VALUES (?, ?, ?, ?, ?) ";
		
		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			prepated.setString(1, systemVersion);
			prepated.setString(2, scenario);
			prepated.setInt(3, qtdAccess);
			prepated.setInt(4, qtdError);
			prepated.setFloat(5, percentage);
			
			prepated.executeUpdate();
		}
		
		
	}


	///  Error Gerador ////

	/**
	 * @param systemVersion
	 * @param scenario
	 * @param traceGerador
	 * @param exception
	 * @param qtd
	 */
	public void insertErrorGerador(String systemVersion, String scenario, String traceGerador, String exception, int qtd) throws SQLException {
	
		boolean exist = false;
		
		String sqlCount = " SELECT COUNT(*) FROM result.infra_error_erro_gerador WHERE system_version = ? AND scenario = ? ";
		
		try ( PreparedStatement stmt = connection.prepareStatement(sqlCount) ) {
			
			stmt.setString(1, systemVersion );
			stmt.setString(2, scenario );
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) 
					exist =  true;
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if( ! exist ) {
			String sqlErro = " INSERT INTO result.infra_error_erro_gerador ( system_version, scenario) VALUES (?, ?) ";
			
			try (PreparedStatement prepated = connection.prepareStatement(sqlErro);) {
	
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.executeUpdate();
			}
		}
		
		
		// here we can have repeated lines //
		String sqlOcorrencias = " INSERT INTO result.infra_error_erro_gerador_ocorrencias ( system_version, scenario, trace_gerador, exception, qtd) VALUES (?, ?, ?, ?, ?) ";
		
		try (PreparedStatement prepated = connection.prepareStatement(sqlOcorrencias);) {

			prepated.setString(1, systemVersion);
			prepated.setString(2, scenario);
			prepated.setString(3, traceGerador);
			prepated.setString(4, exception);
			prepated.setInt(5, qtd);
			prepated.executeUpdate();
		}
		
		
	}
	
	
	

}
