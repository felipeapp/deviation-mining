package br.ufrn.ase.dao.mongo;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

import br.ufrn.ase.util.MigrationUtil;

public abstract class AbstractBasicMongoDAO {

	protected static MongoOperations mongo_ops;

	protected AbstractBasicMongoDAO() {
		if (mongo_ops == null) {
			String db = MigrationUtil.getProperty("nosql_db");
			String host = MigrationUtil.getProperty("nosql_host");
			String port = MigrationUtil.getProperty("nosql_port");
			String user = MigrationUtil.getProperty("nosql_user");
			String pwd = MigrationUtil.getProperty("nosql_password");

			String connection = "mongodb://" + (!user.isEmpty() && !pwd.isEmpty() ? user + ":" + pwd + "@" : "") + host
					+ ":" + port + "/" + db + "?authSource=admin";

			MongoClientURI uri = new MongoClientURI(connection);

			try {
				mongo_ops = new MongoTemplate(new SimpleMongoDbFactory(uri));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

}
