package br.ufrn.ase.domain;

import java.util.Date;
import java.util.List;

import br.ufrn.ase.util.MigrationUtil;

public class RegistroEntrada {

	private String id;
	private int idEntrada;
	private int idUsuario;
	private Date dataEntrada;
	private Date dataSaida;
	private String ip;
	private String ipInternoNat;
	private String host;
	private String userAgent;
	private String resolucao;
	private int passaporte;
	private String canal;
	private Sistema sistema;
	private List<LogDB> logDB;
	private List<LogDBLeitura> logDBLeitura;
	private List<LogJDBCUpdate> logJDBCUpdate;
	private List<LogMovimento> logMovimento;
	private List<LogOperacao> logOperacao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpInternoNat() {
		return ipInternoNat;
	}

	public void setIpInternoNat(String ipInternoNat) {
		this.ipInternoNat = ipInternoNat;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
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

	public List<LogDB> getLogDB() {
		return logDB;
	}

	public void setLogDB(List<LogDB> logDB) {
		this.logDB = logDB;
	}

	public List<LogDBLeitura> getLogDBLeitura() {
		return logDBLeitura;
	}

	public void setLogDBLeitura(List<LogDBLeitura> logDBLeitura) {
		this.logDBLeitura = logDBLeitura;
	}

	public List<LogJDBCUpdate> getLogJDBCUpdate() {
		return logJDBCUpdate;
	}

	public void setLogJDBCUpdate(List<LogJDBCUpdate> logJDBCUpdate) {
		this.logJDBCUpdate = logJDBCUpdate;
	}

	public List<LogMovimento> getLogMovimento() {
		return logMovimento;
	}

	public void setLogMovimento(List<LogMovimento> logMovimento) {
		this.logMovimento = logMovimento;
	}

	public List<LogOperacao> getLogOperacao() {
		return logOperacao;
	}

	public void setLogOperacao(List<LogOperacao> logOperacao) {
		this.logOperacao = logOperacao;
	}

	@Override
	public String toString() {
		return "RegistroEntrada [id=" + id + ", idEntrada=" + idEntrada + ", idUsuario=" + idUsuario + ", dataEntrada="
				+ MigrationUtil.formatDateWithMs(dataEntrada) + ", dataSaida="
				+ MigrationUtil.formatDateWithMs(dataSaida) + ", ip=" + ip + ", ipInternoNat=" + ipInternoNat
				+ ", host=" + host + ", userAgent=" + userAgent + ", resolucao=" + resolucao + ", passaporte="
				+ passaporte + ", canal=" + canal + ", sistema=" + sistema + ", logDB=" + logDB + ", logDBLeitura="
				+ logDBLeitura + ", logJDBCUpdate=" + logJDBCUpdate + ", logMovimento=" + logMovimento
				+ ", logOperacao=" + logOperacao + "]";
	}

}
