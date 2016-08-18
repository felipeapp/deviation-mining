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

	private static Connection miningConnection = null;
	
	private static Connection resultConnection = null;
	
	private static Connection temporaryConnection = null;
	
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
			return cls.getConstructor(Connection.class).newInstance(getMiningConnection());
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
	private static Connection getMiningConnection() {
		try {
			if (miningConnection == null) {
				String db   = SettingsUtil.getProperty("mining_db");
				String host = SettingsUtil.getProperty("mining_host");
				String port = SettingsUtil.getProperty("mining_port");
				String user = SettingsUtil.getProperty("mining_user");
				String pwd  = SettingsUtil.getProperty("mining_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				miningConnection = DriverManager.getConnection(url, user, pwd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return miningConnection;
	}
	
	
	/** Close JDBC connection */
	public static void closeMiningConnection(){
		if(miningConnection != null)
			try {
				miningConnection.close();
				miningConnection = null;
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
	

	/** Close JDBC connection */
	public static void closeResultConnection(){
		if(resultConnection != null)
			try {
				resultConnection.close();
				resultConnection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	/***
	 * Create the JDBC connection
	 * @return
	 */
	private static Connection getTemporaryRelationConnection() {
		
		try {
			if (temporaryConnection == null) {
				String db   = SettingsUtil.getProperty("temporary_db");
				String host = SettingsUtil.getProperty("temporary_host");
				String port = SettingsUtil.getProperty("temporary_port");
				String user = SettingsUtil.getProperty("temporary_user");
				String pwd  = SettingsUtil.getProperty("temporary_password");

				String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

				temporaryConnection = DriverManager.getConnection(url, user, pwd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temporaryConnection;
	}
	
	/** Close JDBC connection */
	public static void closeTemporaryConnection(){
		if(temporaryConnection != null)
			try {
				temporaryConnection.close();
				temporaryConnection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
