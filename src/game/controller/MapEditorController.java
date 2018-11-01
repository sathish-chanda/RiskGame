package game.controller;

import game.model.Continent;
import game.model.RiskModel;
import game.model.Territory;
import game.utils.LogHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class MapEditorController implements Initializable {

    private RiskModel riskModel;
    private Map<String, String> mapComponents;
    private List<Continent> continentList;
    private List<Territory> territoryList;
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
    @FXML
    private ListView territoriesListView;
    @FXML
    private ComboBox territoriesComboBox;
    @FXML
    private ListView adjacentTerritoriesListView;


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
        setViewElementsListeners();
    }

    private void setViewElementsListeners() {
        //continentsListView.setOnMouseClicked(event -> loadTerritoriesListView(continentsListView.getSelectionModel().getSelectedIndex()));
        continentsComboBox.setOnAction(event -> loadTerritoriesListView(continentsComboBox.getSelectionModel().getSelectedIndex()));
        territoriesComboBox.setOnAction(event -> loadAdjacentTerritoriesListView(territoriesComboBox.getSelectionModel().getSelectedIndex()));
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
        loadTerritoriesComboBox();
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
     * Method to load territories into list view
     */
    private void loadTerritoriesListView(int position) {
        territoriesListView.getItems().clear();
        List<Territory> territoriesList = continentList.get(position).getTerritoryList();
        for (Territory territory : territoriesList) {
            territoriesListView.getItems().add(territory.getTerritoryName());
        }
    }

    /**
     * Method to load territories into combo box
     */
    private void loadTerritoriesComboBox() {
        prepareTerritoriesList();
        for (Territory territory : territoryList) {
            territoriesComboBox.getItems().add(territory.getTerritoryName());
        }
    }

    /**
     * Method to create territories list from existing continent list
     */
    private void prepareTerritoriesList() {
        territoryList = new ArrayList<>();
        for (Continent continent : continentList) {
            territoryList.addAll(continent.getTerritoryList());
        }
    }

    /**
     * Method to load adjacent territories into list view
     */
    private void loadAdjacentTerritoriesListView(int position) {
        adjacentTerritoriesListView.getItems().clear();
        if (position >= 0) {
            List<String> adjacentTerritoriesList = territoryList.get(position).getAdjacentCountryList();
            for (String adjacentTerritory : adjacentTerritoriesList) {
                adjacentTerritoriesListView.getItems().add(adjacentTerritory);
            }
        }

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
