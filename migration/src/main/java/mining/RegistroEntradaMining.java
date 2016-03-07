package mining;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import domain.RegistroEntrada;

public class RegistroEntradaMining {

	public int getMaxIdEntrada() {
		MongoOperations mongoOp = MongoDatabase.buildMongoDatabase();

		RegistroEntrada r = mongoOp.findOne(new Query().with(new Sort(Sort.Direction.DESC, "idEntrada")),
				RegistroEntrada.class);

		return r == null ? 0 : r.getIdEntrada();
	}

}
