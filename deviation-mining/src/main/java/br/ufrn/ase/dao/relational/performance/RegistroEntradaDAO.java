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
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class RegistroEntradaDAO extends AbstractBasicRelationalDAO{
	
	
	public RegistroEntradaDAO(Connection connection){
		super(connection);
	}
	
	
	/**
	 * Retrieves ALL information of registro de entrada between data and for each registro de entrada, All information for log operacao
	 * 
	 * @param systemName
	 * @param initialDate
	 * @param finalDate
	 * @return
	 */
	public List<RegistroEntrada> findAllBySystemVersion(String systemName, Date initialDate, Date finalDate) {
		
		List<RegistroEntrada> list = new ArrayList<RegistroEntrada>();

		String sql = "select * from registro_entrada where data between ? and ? and id_sistema = ?";
		
		// find all RegistroEntrada
		try {
		
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate));
			stmt.setInt(3, Sistema.valueOf(systemName).getValue());

			ResultSet rs = stmt.executeQuery();

			// for each RegistroEntrada, do a new query to recovery logOperacao
			while (rs.next()) {
				RegistroEntrada entrada = createCompleteRegistroEntrataFromRS(rs);

				entrada.setLogOperacao(findAllLogOoperacaoByIdEntrada(entrada.getIdEntrada()));

				if (!entrada.getLogOperacao().isEmpty())
					list.add(entrada);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * Retrieves ALL information of log operacao for a specific registro de entrada
	 * 
	 * @param id_entrada
	 * @return
	 */
	public List<LogOperacao> findAllLogOoperacaoByIdEntrada(int id_entrada) {
		
		List<LogOperacao> logs = new ArrayList<LogOperacao>();

		try {
			PreparedStatement stmt = connection.prepareStatement(" select * from log_operacao where id_registro_entrada = ? order by id_operacao " );

			stmt.setInt(1, id_entrada);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
				logs.add(createLogOperacaoFromRS(rs));

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logs;
	}
	
	
	private RegistroEntrada createCompleteRegistroEntrataFromRS(ResultSet rs) throws SQLException {
		RegistroEntrada entrada = new RegistroEntrada();

		entrada.setCanal(rs.getString("canal"));
		entrada.setDataEntrada(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		entrada.setDataSaida(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("data_saida")));
		entrada.setHost(rs.getString("host"));
		entrada.setIdEntrada(rs.getInt("id_entrada"));
		entrada.setIdUsuario(rs.getInt("id_usuario"));
		entrada.setIp(rs.getString("ip"));
		entrada.setIpInternoNat(rs.getString("ip_interno_nat"));
		entrada.setPassaporte(rs.getInt("passaporte"));
		entrada.setResolucao(rs.getString("resolucao"));
		entrada.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		entrada.setUserAgent(rs.getString("user_agent"));

		return entrada;
	}
	
	private LogOperacao createLogOperacaoFromRS(ResultSet rs) throws SQLException {
		LogOperacao log = new LogOperacao();

		log.setAction(rs.getString("action"));
		log.setErro(rs.getBoolean("erro"));
		log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("hora")));
		log.setIdAcessoPublico(rs.getInt("id_acesso_publico"));
		log.setIdOperacao(rs.getInt("id_operacao"));
		log.setMensagens(rs.getString("mensagens"));
		log.setParametros(rs.getString("parametros"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		log.setTempo(rs.getInt("tempo"));
		log.setTrace(rs.getString("trace"));

		return log;
	}

}
