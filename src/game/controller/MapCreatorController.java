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
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class MapCreatorController implements Initializable {

    private Map<String, String> mapComponents = new HashMap<>();
    private List<Continent> continentList = new ArrayList<>();

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
    @FXML
    private Button addContinentButton;
    @FXML
    private Button deleteContinentButton;
    @FXML
    private Button addTerritoryButton;
    @FXML
    private Button deleteTerritoryButton;

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
     * Method to set click listeners for UI elements
     */
    private void initViewListeners() {
        saveMapButton.setOnAction(event -> saveMapFile());
        exitMapEditorButton.setOnAction(event -> closeMapEditorWindow());

        addContinentButton.setOnAction(event -> addContinent());
        deleteContinentButton.setOnAction(event -> deleteContinent(continentsListView.getSelectionModel().getSelectedIndex()));

        continentsListView.setOnMouseClicked(event -> setContinentFieldsData(continentsListView.getSelectionModel().getSelectedIndex()));
        continentsComboBox.setOnAction(event -> loadTerritoriesListView(continentsComboBox.getSelectionModel().getSelectedIndex()));

        addTerritoryButton.setOnAction(event -> addTerritory(continentsComboBox.getSelectionModel().getSelectedIndex()));
        deleteTerritoryButton.setOnAction(event -> deleteTerritory(continentsComboBox.getSelectionModel().getSelectedIndex(),
                territoriesListView.getSelectionModel().getSelectedIndex()));

        territoriesListView.setOnMouseClicked(event -> setTerritoryFieldsData(continentsComboBox.getSelectionModel().getSelectedIndex(),
                territoriesListView.getSelectionModel().getSelectedIndex()));


    }


    /**
     * Method to add continent
     */
    private void addContinent() {
        String continentName = continentTextField.getText();
        int continentValue = Integer.parseInt(continentValueTextField.getText());
        Continent continent = new Continent(continentName, continentValue);
        continentList.add(continent);
        loadContinentsListView();
        loadContinentsComboBox();
    }

    /**
     * Method to delete continent
     */
    private void deleteContinent(int position) {
        continentList.remove(position);
        loadContinentsListView();
        loadContinentsComboBox();
    }

    /**
     * Method to add Territory
     */
    private void addTerritory(int continentPosition) {
        clearContinentTextFields();
        String continentName = (String) continentsComboBox.getSelectionModel().getSelectedItem();
        LogHelper.printMessage("continentName " + continentName);
        String territoryName = territoryTextField.getText();
        String latitude = latitudeTextField.getText();
        String longitude = longitudeTextField.getText();
        Territory territory = new Territory(territoryName, latitude, longitude, continentName);
        continentList.get(continentPosition).addTerritoryList(territory);

        LogHelper.printMessage("continent position = " + continentPosition);
        LogHelper.printMessage("added territoryName " + territoryName + " in " + continentList.get(continentPosition).getContinentName());

        loadTerritoriesListView(continentPosition);
        //loadTerritoryComboBox();
    }

    /**
     * Method to delete Territory
     */
    private void deleteTerritory(int continentPosition, int territoryPosition) {
        clearContinentTextFields();
        continentList.get(continentPosition).getTerritoryList().remove(territoryPosition);
        loadTerritoriesListView(continentPosition);
    }

    /**
     * Method to load continents into list view
     */
    private void loadContinentsListView() {
        continentsListView.getItems().clear();
        for (Continent continent : continentList) {
            continentsListView.getItems().add(continent.getContinentName());
        }
    }

    /**
     * Method to load territories into list view
     */
    private void loadTerritoriesListView(int continentPosition) {
        clearTerritoryTextFields();
        territoriesListView.getItems().clear();
        if (continentPosition >= 0) {
            List<Territory> territoriesList = continentList.get(continentPosition).getTerritoryList();
            if (territoriesList != null) {
                for (Territory territory : territoriesList) {
                    territoriesListView.getItems().add(territory.getTerritoryName());
                }
            }
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
     * Method to set territory UI fields data
     *
     * @param continentPosition
     * @param territoryPosition
     */
    private void setTerritoryFieldsData(int continentPosition, int territoryPosition) {
        if (continentPosition >= 0 && territoryPosition >= 0) {
            List<Territory> territoryList = continentList.get(continentPosition).getTerritoryList();
            Territory territory = continentList.get(continentPosition).getTerritoryList().get(territoryPosition);
            String territoryName = territory.getTerritoryName();
            String latitude = territory.getLatitude();
            String longitude = territory.getLongitude();
            territoryTextField.setText(territoryName);
            latitudeTextField.setText(latitude);
            longitudeTextField.setText(longitude);
            //setTerritoryComboBoxData(territoryList, territoryPosition);
        }
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
     * Method to load continents into combo box
     */
    private void loadContinentsComboBox() {
        continentsComboBox.getItems().clear();
        for (Continent continent : continentList) {
            continentsComboBox.getItems().add(continent.getContinentName());
        }
    }

    /**
     * Method to clear continents text field
     */
    private void clearContinentTextFields() {
        continentTextField.clear();
        continentValueTextField.clear();
    }

    /**
     * Method to clear territory text field
     */
    private void clearTerritoryTextFields() {
        territoryTextField.clear();
        latitudeTextField.clear();
        longitudeTextField.clear();
    }

    /**
     * Method to save user selection map file
     */
    private void saveMapFile() {
        saveMapComponents();
    }

    /**
     * Method to load map components into text fields
     */
    private void saveMapComponents() {
        mapComponents.put("author", checkEmptyData(authorTextField.getText()));
        mapComponents.put("image", checkEmptyData(imageTextField.getText()));
        mapComponents.put("scroll", checkEmptyData(scrollTextField.getText()));
        mapComponents.put("warn", checkEmptyData(warnTextField.getText()));
        mapComponents.put("wrap", checkEmptyData(wrapTextField.getText()));
    }

    private String checkEmptyData(String data) {
        return (data == null || data.equals("")) ? "null data" : data;
    }

    /**
     * Method to close Map Editor window
     */
    private void closeMapEditorWindow() {
        Stage stage = (Stage) exitMapEditorButton.getScene().getWindow();
        stage.close();
    }

}
