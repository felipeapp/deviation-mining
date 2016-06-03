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

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, scenario);
				prepated.setString(2, system_version);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
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

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
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

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, scenario);
				prepated.setString(2, system_version);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
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

		String sql = " SELECT scenario, qtd_access FROM result.most_access_scenarios WHERE system_version = ? ORDER BY variation DESC";

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

		try (PreparedStatement prepated = connection.prepareStatement(sql);) {

			for (String scenario : mapRange.keySet()) {
				prepated.setString(1, systemVersion);
				prepated.setString(2, scenario);
				prepated.setDouble(3, mapRange.get(scenario));
				prepated.addBatch();
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
	

}
