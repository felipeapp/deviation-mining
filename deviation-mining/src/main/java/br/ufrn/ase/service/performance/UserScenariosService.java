/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.ase.analysis.UserScenariosStatistics;
import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.util.SettingsUtil;
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
	public Map<String, List<Double>> findUserScenario(String system_version, boolean is_user_enabled) {
		Date initialDate = new VersionMapUtil().getInitialDateOfVersion(system_version);
		Date finalDate = new VersionMapUtil().getFinalDateOfVersion(system_version);
		String system_name = system_version.substring(0, system_version.indexOf('-')).trim().toUpperCase();

		RegistroEntradaDAO dao = DAOFactory.getDAO(RegistroEntradaDAO.class);

		long start = System.currentTimeMillis();

		List<RegistroEntrada> registros = dao.findAllBySystemVersion(system_name, initialDate, finalDate);

		System.out.println("###;" + registros.size() + ";" + SettingsUtil.getProperty("default_db") + ";"
				+ (System.currentTimeMillis() - start) / 1000.0);

		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();

		for (RegistroEntrada registroEntrada : registros) {
			for (LogOperacao log : registroEntrada.getLogOperacao()) {

				String key = (is_user_enabled ? registroEntrada.getIdUsuario() : "") + log.getAction();

				List<Double> tempos = retorno.get(key);

				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}

				tempos.add((double) log.getTempo());
			}
		}

		return retorno;
	}

}
