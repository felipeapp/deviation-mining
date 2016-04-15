package br.ufrn.ase.gui;

import java.util.List;
import java.util.Map;

import org.slf4j.impl.SimpleLogger;

import br.ufrn.ase.service.performance.UserScenariosService;

public class ConsoleQueryTest {

	public static void main(String[] args) {
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");

		Map<String, List<Double>> map = new UserScenariosService().findUserScenario("SIGAA-3.21.0", true);

		if (args.length == 1 && args[0].equals("print")) {
			System.out.println("-----------------------------------------");
			System.out.println(map.size() + " scenarios");
			System.out.println("-----------------------------------------");

			for (String key : map.keySet()) {
				System.out.println(key + ": " + map.get(key));
			}
		}
	}

}
