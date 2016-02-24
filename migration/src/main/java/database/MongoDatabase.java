package database;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

import util.MigrationUtil;

/**
 * This class Controls the connection with the mongobd
 * 
 * @author felipe
 *
 */
public class MongoDatabase {

	/* Allow to make inserts, updates and queries in the mongobd */
	private static MongoOperations mongo_ops;

	/**
	 * Public Constructor
	 */
	private MongoDatabase() {
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
	
	/** Static Factory Method to build a MongoDatabase object  */
	public static MongoOperations buildMongoDatabase() { return new MongoDatabase().getMongo_ops(); }
	
	
	private MongoOperations getMongo_ops() { return mongo_ops; }

	
//	/** Save a RegistroEntrada document in MongoBD */
//	public void save(RegistroEntrada entrada) {
//		try {
//			mongo_ops.insert(entrada);
//		} catch (InvalidDataAccessResourceUsageException e) {
//			System.out.println("Entrada " + entrada.getIdEntrada() + " não salva: " + e.getMessage());
//		}
//	}

}
