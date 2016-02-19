package domain;

import java.util.Date;

public class LogMovimento {

	private int id_movimento;
	private int id_log_movimento;
	private int codigo_movimento;
	private Date horario;
	private Sistema sistema;

	public LogMovimento() {

	}

	public LogMovimento(int id_movimento, int id_log_movimento, int codigo_movimento, Date horario, Sistema sistema) {
		this.id_movimento = id_movimento;
		this.id_log_movimento = id_log_movimento;
		this.codigo_movimento = codigo_movimento;
		this.horario = horario;
		this.sistema = sistema;
	}

	public int getId_movimento() {
		return id_movimento;
	}

	public void setId_movimento(int id_movimento) {
		this.id_movimento = id_movimento;
	}

	public int getId_log_movimento() {
		return id_log_movimento;
	}

	public void setId_log_movimento(int id_log_movimento) {
		this.id_log_movimento = id_log_movimento;
	}

	public int getCodigo_movimento() {
		return codigo_movimento;
	}

	public void setCodigo_movimento(int codigo_movimento) {
		this.codigo_movimento = codigo_movimento;
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
		return "LogMovimento [id_movimento=" + id_movimento + ", id_log_movimento=" + id_log_movimento
				+ ", codigo_movimento=" + codigo_movimento + ", horario=" + horario + ", sistema=" + sistema + "]";
	}

}
