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

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.LogOperacaoDao;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.util.DateUtil;
import br.ufrn.ase.util.MapUtil;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * Execute the User Scenario Mining
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class UserScenariosService {

	public Map<String, Double> calculateExecutionMeanScenario(String system_version) {
		Map<String, List<Double>> map = findUserScenario(system_version, true);

		Map<String, Double> mapExecutionMeanScenario = new UserScenariosStatistics()
				.calculateExecutionMeanScenario(map);

		return mapExecutionMeanScenario;
	}

	public Map<String, Double> calculateCoefficientOfVariation(String system_version) {
		Map<String, List<Double>> map = findUserScenario(system_version, true);

		Map<String, Double> mapCVScenario = new UserScenariosStatistics().calculateCoefficientOfVariation(map, true);

		return mapCVScenario;
	}

	/**
	 * This method gets from the mongodb a map of all users and the time that
	 * each user spends to access all pages (scenarios) in a specific version of
	 * a system.
	 * 
	 * @param system_version
	 *            The system and its version
	 * @param is_user_enabled
	 *            If true the map key will be like user_id+scenario. If false it
	 *            will be only scenario.
	 * @return A map like <user_id+scenario, {timeScenario1, timeScenario2,
	 *         timeScenario3, ..., timeScenarioN}> if is_user_enabled is true,
	 *         or <scenario, {timeScenario1, timeScenario2, timeScenario3, ...,
	 *         timeScenarioN}> if is_user_enabled is false.
	 */
	public Map<String, List<Double>> findUserScenario(String systemVersion, boolean is_user_enabled) {
		
		
		Date initialDate   = new VersionMapUtil().getInitialDateOfVersion(systemVersion);
		Date finalDate     = new VersionMapUtil().getFinalDateOfVersion(systemVersion);
		String systemName = systemVersion.substring(0, systemVersion.indexOf('-')).trim().toUpperCase();

		LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);
		
		final int INTERVAL = 60; // 1 hour
		
		LocalDateTime initialTime =  DateUtil.toLocalDateTime(initialDate); 
		LocalDateTime finalTime =    DateUtil.toLocalDateTime(finalDate); 
		
		System.out.println(">>>>> teste: "+initialTime+" "+finalTime);
		
		LocalDateTime currentTime = initialTime;
		LocalDateTime nextTime = DateUtil.getNextInterval(INTERVAL, initialTime, finalTime);
	
		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();
		List<Double> tempos = null;
		List<LogOperacao> logs = null;
		String key = "";
		
		while(nextTime.isBefore(finalTime) ){
			
			System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
			
			logs = dao.findAllBySystemVersion(systemName, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			
			System.out.println("return "+logs.size()+" logs");
			
			for (LogOperacao log : logs) {
	
				key = (is_user_enabled ? log.getRegistroEntrada().getIdUsuario()+"_" : "") + log.getAction();
	
				tempos = retorno.get(key);
	
				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}
	
				tempos.add((double) log.getTempo());
			}
			
			System.out.println(" logs to null ");
			logs = null;
			System.gc();
			
			MapUtil.printMapSize(retorno);
			
			// Updates the interval, goes to the next interval
			currentTime = nextTime;
			nextTime = DateUtil.getNextInterval(INTERVAL, nextTime, finalTime);
		}

		return retorno;
	}
	
}
