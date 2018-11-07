package game.main;

import game.controller.MapEditorOptionsController;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Class used to crate editor window for Map using java Fx
 */
public class MapEditorOptions implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        try {
            createMapEditorOptionsLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditorOptions.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

    /**
     * Method used to create map editor layout
     * @throws Exception
     */
    private void createMapEditorOptionsLayout() throws Exception {
        Stage mapEditorOptionStage = new Stage();
        mapEditorOptionStage.setTitle("Edit Map Options");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/map_editor_options.fxml"));
        MapEditorOptionsController mapEditorOptionsController = new MapEditorOptionsController();
        fxmlLoader.setController(mapEditorOptionsController);
        Parent root = fxmlLoader.load();
        mapEditorOptionStage.setScene(new Scene(root, 300, 275));
        mapEditorOptionStage.show();
    }

}
