package test.suiterunner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.utils.TestMapFileHelper;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestMapFileHelper.class})
public class TestSuiteRunner {

}
