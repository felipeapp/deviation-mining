/**
 * 
 */
package mining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.primitives.Doubles;

import util.StatisticsUtil;

/**
 * Class to calculate statistic of the data return by mining.
 * 
 * @author jadson
 *
 */
public class UserScenariosMiningStatistics {
	
	
	/***
	 * Calculate the average of the times of a user
	 * @param mapScenarioExecutionTime
	 * @return
	 */
	public Map<String, Double> calculateExecutionMeanScenario(Map<String, List<Double>> mapScenarioExecutionTime){
		
		Map<String, Double> mapExecutionMeanScenario = new HashMap<String, Double>();
		
		for (String key : mapScenarioExecutionTime.keySet()) {
			mapExecutionMeanScenario.put(key, StatisticsUtil.mean(   Doubles.toArray( mapScenarioExecutionTime.get(key) ) )   );
		}
		
		return mapExecutionMeanScenario;
		
	}

}
