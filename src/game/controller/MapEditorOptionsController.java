package game.controller;

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

public class MapEditorOptionsController implements Initializable {

    private RiskModel riskModel;

    @FXML
    private Button createMapButton;
    @FXML
    private Button loadEditMapButton;
    @FXML
    private Button backButton;

    /**
     * Constructor of class {@link MapEditorOptionsController}
     *
     * @param riskModel
     */
    public MapEditorOptionsController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

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
     * Method to open map editor dialog
     */
    private MapEditor loadAndEditMapFileDialog() {
        MapEditor mapEditor = new MapEditor(true);
        return mapEditor;
    }

    /**
     *
     */
    private MapEditor createMapFileDialog() {
        MapEditor mapEditor = new MapEditor(false);
        return mapEditor;
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
