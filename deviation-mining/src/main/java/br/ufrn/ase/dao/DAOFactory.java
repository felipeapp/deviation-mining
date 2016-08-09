/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

import br.ufrn.ase.util.SettingsUtil;

/**
 * Allow to change the database where the mining will happen.
 * 
 * We don't know yet if mongodb was a good choice to mining, so this class
 * provide the possibility of changing mongodb to postgres or another database
 * any time.
 * 
 * @author felipe
 * @author jadson - jadsonjs@gmail.com
 */
public abstract class DAOFactory {

	
	///////// No Relational Databases  //////////
	
	
	private static MongoOperations mongoOps = null;

	private static Connection relationConnection = null;
	
	private static Connection resultConnection = null;
	
	/**
	 * @param cls
	 *            The class to create the DAO
	 * @return return the desired DAO
	 */
	public static <T> T getNoSQLDAO(Class<T> cls) {
		
		try {
			return cls.getConstructor(MongoOperations.class).newInstance(getMongoDBOperations());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
	}

	
	
	///////// Relational Databases  //////////
	
	
	/**
	 * Return just relational DAOS, DAOs that execution queries on PostgreSQL
	 * 
	 * Instantiate the DAO creating a JDBC connection
	 * 
	 * @param cls The class to create the DAO
	 * @return return the desired DAO
	 */
	public static <T> T getRelationalDAO(Class<T> cls) {
		try {
			return cls.getConstructor(Connection.class).newInstance(getRelationConnection());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/** Return the Dao used to save results */
	public static <T> T getRelationalResultDAO(Class<T> cls) {
		try {
			return cls.getConstructor(Connection.class).newInstance(getResultRelationConnection());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/** Return the Dao used to save results */
	public static <T> T getRelationalTemporaryDAO(Class<T> cls) {
		try {
			return cls.getConstructor(Connection.class).newInstance(getTemporaryRelationConnection());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/////////////////////////////////////////////////////////////
	
	
	
	/***
	 * Create the JDBC connection
	 * @return
	 */
	private static Connection getRelationConnection() {
		try {
			if (relationConnection == null) {
				String db   = SettingsUtil.getProperty("sql_db");
				String host = SettingsUtil.getProperty("sql_host");
				String port = SettingsUtil.getProperty("sql_port");
				String user = SettingsUtil.getProperty("sql_user");
				String pwd  = SettingsUtil.getProperty("sql_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				relationConnection = DriverManager.getConnection(url, user, pwd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relationConnection;
	}
	
	
	/** Close JDBC connection */
	public static void closeRelationConnection(){
		if(relationConnection != null)
			try {
				relationConnection.close();
				relationConnection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	/***
	 * Create the JDBC connection
	 * @return
	 */
	private static Connection getResultRelationConnection() {
		
		try {
			if (resultConnection == null) {
				String db   = SettingsUtil.getProperty("result_db");
				String host = SettingsUtil.getProperty("result_host");
				String port = SettingsUtil.getProperty("result_port");
				String user = SettingsUtil.getProperty("result_user");
				String pwd  = SettingsUtil.getProperty("result_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				resultConnection = DriverManager.getConnection(url, user, pwd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultConnection;
	}
	
	
	/***
	 * Create the JDBC connection
	 * @return
	 */
	private static Connection getTemporaryRelationConnection() {
		
		try {
			if (resultConnection == null) {
				String db   = SettingsUtil.getProperty("temporary_db");
				String host = SettingsUtil.getProperty("temporary_host");
				String port = SettingsUtil.getProperty("temporary_port");
				String user = SettingsUtil.getProperty("temporary_user");
				String pwd  = SettingsUtil.getProperty("temporary_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				resultConnection = DriverManager.getConnection(url, user, pwd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultConnection;
	}
	

	/**
	 * create a mongodb connection
	 * @return
	 */
	private static MongoOperations getMongoDBOperations() {
		
		if (mongoOps == null) {
			String db = SettingsUtil.getProperty("nosql_db");
			String host = SettingsUtil.getProperty("nosql_host");
			String port = SettingsUtil.getProperty("nosql_port");
			String user = SettingsUtil.getProperty("nosql_user");
			String pwd = SettingsUtil.getProperty("nosql_password");

			String connection = "mongodb://" + (!user.isEmpty() && !pwd.isEmpty() ? user + ":" + pwd + "@" : "") + host
					+ ":" + port + "/" + db + "?authSource=admin";

			MongoClientURI uri = new MongoClientURI(connection);

			try {
				mongoOps = new MongoTemplate(new SimpleMongoDbFactory(uri));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return mongoOps;
	}
	

}
