package game.controller;

import game.model.Continent;
import game.model.RiskModel;
import game.model.Territory;
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
        initViewListeners();
    }

    /**
     * Method to set listeners for UI elements
     */
    private void initViewListeners() {
        continentsListView.setOnMouseClicked(event -> setContinentFieldsData(continentsListView.getSelectionModel().getSelectedIndex()));
        territoriesListView.setOnMouseClicked(event -> setTerritoryFieldsData(continentsComboBox.getSelectionModel().getSelectedIndex(),
                territoriesListView.getSelectionModel().getSelectedIndex()));
        continentsComboBox.setOnAction(event -> loadTerritoriesListView(continentsComboBox.getSelectionModel().getSelectedIndex()));
        territoriesComboBox.setOnAction(event -> loadAdjacentTerritoriesListView(territoriesComboBox.getSelectionModel().getSelectedIndex()));
    }

    /**
     * Method to set map data to user interface elements
     */
    private void setDataToUserInterfaceElements() {
        loadMapComponents();
        loadContinentsListView();
        loadContinentsComboBox();
    }


    /**
     * Method to load map components into text fields
     */
    private void loadMapComponents() {
        authorTextField.setText(mapComponents.get("author"));
        imageTextField.setText(mapComponents.get("image"));
        scrollTextField.setText(mapComponents.get("scroll"));
        warnTextField.setText(mapComponents.get("warn"));
        wrapTextField.setText(mapComponents.get("wrap"));
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
     * Method to load territories into combo box
     */
    private void loadTerritoriesComboBox() {
        for (Continent continent : continentList) {
            for (Territory territory : continent.getTerritoryList()) {
                territoriesComboBox.getItems().add(territory.getTerritoryName());
            }
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
     * Method to set continents UI fields data
     *
     * @param position
     */
    private void setContinentFieldsData(int position) {
        String continentName = continentList.get(position).getContinentName();
        int continentValue = continentList.get(position).getMaximumArmy();
        continentTextField.setText(continentName);
        continentValueTextField.setText(String.valueOf(continentValue));
        setContinentComboBoxData(position);
    }

    /**
     * Method to set continents UI combo vox data
     *
     * @param position
     */
    private void setContinentComboBoxData(int position) {
        continentsComboBox.getSelectionModel().select(position);
    }

    /**
     * Method to set territory UI fields data
     *
     * @param continentPosition
     * @param territoryPosition
     */
    private void setTerritoryFieldsData(int continentPosition, int territoryPosition) {
        if (continentPosition >= 0 && territoryPosition >= 0) {
            Territory territory = continentList.get(continentPosition).getTerritoryList().get(territoryPosition);
            String territoryName = territory.getTerritoryName();
            String latitude = territory.getLatitude();
            String longitude = territory.getLongitude();
            territoryTextField.setText(territoryName);
            latitudeTextField.setText(latitude);
            longitudeTextField.setText(longitude);
            setTerritoryComboBoxData(continentPosition, territoryPosition);
        }
    }

    /**
     * Method to set territory combo box data
     *
     * @param continentPosition
     * @param territoryPosition
     */
    private void setTerritoryComboBoxData(int continentPosition, int territoryPosition) {
        Territory territory = continentList.get(continentPosition).getTerritoryList().get(territoryPosition);
        territoriesComboBox.getSelectionModel().select(territoryPosition);
        /*territoryTextField.setText(territory.getTerritoryName());
        latitudeTextField.setText(territory.getLatitude());
        longitudeTextField.setText(territory.getLongitude());*/
    }

    /**
     * Method to load adjacent territories into list view
     */
    private void loadAdjacentTerritoriesListView(int position) {
        /*adjacentTerritoriesListView.getItems().clear();
        if (position >= 0) {
            List<String> adjacentTerritoriesList = territoryList.get(position).getAdjacentCountryList();
            for (String adjacentTerritory : adjacentTerritoriesList) {
                adjacentTerritoriesListView.getItems().add(adjacentTerritory);
            }
        }*/

    }
}
