package br.ufrn.ase.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.ase.dao.Database;
import br.ufrn.ase.dao.RegistroEntradaDAO;
import br.ufrn.ase.domain.RegistroEntrada;
import br.ufrn.ase.util.VersionMapUtil;

public class RegistroEntradaMongoDAO implements RegistroEntradaDAO {

	/**
	 * This method gets all entries from the database for a specific system
	 * version.
	 * 
	 * @param system_version
	 *            The system and its version
	 * @param fields
	 *            The list of fields to be projected in the result. In this
	 *            method the _id is always included.
	 * @return The list with all entries
	 */
	public List<RegistroEntrada> findAllBySystemVersion(String system_version, String... fields) {
		Date initialDate = new VersionMapUtil().getInitialDateOfVersion(system_version);
		Date finalDate = new VersionMapUtil().getFinalDateOfVersion(system_version);
		String system = system_version.substring(0, system_version.indexOf('-')).trim().toUpperCase();

		MongoOperations mongoOps = Database.buildMongoDatabase();

		Query query = query(where("dataEntrada").gt(initialDate).lt(finalDate).and("sistema").is(system));

		for (String f : fields)
			query.fields().include(f);

		return mongoOps.find(query, RegistroEntrada.class);
	}

}
