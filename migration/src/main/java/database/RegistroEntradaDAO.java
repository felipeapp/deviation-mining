package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.RegistroEntrada;
import domain.Sistema;

public class RegistroEntradaDAO extends AbstractDAO {

	public RegistroEntradaDAO() {
		super();
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
		RegistroEntrada re = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from registro_entrada where id_entrada = ?");

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				re = new RegistroEntrada(rs.getInt("id_entrada"), rs.getInt("id_usuario"), rs.getDate("data"),
						rs.getDate("data_saida"), rs.getString("ip"), rs.getString("ip_interno_nat"),
						rs.getString("host"), rs.getString("user_agent"), rs.getString("resolucao"),
						rs.getInt("passaporte"), rs.getString("canal"), Sistema.fromValue(rs.getInt("id_sistema")),
						null, null, null, null, null);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return re;
	}

}
