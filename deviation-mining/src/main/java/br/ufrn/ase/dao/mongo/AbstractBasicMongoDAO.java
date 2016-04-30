/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public abstract class AbstractBasicMongoDAO {
	
	/** MongoOperations to be used by son classes */
	protected MongoOperations mongoOps;
	
	public AbstractBasicMongoDAO(MongoOperations mongoOps){
		if(mongoOps == null)
			throw new IllegalArgumentException("Connection is null");
		this.mongoOps = mongoOps;
	}

}
