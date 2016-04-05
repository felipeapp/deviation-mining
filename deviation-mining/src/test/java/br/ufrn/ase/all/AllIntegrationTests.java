/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufrn.ase.r.JRItest;

/**
 * Execute all integration test of the tool. Integration tests are tests when the tool communicates 
 * with others system, like database or R execution environment.
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ JRItest.class })
public class AllIntegrationTests {

}
