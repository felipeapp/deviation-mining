package br.ufrn.ase.dao.mongo.migration;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.ase.dao.mongo.AbstractBasicMongoDAO;
import br.ufrn.ase.domain.RegistroEntrada;

/**
 * Make query the RegistroEntrada on MongoDB for the SINFO log database structure.
 *
 */
public class RegistroEntradaMongoDAO extends AbstractBasicMongoDAO  {

	
	/**
	 * @param mongoOps
	 */
	public RegistroEntradaMongoDAO(MongoOperations mongoOps) {
		super(mongoOps);
	}

	/**
	 * Return all RegistroEntrada of a system between a period of time
	 */
	public List<RegistroEntrada> findAllBySystemVersion(String system_name, Date initialDate, Date finalDate) {
		Query query = query(where("dataEntrada").gte(initialDate).lte(finalDate).and("sistema").is(system_name)
				.and("logOperacao").exists(true).not().size(0));

		String[] fields = { "idUsuario", "logOperacao.action", "logOperacao.tempo" };

		for (String f : fields)
			query.fields().include(f);

		return mongoOps.find(query, RegistroEntrada.class);
	}

	/**
	 * Return the last RegistroEntrada identifier
	 */
	public int getMaxIdEntrada() {
		RegistroEntrada r = mongoOps.findOne(new Query().with(new Sort(Sort.Direction.DESC, "idEntrada")),
				RegistroEntrada.class);

		return r == null ? 0 : r.getIdEntrada();
	}

	/**
	 * Insert a new RegistroEntrada in the database
	 */
	public void insert(RegistroEntrada r) {
		mongoOps.insert(r);
	}

	public RegistroEntrada findByID(int idEntrada) {
		throw new UnsupportedOperationException("findByID() is not implemented by RegistroEntradaMongoDAO");
	}

	public List<Integer> getIDListGreaterThan(int idEntrada) {
		throw new UnsupportedOperationException("getIDListGreaterThan() is not implemented by RegistroEntradaMongoDAO");
	}


}
