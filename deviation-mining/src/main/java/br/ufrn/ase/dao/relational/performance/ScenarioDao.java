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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;
import br.ufrn.ase.util.DateUtil;

/**
 * Dao to make queries for scenarios.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ScenarioDao extends AbstractBasicRelationalDAO {
	
	public ScenarioDao(Connection connection){
		super(connection);
	}
	
	
	/**
	 * return All scenarios of a system
	 * 
	 * @param systemId
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<String> findAllScenariosByDateAndSystem(int systemId, Date initialDate, Date finalDate){

		List<String> retorno = new ArrayList<String>();
		
		String sql = " SELECT DISTINCT log.action "
				+" FROM log_operacao log "
				+" WHERE log.hora BETWEEN ? AND ? AND log.id_sistema = ? "
				+" AND action NOT LIKE '%/a4j/%' ";
		
		try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				retorno.add(rs.getString(1));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
}
