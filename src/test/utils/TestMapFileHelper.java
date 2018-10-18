package test.utils;

import game.utils.MapFileHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;

import static junit.framework.TestCase.assertTrue;

public class TestMapFileHelper {

    private MapFileHelper mapFileHelper;

    /**
     * Method to inititalize TestMapFileHelper
     */
    @Before
    public void init() {
        mapFileHelper = MapFileHelper.getInstance();
    }

    @Test
    public void testReadMapFile() {
        mapFileHelper.readMapFile();
        assertTrue(mapFileHelper.isMapValid());
    }

    @Test
    public void testSaveMapFile() {
        mapFileHelper.saveMapFile();
        assertTrue(mapFileHelper.isFileSaveSuccess());
    }

}
