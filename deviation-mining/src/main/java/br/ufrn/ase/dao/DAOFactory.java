/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import br.ufrn.ase.dao.mongodb.RegistroEntradaMongoDAO;
import br.ufrn.ase.dao.mongodb.UserScenariosMongoDAO;

/**
 * 
 * Allow to change the database where the mining will happen.
 * 
 * We don't know yet if mongodb was a good choice to mining, so this class
 * provide the possibility of changing mongodb to postgres or another database
 * any time.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public abstract class DAOFactory {

	/**
	 * The supported database
	 */
	public enum DATABASE {
		POSTGRES, MONGODB
	};

	/**
	 * Current default database
	 */
	private static final DATABASE db = DATABASE.MONGODB;

	/**
	 * @param cls
	 *            The class to create the DAO
	 * @return return the desired DAO
	 */
	public static <T> T getDAO(Class<T> cls) {
		if (db == DATABASE.MONGODB) {
			if (cls == RegistroEntradaDAO.class)
				return cls.cast(new RegistroEntradaMongoDAO());
			else if (cls == UserScenariosDAO.class)
				return cls.cast(new UserScenariosMongoDAO());
			else
				throw new UnsupportedOperationException("DAO support for " + cls.getName() + " is not implemented");
		} else {
			throw new UnsupportedOperationException("Databse support for " + db + " is not implemented");
		}
	}

}
