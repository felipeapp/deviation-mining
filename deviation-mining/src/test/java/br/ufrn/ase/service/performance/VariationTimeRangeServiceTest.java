/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.ufrn.ase.analysis.VariationTimeRangeStatistics;
import br.ufrn.ase.dao.relational.migration.ResultDataAnalysisDAO;
import br.ufrn.ase.util.MapUtil;

/**
 * Test the service class.
 * 
 * @author jadson - jadsonjs@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class VariationTimeRangeServiceTest {

	@Mock
	private ResultDataAnalysisDAO resultDataAnalysisDAO;
	@Mock
	private UserScenariosPerformanceService userScenariosService;
	@Mock
	private VariationTimeRangeStatistics variationTimeRangeStatistics;

	/**
	 * Test method for {@link br.ufrn.ase.service.performance.VariationTimeRangeService#calculateTimeRange(java.lang.String)}.
	 */
	@Test
	public void testCalculateTimeRangeUsingCache() {
		String system_version = "SIGAA-3.21.0";

		Map<String, Double> mapRange = new HashMap<String, Double>();
		mapRange.put("1.jsp", 12.000d);
		mapRange.put("2.jsp", 2.000d);
		mapRange.put("3.jsp", 0.01d);

		mapRange = MapUtil.sortByValue(mapRange);

		try {
			Mockito.when(resultDataAnalysisDAO.countVariationTimeRanges(system_version)).thenReturn(1);
			Mockito.when(resultDataAnalysisDAO.findVariationTimeRanges(system_version)).thenReturn(mapRange);

			Double previusValue = Double.MAX_VALUE;
			for (String key : mapRange.keySet()) {
				Double value = mapRange.get(key);
				if (value > previusValue)
					Assert.fail("HasMap is not orded");
				else
					previusValue = value;
			}

			Assert.assertTrue(true); // ok
		} catch (SQLException e) {
			Assert.fail(e.getMessage());
		}
	}

}
