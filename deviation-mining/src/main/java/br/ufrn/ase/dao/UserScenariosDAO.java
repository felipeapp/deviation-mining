/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.dao;

import java.util.List;
import java.util.Map;

/**
 * Declare Mining Methods
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
public interface UserScenariosDAO {

	public Map<String, List<Double>> findUserScenario(String system_version, boolean is_user_enabled);

}
