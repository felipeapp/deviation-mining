package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domain.LogOperacao;
import domain.Sistema;
import util.MigrationUtil;

public class LogOperacaoDAO extends AbstractDAO<LogOperacao> {

	@Override
	protected LogOperacao getAttributesFromRS(ResultSet rs) throws SQLException {
		LogOperacao log = new LogOperacao();

		log.setAction(rs.getString("action"));
		log.setErro(rs.getBoolean("erro"));
		log.setHorario(MigrationUtil.getDateFromDBTimestamp(rs.getTimestamp("hora")));
		log.setIdAcessoPublico(rs.getInt("id_acesso_publico"));
		log.setIdOperacao(rs.getInt("id_operacao"));
		log.setMensagens(rs.getString("mensagens"));
		log.setParametros(rs.getString("parametros"));
		log.setSistema(Sistema.fromValue(rs.getInt("id_sistema")));
		log.setTempo(rs.getInt("tempo"));
		log.setTrace(rs.getString("trace"));

		return log;
	}

	@Override
	protected String getTableName() {
		return "log_operacao";
	}

	@Override
	protected String getPKFieldName() {
		return "id_operacao";
	}

	public static void main(String[] args) {

		LogOperacaoDAO dao = new LogOperacaoDAO();

		List<LogOperacao> logs = dao.findByIdEntrada(53242492);

		System.out.println("Size: " + logs.size());

		for (LogOperacao l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

		dao.close();

	}

}
