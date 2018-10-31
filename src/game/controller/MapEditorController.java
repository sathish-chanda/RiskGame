package game.controller;

import game.model.RiskModel;
import game.utils.Constants;
import game.view.MapEditorView;
import game.view.RiskView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class MapEditorController implements EventHandler<ActionEvent> {

    private RiskModel riskModel;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextField wrapTextField;
    @FXML
    private TextField scrollTextField;
    @FXML
    private TextField warnTextField;
    @FXML
    private Button saveMapButoon;
    @FXML
    private Button exitMapButoon;

    /**
     * Constructor of class {@link MapEditorController}
     */
    public MapEditorController() {
    }

    @Override
    public void handle(ActionEvent event) {

    }


    /**
     * method to load map file
     */
    private void loadMapFile() {

    }

    /**
     * method to create map file
     */
    private void createMapFile() {

    }

    /**
     * Method to save map file
     */
    private void saveMapFile() {

    }

}
