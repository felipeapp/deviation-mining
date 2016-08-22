/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational.performance.mining;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	
	/** Common projection used in the analisis de erros*/
	public static final String COMMON_PROJECTION_ERROR = " log.action, log.hora, log.tempo, log.trace, log.id_registro_entrada, registro.id_usuario ";
	
		
	public LogOperacaoDao(Connection connection){
		super(connection);
	}
	
	/**
	 * MAIN query used in the tools for performance operation
	 * 
	 * This method returns the action and the time spend for this action in an interval of data
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllLogOperacaoInsideIntervalBySystemVersion(int systemId, Date initialDate, Date finalDate) {
		
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
			stmt.setInt(3, systemId);

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
	 * MAIN query used in the tools for performance operation
	 * 
	 * This method returns the action and the time spend for this action in an interval of data
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllLogOperacaoInsideIntervalBySystemVersionForError(int systemId, Date initialDate, Date finalDate) {
		
		/**General query for performance */
		String sql = 
				" SELECT "+COMMON_PROJECTION_ERROR+
				" FROM log_operacao log " +
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE log.hora BETWEEN ? AND  ?  "+ 
				" AND log.id_sistema = ? AND log.erro = ? ";
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);
			stmt.setBoolean(4, true);

			ResultSet rs = stmt.executeQuery();

			list = mountCommonProjectionForErro(rs);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	/**
	 * 
	 * This method returns the time spend for a specific action in an interval of data for a system
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public Map<String, List<Double>> findAllLogOperacaoOfScenarioInsideIntervalBySystemVersion(String scenario, int systemId, Date initialDate, Date finalDate) {
		
		/**General query for performance */
		String sql = 
				" SELECT log.action, log.tempo "+
				" FROM log_operacao log " +
				" WHERE log.hora BETWEEN ? AND  ? AND log.id_sistema = ? AND log.action = ? ";
		
		Map<String, List<Double>> map = new HashMap<>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);
			stmt.setString(4, scenario);

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String action = rs.getString(1);
				Double time = new Double(rs.getInt( 2) );
				
				if(map.containsKey(action)){
					map.get(action).add( time );
				}else{
					List<Double> array = new ArrayList<>();
					array.add( time );
					map.put(action, array);
				}
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}
	
	
	/**
	 * Find all scenario that executes over average 
	 * 
	 * @param topScenarios  normally the just the top scenarios
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllOperationAboveAverage(Map<String, Double> topScenarios, Date initialDate, Date finalDate) {
		
		String sqlAboreAverage = 
				" SELECT "+COMMON_PROJECTION+
				" FROM log_operacao log "+
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE log.action= ? AND log.tempo > ? AND (log.hora between ? AND ?)";
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		// for each scenario find the execution above the average
		for (String scenario : topScenarios.keySet()) {
			
			Double average = topScenarios.get(scenario);
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sqlAboreAverage);
	
				stmt.setString(1, scenario );
				stmt.setDouble(2, average  );
				stmt.setDate(3, new java.sql.Date( initialDate.getTime())  );
				stmt.setDate(4, new java.sql.Date( finalDate.getTime()  )  );
	
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
	public List<String> findAllScenariosInTheInterval(List<Interval> commonTimesOverload, String systemName) {
		
		String sql = 
				" SELECT DISTINCT log.action "+
				" FROM log_operacao log "+
				" INNER JOIN registro_entrada registro ON registro.id_entrada = log.id_registro_entrada "+
				" WHERE ( log.hora between ? AND ? ) AND log.id_sistema = ? ";
		
		Set<String> set = new HashSet<String>();
		
		for (Interval interval : commonTimesOverload) {
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
	
				stmt.setTimestamp(1, new Timestamp( DateUtil.toDate( interval.getInitialTime() ).getTime() ) );
				stmt.setTimestamp(2, new Timestamp( DateUtil.toDate( interval.getFinalTime() ).getTime() ) );
				stmt.setInt(      3, Sistema.valueOf(systemName).getValue()      );
	
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					set.add( rs.getString(1) );
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return new ArrayList<String>(set);
	}
	
	
	/**
	 * This method returns the action and the time spend for this action in an interval of data
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<LogOperacao> findAllByScenario(List<String> scenarios, Date initialDate, Date finalDate) {
		
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
			
			list.addAll(findAllByScenarioWithLimit(scenariosTemp, initialDate, finalDate, supported));
			
		}

		return list;
	}
	
	/***
	 * Execute the 1 query with max of 100 parameters
	 * @param scenarios
	 * @return
	 */
	private List<LogOperacao> findAllByScenarioWithLimit(List<String> scenarios, Date initialDate, Date finalDate, int limit) {
		
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
		
		sql.append(" ) ");
		
		sql.append(" AND (log.hora between ? AND ? ) ");
		
		sql.append(" ORDER BY action");
		
		try {
			
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			
			int paramIndex =0;
			for ( ; paramIndex < scenarios.size(); paramIndex++) {
		        String param = scenarios.get(paramIndex);
		        stmt.setString(paramIndex+1, param);
		    }

			stmt.setDate(paramIndex+1, new java.sql.Date( initialDate.getTime())  );
			stmt.setDate(paramIndex+2, new java.sql.Date( finalDate.getTime()  )  );
			
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
			log.getRegistroEntrada().setIdEntrada(  rs.getInt(4) );
			log.getRegistroEntrada().setIdUsuario(  rs.getInt(5) );
			list.add(log);
		}
		return list;
	}

	
	/**
	 * For all methods that use COMMON_PROJECTION
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<LogOperacao> mountCommonProjectionForErro(ResultSet rs) throws SQLException {
		List<LogOperacao> list = new ArrayList<LogOperacao>();
		
		while (rs.next()) {
			LogOperacao log = new LogOperacao();
			log.setAction(rs.getString(1));
			log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp(2)));
			log.setTempo(rs.getInt(3));
			log.setTrace(  rs.getString(4) );
			log.setRegistroEntrada( new RegistroEntrada()  );
			log.getRegistroEntrada().setIdEntrada(  rs.getInt(5) );
			log.getRegistroEntrada().setIdUsuario(  rs.getInt(6) );
			list.add(log);
		}
		return list;
	}

}
