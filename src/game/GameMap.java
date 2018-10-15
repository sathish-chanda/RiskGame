package game;

import game.listeners.GameListener;
import game.model.Continent;
import game.model.Territory;
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
    public List<Territory> territoryList = new ArrayList<>();
    private List<Continent> continentList = new ArrayList<>();
    public List<Continent> continentListMap;
    private List<Territory> territoryListMap;
    private MapFileHelper mapFileHelper;
    public Map<String, Integer> continentsHashMap;

    public GameMap(GameListener gameListener) {
        this.gameListener = gameListener;
        // ToDo the input of constructor is the name of the .txt
        // file some code for reading the file should be applied here
        // insert countries in counrty map
        // insert all continents in continnent map
        // insert all countries in repective continent
    }

    /**
     * This method is used to load map data from file
     */
    public void loadMap() {
        mapFileHelper = MapFileHelper.getInstance();
        mapFileHelper.readMapFile();
        onMapLoaded();
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
        for (int i = 0; i < territoryList.size(); i++)
            if (territoryList.get(i).getTerritoryName() == countryName)
                return territoryList.get(i);
        return null;
    }

    public Continent searchContinent(String continentName) {
        for (int i = 0; i < continentListMap.size(); i++)
            if (continentListMap.get(i).getContinentName() == continentName)
                return continentListMap.get(i);
        return null;
    }

    /**
     * The verifyContinentMap method verify if all the country in a continent is connected.
     * First it traverse the ArrayList continentListMap.
     * Then for each continent, the method traverse all the country in that continent.
     * if one country in the continent is in the adjacentCountryList of another country,
     * it means these two countries are connected.
     *
     * @return boolean
     */
    private boolean verifyContinentMap() {
        int continentMapSize = continentListMap.size();
        for (int i = 0; i < continentMapSize; i++) {
            int countryMapSize = continentListMap.get(i).getcountryMap().size();
            if ((countryMapSize == 1) || (countryMapSize == 0)) {
                return true;
            } else {
                List<Territory> countryList = continentListMap.get(i).getcountryMap();
                for (int j = 0; j < countryMapSize; j++) {
                    String countryName = countryList.get(j).getTerritoryName();
                    for (int k = 0; k < countryMapSize; k++) {
                        if (countryList.get(k).getAdjacentCountry().contains(countryName))
                            break;
                        else {
                            System.out.println("the input map is invalid");
                            System.out.println("the problem is in the country" + this.territoryList.get(k).getTerritoryName());
                            return false;
                        }
                    }
                }
            }
            Queue<Territory> q = new LinkedList<Territory>();
            int totalVisitedCountry = 0;
            for (int x = 0; x < countryMapSize; x++) {
                Territory c = continentListMap.get(i).getcountryMap().get(x);
                if (c.visitedContinentMap == false) {
                    q.offer(c);
                    totalVisitedCountry++;
                    c.visitedContinentMap = true;
                    while (!q.isEmpty()) {
                        Territory d = q.element();
                        q.poll();
                        for (int y = 0; y < d.getAdjacentCountry().size(); y++) {
                            Territory adjacentCountry = searchCountry(territoryList.get(x).getAdjacentCountry().get(y));
                            if (adjacentCountry.visitedContinentMap == false && adjacentCountry.getContinentName() == continentListMap.get(i).getContinentName()) {
                                q.offer(adjacentCountry);
                                totalVisitedCountry++;
                                adjacentCountry.visitedContinentMap = true;
                            }
                        }
                    }
                }
            }
            if (totalVisitedCountry != territoryList.size()) {
                System.out.println("the input map is invalid");
                System.out.println("the problem is in the continent" + continentListMap.get(i).getContinentName());
                return false;
            } else {
                System.out.println("the continent" + continentListMap.get(i).getContinentName() + " map is valid");
            }
        }
        return true;
    }

    /**
     * The verifyCountryMap method verify if all the country of the whole map is connected.
     * The method traverse the ArrayList territoryList.
     * For every country in the territoryList, its adjacent countries(in the adjacentCountry), must also be in the territoryList.
     *
     * @return boolean
     */
    private boolean verifyCountryMap() {
        int countryMapSize = territoryList.size();
        for (int i = 0; i < countryMapSize; i++) {
            ArrayList<String> adjacentCountryNameList = territoryList.get(i).getAdjacentCountry();
            for (int j = 0; j < adjacentCountryNameList.size(); j++) {
                String adjacentCountryName = adjacentCountryNameList.get(j);
                boolean flag = false;
                for (int k = 0; k < countryMapSize; k++) {
                    if (territoryList.get(k).getTerritoryName() == adjacentCountryName) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    LogHelper.printMessage("the input map is invalid");
                    LogHelper.printMessage("the problem is in the country" + territoryList.get(i).getTerritoryName());
                    return false;
                }
            }
        }
        Queue<Territory> q = new LinkedList<Territory>();
        int totalVisitedCountry = 0;
        for (int i = 0; i < territoryList.size(); i++) {
            if (territoryList.get(i).visitedWholeMap == false) {
                q.offer(territoryList.get(i));
                totalVisitedCountry++;
                territoryList.get(i).visitedWholeMap = true;
                while (!q.isEmpty()) {
                    Territory c = q.element();
                    q.poll();
                    for (int j = 0; j < c.getAdjacentCountry().size(); j++) {
                        Territory adjacentCountry = searchCountry(territoryList.get(i).getAdjacentCountry().get(j));
                        if (adjacentCountry.visitedWholeMap == false) {
                            q.offer(adjacentCountry);
                            totalVisitedCountry++;
                            adjacentCountry.visitedWholeMap = true;
                        }
                    }
                }
            }
        }
        if (totalVisitedCountry != territoryList.size()) {
            LogHelper.printMessage("the input map is invalid");
            return false;
        }
        LogHelper.printMessage("the input map is valid");
        return true;
    }
}


