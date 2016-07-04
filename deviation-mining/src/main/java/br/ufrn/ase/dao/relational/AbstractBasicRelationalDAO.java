/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.relational;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public abstract class AbstractBasicRelationalDAO {

	/** Connection to be used by son classes */
	protected Connection connection;
	
	public AbstractBasicRelationalDAO(Connection connection){
		if(connection == null)
			throw new IllegalArgumentException("Connection is null");
		this.connection = connection;
	}
	
	/** Close JDBC connection */
	public void close(){
		if(this.connection != null)
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
