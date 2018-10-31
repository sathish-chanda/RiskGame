package game.main;

import game.controller.MapEditorController;
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

public class MapEditor implements EventHandler<ActionEvent> {

    private boolean loadAndEditMapFile;
    private MapFileHelper mapFileHelper;

    public MapEditor(boolean loadAndEditMapFile) {
        mapFileHelper = MapFileHelper.getInstance();
        this.loadAndEditMapFile = loadAndEditMapFile;
    }

    @Override
    public void handle(ActionEvent event) {
        LogHelper.printMessage("Opening Map editor");
        initMapEditorDialog();
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
        MapEditorController mapEditorController = new MapEditorController();
        fxmlLoader.setController(mapEditorController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 950, 500));
        mapEditorStage.show();
    }

    private void initMapEditorDialog() {
        if (loadAndEditMapFile) {
            populateMapDataToMapEditor();
        } else {
            openMapEditorLayout();
        }
    }

    /**
     * Populate data to map editor
     */
    private void populateMapDataToMapEditor() {
        File file = MapFileHelper.getFileFromFileChooser();
        if (file != null) {
            mapFileHelper.readMapFile(file.getPath());

        } else {
            LogHelper.printMessage("Unable to read file or Invalid file");
        }

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
