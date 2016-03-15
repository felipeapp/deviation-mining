/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import br.ufrn.ase.dao.mongodb.UserScenariosMongoDBDAO;

/**
 * 
 * Allow to change the database where the mining will happen. if is relational database or nosql database.
 * 
 * We don't know yet if mongodb was a good choice to mining, so this class provide change mongodb to postgre 
 * or another database any time.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class UserScenariosDAOFactory {

	/**
	 * The database supported
	 */
	public enum DATABASE{POSTGRES, MONGODB};
	
	/**
	 * @param database database type
	 * @return return the mining for specific database
	 */
	public static UserScenariosDAO getDAO(DATABASE database){
		if(database == DATABASE.MONGODB)
			return new UserScenariosMongoDBDAO();
		else 
			throw new UnsupportedOperationException("Mining in postgres not implemented");
	}
}
