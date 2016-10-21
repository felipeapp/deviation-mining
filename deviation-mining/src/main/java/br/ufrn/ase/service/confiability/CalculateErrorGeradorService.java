/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.confiability;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.confiability.InfraErroDao;
import br.ufrn.ase.dao.relational.performance.temporary.TemporaryDataAnalysisDAO;
import br.ufrn.ase.domain.InfraError;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StringUtil;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class CalculateErrorGeradorService {

	
	/** Interval of time to get log from the data base. As bigger as this interval, more log are recovery and can give out of memory*/
	private final int SEARCH_INTERVAL = 60; // 60 minutes
	
	/** Qtd of result keep in memory at a time */
	private final int RESULT_MAP_SIZE = 30000000; // 30MB
	
	
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
		
		int qtd = 1;		
		
		final int PAGE_SIZE = 100;
		
		int qtdTotal = countTotalOfScenarios();
		
		int qtdPagina = ( qtdTotal / PAGE_SIZE);
		
		if(qtdTotal % PAGE_SIZE != 0)
			qtdPagina+=1;
		
		int inicial = 1;
		int ultimo = PAGE_SIZE*qtd;
		
		
		for(int i = 0 ; i < qtdPagina; i++){
		
			Map<String, Integer> mapTemp = readAllScenariosError(inicial, ultimo);
		
			for (String scenarioLineOfCode :  mapTemp.keySet()) {
				// salvar no banco result
			}
			
			qtd++;
			
			// next page
			inicial = PAGE_SIZE*(qtd-1)+1;
			ultimo = PAGE_SIZE*qtd;
			
		}
		
		// TODO
		
//		Map<String, Integer> mapTemp = readAllScenariosError(inicial, ultimo);
//		
//		for (String scenarioLineOfCode :  mapTemp.keySet()) {
//			// salvar no banco result
//		}
//		
//		forScenario:
//		for (String scenarioLineOfCode :  mapTemp.keySet()) { // for each scenario that was mined
//			
			
//			
//			if(qtd % 100 == 0 ){
//				System.out.println("Calculating information for scenario:  "+scenario);
//			}
//			
//			// all the traces 
//			values = readScenariosValuesError(scenario); // just for one scenario to save memory
//	
//			Map<String, Integer> result = calculateAmountOfTraces(values);
//			
//			if(qtd % 100 == 0 ){
//				System.out.println("Saving on result database ... ");
//			}
//			
//			
//			saveResultsTotalOfError(systemVersion, scenario, values.size());
//			saveResultsByScenario(systemVersion, scenario, result);
//			
//			
//			
//			values = new ArrayList<>();
//			
			
			qtd++;
			
			// next page
			inicial = PAGE_SIZE*(qtd-1)+1;
			ultimo = PAGE_SIZE*qtd;
			
			//if(ultimo > qtdTotal)
			//	break forScenario;
		
			//mapTemp = readAllScenariosError(inicial, ultimo);
			
		//}
		
		
	}
	
	
	
	
	/**
	 * @return
	 */
	private int countTotalOfScenarios() {
		return 0;
	}




	/**
	 * @return
	 */
	private Map<String, Integer> readAllScenariosError(int inicial, int ultimo) {
		return null;
	}




	/**
	 * This is the method that execute the mining in the database, table infra.error.
	 * 
	 * @param systemVersion
	 * @param isUserEnabled
	 * @return
	 */
	public void executeMining(String systemVersion, boolean isUserEnabled) {
		
		
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
				key = infraError.scenario+";"+infraError.traceGerador;
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
