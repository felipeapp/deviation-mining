package domain;

import java.util.Date;

import util.MigrationUtil;

public class LogMovimento {

	private int idMovimento;
	private int idLogMovimento;
	private int codigoMovimento;
	private Date horario;
	private Sistema sistema;

	public int getIdMovimento() {
		return idMovimento;
	}

	public void setIdMovimento(int idMovimento) {
		this.idMovimento = idMovimento;
	}

	public int getIdLogMovimento() {
		return idLogMovimento;
	}

	public void setIdLogMovimento(int idLogMovimento) {
		this.idLogMovimento = idLogMovimento;
	}

	public int getCodigoMovimento() {
		return codigoMovimento;
	}

	public void setCodigoMovimento(int codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public String toString() {
		return "LogMovimento [idMovimento=" + idMovimento + ", idLogMovimento=" + idLogMovimento + ", codigoMovimento="
				+ codigoMovimento + ", horario=" + MigrationUtil.formatDateWithMs(horario) + ", sistema=" + sistema
				+ "]";
	}

}
