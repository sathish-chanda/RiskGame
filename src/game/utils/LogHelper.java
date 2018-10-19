package game.utils;

/**
 * This class is used to print log messages
 */
public class LogHelper {

    private static String printedMessage;

    /**
     * This method is used to print Log messages.
     *
     * @param message of type String
     */
    public static void printMessage(String message) {
        printedMessage = message;
        System.out.println(message);
    }

    /**
     * This method is used to print Log messages.
     *
     * @param message
     * @param appendedMessage
     */
    public static void printMessage(String message, String appendedMessage) {
        printedMessage = message + appendedMessage;
        System.out.println(appendedMessage + message);
    }

    /**
     * Returns current message
     *
     * @return
     */
    public static String getPrintedMessage() {
        return printedMessage;
    }
}
