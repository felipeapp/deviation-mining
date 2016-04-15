package br.ufrn.ase.dao.nosql;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.service.performance.UserScenariosService;

public class RegistroEntradaMongoDAO extends AbstractBasicMongoDAO implements RegistroEntradaDAO {

	@Override
	public List<RegistroEntrada> findAllBySystemVersion(String system_name, Date initialDate, Date finalDate) {
		Query query = query(where("dataEntrada").gte(initialDate).lte(finalDate).and("sistema").is(system_name)
				.and("logOperacao").exists(true).not().size(0));

		String[] fields = { "idUsuario", "logOperacao.action", "logOperacao.tempo" };

		for (String f : fields)
			query.fields().include(f);

		return mongo_ops.find(query, RegistroEntrada.class);
	}

	@Override
	public int getMaxIdEntrada() {
		RegistroEntrada r = mongo_ops.findOne(new Query().with(new Sort(Sort.Direction.DESC, "idEntrada")),
				RegistroEntrada.class);

		return r == null ? 0 : r.getIdEntrada();
	}

	@Override
	public void insert(RegistroEntrada r) {
		mongo_ops.insert(r);
	}

	@Override
	public RegistroEntrada findByID(int idEntrada) {
		throw new UnsupportedOperationException("findByID() is not implemented by RegistroEntradaMongoDAO");
	}

	@Override
	public List<Integer> getIDListGreaterThan(int idEntrada) {
		throw new UnsupportedOperationException("getIDListGreaterThan() is not implemented by RegistroEntradaMongoDAO");
	}

	public static void main(String[] args) {
		UserScenariosService s = new UserScenariosService();

		System.out.println("######################################");

		long start = System.currentTimeMillis();

		Map<String, List<Double>> map = s.findUserScenario("SIGAA-3.21.0", true);

		System.out.println("Time: " + (System.currentTimeMillis() - start) / 1000.0 + " segundos");
		System.out.println("Size: " + map.size());
	}

}
