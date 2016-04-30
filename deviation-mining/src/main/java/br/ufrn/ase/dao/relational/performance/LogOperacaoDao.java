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
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;

/**
 * Queries in the LogOperacao table
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class LogOperacaoDao extends AbstractBasicRelationalDAO{
	
	
	private String sqlForPerformance = 
			" SELECT log.action, log.hora, log.tempo, log.id_registro_entrada, r.id_usuario "+
			" FROM log_operacao log " +
			" INNER JOIN registro_entrada r ON r.id_entrada = log.id_registro_entrada "+
			" WHERE log.hora BETWEEN ? AND  ?  "+ 
			" AND log.id_sistema = ? ";
	
	
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
		
		List<LogOperacao> list = new ArrayList<LogOperacao>();

		try {
			PreparedStatement stmt = connection.prepareStatement(sqlForPerformance);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate)  );
			stmt.setInt(      3, Sistema.valueOf(systemName).getValue()      );

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				LogOperacao log = new LogOperacao();

				log.setAction(rs.getString(1));
				log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp(2)));
				log.setTempo(rs.getInt(3));
				log.setRegistroEntrada( new RegistroEntrada()  );
				log.getRegistroEntrada().setIdUsuario(  rs.getInt(4) );
				list.add(log);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
