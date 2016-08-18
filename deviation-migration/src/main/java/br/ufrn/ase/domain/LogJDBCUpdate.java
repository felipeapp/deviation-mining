package br.ufrn.ase.domain;

import java.util.Date;

import br.ufrn.ase.util.DateUtil;

public class LogJDBCUpdate {

	private int idLogJdbcUpdate;
	private String sql;
	private int idUusuario;
	private Date horario;
	private int codigoMovimento;
	private Sistema sistema;
	private String parametros;

	public int getIdLogJdbcUpdate() {
		return idLogJdbcUpdate;
	}

	public void setIdLogJdbcUpdate(int idLogJdbcUpdate) {
		this.idLogJdbcUpdate = idLogJdbcUpdate;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getIdUusuario() {
		return idUusuario;
	}

	public void setIdUusuario(int idUusuario) {
		this.idUusuario = idUusuario;
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

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	@Override
	public String toString() {
		return "LogJDBCUpdate [idLogJdbcUpdate=" + idLogJdbcUpdate + ", sql=" + sql + ", idUusuario=" + idUusuario
				+ ", horario=" + DateUtil.formatDateWithMs(horario) + ", codigoMovimento=" + codigoMovimento
				+ ", sistema=" + sistema + ", parametros=" + parametros + "]";
	}

}
