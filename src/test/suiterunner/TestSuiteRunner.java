package test.suiterunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.TestGameMap;
import test.model.TestContinent;
import test.model.TestTerritory;
import test.utils.TestLogHelper;
import test.utils.TestMapFileHelper;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestMapFileHelper.class,
        TestContinent.class,
        TestTerritory.class,
        TestGameMap.class,
        TestLogHelper.class})
public class TestSuiteRunner {

}
