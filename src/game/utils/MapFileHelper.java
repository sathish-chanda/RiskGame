package game.utils;

import game.model.Continent;
import game.model.MapValidator;
import game.model.Territory;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * MapFileHelper class used to store map file data
 */
public class MapFileHelper implements Serializable {

    private static MapFileHelper instance = null;
    Map<String, String> mapComponents = new HashMap<>();
    private List<String> mapComponentList = new ArrayList<>();
    private List<String> continentsComponentList = new ArrayList<>();
    private List<String> territoriesComponentList = new ArrayList<>();
    private List<Continent> continentsAndTerritoriesList = new ArrayList<>();
    private MapValidator mapValidator;
    private String line;
    private boolean isMapInvalid = false;
    private String errorMessage;

    /**
     * constructor class for map file helper
     */
    private MapFileHelper() {
        // private constructor needed for singleton class
    }

    /**
     * Method is used to get instance of map file helper class
     * @return instance of this class
     */
    public static MapFileHelper getInstance() {
        if (instance == null) {
            instance = new MapFileHelper();
        }
        return instance;
    }

    /**
     * This method is used to read data from the map file.
     */
    public void readMapFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            mapValidator = new MapValidator();
            mapValidator.setCurrentKey(Constants.MAP_KEY);
            while ((line = bufferedReader.readLine()) != null) {
                if (!isMapInvalid) {
                    validateMapFile();
                } else {
                    invalidMapFile(errorMessage);
                    break;
                }
            }
        } catch (IOException e) {
            isMapInvalid = true;
            LogHelper.printMessage("File not found");
            e.printStackTrace();
        }
    }

    /**
     * Method to get file from file chooser
     *
     * @return
     */
    public static File getFileFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        return file;
    }

    /**
     * This method is used to validate map file
     */
    private void validateMapFile() {
        switch (mapValidator.getCurrentKey()) {
            case Constants.MAP_KEY:
                validateMapComponents(line);
                break;
            case Constants.CONTINENTS_KEY:
                validateContinentComponents(line);
                break;
            case Constants.TERRITORIES_KEY:
                validateTerritoriesComponent(line);
                break;
            default:
                invalidMapFile("default");
                break;
        }
    }

    /**
     * This method checks validity of map components
     *
     * @param line refers line of data from the map file
     */

    private void validateMapComponents(String line) {
        if (line.equals(Constants.MAP_KEY) || isPatternMatches(Constants.MAP_KEY_PATTERN, line) || mapValidator.isMapKeyValid()) {
            addMapComponents();
        } else {
            invalidMapFile(Constants.MAP_KEY);
        }
    }

    /**
     * This method checks validity of continents from the map file
     *
     * @param line refers line of data from the map file
     */
    private void validateContinentComponents(String line) {
        if (line.equals(Constants.CONTINENTS_KEY) || isPatternMatches(Constants.CONTINENTS_KEY_PATTERN, line)
                || mapValidator.isContinentKeyValid()) {
            addContinentComponents();
        } else {
            invalidMapFile(Constants.CONTINENTS_KEY);
        }
    }

    /**
     * This method checks validity of territory from the map file
     *
     * @param line refers line of data from the map file
     */
    private void validateTerritoriesComponent(String line) {
        if (line.equals(Constants.TERRITORIES_KEY) || mapValidator.isTerrtitoryKeyValid()) {
            addTerritoryComponents();
        } else {
            invalidMapFile(Constants.TERRITORIES_KEY);
        }
    }

    /**
     * Method used to add map validity key components
     */
    private void addMapComponents() {
        if (line.equals(Constants.MAP_KEY)) {
            mapValidator.setMapKeyValid(true);
        } else if (line.equals(Constants.CONTINENTS_KEY)) {
            mapValidator.setCurrentKey(Constants.CONTINENTS_KEY);
            validateMapFile();
        } else {
            mapComponentList.add(line);
        }
    }

    /**
     * method used to save continent data from the map
     */
    private void addContinentComponents() {
        if (line.equals(Constants.CONTINENTS_KEY)) {
            mapValidator.setContinentKeyValid(true);
        } else if (line.equals(Constants.TERRITORIES_KEY)) {
            mapValidator.setCurrentKey(Constants.TERRITORIES_KEY);
            validateMapFile();
        } else {
            continentsComponentList.add(line);
        }
    }

    /**
     * Method used to save territory data from the map
     */
    private void addTerritoryComponents() {
        if (line.equals(Constants.TERRITORIES_KEY)) {
            mapValidator.setTerrtitoryKeyValid(true);
        } else {
            territoriesComponentList.add(line);
        }
    }

    /**
     * This method is used to save the map file
     */
    public void saveMapFile() {
        try {
            FileWriter fileWriter = new FileWriter(Constants.FILE_DOMAIN_PATH + Constants.USER_MAP_FILE_NAME);
            mapValidator = new MapValidator();
            mapValidator.setCurrentKey(Constants.MAP_KEY);
            saveUserMapFileComponents(fileWriter);
            fileWriter.flush();
            fileWriter.close();
            mapValidator.setFileSaveSuccess(true);
        } catch (IOException e) {
            mapValidator.setFileSaveSuccess(false);
            LogHelper.printMessage("File not created");
            e.printStackTrace();
        }

    }

    /**
     * Method to initialize map file saving process
     */
    public void initMapFileSaver() {
        try {
            openSaveMapFileChooserDialog();
        } catch (Exception exception) {
            LogHelper.printMessage(MapFileHelper.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

    /**
     * This method is used to save map file
     */
    public void openSaveMapFileChooserDialog() throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        LogHelper.printMessage("file path == " + file.getPath());
        writeToMapFile(file);
    }

    /**
     * Method to write data to file
     *
     * @param file
     */
    private void writeToMapFile(File file) {
        String appendedMapData = createMapData();
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(appendedMapData);
            fileWriter.close();
        } catch (Exception exeption) {
            LogHelper.printMessage("Error Message " + exeption);
        }
    }

    /**
     * Method to create map data
     */
    private String createMapData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appendMapComponents());
        stringBuilder.append(appendContinents());
        stringBuilder.append(appendTerritories());
        return stringBuilder.toString();
    }

    /**
     * Method to create map component data
     *
     * @return
     */
    private StringBuilder appendMapComponents() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.MAP_KEY);
        stringBuilder.append(Constants.NEXT_LINE);
        for (Map.Entry<String, String> map : mapComponents.entrySet()) {
            String mapComponent = map.getKey() + "=" + map.getValue();
            stringBuilder.append(mapComponent);
            stringBuilder.append(Constants.NEXT_LINE);
        }
        return stringBuilder;
    }


    /**
     * Method to append continents data
     *
     * @return
     */
    private StringBuilder appendContinents() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.NEXT_LINE);
        stringBuilder.append(Constants.CONTINENTS_KEY);
        for (Continent continent : continentsAndTerritoriesList) {
            stringBuilder.append(Constants.NEXT_LINE);
            stringBuilder.append(continent.getContinentName() + "=" + continent.getMaximumArmy());
        }
        stringBuilder.append(Constants.NEXT_LINE);
        return stringBuilder;
    }

    /**
     * Method to append territories data
     *
     * @return
     */
    private StringBuilder appendTerritories() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.NEXT_LINE);
        stringBuilder.append(Constants.TERRITORIES_KEY);
        for (Continent continent : continentsAndTerritoriesList) {
            stringBuilder.append(Constants.NEXT_LINE);
            for (Territory territory : continent.getTerritoryList()) {
                stringBuilder.append(territory.getTerritoryName() + "," + territory.getLatitude() + ","
                        + territory.getLongitude() + "," + territory.getContinentName() + "," + appendAdjacentTerritories(territory.getAdjacentCountryList()));
                stringBuilder.append(Constants.NEXT_LINE);
            }
        }
        return stringBuilder;
    }

    /**
     * Method to append adjacent territories data
     *
     * @param adjacentCountryList
     * @return
     */
    private String appendAdjacentTerritories(ArrayList<String> adjacentCountryList) {
        StringBuilder stringBuilder = new StringBuilder();
        String adjacentTerritories = "";
        for (String adjacentTerritory : adjacentCountryList) {
            if (adjacentTerritory.equals(adjacentCountryList.get(adjacentCountryList.size() - 1))) {
                stringBuilder.append(adjacentTerritory);
            } else {
                stringBuilder.append(adjacentTerritory + ",");
            }
        }
        adjacentTerritories = stringBuilder.toString();
        return adjacentTerritories;
    }


    /**
     * Method to set map components
     *
     * @param mapComponents
     */
    public void setMapComponents(Map<String, String> mapComponents) {
        this.mapComponents = mapComponents;
    }

    /**
     * Method to get map components
     *
     * @return
     */
    public Map<String, String> getMapComponents() {
        return mapComponents;
    }

    /**
     * Method to start saving user map file components
     *
     * @param fileWriter
     * @throws IOException
     */
    private void saveUserMapFileComponents(FileWriter fileWriter) throws IOException {
        switch (mapValidator.getCurrentKey()) {
            case Constants.MAP_KEY:
                saveMapComponents(fileWriter);
                break;
            case Constants.CONTINENTS_KEY:
                saveContinentComponents(fileWriter);
                break;
            case Constants.TERRITORIES_KEY:
                saveTerritoryComponents(fileWriter);
                break;
            default:
                LogHelper.printMessage("Saving File Error");
                break;
        }
    }

    /**
     * Method to save map components
     *
     * @param fileWriter
     * @throws IOException
     */
    private void saveMapComponents(FileWriter fileWriter) throws IOException {
        fileWriter.write(Constants.MAP_KEY + Constants.NEXT_LINE);
        Iterator<String> iterator = mapComponentList.iterator();
        while (iterator.hasNext()) {
            fileWriter.write(iterator.next() + Constants.NEXT_LINE);
        }
        mapValidator.setCurrentKey(Constants.CONTINENTS_KEY);
        saveUserMapFileComponents(fileWriter);
    }

    /**
     * Method to save continent components
     *
     * @param fileWriter
     * @throws IOException
     */
    private void saveContinentComponents(FileWriter fileWriter) throws IOException {
        fileWriter.write(Constants.CONTINENTS_KEY + Constants.NEXT_LINE);
        Iterator<String> iterator = continentsComponentList.iterator();
        while (iterator.hasNext()) {
            fileWriter.write(iterator.next() + Constants.NEXT_LINE);
        }
        mapValidator.setCurrentKey(Constants.TERRITORIES_KEY);
        saveUserMapFileComponents(fileWriter);
    }

    /**
     * Method to save territory components
     *
     * @param fileWriter
     * @throws IOException
     */
    private void saveTerritoryComponents(FileWriter fileWriter) throws IOException {
        fileWriter.write(Constants.TERRITORIES_KEY + Constants.NEXT_LINE);
        Iterator<String> iterator = territoriesComponentList.iterator();
        while (iterator.hasNext()) {
            fileWriter.write(iterator.next() + Constants.NEXT_LINE);
        }
    }

    /**
     * Method checks pattern matches in the map file
     *
     * @param pattern
     * @param line
     * @return pattern and line
     */
    private boolean isPatternMatches(String pattern, String line) {
        return Pattern.matches(pattern, line);
    }

    /**
     * This method prints Invalid GameMap
     */
    private void invalidMapFile(String errorMessage) {
        isMapInvalid = true;
        this.errorMessage = errorMessage;
    }

    /**
     * method to get error message
     *
     * @return eroor messag
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * method go get map valid status
     *
     * @return boolean
     */
    public boolean isMapValid() {
        return !isMapInvalid;
    }

    /**
     * method to reset the validity status of map
     */
    public void resetMapValidity() {
        isMapInvalid = false;
    }

    /**
     * Method to check the map file saved successfully.
     *
     * @return boolean
     */
    public boolean isFileSaveSuccess() {
        return mapValidator.isFileSaveSuccess();
    }

    /**
     * Method to get all components list of map
     *
     * @return map component list
     */
    public List<String> getMapComponentList() {
        return mapComponentList;
    }

    /**
     * method to get continent list from the map file
     *
     * @return list of continent
     */
    public List<String> getContinentsComponentList() {
        return continentsComponentList;
    }

    /**
     * method to set continent list
     *
     * @param continentsComponentList
     */
    public void setContinentsComponentList(List<String> continentsComponentList) {
        this.continentsComponentList = continentsComponentList;
    }

    /**
     * method to set territory component list
     *
     * @param territoriesComponentList
     */
    public void setTerritoriesComponentList(List<String> territoriesComponentList) {
        this.territoriesComponentList = territoriesComponentList;
    }

    /**
     * method to get territory component list
     *
     * @return territory component list
     */
    public List<String> getTerritoriesComponentList() {
        return territoriesComponentList;
    }

    /**
     * method to get complete data of continents and its territories
     *
     * @return
     */
    public List<Continent> getContinentsAndTerritoriesList() {
        return continentsAndTerritoriesList;
    }

    /**
     * method to set complete data of continents and its territories
     *
     * @param continentsAndTerritoriesList
     */
    public void setContinentsAndTerritoriesList(List<Continent> continentsAndTerritoriesList) {
        this.continentsAndTerritoriesList = continentsAndTerritoriesList;
    }

    /**
     * Method to clear  stored values
     */
    public void cleanUp() {
        mapComponentList.clear();
        continentsComponentList.clear();
        territoriesComponentList.clear();
    }
}
