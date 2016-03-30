/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.ufrn.ase.util.RUtil;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class RUtilTest {

	/**
	 * Test method for {@link br.ufrn.ase.util.RUtil#formatRVector(java.util.List)}.
	 */
	@Test
	public void testFormatRVector() {
		Assert.assertEquals("c(1,2,3,4,5)", RUtil.formatRVector( Arrays.asList( new String[]{"1", "2", "3", "4", "5"})) );
	}

}
