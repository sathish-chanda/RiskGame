package game;

import game.listeners.GameListener;
import game.model.Continent;
import game.model.Territory;
import game.utils.Constants;
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
    private Map<String, Integer> continentsHashMap;

    public GameMap(String mapName) {
        // ToDo the input of constructor is the name of the .txt
        // file some code for reading the file should be applied here
    }

    public GameMap(GameListener gameListener) {
        this.gameListener = gameListener;
        mapFileHelper = MapFileHelper.getInstance();

        // ToDo the input of constructor is the name of the .txt
        // file some code for reading the file should be applied here
        // insert countries in counrty map
        // insert all continents in continnent map
        // insert all countries in repective continent
    }

    /**
     * This method is used to load map data from file
     */
    public void loadMap(String mapFileName) {
        mapFileHelper.readMapFile(mapFileName);
        onMapLoaded();
    }

    public void saveMap() {
        mapFileHelper.saveMapFile();
        LogHelper.printMessage("User map file is saved");
        gameListener.onUserMapSaveSuccess();
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

    private void onMapSaved() {

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
        LogHelper.printMessage("territory List = " + territoryList);
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
        LogHelper.printMessage("continent map == " + continentListMap);
    }

    public Territory searchCountry(String countryName) {
        Territory t = null;
        for (int i = 0; i < territoryList.size(); i++) {
            String s = territoryList.get(i).getTerritoryName();
            if (s.matches(countryName)) {
                t = territoryList.get(i);
            }
        }
        return t;
    }

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
                System.out.println("the input map is invalid");
                System.out.println("the problem is in the continent" + continentListMap.get(i).getContinentName());
                return;
            } else {
                isMapValid = true;
                System.out.println("the continent" + continentListMap.get(i).getContinentName() + " map is valid");
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
                    System.out.println("the input map is invalid");
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
            System.out.println("the input map is invalid");
            return;
        } else {
            System.out.println("the input map is valid");
            gameListener.onTerritoryMapValid();
            return;
        }
    }


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
            System.out.println("please input the number of armys you want to move");
            System.out.println("you can move up to " + searchCountry(countrySourceName).getArmyNum() + " armies");
            Scanner readInput = new Scanner(System.in);
            if (readInput.hasNextInt())
                moveArmyNum = readInput.nextInt();
            while (moveArmyNum > searchCountry(countrySourceName).getArmyNum()) {
                System.out.println("exceed maximum number you can move");
                System.out.println("please input the number of armys you want to move");
                System.out.println("you can move up to " + searchCountry(countrySourceName).getArmyNum() + " armies");
                if (readInput.hasNextInt())
                    moveArmyNum = readInput.nextInt();
            }
            t1.updateArmyNum(0 - moveArmyNum);
            System.out.println("now " + countrySourceName + " has " + t1.getArmyNum() + " armies");
            t2.updateArmyNum(moveArmyNum);
            System.out.println("now " + countryDestinationName + " has " + t2.getArmyNum() + " armies");

        } else
            System.out.println("cant move army");
        return;
    }


    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public List<Continent> getContinentListMap() {
        return continentListMap;
    }

    public List<Territory> getTerritoryListMap() {
        return territoryListMap;
    }

    public MapFileHelper getMapFileHelper() {
        return mapFileHelper;
    }
}