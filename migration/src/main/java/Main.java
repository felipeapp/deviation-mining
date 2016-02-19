import java.util.List;

import database.RegistroEntradaDAO;

public class Main {

	public static void main(String[] args) {

		RegistroEntradaDAO dao = new RegistroEntradaDAO();

		long start = System.currentTimeMillis();

		List<Integer> ids = dao.getIDList();

		System.out.println("Total de entradas: " + ids.size());

		for (int i = 0; i < ids.size(); i++) {
			System.out.println(i + 1 + " / " + ids.size());
			System.out.println(dao.findByID(ids.get(i)));
		}

		System.out.println((System.currentTimeMillis() - start) / 1000.0);

		dao.close();

	}

}
