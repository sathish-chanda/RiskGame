package game.controller;

import game.model.Continent;
import game.model.RiskModel;
import game.model.Territory;
import game.utils.Constants;
import game.utils.ErrorViewUtils;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MapController implements Initializable {

    private boolean loadDataToMapEditor;
    private RiskModel riskModel;
    protected Map<String, String> mapComponents = new HashMap<>();
    protected List<Continent> continentList = new ArrayList<>();
    protected MapFileHelper mapFileHelper;

    @FXML
    protected Button saveMapButton;
    @FXML
    protected Button exitMapEditorButton;
    @FXML
    protected TextField authorTextField;
    @FXML
    protected TextField imageTextField;
    @FXML
    protected TextField wrapTextField;
    @FXML
    protected TextField scrollTextField;
    @FXML
    protected TextField warnTextField;
    @FXML
    protected ListView continentsListView;
    @FXML
    protected ComboBox continentsComboBox;
    @FXML
    protected ListView territoriesListView;
    @FXML
    protected ComboBox territoriesComboBox;
    @FXML
    protected ListView adjacentTerritoriesListView;
    @FXML
    protected TextField continentTextField;
    @FXML
    protected TextField continentValueTextField;
    @FXML
    protected TextField territoryTextField;
    @FXML
    protected TextField latitudeTextField;
    @FXML
    protected TextField longitudeTextField;
    @FXML
    protected Button addContinentButton;
    @FXML
    protected Button deleteContinentButton;
    @FXML
    protected Button addTerritoryButton;
    @FXML
    protected Button deleteTerritoryButton;
    @FXML
    protected ComboBox adjacentTerritoriesComboBox;
    @FXML
    protected Button addAdjacentTerritoryButton;
    @FXML
    protected Button deleteAdjacentTerritoryButton;

    /**
     * Constructor of class {@link MapController}
     */
    public MapController(boolean loadDataToMapEditor) {
        this.loadDataToMapEditor = loadDataToMapEditor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loadDataToMapEditor) {
            riskModel = new RiskModel();
            clearData();
            populateMapDataToMapEditor();
            setDataToUserInterfaceElements();
        }
        mapFileHelper = MapFileHelper.getInstance();
        initViewListeners();
    }

    /**
     * Populate data to map editor
     */
    private void populateMapDataToMapEditor() {
        File file = MapFileHelper.getFileFromFileChooser();
        if (file != null) {
            riskModel.loadMapData(file.getPath());
        } else {
            LogHelper.printMessage("Unable to read file or Invalid file");
        }
    }

    /**
     * Method to set map data to user interface elements
     */
    private void setDataToUserInterfaceElements() {
        mapComponents = riskModel.getGame().getGameMap().getMapComponentsHashMap();
        continentList = riskModel.getGame().getGameMap().getContinentListMap();
        loadMapComponents();
        loadContinentsListView();
        loadContinentsComboBox();
    }

    /**
     * Method to load map components into text fields
     */
    protected void loadMapComponents() {
        authorTextField.setText(mapComponents.get("author"));
        imageTextField.setText(mapComponents.get("image"));
        scrollTextField.setText(mapComponents.get("scroll"));
        warnTextField.setText(mapComponents.get("warn"));
        wrapTextField.setText(mapComponents.get("wrap"));
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

        addAdjacentTerritoryButton.setOnAction(event -> addAdjacentTerritory());
        deleteAdjacentTerritoryButton.setOnAction(event -> deleteAdjacentTerritory());

        territoriesComboBox.setOnAction(event -> loadAdjacentTerritoriesListView(getContinentComboBoxCurrentIndex(),
                territoriesComboBox.getSelectionModel().getSelectedIndex()));

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
        if (position >= 0) {
            continentList.remove(position);
            loadContinentsListView();
            loadContinentsComboBox();
        }
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
    protected void setContinentFieldsData(int position) {
        if (position >= 0) {
            String continentName = continentList.get(position).getContinentName();
            int continentValue = continentList.get(position).getMaximumArmy();
            continentTextField.setText(continentName);
            continentValueTextField.setText(String.valueOf(continentValue));
            setContinentComboBoxData(position);
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
            setTerritoryComboBoxData(territoryList, territoryPosition);
        }
    }

    /**
     * Method to set territory combo box data
     *
     * @param territoryList
     * @param territoryPosition
     */
    private void setTerritoryComboBoxData(List<Territory> territoryList, int territoryPosition) {
        if (territoryPosition >= 0) {
            territoriesComboBox.getItems().clear();
            for (Territory territory : territoryList) {
                territoriesComboBox.getItems().add(territory.getTerritoryName());
            }
            territoriesComboBox.getSelectionModel().select(territoryPosition);
        }
        //ToDo change the position of this function
        loadAllTerritoriesInAdjacentComboBox();
        loadAdjacentTerritoriesListView(getContinentComboBoxCurrentIndex(), territoryPosition);
    }

    /**
     * Method to load adjacent territories into list view
     *
     * @param continentPosition
     * @param territoryPosition
     */
    private void loadAdjacentTerritoriesListView(int continentPosition, int territoryPosition) {
        if (continentPosition >= 0 && territoryPosition >= 0) {
            Territory territory = continentList.get(continentPosition).getTerritoryList().get(territoryPosition);
            adjacentTerritoriesListView.getItems().clear();
            for (String adjacentTerritory : territory.getAdjacentCountryList()) {
                adjacentTerritoriesListView.getItems().add(adjacentTerritory);
            }
        }
    }

    /**
     * Method to load all territories in adjacent territory combo box
     */
    private void loadAllTerritoriesInAdjacentComboBox() {
        adjacentTerritoriesComboBox.getItems().clear();
        for (Continent continent : continentList) {
            List<Territory> territoryList = continent.getTerritoryList();
            if (territoryList != null) {
                for (Territory territory : territoryList) {
                    adjacentTerritoriesComboBox.getItems().add(territory.getTerritoryName());
                }
            }
        }
    }

    /**
     * Method to add adjacent territory
     */
    private void addAdjacentTerritory() {
        String territory = String.valueOf(territoriesComboBox.getSelectionModel().getSelectedItem());
        String adjacentTerritory = String.valueOf(adjacentTerritoriesComboBox.getSelectionModel().getSelectedItem());
        String message = "Territory = " + territory + Constants.NEXT_LINE + "Adjacent Territory = " + adjacentTerritory;
        if (territory.equals(adjacentTerritory)) {
            ErrorViewUtils.showErrorDialog("Cannot add Adjacent Territory ", message, "Territory and Adjacent Territory Error cannot be same");
        } else {
            continentList.get(getContinentComboBoxCurrentIndex()).getTerritoryList().get(getTerritoryCurrentIndex()).getAdjacentCountryList().add(adjacentTerritory);
            syncAddAdjacentTerritory(adjacentTerritory);
        }
    }

    /**
     * Method to synchronize and add adjacent adjacent territory
     *
     * @param currentTerritory
     */
    private void syncAddAdjacentTerritory(String currentTerritory) {
        String adjacentTerritory = String.valueOf(territoriesComboBox.getSelectionModel().getSelectedItem());
        LogHelper.printMessage("currentTerritory = " + currentTerritory + " adjacentTerritory " + adjacentTerritory);
        for (int i = 0; i < continentList.size(); i++) {
            if (continentList.get(i).getTerritoryList() != null && continentList.get(i).getTerritoryList().size() > 0) {
                for (int j = 0; j < continentList.get(i).getTerritoryList().size(); j++) {
                    if (continentList.get(i).getTerritoryList().get(j).getTerritoryName().equals(currentTerritory)) {
                        continentList.get(i).getTerritoryList().get(j).addAdjacentCountry(adjacentTerritory);
                        LogHelper.printMessage("added " + adjacentTerritory + " into " + currentTerritory);
                    }
                }
            }
        }
        LogHelper.printMessage("territories synced");
    }

    /**
     * Method to delete adjacent territory
     */
    private void deleteAdjacentTerritory() {
        String territory = String.valueOf(territoriesComboBox.getSelectionModel().getSelectedItem());
        String adjacentTerritory = String.valueOf(adjacentTerritoriesComboBox.getSelectionModel().getSelectedItem());
        String message = "Territory = " + territory + Constants.NEXT_LINE + "Adjacent Territory = " + adjacentTerritory;
        if (territory.equals(adjacentTerritory)) {
            ErrorViewUtils.showErrorDialog("Cannot delete Adjacent Territory ", message, "Territory and Adjacent Territory Error cannot be same");
        } else {
            continentList.get(getContinentComboBoxCurrentIndex()).getTerritoryList().get(getTerritoryCurrentIndex()).getAdjacentCountryList().remove(adjacentTerritory);
            syncDeleteAdjacentTerritory(adjacentTerritory);
            LogHelper.printMessage("deleted");
        }
    }

    /**
     * Method to synchronize and delete adjacent territory
     *
     * @param currentTerritory
     */
    private void syncDeleteAdjacentTerritory(String currentTerritory) {
        String adjacentTerritory = String.valueOf(territoriesComboBox.getSelectionModel().getSelectedItem());
        LogHelper.printMessage("currentTerritory = " + currentTerritory + " adjacentTerritory " + adjacentTerritory);
        for (int i = 0; i < continentList.size(); i++) {
            for (int j = 0; j < continentList.get(i).getTerritoryList().size(); j++) {
                if (continentList.get(i).getTerritoryList().get(j).getTerritoryName().equals(currentTerritory)) {
                    continentList.get(i).getTerritoryList().get(j).removeAdjacentCountry(adjacentTerritory);
                    LogHelper.printMessage("deleted " + adjacentTerritory + " from " + currentTerritory);
                }
            }
        }
        LogHelper.printMessage("territories synced");
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
        saveCompleteMapDataList();
        if (isMapFileValid()) {
            mapFileHelper.initMapFileSaver();
        }
    }

    /**
     * Method to validate saved map file
     */
    private boolean isMapFileValid() {
        //TODO validate Map with Yu's code
        return true;
    }

    /**
     * Method to save complete map data list
     */
    private void saveCompleteMapDataList() {
        mapFileHelper.setContinentsAndTerritoriesList(continentList);
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
        mapFileHelper.setMapComponents(mapComponents);
    }

    private String checkEmptyData(String data) {
        return (data == null || data.equals("")) ? "null" : data;
    }

    /**
     * Method to close Map Editor window
     */
    private void closeMapEditorWindow() {
        Stage stage = (Stage) exitMapEditorButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method to get current index of continent from continent combo box
     */
    private int getContinentComboBoxCurrentIndex() {
        return continentsComboBox.getSelectionModel().getSelectedIndex();
    }

    /**
     * Method to get current index of territroy from territory List view
     */
    private int getTerritoryCurrentIndex() {
        return territoriesListView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Method to clear data
     */
    private void clearData() {
        riskModel.getGame().getGameMap().getMapFileHelper().cleanUp();
    }

}
