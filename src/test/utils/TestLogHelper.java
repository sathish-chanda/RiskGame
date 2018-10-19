package test.utils;

import game.utils.LogHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * The class <code>{@Link TestLogHelper}</code> contains test cases
 * for the class <code>{@Link LogHelper}</code>
 */
public class TestLogHelper {

    /**
     * perform printing of a value test
     */
    @Test
    public void testPrintMessage() {
        String message = "message";
        LogHelper.printMessage(message);
        assertEquals(LogHelper.getPrintedMessage(), message);
    }

    /**
     * perform printing of a value test with two parameters
     */
    @Test
    public void testPrintMessageWithTwoParams() {
        String message = "message";
        String appendedMessage = "append";
        LogHelper.printMessage(message, appendedMessage);
        assertEquals(LogHelper.getPrintedMessage(), message + appendedMessage);
    }


}
