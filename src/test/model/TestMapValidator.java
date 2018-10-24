package test.model;

import game.model.MapValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;

/**
 * The class <code>{@Link TestMapValidator}</code> contains test cases
 * for the class <code>{@Link MapValidator}</code>
 */
public class TestMapValidator {
    private MapValidator mapValidator;

    /**
     * Initialize member variables for class TestMapValidator prior to execution of test cases
     */
    @Before
    public void init() {
        mapValidator = new MapValidator();
    }

    /**
     * perform test for isMapKeyValid method
     */
    @Test
    public void testisMapkeyValid()
    {
        mapValidator.setMapKeyValid(false);
        assertFalse(mapValidator.isMapKeyValid());
    }

    /**
     * perform test for setMapKeyValid method
     * setMapKeyValid(boolean)
     */
    @Test
    public void testsetMapKeyValid() {
        mapValidator.setMapKeyValid(true);
        assertTrue(mapValidator.isMapKeyValid());
    }

    /**
     * perform test for the isContinentKeyValid method
     */
    @Test
    public void testisContinentKeyValid()
    {
        mapValidator.setContinentKeyValid(true);
        assertTrue(mapValidator.isContinentKeyValid());
    }

    /**
     * perform test for setContinentKeyValid method
     * setContinentKeyValid(boolean)
     */
    @Test
    public void testsetContinentKeyValid()
    {
        mapValidator.setContinentKeyValid(true);
        assertTrue(mapValidator.isContinentKeyValid());
    }

    /**
     * perform test for the isTerrtitoryKeyValid method
     */
    @Test
    public void testisTerrtitoryKeyValid()
    {
        mapValidator.setTerrtitoryKeyValid(true);
        assertTrue(mapValidator.isTerrtitoryKeyValid());
    }

    /**
     * perform test for the setTerrtitoryKeyValid method
     */
    @Test
    public void testsetTerrtitoryKeyValid()
    {
        mapValidator.setTerrtitoryKeyValid(false);
        assertFalse(mapValidator.isTerrtitoryKeyValid());
    }

    /**
     * perform test for the getCurrentKey method
     */
    @Test
    public void testgetCurrentKey()
    {
        mapValidator.setCurrentKey("Concordia");
        String expected = "Concordia";
        assertEquals(expected, mapValidator.getCurrentKey());
    }

    /**
     * perform test for the setCurrentKey method
     * setCurrentKey(String)
     */
    @Test
    public void testsetCurrentKey()
    {
        mapValidator.setCurrentKey("Advanced Programming Practices");
        String expected = "Advanced Programming Practices";
        assertEquals(expected,mapValidator.getCurrentKey());
    }

    /**
     * perform the test for the isFileSaveSuccess method
     */
    @Test
    public void testisFileSaveSuccess()
    {
        mapValidator.setFileSaveSuccess(true);
        assertTrue(mapValidator.isFileSaveSuccess());
    }

    /**
     * perform test for setFileSaveSuccess method
     * setFileSaveSuccess(boolean)
     */
    @Test
    public void testsetFileSaveSuccess()
    {
        mapValidator.setFileSaveSuccess(false);
        assertFalse(mapValidator.isFileSaveSuccess());
    }

    /**
     * perform clean up activity, post execution of all test cases
     */
    @After
    public void clearUp()
    {
        mapValidator = null;
    }
}
