package all;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import util.RUtilTest;
import util.StatisticsUtilTest;
import util.VersionMapUtilTest;

/**
 * Run all tests of the tool
 * @author jadson
 */
@RunWith(Suite.class)
@SuiteClasses({VersionMapUtilTest.class, StatisticsUtilTest.class, RUtilTest.class})
public class AllUnitTests {

}
