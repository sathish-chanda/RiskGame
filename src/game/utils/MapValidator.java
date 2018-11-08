package game.utils;

import game.model.Continent;
import game.model.Territory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MapValidator {

    private List<Continent> continentListMap;
    private List<Continent> continentList;
    private List<Territory> territoryList;

    /**
     * Constructor of class {@link MapValidator}
     *
     * @param continentListMap
     * @param continentList
     * @param territoryList
     */
    public MapValidator(List<Continent> continentListMap, List<Continent> continentList, List<Territory> territoryList) {
        this.continentListMap = continentListMap;
        this.continentList = continentList;
        this.territoryList = territoryList;
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
                        LogHelper.printMessage("the input map is invalid 1");
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
                LogHelper.printMessage("the input map is invalid 2");
                LogHelper.printMessage("the problem is in the continent " + continentListMap.get(i).getContinentName());
                return;
            } else {
                isMapValid = true;
                LogHelper.printMessage("the continent " + continentListMap.get(i).getContinentName() + " map is valid 1");
            }
        } //end of for loop
        if (isMapValid) {
            LogHelper.printMessage("Map is valid 2");
        }
        return;
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


}
