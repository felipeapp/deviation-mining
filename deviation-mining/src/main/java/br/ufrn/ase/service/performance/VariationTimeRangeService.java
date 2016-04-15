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
import br.ufrn.ase.dao.relational.ResultDataAnalysisDAO;

/**
 * This service calculate scenarios of larger and smaller variation at runtime
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class VariationTimeRangeService {

	private ResultDataAnalysisDAO resultDataAnalysisDAO;
	private UserScenariosService userScenariosService;
	private VariationTimeRangeStatistics variationTimeRangeStatistics;

	public VariationTimeRangeService(ResultDataAnalysisDAO resultDataAnalysisDao,
			UserScenariosService userScenariosService, VariationTimeRangeStatistics variationTimeRangeStatistics) {

		if (userScenariosService == null || variationTimeRangeStatistics == null)
			throw new IllegalArgumentException("Informaiton missing");

		this.resultDataAnalysisDAO = resultDataAnalysisDao;
		this.userScenariosService = userScenariosService;
		this.variationTimeRangeStatistics = variationTimeRangeStatistics;
	}

	public Map<String, Double> calculateTimeRange(String system_version) {
		Map<String, List<Double>> map = new HashMap<String, List<Double>>();
		Map<String, Double> mapRange = new HashMap<String, Double>();

		try {
			// Cache is not configured
			if (resultDataAnalysisDAO == null) {
				// Mining information
				map = userScenariosService.findUserScenario(system_version, true);

				// Calculate result
				mapRange = variationTimeRangeStatistics.calculateVariationTimeRange(map);
			}
			// Cache is clear
			else if (resultDataAnalysisDAO.countVariationTimeRanges(system_version) == 0) {
				// Mining information
				map = userScenariosService.findUserScenario(system_version, true);

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
