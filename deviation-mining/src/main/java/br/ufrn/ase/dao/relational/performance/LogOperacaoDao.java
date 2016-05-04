/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational.performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.domain.degradation.Interval;
import br.ufrn.ase.util.DateUtil;

/**
 * Queries in the LogOperacao table
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class LogOperacaoDao extends AbstractBasicRelationalDAO{
	
	
	/** Common projection used in the most of query with necessary information */
	public static final String COMMON_PROJECTION = " log.action, log.hora, log.tempo, log.id_registro_entrada, registro.id_usuario ";
	
		
	public LogOperacaoDao(Connection connection){
		super(connection);
	}
	
	/**
	 * This method returns the action and the time spend for this action in an interval of data
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllBySystemVersion(String systemName, Date initialDate, Date finalDate) {
		
		/**General query for performance */
		String sql = 
				" SELECT "+COMMON_PROJECTION+
				" FROM log_operacao log " +
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE log.hora BETWEEN ? AND  ?  "+ 
				" AND log.id_sistema = ? ";
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(      3, Sistema.valueOf(systemName).getValue()      );

			ResultSet rs = stmt.executeQuery();

			list = mountCommonProjection(rs);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	/**
	 * Find all scenario that executes over average 
	 * 
	 * @param topScenarios  normally the just the top scenarios
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllOperationAboveAverage(Map<String, Double> topScenarios) {
		
		String sqlAboreAverage = 
				" SELECT "+COMMON_PROJECTION+
				" FROM log_operacao log "+
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE log.action= ? AND log.tempo > ? ";
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		// for each scenario find the execution above the average
		for (String scenario : topScenarios.keySet()) {
			
			Double average = topScenarios.get(scenario);
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sqlAboreAverage);
	
				stmt.setString(1, scenario );
				stmt.setDouble(2, average  );
	
				ResultSet rs = stmt.executeQuery();
				
				list.addAll( mountCommonProjection(rs) );
				
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	
	/**
	 * @param commonTimesOverload
	 * @return
	 */
	public List<LogOperacao> findAllOperationInTheInterval(List<Interval> commonTimesOverload, String systemName) {
		
		String sql = 
				" SELECT "+COMMON_PROJECTION+
				" FROM log_operacao log "+
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE ( log.hora between ? AND ? ) AND log.id_sistema = ? ";
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		for (Interval interval : commonTimesOverload) {
			
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
	
				stmt.setTimestamp(1, new Timestamp( DateUtil.toDate( interval.getInitialTime() ).getTime() ) );
				stmt.setTimestamp(2, new Timestamp( DateUtil.toDate( interval.getFinalTime() ).getTime() ) );
				stmt.setInt(      3, Sistema.valueOf(systemName).getValue()      );
	
				ResultSet rs = stmt.executeQuery();
				
				list.addAll(mountCommonProjection(rs));
				
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	
	/**
	 * This method returns the action and the time spend for this action in an interval of data
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllByScenario(List<String> scenarios) {
		
		if(scenarios.size() == 0 ) return new ArrayList<>();
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		int supported = 500;
		
		//    0 to 500
		//  501 to 1000
		// 1001 to 1500
		// 1501 to 2000
		// 2001 to 2500
		// 2501 to list.size()
		
		int steps = scenarios.size() / supported;
		
		for (int index = 0; index <= steps; index++) {
			
			int fromIndex = index == 0 ? 0 : (index*supported)+1;
			int toIndex = (index+1)*supported < scenarios.size() ?  (index+1)*supported : scenarios.size();
		
			List<String> scenariosTemp = scenarios.subList(fromIndex, toIndex);
			
			list.addAll(findAllByScenarioWithLimit(scenariosTemp, supported));
			
		}

		return list;
	}
	
	/***
	 * Execute the 1 query with max of 100 parameters
	 * @param scenarios
	 * @return
	 */
	private List<LogOperacao> findAllByScenarioWithLimit(List<String> scenarios, int limit) {
		
		if(scenarios.size() == 0 ) return new ArrayList<>();
		
		if(scenarios.size() > limit ) throw new IllegalArgumentException("scenarios size"+scenarios.size()+" allow: "+limit);
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
	
		StringBuilder sql = new  StringBuilder(
				" SELECT log.action, log.tempo, log.hora "+
				" FROM log_operacao log " +
				" WHERE log.action in (  ");
		
		for (int i = 0; i < scenarios.size(); i++) {
			if(i == 0 )
				sql.append("?");
			else
				sql.append(",?");
	    }
		
		sql.append(" ) ORDER BY action");
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			
			for (int i = 0; i < scenarios.size(); i++) {
		        String param = scenarios.get(i);
		        stmt.setString(i+1, param);
		    }

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LogOperacao log = new LogOperacao();
				log.setAction(  rs.getString(1) );
				log.setTempo(   rs.getInt(2)    );
				log.setHorario( DateUtil.getDateFromDBTimestamp(rs.getTimestamp(3))  );
				list.add(log);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	/**
	 * For all methods that use COMMON_PROJECTION
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<LogOperacao> mountCommonProjection(ResultSet rs) throws SQLException {
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		while (rs.next()) {
			LogOperacao log = new LogOperacao();
			log.setAction(rs.getString(1));
			log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp(2)));
			log.setTempo(rs.getInt(3));
			log.setRegistroEntrada( new RegistroEntrada()  );
			log.getRegistroEntrada().setIdUsuario(  rs.getInt(4) );
			list.add(log);
		}
		return list;
	}


}
