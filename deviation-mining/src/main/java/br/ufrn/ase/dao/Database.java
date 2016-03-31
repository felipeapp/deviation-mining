package br.ufrn.ase.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

import br.ufrn.ase.util.MigrationUtil;

/**
 * This class Controls the connection with the mongodb
 * 
 * @author felipe
 */
public class Database {

	/* It allows to make inserts, updates and queries in the MongoDB */
	private static MongoOperations mongo_ops;
	
	/* Relation database connection */
	private static Connection con;
	
	/**
	 * Public Constructor
	 */
	private Database() {
		
	}

	/** Static Factory Method to build a MongoDatabase object */
	public static MongoOperations buildMongoDatabase() {
		
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
		
		return mongo_ops;
	}

	/** Static Factory Method to build a MongoDatabase object */
	public static Connection buildResultDatabaseConnection() {
		
		if(con == null){
			String driver = "org.postgresql.Driver";
	
	        String db = MigrationUtil.getProperty("db_result");
			String host = MigrationUtil.getProperty("host_result");
			String port = MigrationUtil.getProperty("port_result");
			String user = MigrationUtil.getProperty("user_result");
			String pwd = MigrationUtil.getProperty("password_result");
			
	        String url = "jdbc:postgresql://"+host+":"+port+"/"+db;
	        
	        try {
	            Class.forName(driver);
	            con = (Connection) DriverManager.getConnection(url, user, pwd);
	           
	        } catch (ClassNotFoundException ex) {
	            System.err.print(ex.getMessage());
	        } catch (SQLException e) {
	            System.err.print(e.getMessage());
	        }
		}
		
		return con;
	}
	
}
