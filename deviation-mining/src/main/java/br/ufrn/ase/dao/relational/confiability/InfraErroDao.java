/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational.confiability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.ase.dao.relational.AbstractBasicRelationalDAO;
import br.ufrn.ase.domain.InfraError;
import br.ufrn.ase.util.CalendarUtil;
import br.ufrn.ase.util.DateUtil;

/**
 * make queries on the infra.erro table.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class InfraErroDao extends AbstractBasicRelationalDAO{

	/**
	 * @param connection
	 */
	public InfraErroDao(Connection connection) {
		super(connection);
	}

	
	/**
	 *  MAKE the mining on the infra.erro table
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<InfraError> findAllErrorsInsideIntervalBySystemVersion(int systemId, Date initialDate, Date finalDate) {
		
		if(initialDate == null || finalDate == null ) return new ArrayList<>();
		
		finalDate = CalendarUtil.configDataTime(finalDate, 23, 59, 59, 000);
		
		/**General query for performance */
		String sql = 
				" SELECT error.trace_completo, trace_gerador, excecao"+
				" FROM infra.erro error " +
				" WHERE error.data BETWEEN ?  AND  ?  AND error.id_sistema = ? ";
		
		List<InfraError> list = new ArrayList<InfraError>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(3, systemId);

			ResultSet rs = stmt.executeQuery();

			list = mountProjection(rs);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}


	/**
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private List<InfraError> mountProjection(ResultSet rs) throws SQLException {
		List<InfraError> list = new ArrayList<InfraError>();
		
		while (rs.next()) {
			list.add(   new InfraError(rs.getString(1), rs.getString(2), rs.getString(3))   );
		}
		return list;
	}
	
	
}
