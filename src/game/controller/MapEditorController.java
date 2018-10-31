package game.controller;

import game.model.Continent;
import game.model.RiskModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class MapEditorController implements Initializable {

    private RiskModel riskModel;
    private Map<String, String> mapComponents;
    private List<Continent> continentList;
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
    @FXML
    private ListView continentsListView;
    @FXML
    private ComboBox continentsComboBox;

    /**
     * Constructor of class {@link MapEditorController}
     */
    public MapEditorController(RiskModel riskModel) {
        this.riskModel = riskModel;
        mapComponents = riskModel.getGame().getGameMap().getMapComponentsHashMap();
        continentList = riskModel.getGame().getGameMap().getContinentListMap();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDataToUserInterfaceElements();
    }

    /**
     * Method to set map data to user interface elements
     */
    private void setDataToUserInterfaceElements() {
        authorTextField.setText(mapComponents.get("author"));
        imageTextField.setText(mapComponents.get("image"));
        scrollTextField.setText(mapComponents.get("scroll"));
        warnTextField.setText(mapComponents.get("warn"));
        wrapTextField.setText(mapComponents.get("wrap"));
        loadContinentsListView();
        loadContinentsComboBox();
    }


    /**
     * Method to load continents into list view
     */
    private void loadContinentsListView() {
        for (Continent continent : continentList) {
            continentsListView.getItems().add(continent.getContinentName());
        }
    }

    /**
     * Method to load continents into combo box
     */
    private void loadContinentsComboBox() {
        for (Continent continent : continentList) {
            continentsComboBox.getItems().add(continent.getContinentName());
        }
    }

    /**
     *
     */
    private void loadTerritoriesListView() {

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
