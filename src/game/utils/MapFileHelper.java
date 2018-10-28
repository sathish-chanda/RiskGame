package game.utils;

import game.model.MapValidator;
import game.view.RiskView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * MapFileHelper class used to store map file data
 */
public class MapFileHelper {

    private static MapFileHelper instance = null;
    private List<String> mapComponentList = new ArrayList<>();
    private List<String> continentsComponentList = new ArrayList<>();
    private List<String> territoriesComponentList = new ArrayList<>();
    private MapValidator mapValidator;
    private String line;
    private boolean isMapInvalid = false;
    private String errorMessage;

    private MapFileHelper() {
        // private constructor needed for singleton class
    }

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
     * Method used to choose file
     */
    public void fileChooser(RiskView view) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Load Risk Game file", "map");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                readMapFile(file.getPath());
            } else {
                LogHelper.printMessage("File not found");
            }
        } else {
            LogHelper.printMessage("Invalid file");
        }
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
     * Method to clear  stored values
     */
    public void cleanUp() {
        mapComponentList.clear();
        continentsComponentList.clear();
        territoriesComponentList.clear();
    }
}
