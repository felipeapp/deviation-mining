package br.ufrn.ase.domain;

import java.util.Date;

import br.ufrn.ase.util.DateUtil;

public class LogDBLeitura {

	private String tabela;
	private int idElemento;
	private int idUsuario;
	private Date horario;
	private int codigoMovimento;
	private int idTurmaVirtual;
	private int idLogDBLeitura;
	private Sistema sistema;

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

	public int getIdTurmaVirtual() {
		return idTurmaVirtual;
	}

	public void setIdTurmaVirtual(int idTurmaVirtual) {
		this.idTurmaVirtual = idTurmaVirtual;
	}

	public int getIdLogDBLeitura() {
		return idLogDBLeitura;
	}

	public void setIdLogDBLeitura(int idLogDBLeitura) {
		this.idLogDBLeitura = idLogDBLeitura;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public String toString() {
		return "LogDBLeitura [tabela=" + tabela + ", idElemento=" + idElemento + ", idUsuario=" + idUsuario
				+ ", horario=" + DateUtil.formatDateWithMs(horario) + ", codigoMovimento=" + codigoMovimento
				+ ", idTurmaVirtual=" + idTurmaVirtual + ", idLogDBLeitura=" + idLogDBLeitura + ", sistema=" + sistema
				+ "]";
	}

}
