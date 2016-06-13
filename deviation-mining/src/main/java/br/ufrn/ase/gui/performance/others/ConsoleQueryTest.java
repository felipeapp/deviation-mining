package br.ufrn.ase.gui.performance.others;

import java.util.List;
import java.util.Map;

import org.slf4j.impl.SimpleLogger;

import br.ufrn.ase.service.performance.UserScenariosPerformanceService;

public class ConsoleQueryTest {

	public static void main(String[] args) {
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");

		if (args.length == 2) {
			//DAOFactory.setDefaultDB(args[0]);

			Map<String, List<Double>> map = new UserScenariosPerformanceService().findTimesExecutionOfUserScenarios("SIGAA-3.21.0", true);

			if (args[1].equalsIgnoreCase("yes")) {
				System.out.println("-----------------------------------------");
				System.out.println(map.size() + " scenarios");
				System.out.println("-----------------------------------------");

				for (String key : map.keySet()) {
					System.out.println(key + ": " + map.get(key));
				}
			}
		} else {
			System.out.println("args: <default_db> <print?>");
		}
	}

}
