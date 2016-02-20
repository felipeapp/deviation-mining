package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.LogDB;
import domain.Sistema;
import util.MigrationUtil;

public class LogDBDAO extends AbstractDAO {

	public LogDBDAO() {
		super();
	}

	private LogDB getLogDBFromRS(ResultSet rs) throws SQLException {
		LogDB log = new LogDB();

		log.setAlteracoes(rs.getString("alteracoes"));
		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		log.setIdElemento(rs.getInt("id_elemento"));
		log.setIdLogDB(rs.getInt("id_log_db"));
		log.setIdUsuario(rs.getInt("id_usuario"));
		log.setOperacao(rs.getString("operacao"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		log.setTabela(rs.getString("tabela"));

		return log;
	}

	public List<LogDB> findByIdEntrada(int id_entrada) {
		List<LogDB> logs = new ArrayList<LogDB>();

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from log_db where id_registro_entrada = ?");

			stmt.setInt(1, id_entrada);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
				logs.add(getLogDBFromRS(rs));

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logs;
	}

	public static void main(String[] args) {

		LogDBDAO dao = new LogDBDAO();

		List<LogDB> logs = dao.findByIdEntrada(123067786);

		System.out.println("Size: " + logs.size());

		for (LogDB l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

		dao.close();

	}

}
