package br.ufrn.ase.dao.mongo;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

import br.ufrn.ase.util.SettingsUtil;

public abstract class AbstractBasicMongoDAO {

	protected static MongoOperations mongo_ops;

	protected AbstractBasicMongoDAO() {
		if (mongo_ops == null) {
			String db = SettingsUtil.getProperty("nosql_db");
			String host = SettingsUtil.getProperty("nosql_host");
			String port = SettingsUtil.getProperty("nosql_port");
			String user = SettingsUtil.getProperty("nosql_user");
			String pwd = SettingsUtil.getProperty("nosql_password");

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
