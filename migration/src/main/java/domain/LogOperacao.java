package domain;

import java.util.Date;

public class LogOperacao {

	private RegistroEntrada registro_entrada;
	private String action;
	private Date horario;
	private String parametros;
	private int tempo;
	private boolean erro;
	private String trace;
	private int id_operacao;
	private int id_acesso_publico;
	private String mensagens;
	private Sistema sistema;

	public LogOperacao() {

	}

	public LogOperacao(RegistroEntrada registro_entrada, String action, Date horario, String parametros, int tempo,
			boolean erro, String trace, int id_operacao, int id_acesso_publico, String mensagens, Sistema sistema) {
		super();
		this.registro_entrada = registro_entrada;
		this.action = action;
		this.horario = horario;
		this.parametros = parametros;
		this.tempo = tempo;
		this.erro = erro;
		this.trace = trace;
		this.id_operacao = id_operacao;
		this.id_acesso_publico = id_acesso_publico;
		this.mensagens = mensagens;
		this.sistema = sistema;
	}

	public RegistroEntrada getRegistro_entrada() {
		return registro_entrada;
	}

	public void setRegistro_entrada(RegistroEntrada registro_entrada) {
		this.registro_entrada = registro_entrada;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public int getId_operacao() {
		return id_operacao;
	}

	public void setId_operacao(int id_operacao) {
		this.id_operacao = id_operacao;
	}

	public int getId_acesso_publico() {
		return id_acesso_publico;
	}

	public void setId_acesso_publico(int id_acesso_publico) {
		this.id_acesso_publico = id_acesso_publico;
	}

	public String getMensagens() {
		return mensagens;
	}

	public void setMensagens(String mensagens) {
		this.mensagens = mensagens;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public String toString() {
		return "LogOperacao [registro_entrada=" + registro_entrada + ", action=" + action + ", horario=" + horario
				+ ", parametros=" + parametros + ", tempo=" + tempo + ", erro=" + erro + ", trace=" + trace
				+ ", id_operacao=" + id_operacao + ", id_acesso_publico=" + id_acesso_publico + ", mensagens="
				+ mensagens + ", sistema=" + sistema + "]";
	}

}
