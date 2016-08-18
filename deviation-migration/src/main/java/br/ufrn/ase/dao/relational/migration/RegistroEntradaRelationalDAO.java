package br.ufrn.ase.dao.relational.migration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;

public class RegistroEntradaRelationalDAO extends AbstractMigrationRelationalDAO<RegistroEntrada> {

	@Override
	protected RegistroEntrada getAttributesFromRS(ResultSet rs) throws SQLException {
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

	@Override
	protected String getTableName() {
		return "registro_entrada";
	}

	@Override
	protected String getPKFieldName() {
		return "id_entrada";
	}

	
	public List<Integer> getIDListGreaterThan(int idEntrada) {
		List<Integer> list = new ArrayList<Integer>();

		try {
			PreparedStatement stmt = connection.prepareStatement(
					"select id_entrada from registro_entrada where id_entrada > ? order by id_entrada");

			stmt.setInt(1, idEntrada);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
				list.add(rs.getInt("id_entrada"));

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	
	public RegistroEntrada findByID(int idEntrada) {
		RegistroEntrada entrada = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from registro_entrada where id_entrada = ?");

			stmt.setInt(1, idEntrada);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				entrada = getAttributesFromRS(rs);

				LogDBRelationalDAO log_db_dao = new LogDBRelationalDAO();
				LogDBLeituraRelationalDAO log_db_leitura_dao = new LogDBLeituraRelationalDAO();
				LogJDBCUpdateRelationalDAO log_jdbc_update_dao = new LogJDBCUpdateRelationalDAO();
				LogMovimentoRelationalDAO log_movimento_dao = new LogMovimentoRelationalDAO();
				LogOperacaoRelationalDAO log_operacao_dao = new LogOperacaoRelationalDAO();

				entrada.setLogDB(log_db_dao.findByIdEntrada(idEntrada));
				entrada.setLogDBLeitura(log_db_leitura_dao.findByIdEntrada(idEntrada));
				entrada.setLogJDBCUpdate(log_jdbc_update_dao.findByIdEntrada(idEntrada));
				entrada.setLogMovimento(log_movimento_dao.findByIdEntrada(idEntrada));
				entrada.setLogOperacao(log_operacao_dao.findByIdEntrada(idEntrada));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entrada;
	}

	
	public List<RegistroEntrada> findAllBySystemVersion(String systemName, Date initialDate, Date finalDate) {
		List<RegistroEntrada> list = new ArrayList<RegistroEntrada>();

		try {
			PreparedStatement stmt = connection
					.prepareStatement("select * from registro_entrada where data between ? and ? and id_sistema = ?");

			stmt.setTimestamp(1, DateUtil.getDBTimestampFromDate(initialDate));
			stmt.setTimestamp(2, DateUtil.getDBTimestampFromDate(finalDate));
			stmt.setInt(3, Sistema.valueOf(systemName).getValue());

			ResultSet rs = stmt.executeQuery();

			LogOperacaoRelationalDAO log_operacao_dao = new LogOperacaoRelationalDAO();

			while (rs.next()) {
				RegistroEntrada entrada = getAttributesFromRS(rs);

				entrada.setLogOperacao(log_operacao_dao.findByIdEntrada(entrada.getIdEntrada()));

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

}
