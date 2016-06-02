/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.Sistema;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * Execute the User Scenario Mining for performance
 * 
 * @author jadson - jadsonjs@gmail.com
 * @author felipe - 
 */
public class UserScenariosPerformanceService {

	/** Interval of time to get log from the data base. As bigger as this interval, more log are recovery and can give out of memory*/
	private final int SEARCH_INTERVAL = 60; // 60 minutes
	
	/**
	 * Return tho
	 * @param systemVersion
	 * @param isUserEnabled
	 * @return
	 */
	public Map<String, List<Double>> findTimesExecutionOfUserScenarios(String systemVersion, boolean isUserEnabled) {
		
		
		Date initialDate   = new VersionMapUtil().getInitialDateOfVersion(systemVersion);
		Date finalDate     = new VersionMapUtil().getFinalDateOfVersion(systemVersion);
		String systemName  = systemVersion.substring(0, systemVersion.indexOf('-')).trim().toUpperCase();
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
		
		while(nextTime.isBefore(finalTime) ){
			
			System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
			
			logs = dao.findAllLogOperacaoInsideIntervalBySystemVersion(systemId, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			
			System.out.println(">>>>> return: "+logs.size()+" logs");
			
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
			System.gc();
			
			MapUtil.printMapSize(retorno);
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = DateUtil.getNextInterval(SEARCH_INTERVAL, nextTime, finalTime);
		}

		return retorno;
	}
	
}
