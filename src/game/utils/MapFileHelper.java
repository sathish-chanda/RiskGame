package game.utils;

import game.model.MapValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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
    public void readMapFile() {
        try {
            FileReader fileReader = new FileReader(Constants.FILE_DOMAIN_PATH + Constants.MAP_FILE_NAME);
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
            LogHelper.printMessage("File not found");
            e.printStackTrace();
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

    private void validateMapComponents(String line) {
        if (line.equals(Constants.MAP_KEY) || isPatternMatches(Constants.MAP_KEY_PATTERN, line) || mapValidator.isMapKeyValid()) {
            addMapComponents();
        } else {
            invalidMapFile(Constants.MAP_KEY);
        }
    }

    private void validateContinentComponents(String line) {
        if (line.equals(Constants.CONTINENTS_KEY) || isPatternMatches(Constants.CONTINENTS_KEY_PATTERN, line)
                || mapValidator.isContinentKeyValid()) {
            addContinentComponents();
        } else {
            invalidMapFile(Constants.CONTINENTS_KEY);
        }
    }

    private void validateTerritoriesComponent(String line) {
        if (line.equals(Constants.TERRITORIES_KEY) || mapValidator.isTerrtitoryKeyValid()) {
            addTerritoryComponents();
        } else {
            invalidMapFile(Constants.TERRITORIES_KEY);
        }
    }

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

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isMapValid() {
        return !isMapInvalid;
    }

    public boolean isFileSaveSuccess() {
        return mapValidator.isFileSaveSuccess();
    }

    public List<String> getMapComponentList() {
        return mapComponentList;
    }

    public List<String> getContinentsComponentList() {
        return continentsComponentList;
    }

    public List<String> getTerritoriesComponentList() {
        return territoriesComponentList;
    }
}
