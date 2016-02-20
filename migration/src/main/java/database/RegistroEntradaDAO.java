package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.RegistroEntrada;
import domain.Sistema;
import util.MigrationUtil;

public class RegistroEntradaDAO extends AbstractDAO {

	public RegistroEntradaDAO() {
		super();
	}

	private RegistroEntrada getAttributesFromRS(ResultSet rs) throws SQLException {
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

	public List<Integer> getIDList() {
		List<Integer> list = new ArrayList<Integer>();

		try (PreparedStatement stmt = connection.prepareStatement("select id_entrada from registro_entrada");
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next())
				list.add(rs.getInt("id_entrada"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public RegistroEntrada findByID(int id) {
		RegistroEntrada entrada = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from registro_entrada where id_entrada = ?");

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				entrada = getAttributesFromRS(rs);

				LogDBDAO log_db_dao = new LogDBDAO();

				entrada.setLogDB(log_db_dao.findByIdEntrada(id));
				entrada.setLogDBLeitura(null);
				entrada.setLogJDBCUpdate(null);
				entrada.setLogMovimento(null);
				entrada.setLogOperacao(null);

				log_db_dao.close();
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

		List<Integer> ids = dao.getIDList();

		System.out.println("Total de entradas: " + ids.size());

		for (int i = 0; i < ids.size(); i++) {
			System.out.println(i + 1 + " / " + ids.size());
			System.out.println(dao.findByID(ids.get(i)));
		}

		System.out.println((System.currentTimeMillis() - start) / 1000.0);

		dao.close();

	}

}
