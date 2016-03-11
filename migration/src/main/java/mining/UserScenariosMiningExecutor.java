/**
 * 
 */
package mining;

import java.util.List;
import java.util.Map;

import mining.UserScenariosMiningFactory.DATABASE;

/**
 * Execute the User Scenario Mining
 * 
 * @author jadson
 *
 */
public class UserScenariosMiningExecutor {

	/**
	 * Starts here.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String, List<Double>> map = UserScenariosMiningFactory.getMining(DATABASE.MONGODB).findUserScenario("SIGAA-3.21.0");
		
		printScenarioMap(map);
		
		Map<String, Double> mapExecutionMeanScenario = new UserScenariosMiningStatistics().calculateExecutionMeanScenario(map);

		printInformationMap(mapExecutionMeanScenario);
		
	}

	private static void printScenarioMap(Map<String, List<Double>> map) {
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("Scenarios of Execution ");
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		for (String key : map.keySet()) {
			System.out.println(key+" - "+map.get(key));
		}
	}
	
	private static void printInformationMap(Map<String, Double> map) {
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		System.out.println("Mean of the execution time ");
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
		for (String key : map.keySet()) {
			System.out.println(key+" - "+map.get(key));
		}
	}
}
