package ase.ajc.example;

public class MainAppTest {

	public static void main(String[] args) {
		System.out.println("Main method execution!");
		new MainAppTest().another();
	}

	public void another() {
		System.out.println("Another method execution!");
	}

}
