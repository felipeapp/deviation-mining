/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.ase.dao.DAOFactory;
import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.dao.UserScenariosDAO;
import br.ufrn.ase.domain.LogOperacao;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.util.VersionMapUtil;

/**
 * This class should have mining method to get information on mongodb database.
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public class UserScenariosMongoDAO implements UserScenariosDAO {

	/**
	 * This method should get from the mongobd a map of ALL users and the time
	 * that each user spend to access ALL pages (scenario) in a specific version
	 * of a system.
	 * 
	 * All these data will be use for construct statistic analysis.
	 * 
	 * @param system_version
	 *            The version of the system
	 * @return A mapping have the <user_id+scenario, {timeScenario1,
	 *         timeScenario2, timeScenario3, ..., timeScenarioN}>
	 */
	public Map<String, List<Double>> findUserScenario(String system_version, boolean is_user_enabled) {
		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();

		List<RegistroEntrada> registros = DAOFactory.getDAO(RegistroEntradaDAO.class)
				.findAllBySystemVersion(system_version, "idUsuario", "logOperacao.action", "logOperacao.tempo");

		for (RegistroEntrada registroEntrada : registros) {
			for (LogOperacao log : registroEntrada.getLogOperacao()) {

				String key = (is_user_enabled ? registroEntrada.getIdUsuario() : "") + log.getAction();

				List<Double> tempos = retorno.get(key);

				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}

				tempos.add(new Double((double) log.getTempo()));
			}
		}

		return retorno;
	}
	
	
	/**
	 * This method should get from the mongobd a map of ALL users and the time
	 * that each user spend to access ALL pages (scenario) in a specific version
	 * of a system.
	 * 
	 * All these data will be use for construct statistic analysis.
	 * 
	 * @param version
	 *            The version of the system
	 * @return A mapping have the <user_id+scenario, {timeScenario1,
	 *         timeScenario2, timeScenario3, ..., timeScenarioN}>
	 */
	public Map<String, List<Double>> findUserScenario(String version) {

		// get all log between these dates for a specific system
		Date initialDate = new VersionMapUtil().getInitialDateOfVersion(version);
		Date finalDate = new VersionMapUtil().getFinalDateOfVersion(version);
		String system = version.substring(0, version.indexOf('-')).trim().toUpperCase();

		MongoOperations mongoOps = MongoDatabase.buildMongoDatabase();

		Query query = query(where("dataEntrada").gt(initialDate).lt(finalDate).and("sistema").is(system));
		query.fields().include("idUsuario").include("logOperacao.action").include("logOperacao.tempo");

		List<RegistroEntrada> registros = mongoOps.find(query, RegistroEntrada.class);

		Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();

		for (RegistroEntrada registroEntrada : registros) {

			for (LogOperacao log : registroEntrada.getLogOperacao()) {

				String key = registroEntrada.getIdUsuario() + log.getAction();

				List<Double> tempos = retorno.get(key);

				if (tempos == null) {
					tempos = new ArrayList<Double>();
					retorno.put(key, tempos);
				}

				tempos.add(new Double((double) log.getTempo()));
			}

		}

		return retorno;
	}

}
