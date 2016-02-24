/**
 * 
 */
package mining;

import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import domain.RegistroEntrada;

/**
 * @author jadson
 *
 */
public class RegistroEntradaMining {
	
	public int getMaxIdEntrada() {
		
		MongoOperations mongoOp = MongoDatabase.buildMongoDatabase();
		
		DBCursor cursor = mongoOp.getCollection("registroEntrada").find().sort(new BasicDBObject("idEntrada", -1))
				.limit(1);

		if (cursor.hasNext())
			return mongoOp.getConverter().read(RegistroEntrada.class, cursor.next()).getIdEntrada();

		return 0;
	}
	

}
