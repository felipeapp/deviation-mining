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
import br.ufrn.ase.dao.Database;
import br.ufrn.ase.dao.mongodb.UserScenariosMongoDAO;
import br.ufrn.ase.dao.postgres.ResultDataAnalysisDao;

/**
 * This service calculate scenarios of larger and smaller variation at runtime
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class VariationTimeRangeService{


	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public Map<String, Double> calculateTimeRange(String system_version) {

		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		Map<String, Double> mapRange = new HashMap<String, Double>();
		
		ResultDataAnalysisDao daoCache = new ResultDataAnalysisDao(Database.buildResultDatabaseConnection());
		
		try{
			if(daoCache.countVariationTimeRanges(system_version) == 0){ // cache is clear
				map = new UserScenariosMongoDAO().findUserScenario(system_version);                 // mining information
				mapRange = new VariationTimeRangeStatistics().calculateVariationTimeRange(map);     // calculate result
				daoCache.insertVariationTimeRanges(mapRange, system_version);                       // save in  the cache
			}else{
				mapRange = daoCache.findVariationTimeRanges(system_version);   // get from the cache
			}
		
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}
		
		return mapRange;

	}
	
}
