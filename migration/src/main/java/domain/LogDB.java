package domain;

import java.util.Date;

public class LogDB {

	private String operacao;
	private String tabela;
	private int id_elemento;
	private int usuario;
	private Date horario;
	private int codigo_movimento;
	private String alteracoes;
	private Sistema sistema;
	private int id_log_db;

	public LogDB() {

	}

	public LogDB(String operacao, String tabela, int id_elemento, int usuario, Date horario, int codigo_movimento,
			String alteracoes, Sistema sistema, int id_log_db) {
		this.operacao = operacao;
		this.tabela = tabela;
		this.id_elemento = id_elemento;
		this.usuario = usuario;
		this.horario = horario;
		this.codigo_movimento = codigo_movimento;
		this.alteracoes = alteracoes;
		this.sistema = sistema;
		this.id_log_db = id_log_db;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public int getId_elemento() {
		return id_elemento;
	}

	public void setId_elemento(int id_elemento) {
		this.id_elemento = id_elemento;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public int getCodigo_movimento() {
		return codigo_movimento;
	}

	public void setCodigo_movimento(int codigo_movimento) {
		this.codigo_movimento = codigo_movimento;
	}

	public String getAlteracoes() {
		return alteracoes;
	}

	public void setAlteracoes(String alteracoes) {
		this.alteracoes = alteracoes;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public int getId_log_db() {
		return id_log_db;
	}

	public void setId_log_db(int id_log_db) {
		this.id_log_db = id_log_db;
	}

	@Override
	public String toString() {
		return "LogDB [operacao=" + operacao + ", tabela=" + tabela + ", id_elemento=" + id_elemento + ", usuario="
				+ usuario + ", horario=" + horario + ", codigo_movimento=" + codigo_movimento + ", alteracoes="
				+ alteracoes + ", sistema=" + sistema + ", id_log_db=" + id_log_db + "]";
	}

}
