package test.suiterunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.model.TestGameMap;
import test.model.TestContinent;
import test.model.TestMapValidator;
import test.model.TestTerritory;
import test.utils.TestLogHelper;
import test.utils.TestMapFileHelper;

/**
 * suite class run the all test classes
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestMapFileHelper.class,
        TestContinent.class,
        TestTerritory.class,
        TestGameMap.class,
        TestLogHelper.class,
        TestMapValidator.class})
public class TestSuiteRunner {

}
