/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.gui;

import java.util.Map;

import br.ufrn.ase.analysis.VariationTimeRangeStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.UserScenariosDAO;
import br.ufrn.ase.r.GraphicPlot;
import br.ufrn.ase.service.performance.VariationTimeRangeService;

/**
 * Console to see the information temporarily
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class ConsoleVariationRange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		System.out.println("Inicando execução... ");

		// ResultDataAnalysisDao resultDao = new ResultDataAnalysisDao(Database.buildResultDatabaseConnection());
		UserScenariosDAO userScenariosMongoDAO = DAOFactory.getDAO(UserScenariosDAO.class);
		VariationTimeRangeStatistics variationTimeRangeStatistics = new VariationTimeRangeStatistics();

		Map<String, Double> mapRange = new VariationTimeRangeService(null, userScenariosMongoDAO,
				variationTimeRangeStatistics).calculateTimeRange("SIGAA-3.21.0");

		GraphicPlot plot = new GraphicPlot();
		plot.drawColumnChart(mapRange);
		plot.drawBoxPlotChart(mapRange);

		System.out.println("Tempo: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");

	}

}
