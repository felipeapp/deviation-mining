package domain;

import java.util.Date;
import java.util.List;

public class RegistroEntrada {

	private int id_entrada;
	private int id_usuario;
	private Date data_entrada;
	private Date data_saida;
	private String ip;
	private String ip_interno_nat;
	private String host;
	private String user_agent;
	private String resolucao;
	private int passaporte;
	private String canal;
	private Sistema sistema;
	private List<LogDB> log_db;
	private List<LogDBLeitura> log_db_leitura;
	private List<LogJDBCUpdate> log_jdbc_leitura;
	private List<LogMovimento> log_movimento;
	private List<LogOperacao> log_operacao;

	public RegistroEntrada() {

	}

	public RegistroEntrada(int id_entrada, int id_usuario, Date data_entrada, Date data_saida, String ip,
			String ip_interno_nat, String host, String user_agent, String resolucao, int passaporte, String canal,
			Sistema sistema, List<LogDB> log_db, List<LogDBLeitura> log_db_leitura,
			List<LogJDBCUpdate> log_jdbc_leitura, List<LogMovimento> log_movimento, List<LogOperacao> log_operacao) {
		this.id_entrada = id_entrada;
		this.id_usuario = id_usuario;
		this.data_entrada = data_entrada;
		this.data_saida = data_saida;
		this.ip = ip;
		this.ip_interno_nat = ip_interno_nat;
		this.host = host;
		this.user_agent = user_agent;
		this.resolucao = resolucao;
		this.passaporte = passaporte;
		this.canal = canal;
		this.sistema = sistema;
		this.log_db = log_db;
		this.log_db_leitura = log_db_leitura;
		this.log_jdbc_leitura = log_jdbc_leitura;
		this.log_movimento = log_movimento;
		this.log_operacao = log_operacao;
	}

	public int getId_entrada() {
		return id_entrada;
	}

	public void setId_entrada(int id_entrada) {
		this.id_entrada = id_entrada;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Date getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(Date data_entrada) {
		this.data_entrada = data_entrada;
	}

	public Date getData_saida() {
		return data_saida;
	}

	public void setData_saida(Date data_saida) {
		this.data_saida = data_saida;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp_interno_nat() {
		return ip_interno_nat;
	}

	public void setIp_interno_nat(String ip_interno_nat) {
		this.ip_interno_nat = ip_interno_nat;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getResolucao() {
		return resolucao;
	}

	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}

	public int getPassaporte() {
		return passaporte;
	}

	public void setPassaporte(int passaporte) {
		this.passaporte = passaporte;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<LogDB> getLog_db() {
		return log_db;
	}

	public void setLog_db(List<LogDB> log_db) {
		this.log_db = log_db;
	}

	public List<LogDBLeitura> getLog_db_leitura() {
		return log_db_leitura;
	}

	public void setLog_db_leitura(List<LogDBLeitura> log_db_leitura) {
		this.log_db_leitura = log_db_leitura;
	}

	public List<LogJDBCUpdate> getLog_jdbc_leitura() {
		return log_jdbc_leitura;
	}

	public void setLog_jdbc_leitura(List<LogJDBCUpdate> log_jdbc_leitura) {
		this.log_jdbc_leitura = log_jdbc_leitura;
	}

	public List<LogMovimento> getLog_movimento() {
		return log_movimento;
	}

	public void setLog_movimento(List<LogMovimento> log_movimento) {
		this.log_movimento = log_movimento;
	}

	public List<LogOperacao> getLog_operacao() {
		return log_operacao;
	}

	public void setLog_operacao(List<LogOperacao> log_operacao) {
		this.log_operacao = log_operacao;
	}

	@Override
	public String toString() {
		return "RegistroEntrada [id_entrada=" + id_entrada + ", id_usuario=" + id_usuario + ", data_entrada="
				+ data_entrada + ", data_saida=" + data_saida + ", ip=" + ip + ", ip_interno_nat=" + ip_interno_nat
				+ ", host=" + host + ", user_agent=" + user_agent + ", resolucao=" + resolucao + ", passaporte="
				+ passaporte + ", canal=" + canal + ", sistema=" + sistema + ", log_db=" + log_db + ", log_db_leitura="
				+ log_db_leitura + ", log_jdbc_leitura=" + log_jdbc_leitura + ", log_movimento=" + log_movimento
				+ ", log_operacao=" + log_operacao + "]";
	}

}
