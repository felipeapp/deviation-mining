/**
 * 
 */
package mining;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import domain.LogOperacao;
import util.VersionMapUtil;

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
	 * @return A mapping have the   <user_id, {timeScenario1, timeScenario2, timeScenario3, ..., timeScenarioN}> 
	 */
	public Map<String, List<Integer>> findUserScenario(String version){
		
		Map<String, List<Integer>> map = new HashMap<>();
		
		// get all log between these dates
		Date initialDate = VersionMapUtil.getInitialDateOfVersion(version);
		Date finalDate = VersionMapUtil.getFinalDateOfVersion(version);
		
		BasicDBObject query = new BasicDBObject("Date", 
			    new BasicDBObject("$gt", initialDate)).append("$lte", finalDate);
		
		
		MongoOperations mongoOp = MongoDatabase.buildMongoDatabase();
		
		DBCursor cursor = mongoOp.getCollection("logOperacao").find(query);
		
		List<LogOperacao> listLogOperacao = new ArrayList<>();
		
		while (cursor.hasNext()){
			listLogOperacao.add( mongoOp.getConverter().read(LogOperacao.class, cursor.next()) );
		}
		
		// TODO install the mongobd and see if this is working and improve the search
		
		return null;
	}

}
