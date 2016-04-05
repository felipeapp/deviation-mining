package br.ufrn.ase.all;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufrn.ase.analysis.VariationTimeRangeStatisticsTest;
import br.ufrn.ase.service.performance.VariationTimeRangeServiceTest;
import br.ufrn.ase.util.RUtilTest;
import br.ufrn.ase.util.StatisticsUtilTest;
import br.ufrn.ase.util.VersionMapUtilTest;

/**
 * Run all unit tests of the tool.
 * 
 * @author jadson
 */
@RunWith(Suite.class)
@SuiteClasses({
	VersionMapUtilTest.class, 
	StatisticsUtilTest.class, 
	RUtilTest.class, 
	VariationTimeRangeStatisticsTest.class, 
	VariationTimeRangeServiceTest.class})
public class AllUnitTests {

}
