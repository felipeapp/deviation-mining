package ase.ajc.example;

import br.ufrn.ppgsc.scenario.analyzer.common.annotations.arq.Scenario;

public class MainAppTest {

	@Scenario(name = "teste aj-gradle")
	public static void main(String[] args) {
		System.out.println("Main method execution!");
		new MainAppTest().another();
	}

	public void another() {
		System.out.println("Another method execution!");
	}

}
