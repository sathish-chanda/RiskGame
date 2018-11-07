package game.main;

import game.controller.MapController;
import game.model.Continent;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Class used to edit the map
 */
public class MapEditor implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        LogHelper.printMessage("Opening Map editor");
        openMapEditorLayout();
    }

    /**
     * Method to create map editor layout
     *
     * @throws Exception
     */
    private void createMapEditorLayout() throws Exception {
        Stage mapEditorStage = new Stage();
        mapEditorStage.setTitle("Edit Map");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/map_viewer.fxml"));
        MapController mapController = new MapController(true);
        fxmlLoader.setController(mapController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 950, 500));
        mapEditorStage.show();
    }

    /**
     * Method to open map editor layout
     */
    private void openMapEditorLayout() {
        try {
            createMapEditorLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditor.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

}
