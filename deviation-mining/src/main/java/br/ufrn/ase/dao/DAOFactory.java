/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import br.ufrn.ase.dao.mongo.RegistroEntradaMongoDAO;
import br.ufrn.ase.dao.relational.RegistroEntradaRelationalDAO;
import br.ufrn.ase.util.SettingsUtil;

/**
 * Allow to change the database where the mining will happen.
 * 
 * We don't know yet if mongodb was a good choice to mining, so this class
 * provide the possibility of changing mongodb to postgres or another database
 * any time.
 * 
 * @author jadson - jadsonjs@gmail.com
 */
public abstract class DAOFactory {

	/**
	 * The supported database
	 */
	private enum DATABASE {
		POSTGRES, MONGODB
	};

	/**
	 * Current default database
	 */
	private static DATABASE db = DATABASE.valueOf(SettingsUtil.getProperty("default_db").toUpperCase());

	/**
	 * @param cls
	 *            The class to create the DAO
	 * @return return the desired DAO
	 */
	public static <T> T getDAO(Class<T> cls) {
		if (db == DATABASE.MONGODB) {
			if (cls == RegistroEntradaDAO.class)
				return cls.cast(new RegistroEntradaMongoDAO());
		} else if (db == DATABASE.POSTGRES) {
			if (cls == RegistroEntradaDAO.class)
				return cls.cast(new RegistroEntradaRelationalDAO());
		} else {
			throw new UnsupportedOperationException("Databse support for " + db + " is not implemented");
		}

		throw new UnsupportedOperationException("DAO support for " + cls.getName() + " is not implemented");
	}

	/**
	 * Change current default database
	 */
	public static void setDefaultDB(String database) {
		db = DATABASE.valueOf(database.toUpperCase());
		SettingsUtil.setProperty("default_db", database);
	}

}
