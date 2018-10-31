package game.main;

import game.controller.MapEditorController;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapEditor implements EventHandler<ActionEvent> {

    private boolean loadAndEditMapFile;

    public MapEditor(boolean loadAndEditMapFile) {
        this.loadAndEditMapFile = loadAndEditMapFile;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Map editor running");
        //To do get fle from file chooser
        try {
            createMapEditorLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditor.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

    private void createMapEditorLayout() throws Exception {
        Stage mapEditorStage = new Stage();
        mapEditorStage.setTitle("Edit Map");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/map_viewer.fxml"));
        MapEditorController mapEditorController = new MapEditorController(null);
        fxmlLoader.setController(mapEditorController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 300, 275));
        mapEditorStage.show();
    }

}
