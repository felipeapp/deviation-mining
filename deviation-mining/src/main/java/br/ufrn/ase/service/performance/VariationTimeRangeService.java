/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.VariationTimeRangeStatistics;
import br.ufrn.ase.dao.mongodb.UserScenariosMongoDAO;
import br.ufrn.ase.dao.postgres.ResultDataAnalysisDao;

/**
 * This service calculate scenarios of larger and smaller variation at runtime
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class VariationTimeRangeService{

	private ResultDataAnalysisDao resultDataAnalysisDao;
	private UserScenariosMongoDAO userScenariosMongoDAO;
	private VariationTimeRangeStatistics variationTimeRangeStatistics;

	public VariationTimeRangeService(ResultDataAnalysisDao resultDataAnalysisDao, UserScenariosMongoDAO userScenariosMongoDAO, 
			VariationTimeRangeStatistics variationTimeRangeStatistics){
		
		if(resultDataAnalysisDao == null || userScenariosMongoDAO == null || variationTimeRangeStatistics == null)
			throw new IllegalArgumentException("Informaiton missing");
		
		this.resultDataAnalysisDao = resultDataAnalysisDao;
		this.userScenariosMongoDAO = userScenariosMongoDAO;
		this.variationTimeRangeStatistics = variationTimeRangeStatistics;
	}

	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public Map<String, Double> calculateTimeRange(String system_version) {

		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		Map<String, Double> mapRange = new HashMap<String, Double>();
		
		try{
			if(resultDataAnalysisDao.countVariationTimeRanges(system_version) == 0){ // cache is clear
				map = userScenariosMongoDAO.findUserScenario(system_version);                 // mining information
				mapRange = variationTimeRangeStatistics.calculateVariationTimeRange(map);     // calculate result
				resultDataAnalysisDao.insertVariationTimeRanges(mapRange, system_version);    // save in  the cache
			}else{
				mapRange = resultDataAnalysisDao.findVariationTimeRanges(system_version);   // get from the cache
			}
		
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}
		
		return mapRange;

	}
	
}
