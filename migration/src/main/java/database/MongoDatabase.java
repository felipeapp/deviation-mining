package database;

import java.net.UnknownHostException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClientURI;

import domain.RegistroEntrada;
import util.MigrationUtil;

public class MongoDatabase {

	private static MongoOperations mongo_ops;

	public MongoDatabase() {
		if (mongo_ops == null) {
			String db = MigrationUtil.getProperty("nosql_db");
			String host = MigrationUtil.getProperty("nosql_host");
			String port = MigrationUtil.getProperty("nosql_port");
			String user = MigrationUtil.getProperty("nosql_user");
			String pwd = MigrationUtil.getProperty("nosql_password");

			MongoClientURI uri = new MongoClientURI(
					"mongodb://" + user + ":" + pwd + "@" + host + ":" + port + "/" + db + "?authSource=admin");

			try {
				mongo_ops = new MongoTemplate(new SimpleMongoDbFactory(uri));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

	public void save(RegistroEntrada entrada) {
		try {
			mongo_ops.insert(entrada);
		} catch (InvalidDataAccessResourceUsageException e) {
			System.out.println("Entrada " + entrada.getIdEntrada() + " não salva: " + e.getMessage());
		}
	}

	public int getMaxIdEntrada() {
		DBCursor cursor = mongo_ops.getCollection("registroEntrada").find().sort(new BasicDBObject("idEntrada", -1))
				.limit(1);

		if (cursor.hasNext())
			return mongo_ops.getConverter().read(RegistroEntrada.class, cursor.next()).getIdEntrada();

		return 0;
	}

}
