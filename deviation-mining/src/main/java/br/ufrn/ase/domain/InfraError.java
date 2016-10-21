/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.domain;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class InfraError {
	
	public String traceCompleto, traceGerador, excecao, scenario;

	/**
	 * @param traceCompleto
	 * @param traceGerador
	 * @param excecao
	 */
	public InfraError(String traceCompleto, String traceGerador, String excecao) {
		this.traceCompleto = traceCompleto;
		this.traceGerador = traceGerador;
		this.excecao = excecao;
	}

	/**
	 *  Extract the URL of the traceCompleto
	 */
	public void extractScenario() {
		if(traceCompleto == null) return;
		
		int index1 = traceCompleto.indexOf("URL:");
		int index2 = traceCompleto.indexOf("Hora:");
		
		scenario = traceCompleto.substring(index1+4, index2).trim();
	}
	
	
//	public static void main(String[] args) {
//		
//		String example = "Server name: www.sipac.ufrn.br "+
//
//		"Servidor do cluster: sipac02-producao.info.ufrn.br "+
//
//		"Endereço remoto:10.10.11.201 "+
//		"Host remoto: 10.10.11.201 "+
//		"URL: https://www.sipac.ufrn.br/sipac/protocolo/processo/cadastro/cadastro_4.jsf?null "+
//		"Hora: Fri Oct 21 10:36:21 BRT 20";
//		
//		InfraError i = new InfraError(example, "", "");
//		
//		i.extractScenario();
//		
//		System.out.println(i.scenario);
//		
//	}
	
	
}
