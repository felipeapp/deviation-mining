package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domain.LogMovimento;
import domain.Sistema;
import util.MigrationUtil;

public class LogMovimentoDAO extends AbstractDAO<LogMovimento> {

	@Override
	protected LogMovimento getAttributesFromRS(ResultSet rs) throws SQLException {
		LogMovimento log = new LogMovimento();

		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		log.setIdLogMovimento(rs.getInt("id_log_movimento"));
		log.setIdMovimento(rs.getInt("id_movimento"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));

		return log;
	}

	@Override
	protected String getTableName() {
		return "log_movimento";
	}

	@Override
	protected String getPKFieldName() {
		return "id_log_movimento";
	}

	public static void main(String[] args) {

		LogMovimentoDAO dao = new LogMovimentoDAO();

		List<LogMovimento> logs = dao.findByIdEntrada(123067786);

		System.out.println("Size: " + logs.size());

		for (LogMovimento l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
