package domain;

public enum Sistema {

	Unknown(0),
	SIPAC(1),
	SIGAA(2),
	COMUM(3),
	SCO(4),
	PROTOCOLO(5),
	IPROJECT(6),
	SIGRH(7),
	SIGADMIN(8),
	SIGED(9),
	AMBIENTS(10),
	PORTAIS(11),
	SIGPP(12),
	SIGELEICAO(13),
	TRANSPARENCIA_ATIVA(15),
	SIGEVENTO(16),
	SUCUPIRA(17),
	REDE_SOCIAL(18),
	PORTAL_INSTITUCIONAL(19),
	SIRI(20);

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
