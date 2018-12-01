package test.suiterunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.model.*;
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
        TestMapValidator.class,
        TestPlayer.class,
        TestRiskModel.class,
        TestTournamentMode.class,
        TestAggressPlayer.class,
        TestCheaterPlayer.class})
public class TestSuiteRunner {

}
