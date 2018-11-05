package game.main;

import game.controller.PlayerSelectController;
import game.utils.Constants;
import game.utils.LogHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class PlayerSelect implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        LogHelper.printMessage("Player select dialog opened");
        openPlayerSelectLayout();
    }

    /**
     * Method to create map editor layout
     *
     * @throws Exception
     */
    private void createPlayerSelectLayout() throws Exception {
        Stage mapEditorStage = new Stage();
        mapEditorStage.setTitle("Select Number of players");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/player_select.fxml"));
        PlayerSelectController playerSelectController = new PlayerSelectController();
        fxmlLoader.setController(playerSelectController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 950, 500));
        mapEditorStage.show();
    }

    /**
     * Method to open player selection layout
     */
    private void openPlayerSelectLayout() {
        try {
            createPlayerSelectLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditor.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }


}
