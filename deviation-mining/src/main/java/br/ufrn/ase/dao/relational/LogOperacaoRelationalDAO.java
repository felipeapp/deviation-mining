package br.ufrn.ase.dao.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;

public class LogOperacaoRelationalDAO extends AbstractMigrationRelationalDAO<LogOperacao> {

	@Override
	protected LogOperacao getAttributesFromRS(ResultSet rs) throws SQLException {
		LogOperacao log = new LogOperacao();

		log.setAction(rs.getString("action"));
		log.setErro(rs.getBoolean("erro"));
		log.setHorario(DateUtil.getDateFromDBTimestamp(rs.getTimestamp("hora")));
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

		LogOperacaoRelationalDAO dao = new LogOperacaoRelationalDAO();

		List<LogOperacao> logs = dao.findByIdEntrada(53242492);

		System.out.println("Size: " + logs.size());

		for (LogOperacao l : logs) {
			System.out.println(l);
			System.out.println("------------------------");
		}

	}

}
