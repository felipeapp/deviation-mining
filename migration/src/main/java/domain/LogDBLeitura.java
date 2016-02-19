package domain;

import java.util.Date;

public class LogDBLeitura {

	private String tabela;
	private int id_elemento;
	private int id_usuario;
	private Date horario;
	private int codigo_movimento;
	private int id_turma_virtual;
	private int id_log_db_leitura;
	private Sistema sistema;

	public LogDBLeitura() {

	}

	public LogDBLeitura(String tabela, int id_elemento, int id_usuario, Date horario, int codigo_movimento,
			int id_turma_virtual, int id_log_db_leitura, Sistema sistema) {
		this.tabela = tabela;
		this.id_elemento = id_elemento;
		this.id_usuario = id_usuario;
		this.horario = horario;
		this.codigo_movimento = codigo_movimento;
		this.id_turma_virtual = id_turma_virtual;
		this.id_log_db_leitura = id_log_db_leitura;
		this.sistema = sistema;
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

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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

	public int getId_turma_virtual() {
		return id_turma_virtual;
	}

	public void setId_turma_virtual(int id_turma_virtual) {
		this.id_turma_virtual = id_turma_virtual;
	}

	public int getId_log_db_leitura() {
		return id_log_db_leitura;
	}

	public void setId_log_db_leitura(int id_log_db_leitura) {
		this.id_log_db_leitura = id_log_db_leitura;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public String toString() {
		return "LogDBLeitura [tabela=" + tabela + ", id_elemento=" + id_elemento + ", id_usuario=" + id_usuario
				+ ", horario=" + horario + ", codigo_movimento=" + codigo_movimento + ", id_turma_virtual="
				+ id_turma_virtual + ", id_log_db_leitura=" + id_log_db_leitura + ", sistema=" + sistema + "]";
	}

}
