package test.utils;

import game.utils.MapFileHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * The class <code>{@Link TestMapFileHelper}</code> contains test cases
 * for the class <code>{@Link MapFileHelper}</code>
 */
public class TestMapFileHelper {

    private MapFileHelper mapFileHelper;

    /**
     * Initialize member variables for class TestMapFileHelper prior to execution of test cases
     */
    @Before
    public void init() {
        mapFileHelper = MapFileHelper.getInstance();
    }

    /**
     * perform reading the map file test
     */
    @Test
    public void testReadMapFile() {
        mapFileHelper.readMapFile();
        assertTrue(mapFileHelper.isMapValid());
    }

    /**
     * perform saving the map file test
     */
    @Test
    public void testSaveMapFile() {
        mapFileHelper.saveMapFile();
        assertTrue(mapFileHelper.isFileSaveSuccess());
    }

    /**
     * perform clean up activity, post execution of all test cases
     */
    @After
    public void cleanUp() {
        mapFileHelper = null;
    }

}