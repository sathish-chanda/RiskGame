package game.controller;

import game.main.CreateNewMap;
import game.main.MapEditor;
import game.model.RiskModel;
import game.utils.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class implements java FX for map editor
 */
public class MapEditorOptionsController implements Initializable {


    @FXML
    private Button createMapButton;
    @FXML
    private Button loadEditMapButton;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonActions();
    }

    /**
     * Method to initialize button actions
     */
    private void initButtonActions() {
        createMapButton.setOnAction(createMapFileDialog());
        loadEditMapButton.setOnAction(loadAndEditMapFileDialog());
        backButton.setOnAction(event -> closeMapEditorOptionsWindow());
    }

    /**
     * Method to open map editor dialog with loaded map data
     */
    private MapEditor loadAndEditMapFileDialog() {
        MapEditor mapEditor = new MapEditor();
        return mapEditor;
    }

    /**
     * Method to open map editor dialog with empty data
     */
    private CreateNewMap createMapFileDialog() {
        CreateNewMap createNewMap = new CreateNewMap();
        return createNewMap;
    }

    /**
     * Method to close Map Editor Options window
     */
    @FXML
    private void closeMapEditorOptionsWindow() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

}
