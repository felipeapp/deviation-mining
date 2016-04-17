/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class StringUtilTest {

	/**
	 * Test method for {@link br.ufrn.ase.util.StringUtil#formatScenarioName(java.lang.String)}.
	 */
	@Test
	public void testFormatScenarioName() {
		Assert.assertEquals("portal_docente.jsp", StringUtil.formatScenarioName("http://localhost:8080/sigaa/graduacao/menu/portal_docente.jsp"));
	}

}
