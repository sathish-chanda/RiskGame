package game.utils;

/**
 * This class is used to print log messages
 */
public class LogHelper {

    /**
     * This method is used to print Log messages.
     *
     * @param message of type String
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * This method is used to print Log messages.
     *
     * @param message
     * @param appendedMessage
     */
    public static void printMessage(String message, String appendedMessage) {
        System.out.println(appendedMessage + message);
    }

}
