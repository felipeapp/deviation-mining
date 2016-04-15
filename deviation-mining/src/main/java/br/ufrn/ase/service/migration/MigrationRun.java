package br.ufrn.ase.service.migration;

import java.util.List;

import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.dao.mongo.RegistroEntradaMongoDAO;
import br.ufrn.ase.dao.relational.RegistroEntradaRelationalDAO;
import br.ufrn.ase.domain.RegistroEntrada;

public class MigrationRun {

	public static void main(String[] args) {
		RegistroEntradaDAO relational_dao = new RegistroEntradaRelationalDAO();
		RegistroEntradaDAO mongo_dao = new RegistroEntradaMongoDAO();

		int max_id_entrada = mongo_dao.getMaxIdEntrada();

		System.out.println("Última entrada migrada: " + max_id_entrada);
		List<Integer> ids = relational_dao.getIDListGreaterThan(max_id_entrada);

		System.out.println("Total de entradas pendentes: " + ids.size());
		long start = System.currentTimeMillis();

		for (int i = 0; i < ids.size(); i++) {
			System.out.println("Migrando entrada: " + ids.get(i) + " - " + (i + 1) + " / " + ids.size());

			RegistroEntrada entrada = relational_dao.findByID(ids.get(i));

			try {
				mongo_dao.insert(entrada);
			} catch (Exception e) {
				System.out.println("Entrada " + entrada.getIdEntrada() + " não salva: " + e.getMessage());
			}

			if ((i + 1) % 10 == 0)
				System.out.println("Tempo parcial: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");
		}

		System.out.println("Tempo total: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");
	}

}
