package game.utils;

import javafx.scene.control.Alert;

public class ErrorViewUtils {

    /**
     * Method to show error dialog
     *
     * @param title
     * @param headerText
     * @param contentText
     */
    public static void showErrorDialog(String title, String headerText, String contentText) {
        Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(headerText);
        alertDialog.setContentText(contentText);
        alertDialog.show();
    }

}
