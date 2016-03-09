package mining;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import domain.LogOperacao;
import domain.RegistroEntrada;
import util.VersionMapUtil;

/**
 * This class should have mining method to get information of mongodb to
 * discovery degradation in UFRN systems
 * 
 * @author jadson
 *
 */
public class UserScenariosMining {

	/**
	 * This method should get from the mongobd a map of ALL users and the time
	 * that each user spend to access ALL pages (scenario) in a specific version
	 * of a system.
	 * 
	 * All these data will be use for construct statistic analysis.
	 * 
	 * FIXME! Is it working now?
	 * 
	 * @param version
	 *            The version of the system
	 * @return A mapping have the <user_id+scenario, {timeScenario1,
	 *         timeScenario2, timeScenario3, ..., timeScenarioN}>
	 */
	public Map<String, List<Integer>> findUserScenario(String version) {

		// get all log between these dates for a specific system
		Date initialDate = VersionMapUtil.getInitialDateOfVersion(version);
		Date finalDate = VersionMapUtil.getFinalDateOfVersion(version);
		String system = version.substring(0, version.indexOf('-')).trim().toUpperCase();

		MongoOperations mongoOps = MongoDatabase.buildMongoDatabase();

		Query query = query(where("dataEntrada").gt(initialDate).lt(finalDate).and("sistema").is(system));

		List<RegistroEntrada> registros = mongoOps.find(query, RegistroEntrada.class);

		Map<String, List<Integer>> retorno = new HashMap<String, List<Integer>>();

		for (RegistroEntrada registroEntrada : registros) {

			for (LogOperacao log : registroEntrada.getLogOperacao()) {

				String key = registroEntrada.getIdUsuario() + log.getAction();

				List<Integer> tempos = retorno.get(key);

				if (tempos == null) {
					tempos = new ArrayList<Integer>();
					retorno.put(key, tempos);
				}

				tempos.add(log.getTempo());
			}

		}

		return retorno;
	}

	public static void main(String[] args) {
		Map<String, List<Integer>> map = new UserScenariosMining().findUserScenario("SIPAC-3.5.0");

		for (String key : map.keySet()) {
			System.out.println(map.get(key));
		}
	}

}
