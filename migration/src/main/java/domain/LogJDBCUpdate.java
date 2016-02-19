package domain;

import java.util.Date;

public class LogJDBCUpdate {

	private int id_log_jdbc_update;
	private String sql;
	private int id_usuario;
	private Date horario;
	private int codigo_movimento;
	private Sistema sistema;
	private String parametros;

	public LogJDBCUpdate() {

	}

	public LogJDBCUpdate(int id_log_jdbc_update, String sql, int id_usuario, Date horario, int codigo_movimento,
			Sistema sistema, String parametros) {
		this.id_log_jdbc_update = id_log_jdbc_update;
		this.sql = sql;
		this.id_usuario = id_usuario;
		this.horario = horario;
		this.codigo_movimento = codigo_movimento;
		this.sistema = sistema;
		this.parametros = parametros;
	}

	public int getId_log_jdbc_update() {
		return id_log_jdbc_update;
	}

	public void setId_log_jdbc_update(int id_log_jdbc_update) {
		this.id_log_jdbc_update = id_log_jdbc_update;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
		return "LogJDBCUpdate [id_log_jdbc_update=" + id_log_jdbc_update + ", sql=" + sql + ", id_usuario=" + id_usuario
				+ ", horario=" + horario + ", codigo_movimento=" + codigo_movimento + ", sistema=" + sistema
				+ ", parametros=" + parametros + "]";
	}

}
