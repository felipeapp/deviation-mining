/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public abstract class RelationalDatabase {
	
	private Connection connection;
	
	public RelationalDatabase(Connection connection){
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}
}
