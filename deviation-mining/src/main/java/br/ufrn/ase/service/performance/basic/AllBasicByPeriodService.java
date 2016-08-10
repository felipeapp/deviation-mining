/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance.basic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.mining.LogOperacaoDao;
import br.ufrn.ase.dao.relational.performance.temporary.TemporaryDataAnalysisDAO;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.StringUtil;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * <p>This service calculate all basic information dividing this information by period of time 
 * and save it on a temporary file to reduce the quantity of memory and execute for big amount of data.</p>
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class AllBasicByPeriodService {

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
	public void calculateAllBasicScenarios(String systemVersion, boolean executeMining){
		
		// pre process the informations 
		if( executeMining ){
			executeMiningOfBasicInformations(systemVersion, false);
		}
		
		Map<String, Double> mapRangeMedian = new HashMap<String, Double>();
		Map<String, Double> mapRangeVariation = new HashMap<String, Double>();
		Map<String, Double> mapRangeAverage = new HashMap<String, Double>();
		Map<String, Double> mapRangeAccessed = new HashMap<String, Double>();
		
		List<Double> values = new ArrayList<>();
		
		int qtd = 0;
		
		for (String scenario : readAllScenarios() ) { // for each scenario that was mined
			
			if(qtd % 100 == 0 ){
				System.out.println("Calculating information for scenario:  "+scenario);
			}
			
			values = readScenariosValues(scenario); // just for one scenario to save memory
	
			UserScenariosStatistics userScenariosStatistics = new UserScenariosStatistics();
			
			mapRangeMedian    = userScenariosStatistics.calculateExecutionMedian(scenario, values);
			mapRangeVariation = userScenariosStatistics.calculateCoefficientOfVariation(scenario, values, true);
			mapRangeAverage   = userScenariosStatistics.calculateExecutionMean(scenario, values);
			mapRangeAccessed  = userScenariosStatistics.calculateExecutionAmount(scenario, values);
			
			if(qtd % 100 == 0 ){
				System.out.println("Saving on result database ... ");
			}
			
			new HighestMedianService().saveResults(systemVersion, mapRangeMedian);
			new HighestVariationService().saveResults(systemVersion, mapRangeVariation);
			new HighestAverageService().saveResults(systemVersion, mapRangeAverage);
			new MostAccessedScenariosService().saveResults(systemVersion, mapRangeAccessed);
			
			values = new ArrayList<>();
			
			qtd++;
			
		}
		
		
	}
	


	/**
	 * This is the method that execute the mining in the database of logs
	 * 
	 * @param systemVersion
	 * @param isUserEnabled
	 * @return
	 */
	public void executeMiningOfBasicInformations(String systemVersion, boolean isUserEnabled) {
		
		
		Date initialDate   = new VersionMapUtil().getInitialDateOfVersion(systemVersion);
		Date finalDate     = new VersionMapUtil().getFinalDateOfVersion(systemVersion);
		String systemName  = StringUtil.getSystemName(systemVersion);
		int systemId       = Sistema.valueOf(systemName).getValue();

		/// calculate several interval to recovery the information by small parts because this is really huge ///
		
		LocalDateTime initialTime =  DateUtil.toLocalDateTime(initialDate); 
		LocalDateTime finalTime =    DateUtil.toLocalDateTime(finalDate); 
		
		
		LocalDateTime currentTime = initialTime;
		LocalDateTime nextTime = DateUtil.getNextInterval(SEARCH_INTERVAL, initialTime, finalTime);
	
		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();
		List<Double> tempos = null;
		List<LogOperacao> logs = null;
		String key = "";
		
		int qtd = 0;
		
		while(nextTime.isBefore(finalTime) ){
			
			LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);
			logs = dao.findAllLogOperacaoInsideIntervalBySystemVersion(systemId, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			DAOFactory.closeMiningConnection();
			
			if(qtd % 100 == 0 ){
				System.out.println(">>>>> Analyzing Interval: "+qtd+" ");
				System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
				System.out.println(">>>>> return: "+logs.size()+" logs");
			}
			
			for (LogOperacao log : logs) {
	
				key = (isUserEnabled ? log.getRegistroEntrada().getIdUsuario()+"_" : "") + log.getAction();
	
				tempos = retorno.get(key);
	
				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}
	
				tempos.add((double) log.getTempo());
			}
			
			// try to clear the JVM memory as much as possible, this list of log can be very big //
			logs = null;
			
			
			// this is very important, we cannot keek this map in the memory, it can be very big
			if( MapUtil.getMapSize(retorno) > RESULT_MAP_SIZE){
				long time = System.currentTimeMillis();
				System.out.println(">>>>> Store in temporary database "+retorno.size()+" keys");
				storeMapInDataBase(retorno);
				retorno = new HashMap<String, List<Double>>();   // try to clear the JVM memory as much as possible, this list of log can be very big
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
	 * Load all scenarios mined with values, just the scenarios
	 * 
	 * @return
	 */
	private List<String> readAllScenarios() {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		List<String> list =  dao.readAllScenarios();
		DAOFactory.closeTemporaryConnection();
		return list;
	}
	
	/**
	 * Load all values of one specific scenario
	 * 
	 * @param scenario
	 * @return
	 */
	private List<Double> readScenariosValues(String scenario) {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		List<Double> list =  dao.readScenariosValues(scenario);
		DAOFactory.closeTemporaryConnection();
		return list;
	}


	/**
	 * Store the entire map in a data, insert a new scenario or update the values of a existent one.
	 * 
	 * @param retorno
	 */
	private static void storeMapInDataBase(Map<String, List<Double>> map) {
		
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		
		for (String scenario : map.keySet()) {
			if(dao.contaisScenarios(scenario)){
				List<Double> list =  dao.readScenariosValues(scenario);
				list.addAll(map.get(scenario));
				
				dao.updateScenarioValues(scenario, list);
				
			}else{
				dao.insertNewScenario(scenario, map.get(scenario));
			}
		}
		DAOFactory.closeTemporaryConnection();
      
	}
	
	
}
