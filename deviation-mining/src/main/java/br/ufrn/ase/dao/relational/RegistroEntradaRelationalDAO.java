package br.ufrn.ase.dao.relational;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.service.performance.UserScenariosService;
import br.ufrn.ase.util.DateUtil;

public class RegistroEntradaRelationalDAO extends AbstractMigrationRelationalDAO<RegistroEntrada>
		implements RegistroEntradaDAO {

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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public int getMaxIdEntrada() {
		throw new UnsupportedOperationException("getMaxIdEntrada() is not implemented by RegistroEntradaRelationalDAO");
	}

	@Override
	public void insert(RegistroEntrada entrada) {
		throw new UnsupportedOperationException("insert() is not implemented by RegistroEntradaRelationalDAO");
	}

	public static void _main(String[] args) {
		RegistroEntradaRelationalDAO dao = new RegistroEntradaRelationalDAO();

		long start = System.currentTimeMillis();

		List<Integer> ids = dao.getIDListGreaterThan(0);

		System.out.println("Total de entradas: " + ids.size());

		for (int i = 0; i < ids.size(); i++) {
			System.out.println(i + 1 + " / " + ids.size());
			System.out.println(dao.findByID(ids.get(i)));
		}

		System.out.println((System.currentTimeMillis() - start) / 1000.0);
	}

	public static void main(String[] args) {
		UserScenariosService s = new UserScenariosService();

		System.out.println("######################################");

		long start = System.currentTimeMillis();

		Map<String, List<Double>> map = s.findUserScenario("SIGAA-3.21.0", true);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");
		System.out.println("Size: " + map.size());
	}

}
