package test.model;

import game.utils.LogHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class <code>{@Link TestLogHelper}</code> contains test cases
 * for the class <code>{@Link LogHelper}</code>
 */
public class TestLogHelper {

    private static LogHelper logHelper;

    /**
     * Initialize member variables for class TestLogHelper prior to execution of test cases
     */
    @Before
    public void before() {
        logHelper = new LogHelper();
    }

    /**
     * perform test for printMessage method
     */
    @Test
    public void testprintMessageWithOneParameter() {
        logHelper.printMessage("Hello World!");
        String expected = "Hello World!";
        assertEquals(expected, logHelper.getPrintedMessage());
    }

    /**
     * perform test for printMessage with two parameters
     */
    @Test
    public void testprintMessageWithTwoParameters() {
        logHelper.printMessage("Welcome to ", "Concordia!");
        String expected = "Welcome to Concordia!";
        assertEquals(expected, logHelper.getPrintedMessage());
    }

    /**
     * perform test for getprintMessage method
     */
    public void testgetprintMessage() {
        logHelper.printMessage("Risk Game");
        String expected = "Risk Game";
        assertEquals(expected, logHelper.getPrintedMessage());
    }
}
