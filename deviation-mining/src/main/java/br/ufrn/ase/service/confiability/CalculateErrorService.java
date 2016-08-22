/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.confiability;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.relational.performance.mining.LogOperacaoDao;
import br.ufrn.ase.dao.relational.performance.result.ResultDataAnalysisDAO;
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
public class CalculateErrorService {

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
		
		List<String> values = new ArrayList<>();
		
		int qtd = 0;
		
		for (String scenario : readAllScenariosError() ) { // for each scenario that was mined
			
			if(qtd % 100 == 0 ){
				System.out.println("Calculating information for scenario:  "+scenario);
			}
			
			// all the traces 
			values = readScenariosValuesError(scenario); // just for one scenario to save memory
	
			Map<String, Integer> result = calculateAmountOfTraces(values);
			
			if(qtd % 100 == 0 ){
				System.out.println("Saving on result database ... ");
			}
			
			
			saveResultsTotalOfError(systemVersion, scenario, values.size());
			saveResultsByScenario(systemVersion, scenario, result);
			
			
			
			values = new ArrayList<>();
			
			qtd++;
			
		}
		
		
	}
	


	/**
	 * @param systemVersion
	 * @param scenario
	 * @param result
	 */
	private void saveResultsByScenario(String systemVersion, String scenario, Map<String, Integer> result) {
		
		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertTotalOfErrorByScenario(systemVersion, scenario, result);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}finally{
			DAOFactory.closeResultConnection();
		}
	}



	/**
	 * @param systemVersion
	 * @param scenario
	 * @param size
	 */
	private void saveResultsTotalOfError(String systemVersion, String scenario, int size) {

		ResultDataAnalysisDAO dao = DAOFactory.getRelationalResultDAO(ResultDataAnalysisDAO.class);
		
		try{
			dao.insertTotalOfError(scenario, size, systemVersion);
		}catch(SQLException sqlEx){
			JOptionPane.showMessageDialog(new JPanel(), sqlEx.getMessage(), "Error Save Results", JOptionPane.ERROR_MESSAGE);
			sqlEx.printStackTrace();
		}finally{
			DAOFactory.closeResultConnection();
		}
		
	}



	/**
	 * @param values
	 * @return
	 */
	private Map<String, Integer> calculateAmountOfTraces(List<String> traces) {
		
		Map<String, Integer> _return = new HashMap<>();
		
		int qtd = 0;
		
		for (String trace : traces) {
			if( ! _return.containsKey(trace)){
				_return.put(trace, 1); // frist time
			}else{
				qtd = _return.get(trace);
				_return.put(trace, qtd+1);
			}
		}
		
		return _return;
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
	
		Map<String, List<String>> retorno = new HashMap<String, List<String>>();
		List<String> traces = null;
		List<LogOperacao> logs = null;
		String key = "";
		
		int qtd = 0;
		
		while(nextTime.isBefore(finalTime) ){
			
			LogOperacaoDao dao = DAOFactory.getRelationalDAO(LogOperacaoDao.class);
			logs = dao.findAllLogOperacaoInsideIntervalBySystemVersionForError(systemId, DateUtil.toDate(currentTime), DateUtil.toDate(nextTime));
			DAOFactory.closeMiningConnection();
			
			if(qtd % 100 == 0 ){
				System.out.println(">>>>> Analyzing Interval: "+qtd+" ");
				System.out.println(">>>>> interval: "+currentTime+" "+nextTime);
				System.out.println(">>>>> return: "+logs.size()+" logs");
			}
			
			for (LogOperacao log : logs) {
	
				key = (isUserEnabled ? log.getRegistroEntrada().getIdUsuario()+"_" : "") + log.getAction();
	
				traces = retorno.get(key);
	
				if (traces == null) {
					traces = new ArrayList<String>();
					retorno.put(key, traces);
				}
	
				traces.add(log.getTrace());
			}
			
			// try to clear the JVM memory as much as possible, this list of log can be very big //
			logs = null;
			
			
			// this is very important, we cannot keek this map in the memory, it can be very big
			if( MapUtil.getMapSize(retorno) > RESULT_MAP_SIZE){
				long time = System.currentTimeMillis();
				System.out.println(">>>>> Store in temporary database "+retorno.size()+" keys");
				storeMapInDataBase(retorno);
				retorno = new HashMap<String, List<String>>();   // try to clear the JVM memory as much as possible, this list of log can be very big
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
	private List<String> readAllScenariosError() {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		List<String> list =  dao.readAllScenariosError();
		DAOFactory.closeTemporaryConnection();
		return list;
	}
	
	/**
	 * Load all values of one specific scenario
	 * 
	 * @param scenario
	 * @return
	 */
	private List<String> readScenariosValuesError(String scenario) {
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		List<String> list =  dao.readScenariosErrorValues(scenario);
		DAOFactory.closeTemporaryConnection();
		return list;
	}


	/**
	 * Store the entire map in a data, insert a new scenario or update the values of a existent one.
	 * 
	 * @param retorno
	 */
	private static void storeMapInDataBase(Map<String, List<String>> map) {
		
		TemporaryDataAnalysisDAO dao = DAOFactory.getRelationalTemporaryDAO(TemporaryDataAnalysisDAO.class);
		
		for (String scenario : map.keySet()) {
			
			if(dao.contaisScenariosError(scenario)){
				
				List<String> traces = map.get(scenario);
				for (String trace : traces) {
					dao.insertScenarioErrorValues(scenario, trace);
				}
				
			}else{
				dao.insertNewScenarioError(scenario);
				
				List<String> traces = map.get(scenario);
				for (String trace : traces) {
					dao.insertScenarioErrorValues(scenario, trace);
				}
			}
		}
		DAOFactory.closeTemporaryConnection();
      
	}
	
	
}
