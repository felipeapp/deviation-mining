package br.ufrn.ase.domain;

import java.util.Date;

import br.ufrn.ase.util.MigrationUtil;

public class LogDB {

	private String operacao;
	private String tabela;
	private int idElemento;
	private int idUsuario;
	private Date horario;
	private int codigoMovimento;
	private String alteracoes;
	private Sistema sistema;
	private int idLogDB;

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

	public int getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(int idElemento) {
		this.idElemento = idElemento;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public int getCodigoMovimento() {
		return codigoMovimento;
	}

	public void setCodigoMovimento(int codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
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

	public int getIdLogDB() {
		return idLogDB;
	}

	public void setIdLogDB(int idLogDB) {
		this.idLogDB = idLogDB;
	}

	@Override
	public String toString() {
		return "LogDB [operacao=" + operacao + ", tabela=" + tabela + ", idElemento=" + idElemento + ", idUsuario="
				+ idUsuario + ", horario=" + MigrationUtil.formatDateWithMs(horario) + ", codigoMovimento="
				+ codigoMovimento + ", alteracoes=" + MigrationUtil.getFirstLine(alteracoes) + ", sistema=" + sistema
				+ ", idLogDB=" + idLogDB + "]";
	}

}
