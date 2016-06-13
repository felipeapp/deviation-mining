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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;
import br.ufrn.ase.util.DateUtil;

/**
 *  <p>This DAO has expecific queries the just work for most access
 *  and highest average scenarios bacause the functions COUNT and AVG of SQL </p>
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class SpecificQueriesDao extends AbstractBasicRelationalDAO {
	
	public SpecificQueriesDao(Connection connection){
		super(connection);
	}
	
	public Map<String, Double> findMostAccessScenarios(int systemId, Date initialDate, Date finalDate){

		Map<String, Double> retorno = new HashMap<String, Double>();
		
		String sql = " SELECT log.action, COUNT(log.tempo) "
				+" FROM log_operacao log "
				+" WHERE log.hora BETWEEN ? AND ? AND log.id_sistema = ? " 
				+" GROUP BY log.action "
				+" ORDER BY 2 DESC ";
		
		try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				retorno.put(rs.getString(1), rs.getDouble(2));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	
	public Map<String, Double> findHighestAverageScenarios(int systemId, Date initialDate, Date finalDate){
		
		Map<String, Double> retorno = new HashMap<String, Double>();
		
		String sql = " SELECT log.action, AVG(log.tempo) "
				+" FROM log_operacao log "
				+" WHERE log.hora BETWEEN ? AND ? AND log.id_sistema = ? " 
				+" GROUP BY log.action "
				+" ORDER BY 2 DESC ";
		
		try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				retorno.put(rs.getString(1), rs.getDouble(2));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retorno;
	} 

}
