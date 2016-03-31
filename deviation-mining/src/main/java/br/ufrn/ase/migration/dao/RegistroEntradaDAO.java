package br.ufrn.ase.migration.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.ase.dao.Database;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.MigrationUtil;

public class RegistroEntradaDAO extends AbstractDAO<RegistroEntrada> {

	public RegistroEntradaDAO() {
		super();
	}

	public int getMaxIdEntrada() {
		MongoOperations mongoOp = Database.buildMongoDatabase();

		RegistroEntrada r = mongoOp.findOne(new Query().with(new Sort(Sort.Direction.DESC, "idEntrada")),
				RegistroEntrada.class);

		return r == null ? 0 : r.getIdEntrada();
	}

	@Override
	protected RegistroEntrada getAttributesFromRS(ResultSet rs) throws SQLException {
		RegistroEntrada entrada = new RegistroEntrada();

		entrada.setCanal(rs.getString("canal"));
		entrada.setDataEntrada(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		entrada.setDataSaida(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data_saida")));
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
	protected List<RegistroEntrada> findByIdEntrada(int id_entrada) {
		return null;
	}

	public List<Integer> getIDListGT(int id_entrada) {
		List<Integer> list = new ArrayList<Integer>();

		try {
			PreparedStatement stmt = connection.prepareStatement(
					"select id_entrada from registro_entrada where id_entrada > ? order by id_entrada");

			stmt.setInt(1, id_entrada);

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

	public RegistroEntrada findByID(int id_entrada) {
		RegistroEntrada entrada = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from registro_entrada where id_entrada = ?");

			stmt.setInt(1, id_entrada);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				entrada = getAttributesFromRS(rs);

				LogDBDAO log_db_dao = new LogDBDAO();
				LogDBLeituraDAO log_db_leitura_dao = new LogDBLeituraDAO();
				LogJDBCUpdateDAO log_jdbc_update_dao = new LogJDBCUpdateDAO();
				LogMovimentoDAO log_movimento_dao = new LogMovimentoDAO();
				LogOperacaoDAO log_operacao_dao = new LogOperacaoDAO();

				entrada.setLogDB(log_db_dao.findByIdEntrada(id_entrada));
				entrada.setLogDBLeitura(log_db_leitura_dao.findByIdEntrada(id_entrada));
				entrada.setLogJDBCUpdate(log_jdbc_update_dao.findByIdEntrada(id_entrada));
				entrada.setLogMovimento(log_movimento_dao.findByIdEntrada(id_entrada));
				entrada.setLogOperacao(log_operacao_dao.findByIdEntrada(id_entrada));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entrada;
	}

	public static void main(String[] args) {

		RegistroEntradaDAO dao = new RegistroEntradaDAO();

		long start = System.currentTimeMillis();

		List<Integer> ids = dao.getIDListGT(0);

		System.out.println("Total de entradas: " + ids.size());

		for (int i = 0; i < ids.size(); i++) {
			System.out.println(i + 1 + " / " + ids.size());
			System.out.println(dao.findByID(ids.get(i)));
		}

		System.out.println((System.currentTimeMillis() - start) / 1000.0);

	}

}
