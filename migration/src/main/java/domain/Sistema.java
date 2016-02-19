package domain;

public enum Sistema {

	SIGAA(1), SIGRH(2), SIPAC(3), SIGAdmin(4), Unknown(5);

	private final int id_sistema;

	private Sistema(int id_sistema) {
		this.id_sistema = id_sistema;
	}

	public static Sistema fromValue(int value) {
		for (Sistema s : Sistema.values())
			if (s.id_sistema == value)
				return s;

		return Unknown;
	}

}
