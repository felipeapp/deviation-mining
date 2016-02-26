/**
 * 
 */
package mining;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import util.VersionMapUtil;
import domain.LogOperacao;
import domain.RegistroEntrada;

/**
 * This class should have mining method to get information of mongodb to discovery degradation in UFRN systems
 * 
 * @author jadson
 *
 */
public class UserScenariosMining {
	

	/**
	 * This method should get from the mongobd a map of ALL users and the time that each user spend to access ALL pages (scenario)
	 * in a specific version of a system.
	 * 
	 * All these data will be use for construct statistic analysis.
	 * 
	 *  FIXME! it is not working
	 *  FIXME!
	 *  FIXME!
	 *  FIXME!
	 * 
	 * @param version of the system
	 * @return A mapping have the   <user_id+scenario, {timeScenario1, timeScenario2, timeScenario3, ..., timeScenarioN}> 
	 */
	public Map<String, List<Integer>> findUserScenario(String version){
		
		Map<String, List<Integer>> map = new HashMap<>();
		
		// get all log between these dates
		Date initialDate = VersionMapUtil.getInitialDateOfVersion(version);
		Date finalDate = VersionMapUtil.getFinalDateOfVersion(version);
		
		
		MongoTemplate mongoTemplate = MongoDatabase.buildMongoDatabaseWithMongoTemplate();
		
		Query query = new Query(Criteria.where("logOperacao.horario").gt(initialDate).lt(finalDate));
		
		List<RegistroEntrada> registros = mongoTemplate.find(query, RegistroEntrada.class);
		
		Map<String, List<Integer>> retorno = new HashMap<String, List<Integer>>();
		
		for (RegistroEntrada registroEntrada : registros) {
			
			for (LogOperacao log : registroEntrada.getLogOperacao() ){
				
				String key = registroEntrada.getIdUsuario()+log.getAction();
				
				if(retorno.containsKey(key) ){
					retorno.get(key).add(log.getTempo());
				}else{
					retorno.put(key, Arrays.asList( new Integer[]{log.getTempo()}));
				}
			}
			
		}
		
		return retorno;
	}

}
