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
import br.ufrn.ase.dao.relational.migration.ResultDataAnalysisDAO;

/**
 * This service calculate scenarios of larger and smaller variation at runtime
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class VariationTimeRangeService {

	private ResultDataAnalysisDAO resultDataAnalysisDAO;
	
	/* To reuse services */
	private UserScenariosPerformanceService userScenariosService;
	
	/* To calculate statistics services */
	private VariationTimeRangeStatistics variationTimeRangeStatistics;
	
	/*If we will analyze user + scenario or just scenario*/
	private boolean userAndScenario;

	/**
	 * Constructor
	 * @param resultDataAnalysisDao
	 * @param userScenariosService
	 * @param variationTimeRangeStatistics
	 */
	public VariationTimeRangeService(ResultDataAnalysisDAO resultDataAnalysisDao,
			UserScenariosPerformanceService userScenariosService, VariationTimeRangeStatistics variationTimeRangeStatistics, boolean userAndScenario) {

		if (userScenariosService == null || variationTimeRangeStatistics == null)
			throw new IllegalArgumentException("Informaiton missing");

		this.resultDataAnalysisDAO = resultDataAnalysisDao;
		this.userScenariosService = userScenariosService;
		this.variationTimeRangeStatistics = variationTimeRangeStatistics;
		this.userAndScenario = userAndScenario;
	}

	public Map<String, Double> calculateTimeRange(String system_version) {
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		Map<String, Double> mapRange = new HashMap<String, Double>();

		try {
			// Cache is not configured
			if (resultDataAnalysisDAO == null) {
				// Mining information
				map = userScenariosService.findTimesExecutionOfUserScenarios(system_version, userAndScenario);

				// Calculate result
				mapRange = variationTimeRangeStatistics.calculateVariationTimeRange(map);
			}
			// Cache is clear
			else if (resultDataAnalysisDAO.countVariationTimeRanges(system_version) == 0) {
				// Mining information
				map = userScenariosService.findTimesExecutionOfUserScenarios(system_version, userAndScenario);

				// Calculate result
				mapRange = variationTimeRangeStatistics.calculateVariationTimeRange(map);

				// Save in cache
				resultDataAnalysisDAO.insertVariationTimeRanges(mapRange, system_version);
			}
			// Get from the cache
			else {
				mapRange = resultDataAnalysisDAO.findVariationTimeRanges(system_version);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mapRange;
	}

}
