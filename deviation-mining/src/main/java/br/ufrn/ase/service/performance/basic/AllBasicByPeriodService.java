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
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
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
	private final int RESULT_MAP_SIZE = 1000;
	
	public void calculateAllBasicScenarios(String systemVersion, boolean executeMining){
		
		
		if( executeMining ){
			
			Map<String, Double> mapRangeMedian = new HashMap<String, Double>();
			Map<String, Double> mapRangeVariation = new HashMap<String, Double>();
			Map<String, Double> mapRangeAverage = new HashMap<String, Double>();
			Map<String, Double> mapRangeAccessed = new HashMap<String, Double>();
			
			findTimesExecutionOfUserScenarios(systemVersion, false);
			
			List<Double> values = new ArrayList<>();
			
			int qtd = 0;
			
			for (String scenario : MapUtil.readAllPropertiesKeys()) { // for each senario
				
				if(qtd % 100 == 0 ){
					System.out.println("Calculating for scenario:  "+scenario);
				}
				
				values = 	MapUtil.readPropertiesValues(scenario);
		
				UserScenariosStatistics userScenariosStatistics = new UserScenariosStatistics();
				
				
				mapRangeMedian    = userScenariosStatistics.calculateExecutionMedian(scenario, values);
				mapRangeVariation = userScenariosStatistics.calculateCoefficientOfVariation(scenario, values, true);
				mapRangeAverage   = userScenariosStatistics.calculateExecutionMean(scenario, values);
				mapRangeAccessed  = userScenariosStatistics.calculateExecutionAmount(scenario, values);
				
				if(qtd % 100 == 0 ){
					System.out.println("Saving on temp database ... ");
				}
				
				new HighestMedianService().saveResults(systemVersion, mapRangeMedian);
				new HighestVariationService().saveResults(systemVersion, mapRangeVariation);
				new HighestAverageService().saveResults(systemVersion, mapRangeAverage);
				new MostAccessedScenariosService().saveResults(systemVersion, mapRangeAccessed);
				
				
				values = new ArrayList<>();
				
				qtd++;
				
			}
		}
		
	}
	
	
	/**
	 * Return the execution time of user
	 * @param systemVersion
	 * @param isUserEnabled
	 * @return
	 */
	public void findTimesExecutionOfUserScenarios(String systemVersion, boolean isUserEnabled) {
		
		
		Date initialDate   = new VersionMapUtil().getInitialDateOfVersion(systemVersion);
		Date finalDate     = new VersionMapUtil().getFinalDateOfVersion(systemVersion);
		String systemName  = StringUtil.getSystemName(systemVersion);
		int systemId       = Sistema.valueOf(systemName).getValue();

		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);
		
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
			
			
			logs = dao.findAllLogOperacaoInsideIntervalBySystemVersion(systemId, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			
			if(qtd % 100 == 0 ){
				System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
				System.out.println(">>>>> return: "+logs.size()+" logs");
				MapUtil.printMapSize(retorno);
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
			if(retorno.size() > RESULT_MAP_SIZE){
				System.out.println(" Store in temporary properties file "+retorno.size());
				MapUtil.storeMapInFile(retorno);
				retorno = new HashMap<String, List<Double>>();   // try to clear the JVM memory as much as possible, this list of log can be very big 
			}
			
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = DateUtil.getNextInterval(SEARCH_INTERVAL, nextTime, finalTime);
			
			qtd++;
			
		}

		MapUtil.storeMapInFile(retorno);
		System.out.println(" Store in temporary properties file "+retorno.size());
		
	}
	
}
