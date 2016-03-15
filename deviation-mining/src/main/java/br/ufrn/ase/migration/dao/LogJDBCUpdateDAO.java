package br.ufrn.ase.migration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.ase.domain.LogJDBCUpdate;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.MigrationUtil;

public class LogJDBCUpdateDAO extends AbstractDAO<LogJDBCUpdate> {

	protected LogJDBCUpdateDAO() {
		super();
	}

	@Override
	protected LogJDBCUpdate getAttributesFromRS(ResultSet rs) throws SQLException {
		LogJDBCUpdate log = new LogJDBCUpdate();

		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		log.setIdLogJdbcUpdate(rs.getInt("id"));
		log.setIdUusuario(rs.getInt("id_usuario"));
		log.setParametros(rs.getString("params"));
		log.setSistema(Sistema.fromValue(rs.getInt("sistema")));
		log.setSql(rs.getString("sql"));

		return log;
	}

	@Override
	protected String getTableName() {
		return "log_jdbc_update";
	}

	@Override
	protected String getPKFieldName() {
		return "id";
	}

	public static void main(String[] args) {

		LogJDBCUpdateDAO dao = new LogJDBCUpdateDAO();

		List<LogJDBCUpdate> logs = dao.findByIdEntrada(2000460849);

		System.out.println("Size: " + logs.size());

		for (LogJDBCUpdate l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
