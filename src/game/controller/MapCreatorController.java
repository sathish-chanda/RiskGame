package game.controller;

import game.model.RiskModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MapCreatorController implements Initializable {

    private RiskModel riskModel;
    @FXML
    private Button saveMapButton;
    @FXML
    private Button exitMapEditorButton;
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
    private ListView continentsListView;
    @FXML
    private ComboBox continentsComboBox;
    @FXML
    private ListView territoriesListView;
    @FXML
    private ComboBox territoriesComboBox;
    @FXML
    private ListView adjacentTerritoriesListView;
    @FXML
    private TextField continentTextField;
    @FXML
    private TextField continentValueTextField;
    @FXML
    private TextField territoryTextField;
    @FXML
    private TextField latitudeTextField;
    @FXML
    private TextField longitudeTextField;
    @FXML
    private TextField adjacentTerritoryTextField;

    /**
     * Constructor of class {@link MapCreatorController}
     *
     * @param riskModel
     */
    public MapCreatorController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initViewListeners();
    }


    /**
     * Method to set listeners for UI elements
     */
    private void initViewListeners() {
        saveMapButton.setOnAction(event -> saveMapFile());
    }

    /**
     * Method to save user selection map file
     */
    private void saveMapFile() {
        //ToDo save map components, continents and its territories
    }

}
