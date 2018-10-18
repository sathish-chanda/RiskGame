package test.suiterunner;

import game.model.Continent;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.model.TestContinent;
import test.model.TestTerritory;
import test.utils.TestMapFileHelper;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestMapFileHelper.class,
        TestContinent.class,
        TestTerritory.class})
public class TestSuiteRunner {

}
