/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertEquals("c(1.0,2.0,3.0,4.0,5.0)", RUtil.formatRVector( Arrays.asList( new Double[]{1d, 2d, 3d, 4d, 5d})) );
	}

}
