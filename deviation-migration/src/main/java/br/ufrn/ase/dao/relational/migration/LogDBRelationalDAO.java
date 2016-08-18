package br.ufrn.ase.dao.relational.migration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.ase.domain.LogDB;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;

public class LogDBRelationalDAO extends AbstractMigrationRelationalDAO<LogDB> {

	@Override
	protected LogDB getAttributesFromRS(ResultSet rs) throws SQLException {
		LogDB log = new LogDB();

		log.setAlteracoes(rs.getString("alteracoes"));
		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		log.setIdElemento(rs.getInt("id_elemento"));
		log.setIdLogDB(rs.getInt("id_log_db"));
		log.setIdUsuario(rs.getInt("id_usuario"));
		log.setOperacao(rs.getString("operacao"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		log.setTabela(rs.getString("tabela"));

		return log;
	}

	@Override
	protected String getTableName() {
		return "log_db";
	}

	@Override
	protected String getPKFieldName() {
		return "id_log_db";
	}

	public static void main(String[] args) {

		LogDBRelationalDAO dao = new LogDBRelationalDAO();

		List<LogDB> logs = dao.findByIdEntrada(123067786);

		System.out.println("Size: " + logs.size());

		for (LogDB l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
