/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.service.performance;

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
public class UserScenariosMiningFactory {

	/**
	 * The database supported
	 */
	public enum DATABASE{POSTGRES, MONGODB};
	
	/**
	 * @param database database type
	 * @return return the mining for specific database
	 */
	public static UserScenariosMining getMining(DATABASE database){
		if(database == DATABASE.MONGODB)
			return new UserScenariosMiningMongoDB();
		else 
			throw new UnsupportedOperationException("Mining in postgres not implemented");
	}
}
