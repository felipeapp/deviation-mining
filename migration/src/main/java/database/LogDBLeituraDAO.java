package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domain.LogDBLeitura;
import domain.Sistema;
import util.MigrationUtil;

public class LogDBLeituraDAO extends AbstractDAO<LogDBLeitura> {

	protected LogDBLeituraDAO() {
		super();
	}

	@Override
	protected LogDBLeitura getAttributesFromRS(ResultSet rs) throws SQLException {
		LogDBLeitura log = new LogDBLeitura();

		log.setCodigoMovimento(rs.getInt("cod_movimento"));
		log.setHorario(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("data")));
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

		LogDBLeituraDAO dao = new LogDBLeituraDAO();

		List<LogDBLeitura> logs = dao.findByIdEntrada(2050375140);

		System.out.println("Size: " + logs.size());

		for (LogDBLeitura l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
