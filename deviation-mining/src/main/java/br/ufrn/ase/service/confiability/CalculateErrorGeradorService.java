/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.confiability;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.confiability.InfraErroDao;
import br.ufrn.ase.dao.relational.performance.result.ResultDataAnalysisDAO;
import br.ufrn.ase.dao.relational.performance.temporary.TemporaryDataAnalysisDAO;
import br.ufrn.ase.domain.InfraError;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StringUtil;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * Mining the table INFRA.ERRO of comum database to get the specific linha the generate the error. 
 * 
 * 
 * Queries Example:
 * 
 * --- trace_gerador mais erros
 * select trace_gerador, SUM(qtd)
 * from result.infra_error_erro_gerador  e
 * inner join result.infra_error_erro_gerador_ocorrencias eo  on e.system_version = eo.system_version AND e.scenario = eo.scenario
 * GROUP BY trace_gerador
 * ORDER BY 2 desc
 * 
 * --- exception mais lancadas
 * select exception, SUM(qtd)
 * from result.infra_error_erro_gerador  e
 * inner join result.infra_error_erro_gerador_ocorrencias eo  on e.system_version = eo.system_version AND e.scenario = eo.scenario
 * GROUP BY exception
 * ORDER BY 2 desc
 * 
 * --- excecoes mais lancadas para o sigaa em setembro by scenario
 * select e.scenario, exception, SUM(qtd)
 * from result.infra_error_erro_gerador  e
 * inner join result.infra_error_erro_gerador_ocorrencias eo  on e.system_version = eo.system_version AND e.scenario = eo.scenario
 * WHERE e.system_version = 'SIGAA-SET'
 * GROUP BY e.scenario, exception
 * ORDER BY 3 desc
 * 
 * 
 * --- linhas com mais errospara o sigaa em setembro by scenario
 * select e.scenario, trace_gerador, SUM(qtd)
 * from result.infra_error_erro_gerador  e
 * inner join result.infra_error_erro_gerador_ocorrencias eo  on e.system_version = eo.system_version AND e.scenario = eo.scenario
 * WHERE e.system_version = 'SIGAA-SET'
 * GROUP BY e.scenario, trace_gerador
 * ORDER BY 3 desc
 * 
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class CalculateErrorGeradorService {

	
	/** Interval of time to get log from the data base. As bigger as this interval, more log are recovery and can give out of memory*/
	private final int SEARCH_INTERVAL = 60; // 60 minutes
	
	/** Qtd of result keep in memory at a time */
	private final int RESULT_MAP_SIZE = 30000000; // 30MB
	
	/** segregate the key inforamtion */
	final String KEY_SEPARATOR = "_;_";
	
	
	/***
	 * Calculate the basic information about the log
	 * 
	 * @param systemVersion
	 * @param executeMining
	 */
	public void calculateErrorGerador(String systemVersion, boolean executeMining){
		
		// pre process the informations 
		if( executeMining ){
			executeMining(systemVersion, false);
		}
		
		processResults(systemVersion);
		
	}
	
	
	
	
	/**
	 * @param systemVersion
	 */
	private void processResults(String systemVersion) {
		Map<String, Integer> retorno = readAllErrorGerador();
		
		for (String key : retorno.keySet()) {
			
			String[] info = key.split(KEY_SEPARATOR);
			
			if(info.length == 3){
				String scenario     = info[0];
				String exception    = info[1];
				String traceGerador = info[2];
				int qtdErro = retorno.get(key);
				
				saveResultsErroGerador(systemVersion, scenario, traceGerador, exception, qtdErro);
			}
		}
	}




	/**
	 * Read the information of temporary database
	 */
	private Map<String, Integer> readAllErrorGerador() {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		Map<String, Integer> retorno = dao.readAllErrorGerador();
		DAOFactory.closeTemporaryConnection();
		return retorno;
	}


	/**
	 * copy informatio for the result database
	 * @param systemVersion
	 * @param scenario
	 * @param size
	 */
	private void saveResultsErroGerador(String systemVersion, String scenario, String traceGerador, String exception, int qtd) {

		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertErrorGerador(systemVersion, scenario, traceGerador, exception, qtd );
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}finally{
			DAOFactory.closeResultConnection();
		}
		
	}
	


	/**
	 * This is the method that execute the mining in the database, table infra.error.
	 * 
	 * This is the heart of the algorithm of mining process.
	 * 
	 * @param systemVersion
	 * @param isUserEnabled
	 * @return
	 */
	public void executeMining(String systemVersion, boolean isUserEnabled) {
		
		clearTemporatyDataBase();
		
		Date initialDate   = new VersionMapUtil().getInitialDateOfVersion(systemVersion);
		Date finalDate     = new VersionMapUtil().getFinalDateOfVersion(systemVersion);
		String systemName  = StringUtil.getSystemName(systemVersion);
		int systemId       = Sistema.valueOf(systemName).getValue();

		/// calculate several interval to recovery the information by small parts because this is really huge ///
		
		LocalDateTime initialTime =  DateUtil.toLocalDateTime(initialDate); 
		LocalDateTime finalTime =    DateUtil.toLocalDateTime(finalDate); 
		
		
		LocalDateTime currentTime = initialTime;
		LocalDateTime nextTime = DateUtil.getNextInterval(SEARCH_INTERVAL, initialTime, finalTime);
	
		/**
		 * key = scenario ; lineofCode
		 * value = qtd of error
		 */
		Map<String, Integer> retorno = new HashMap<String, Integer>();
		
		List<InfraError> error = null;
		String key = "";
		
		int qtd = 0;
		
		while(nextTime.isBefore(finalTime) ){
			
			InfraErroDao dao = DAOFactory.getRelationalDAO(InfraErroDao.class);
			error = dao.findAllErrorsInsideIntervalBySystemVersion(systemId, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			DAOFactory.closeMiningConnection();
			
			if(qtd % 100 == 0 ){
				System.out.println(">>>>> Analyzing Interval: "+qtd+" ");
				System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
				System.out.println(">>>>> return: "+error.size()+" logs");
			}
			
			
			for (InfraError infraError : error) {
				infraError.extractScenario();
				key = infraError.scenario+KEY_SEPARATOR+infraError.excecao+KEY_SEPARATOR+infraError.traceGerador; // build the key of the map = scenario + excecao + traceGerador
				if(! retorno.containsKey(key))
					retorno.put(key, 1);   // primeiro erro encontrado
				else{
					Integer qtd2 = retorno.get(key);
					retorno.put(key, ++qtd2); // incrementa a quantidade de erros no cenário
				}
					
			}
			
			// this is very important, we cannot keek this map in the memory, it can be very big
			if( MapUtil.getMapSize(retorno) > RESULT_MAP_SIZE){
				long time = System.currentTimeMillis();
				System.out.println(">>>>> Store in temporary database "+retorno.size()+" keys");
				storeMapInDataBase(retorno);
				retorno = new HashMap<String, Integer>();   // try to clear the JVM memory as much as possible, this list of log can be very big
				System.out.println(((System.currentTimeMillis()-time)/1000)+" seconds");
			}
			
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = DateUtil.getNextInterval(SEARCH_INTERVAL, nextTime, finalTime);
			
			qtd++;
			
		}

		storeMapInDataBase(retorno);
		System.out.println(">>>>> Last store in temporary database "+retorno.size()+" keys");
		
	}
	
	
	
	/**
	 * 
	 */
	private void clearTemporatyDataBase() {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		dao.clearErrorGeradorTable();
		DAOFactory.closeTemporaryConnection();
	}




	/**
	 * Store the entire map in a data, insert a new scenario or update the values of a existent one.
	 * 
	 * @param retorno
	 */
	private static void storeMapInDataBase(Map<String, Integer> map) {
		
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		
		for (String scenario : map.keySet()) {
			
			Integer qtdError = map.get(scenario);
			
			if( dao.containsScenarioLineOfCode(scenario) ){
				dao.updateQtdScenarioLineOfCode(scenario, qtdError);
			}else{
				dao.insertNewQtdScenarioLineOfCode(scenario, qtdError);
			}
		}
		DAOFactory.closeTemporaryConnection();
      
	}
	
	
}
