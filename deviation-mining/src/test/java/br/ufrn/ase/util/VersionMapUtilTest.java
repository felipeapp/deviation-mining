package br.ufrn.ase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test extract data of UFRN system published versions.
 * 
 * @author jadson
 */
public class VersionMapUtilTest {

	private VersionMapUtil versionMapUtil;
	
	@Before
	public void setup(){
		
		versionMapUtil = new VersionMapUtil();
		versionMapUtil.setCsvFile("src/test/resources/test_versions_map.csv");
	}
	
	/**
	 * Test for a specific version of the SIGAA Version
	 */
	@Test
	public void testGetDataInicialVersao() {
		try {
			Assert.assertEquals( new SimpleDateFormat("dd/MM/yy").parse("22/11/14"), versionMapUtil.getInitialDateOfVersion("SIGAA-3.5.0") );
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	/**
	 * Test for a specific version of the SIGAA Version
	 */
	@Test
	public void testGetDataFinalVersao() {
		try {
			Assert.assertEquals( new SimpleDateFormat("dd/MM/yy").parse("27/03/15"), versionMapUtil.getFinalDateOfVersion("SIGAA-3.5.0") );
		} catch (ParseException e) {
			Assert.fail();
		}
	}

}
