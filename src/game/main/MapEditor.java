package game.main;

import game.controller.MapEditorController;
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

public class MapEditor implements EventHandler<ActionEvent> {

    private boolean loadAndEditMapFile;
    private RiskModel riskModel;

    public MapEditor(boolean loadAndEditMapFile) {
        riskModel = new RiskModel();
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
        MapEditorController mapEditorController = new MapEditorController(riskModel);
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
            riskModel.loadMapData(file.getPath());
            Map<String, String> mapComponents = riskModel.getGame().getGameMap().getMapComponentsHashMap();
            List<Continent> continents = riskModel.getGame().getGameMap().getContinentListMap();
            openMapEditorLayout();
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
