package domain;

public enum Sistema {

	SIGAA(1), SIGRH(2), SIPAC(3), SIGAdmin(4);

	private final int id_sistema;

	private Sistema(int id_sistema) {
		this.id_sistema = id_sistema;
	}

	public int getId_sistema() {
		return id_sistema;
	}

}
