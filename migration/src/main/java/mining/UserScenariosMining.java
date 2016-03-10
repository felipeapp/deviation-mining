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
import util.StatisticsUtil;
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
	public Map<String, List<Double>> findUserScenario(String version) {

		// get all log between these dates for a specific system
		Date initialDate = VersionMapUtil.getInitialDateOfVersion(version);
		Date finalDate = VersionMapUtil.getFinalDateOfVersion(version);
		String system = version.substring(0, version.indexOf('-')).trim().toUpperCase();

		MongoOperations mongoOps = MongoDatabase.buildMongoDatabase();

		Query query = query(where("dataEntrada").gt(initialDate).lt(finalDate).and("sistema").is(system));

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

				// TODO: Avaliar se não existe forma melhor de codificar isso
				tempos.add(Double.parseDouble(new Integer(log.getTempo()).toString()));
			}

		}

		return retorno;
	}
	
	public Map<String, Double> calculateExecutionMeanScenario(Map<String, List<Double>> mapScenarioExecutionTime){
		
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();
		
		for (String key : mapScenarioExecutionTime.keySet()) {
			
			Double[] listScenarioExecutionTimeAux = mapScenarioExecutionTime.get(key).toArray(new Double[0]);
			
			double[] listScenarioExecutionTime = new double[listScenarioExecutionTimeAux.length];
			
			for (int i = 0; i < listScenarioExecutionTimeAux.length; i++) {
				listScenarioExecutionTime[i] = listScenarioExecutionTimeAux[i];
			}
			
			mapExecutionMeanScenario.put(key, StatisticsUtil.mean(listScenarioExecutionTime));
		}
		
		return mapExecutionMeanScenario;
		
	}

	public static void main(String[] args) {
		Map<String, List<Double>> map = new UserScenariosMining().findUserScenario("SIGAA-3.21.0");

		System.out.println("Cenarios Executados e Tempos de Execucao");		
		for (String key : map.keySet()) {
			System.out.println(key+" - "+map.get(key));
		}
		
		Map<String, Double> mapExecutionMeanScenario = new UserScenariosMining().calculateExecutionMeanScenario(map);

		
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("Media de tempo de execução ");
		for (String key : mapExecutionMeanScenario.keySet()) {
			System.out.println(key+" - "+mapExecutionMeanScenario.get(key));
		}
						
	}

}
