package br.ufrn.ase.dao.relational.migration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.ase.domain.LogDBLeitura;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;

public class LogDBLeituraRelationalDAO extends AbstractMigrationRelationalDAO<LogDBLeitura> {

	@Override
	protected LogDBLeitura getAttributesFromRS(ResultSet rs) throws SQLException {
		LogDBLeitura log = new LogDBLeitura();

		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
		log.setIdElemento(rs.getInt("id_elemento"));
		log.setIdLogDBLeitura(rs.getInt("id_log_db_leitura"));
		log.setIdTurmaVirtual(rs.getInt("id_turma_virtual"));
		log.setIdUsuario(rs.getInt("id_usuario"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		log.setTabela(rs.getString("tabela"));

		return log;
	}

	@Override
	protected String getTableName() {
		return "log_db_leitura";
	}

	@Override
	protected String getPKFieldName() {
		return "id_log_db_leitura";
	}

	public static void main(String[] args) {

		LogDBLeituraRelationalDAO dao = new LogDBLeituraRelationalDAO();

		List<LogDBLeitura> logs = dao.findByIdEntrada(2050375140);

		System.out.println("Size: " + logs.size());

		for (LogDBLeitura l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
