package game.model;

import game.listeners.GameListener;
import game.utils.LogHelper;
import game.utils.MapFileHelper;

import java.util.Map;

import java.util.*;

/**
 * This class implements the idea of a map.
 * It contains two variables, territoryList and countinentMap.
 * The territoryList is an ArrayList which contains all the countries.
 * The continentListMap is an ArrayList which contains several sub-maps as continents.
 * This class also contains two methods to verify the correctness of input maps.
 */
public class GameMap {

    private GameListener gameListener;
    private List<Territory> territoryList = new ArrayList<>();
    private List<Continent> continentList = new ArrayList<>();
    private List<Continent> continentListMap;
    private List<Territory> territoryListMap;
    private MapFileHelper mapFileHelper;
    private Map<String, String> mapComponentsHashMap;
    private Map<String, Integer> continentsHashMap;

    /**
     * Constructor of class GameMap
     *
     * @param gameListener
     */
    public GameMap(GameListener gameListener) {
        this.gameListener = gameListener;
        mapFileHelper = MapFileHelper.getInstance();
    }

    /**
     * This method is used to load map data from file
     */
    public void loadMap(String mapFileName) {
        mapFileHelper.readMapFile(mapFileName);
        onMapLoaded();
    }

    /**
     * This method is used to save map data to file
     */
    public void saveMap() {
        mapFileHelper.saveMapFile();
        LogHelper.printMessage("User map file is saved");
        gameListener.onUserMapSaveSuccess();
    }

    /**
     * This method is used to initialize editing of map data to file
     */
    public void initEditMap() {
        LogHelper.printMessage("Insert 1 to add continent and 2 to Delete continent");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        choiceToAddOrDeleteContinent(choice);
    }

    /**
     * User choice for adding and deleting a continent
     *
     * @param choice
     */
    private void choiceToAddOrDeleteContinent(int choice) {
        switch (choice) {
            case 1:
                addContinent();
                break;
            case 2:
                showEditedList();
                deleteContinent();
                break;
            default:
                LogHelper.printMessage("Invalid Input");
        }
    }

    /**
     * Method to delete continent
     */
    private void deleteContinent() {
        LogHelper.printMessage("Select Continent to be deleted");
        Scanner scanner = new Scanner(System.in);
        int position = scanner.nextInt();
        if (position <= 0 && position > continentListMap.size()) {
            LogHelper.printMessage("invalid input");
        } else {
            continentListMap.remove(position - 1);
        }
        showEditedList();
        reCreateContinentComponentData();
        reCreateTerritoryMapData();
        gameListener.onUserMapEditSuccess();
    }

    /**
     * Method to show edited list of continents
     */
    private void showEditedList() {
        for (int i = 0; i < continentListMap.size(); i++) {
            String continentName = continentListMap.get(i).getContinentName();
            LogHelper.printMessage(continentName + " - " + (i + 1));
        }
    }

    /**
     * Method to add continent
     */
    private void addContinent() {
        Scanner scanner = new Scanner(System.in);
        LogHelper.printMessage("Please enter continent name");
        String continentName = scanner.next();
        LogHelper.printMessage("Select army value of the continent");
        int maxArmy = scanner.nextInt();
        Continent continent = new Continent(continentName, maxArmy);
        initTerritoryList(continent);
        reCreateContinentComponentData();
        reCreateTerritoryMapData();
        gameListener.onUserMapEditSuccess();
    }

    /**
     * Method to initialize list of territories
     */
    private void initTerritoryList(Continent continent) {
        Scanner scanner = new Scanner(System.in);
        List<Territory> territoryList = new ArrayList<>();
        LogHelper.printMessage("Please enter total number of territories you want to add");
        int totalNumberOfTerritories = scanner.nextInt();
        for (int i = 0; i < totalNumberOfTerritories; i++) {
            addTerritory(continent, i, territoryList);
        }
    }

    /**
     * Method to add territory
     */
    private void addTerritory(Continent continent, int position, List<Territory> territoryList) {
        Scanner scanner = new Scanner(System.in);
        LogHelper.printMessage("Please enter " + position + " territory name");
        String territoryName = scanner.next();
        LogHelper.printMessage("Please enter latitude value");
        String latitude = scanner.next();
        LogHelper.printMessage("Please enter longitude value");
        String longitiude = scanner.next();
        LogHelper.printMessage("Please enter total number of adjacent countries you want to add");
        int totalAdjacentCountries = scanner.nextInt();
        ArrayList<String> adjacentTerritoryList = new ArrayList<>();
        for (int i = 0; i < totalAdjacentCountries; i++) {
            addAdjacentTerritory(territoryName, adjacentTerritoryList, i);
        }
        Territory territory = new Territory(territoryName, latitude, longitiude, continent.getContinentName(), adjacentTerritoryList);
        territoryList.add(territory);
        continent = new Continent(continent.getContinentName(), continent.getMaximumArmy(), territoryList);
        continentListMap.add(continent);
    }

    /**
     * Method used to recreate continent map data
     */
    private void reCreateContinentComponentData() {
        List<String> continentsComponentList = new ArrayList<>();
        for (int i = 0; i < continentListMap.size(); i++) {
            String continentComponentName = continentListMap.get(i).getContinentName()
                    + "=" + continentListMap.get(i).getMaximumArmy();
            continentsComponentList.add(continentComponentName);
        }
        mapFileHelper.setContinentsComponentList(continentsComponentList);
        LogHelper.printMessage("" + continentsComponentList);
    }

    /**
     * Method used to recreate territory map data
     */
    private void reCreateTerritoryMapData() {
        String territoryComponentName;
        List<String> territoryComponentList = new ArrayList<>();
        for (int i = 0; i < continentListMap.size(); i++) {
            for (int j = 0; j < continentListMap.get(i).getTerritoryList().size(); j++) {
                String territoryName = continentListMap.get(i).getTerritoryList().get(j).getTerritoryName();
                String lat = continentListMap.get(i).getTerritoryList().get(j).getLatitude();
                String lon = continentListMap.get(i).getTerritoryList().get(j).getLongitude();
                String continentName = continentListMap.get(i).getTerritoryList().get(j).getContinentName();
                String adjacentCountryList = getAdjacentCountryList(continentListMap.get(i).getTerritoryList().get(j));
                territoryComponentName = territoryName + "," + lat + "," + lon + "," + continentName + "," + adjacentCountryList;
                territoryComponentList.add(territoryComponentName);
            }
        }
        LogHelper.printMessage("territory component list " + territoryComponentList);
        mapFileHelper.setTerritoriesComponentList(territoryComponentList);
    }

    /**
     * Method to get adjacent country List
     *
     * @param territory
     * @return
     */
    private String getAdjacentCountryList(Territory territory) {
        String territoryListValue = "";
        for (int i = 0; i < territory.getAdjacentCountryList().size(); i++) {
            if (i == 0) {
                territoryListValue = territory.getAdjacentCountryList().get(i);
            } else {
                territoryListValue = territoryListValue + "," + territory.getAdjacentCountryList().get(i);
            }
        }
        LogHelper.printMessage(territoryListValue);
        return territoryListValue;
    }

    /**
     * Method to add adjacent territory
     */
    private void addAdjacentTerritory(String territoryName, List<String> adjacentTerritoryList, int position) {
        LogHelper.printMessage("Please enter postion " + (position + 1) + " adjacent territory name for country " + territoryName);
        Scanner scanner = new Scanner(System.in);
        String adjacentTerritory = scanner.next();
        adjacentTerritoryList.add(adjacentTerritory);
    }


    /**
     * This method validates if mad is valid or not
     */
    private void onMapLoaded() {
        if (mapFileHelper.isMapValid()) {
            gameListener.onMapLoadSuccess();
        } else {
            gameListener.onMapLoadFailure(mapFileHelper.getErrorMessage());
        }
    }

    /**
     * This method load territories from mapFileHelper class
     * This also sets adjacent countries of a country
     * This also sets a continent of a country
     */
    public void loadTerritories() {
        List<String> territoryComponentList = mapFileHelper.getTerritoriesComponentList();
        List<String> territorySplitList;
        for (int i = 0; i < territoryComponentList.size(); i++) {
            territorySplitList = Arrays.asList(territoryComponentList.get(i).split(","));
            if (!territorySplitList.get(0).isEmpty()) {
                ArrayList<String> adjacentCountryList = new ArrayList<>();
                for (int j = 4; j < territorySplitList.size(); j++) {
                    adjacentCountryList.add(territorySplitList.get(j));
                }
                Territory territory = new Territory(territorySplitList.get(0), territorySplitList.get(1),
                        territorySplitList.get(2), territorySplitList.get(3), adjacentCountryList);
                territoryList.add(territory);
            }
        }
    }

    /**
     * This method load map components from mapFileHelper class
     */
    public void loadMapComponents() {
        List<String> mapComponentList = mapFileHelper.getMapComponentList();
        List<String> mapComponentSplitList;
        mapComponentsHashMap = new HashMap<>();
        for (int i = 0; i < mapComponentList.size(); i++) {
            mapComponentSplitList = Arrays.asList(mapComponentList.get(i).split("="));
            if (mapComponentSplitList.size() == 2) {
                mapComponentsHashMap.put(mapComponentSplitList.get(0), mapComponentSplitList.get(1));
            }
        }
    }

    /**
     * This method load continents from mapFileHelper class
     */
    public void loadContinents() {
        List<String> continentComponentList = mapFileHelper.getContinentsComponentList();
        List<String> continentSplitList;
        continentsHashMap = new HashMap<>();
        for (int i = 0; i < continentComponentList.size(); i++) {
            continentSplitList = Arrays.asList(continentComponentList.get(i).split("="));
            if (continentSplitList.size() == 2) {
                continentsHashMap.put(continentSplitList.get(0),
                        Integer.parseInt(continentSplitList.get(1)));
            }
        }
        for (Map.Entry<String, Integer> mapKeyValue : continentsHashMap.entrySet()) {
            Continent continent = new Continent(mapKeyValue.getKey(), mapKeyValue.getValue());
            continentList.add(continent);
        }
    }

    /**
     * This method assigns/synchronizes territories to their respective continents
     */
    public void syncContinentsAndTerritories() {
        continentListMap = new ArrayList<>();
        for (int i = 0; i < continentList.size(); i++) {
            territoryListMap = new ArrayList<>();
            for (int j = 0; j < territoryList.size(); j++) {
                if (continentList.get(i).getContinentName().equals(territoryList.get(j).getContinentName())) {
                    territoryListMap.add(territoryList.get(j));
                }
            }
            Continent continent = new Continent(continentList.get(i).getContinentName(), continentList.get(i).getMaximumArmy(), territoryListMap);
            continentListMap.add(continent);
        }
    }

    /**
     * Method to search territory name in the territory list
     *
     * @param countryName, territory to be searched
     * @return territory found in the list
     */
    public Territory searchCountry(String countryName) {
        Territory t = null;
        for (int i = 0; i < territoryList.size(); i++) {
            String s = territoryList.get(i).getTerritoryName();
            if (s.equalsIgnoreCase(countryName)) {
                t = territoryList.get(i);
            }
        }
        return t;
    }

    /**
     * Method to search continent name in the continent list
     *
     * @param continentName, continent to be searched
     * @return continent found in the list
     */
    public Continent searchContinent(String continentName) {
        Continent t = null;
        for (int i = 0; i < continentListMap.size(); i++) {
            String s = continentListMap.get(i).getContinentName();
            if (s.matches(continentName)) {
                t = continentListMap.get(i);
            }
        }
        return t;
    }

    /**
     * The verifyContinentMap method verify if all the country in a continent is connected.
     * First it traverse the ArrayList continentListMap.
     * Then for each continent, the method traverse all the country in that continent.
     * if one country in the continent is in the adjacentCountryList of another country,
     * it means these two countries are connected.
     */
    public void verifyContinentMap() {
        boolean isMapValid = false;
        int continentMapSize = continentListMap.size();
        for (int i = 0; i < continentMapSize; i++) {
            Queue<Territory> q = new LinkedList<Territory>();
            int totalVisitedCountry = 0;
            Territory startCountry = continentListMap.get(i).getTerritoryList().get(0);
            totalVisitedCountry++;
            startCountry.visitedContinentMap = true;
            q.offer(startCountry);
            while (!q.isEmpty()) {
                Territory c = q.poll();
                for (int y = 0; y < c.getAdjacentCountryList().size(); y++) {
                    Territory adjacentCountry = searchCountry(c.getAdjacentCountryList().get(y));
                    if (adjacentCountry == null) {
                        isMapValid = false;
                        LogHelper.printMessage("the input map is invalid");
                        return;
                    }
                    if ((adjacentCountry.visitedContinentMap == false) && (adjacentCountry.getContinentName().matches(continentList.get(i).getContinentName()))) {
                        totalVisitedCountry++;
                        adjacentCountry.visitedContinentMap = true;
                        q.offer(adjacentCountry);
                    }
                }
            }
            if (totalVisitedCountry != continentListMap.get(i).getTerritoryList().size()) {
                isMapValid = false;
                LogHelper.printMessage("the input map is invalid");
                LogHelper.printMessage("the problem is in the continent " + continentListMap.get(i).getContinentName());
                return;
            } else {
                isMapValid = true;
                LogHelper.printMessage("the continent " + continentListMap.get(i).getContinentName() + " map is valid");
            }
        } //end of for loop
        if (isMapValid) {
            gameListener.onContinentMapValid();
        }
        return;
    }


    /**
     * The verifyCountryMap method verify if all the country of the whole map is connected.
     * The method traverse the ArrayList territoryList.
     * For every country in the territoryList, its adjacent countries(in the adjacentCountry), must also be in the territoryList.
     *
     * @return boolean
     */
    public void verifyTerritoryMap() {
        boolean isMapValid = false;
        int territoryListSize = territoryList.size();
        Queue<Territory> q = new LinkedList<Territory>();
        int totalVisitedTerritory = 0;
        territoryList.get(0).visitedWholeMap = true;
        q.offer(territoryList.get(0));
        totalVisitedTerritory++;
        while (!q.isEmpty()) {
            Territory c = q.poll();
            for (int j = 0; j < c.getAdjacentCountryList().size(); j++) {
                Territory adjacentTerritory = searchCountry(c.getAdjacentCountryList().get(j));
                if (adjacentTerritory == null) {
                    LogHelper.printMessage("the input map is invalid");
                    return;
                }
                if (adjacentTerritory.visitedWholeMap == false) {
                    totalVisitedTerritory++;
                    adjacentTerritory.visitedWholeMap = true;
                    q.offer(adjacentTerritory);
                }
            }
        }
        if (totalVisitedTerritory != territoryListSize) {
            LogHelper.printMessage("the input map is invalid");
            return;
        } else {
            LogHelper.printMessage("the input map is valid");
            gameListener.onTerritoryMapValid();
            return;
        }
    }


    /**
     * This method setup fortification phase for the game
     * In fortification phase, sharing armies between adjacent territories will happen.
     *
     * @param countrySourceName,      territory going to share army
     * @param countryDestinationName, adjacent territory going to accept army
     * @param playerID,               player unique Id
     */
    public void fortification(String countrySourceName, String countryDestinationName, int playerID) {
        Territory t1 = searchCountry(countrySourceName);
        Territory t2 = searchCountry(countryDestinationName);
        Queue<Territory> q = new LinkedList<Territory>();
        boolean flag = false;
        Territory startCountry = t1;
        q.offer(startCountry);
        ArrayList<Territory> searchedTerritory = new ArrayList<Territory>();

        while (!q.isEmpty()) {
            Territory c = q.poll();
            searchedTerritory.add(c);
            for (int i = 0; i < c.getAdjacentCountryList().size(); i++) {
                Territory adjacentCountry = searchCountry(c.getAdjacentCountryList().get(i));
                if (adjacentCountry.getTerritoryName().matches(countryDestinationName) && (adjacentCountry.getPlayerID() == playerID)) {
                    flag = true;
                    break;
                } else if ((adjacentCountry.getPlayerID() == playerID) && !searchedTerritory.contains(adjacentCountry)) {
                    q.offer(adjacentCountry);
                } else
                    continue;
            }
        }
        if (flag) {
            int moveArmyNum = 0;
            LogHelper.printMessage("please input the number of armys you want to move");
            LogHelper.printMessage("you can move up to " + searchCountry(countrySourceName).getArmyNum() + " armies");
            Scanner readInput = new Scanner(System.in);
            if (readInput.hasNextInt())
                moveArmyNum = readInput.nextInt();
            while (moveArmyNum > searchCountry(countrySourceName).getArmyNum()) {
                LogHelper.printMessage("exceed maximum number you can move");
                LogHelper.printMessage("please input the number of armys you want to move");
                LogHelper.printMessage("you can move up to " + searchCountry(countrySourceName).getArmyNum() + " armies");
                if (readInput.hasNextInt())
                    moveArmyNum = readInput.nextInt();
            }
            t1.updateArmyNum(0 - moveArmyNum);
            LogHelper.printMessage("now " + countrySourceName + " has " + t1.getArmyNum() + " armies");
            t2.updateArmyNum(moveArmyNum);
            LogHelper.printMessage("now " + countryDestinationName + " has " + t2.getArmyNum() + " armies");

        } else
            LogHelper.printMessage("cant move army");
        return;
    }

    /**
     * method to get territory list
     *
     * @return territory list
     */
    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    /**
     * method to get continent list
     *
     * @return continent list
     */
    public List<Continent> getContinentList() {
        return continentList;
    }

    /**
     * method to get continent list from the map
     *
     * @return continent list
     */
    public List<Continent> getContinentListMap() {
        return continentListMap;
    }

    /**
     * method to get territory list from the map
     *
     * @return territory list
     */
    public List<Territory> getTerritoryListMap() {
        return territoryListMap;
    }

    /**
     * method to get map file helper class components
     *
     * @return map file component
     */
    public MapFileHelper getMapFileHelper() {
        return mapFileHelper;
    }

    public Map<String, String> getMapComponentsHashMap() {
        return mapComponentsHashMap;
    }

    /**
     * Method to clear  stored values
     */
    public void cleanUp() {
        territoryList.clear();
        continentList.clear();
        continentListMap.clear();
        territoryListMap.clear();
        mapFileHelper.cleanUp();
    }
}