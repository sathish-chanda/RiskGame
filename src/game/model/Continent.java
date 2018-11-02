package game.model;

import java.util.List;

/**
 * This class represents a continent in a map, it's a sub-graph of a map.
 * It has an ArrayList variable called territoryList which represents countries in a continent.
 * The elements in territoryList are exmples of Territory class.
 */
public class Continent {

    private List<Territory> territoryList;// all the countries in a continent
    private int maximumArmy = 0;//army number on a continent if the continent is owned by one player
    private String continentName;//name of continent
    private int countryNum;
    private int continentStrength;

    public Continent() {
        //Empty constructor required
    }

    /**
     * In the constructor, the first input is the continent name.
     * The second input is the maximum value assigned to the continent
     * After the initialization of continent and  value, the next process is assign respective territories to the continent.
     */
    public Continent(String continentName, int maximumArmy) {
        this.continentName = continentName;
        this.maximumArmy = maximumArmy;
    }

    /**
     * In the overloading constructor, adding list of countries to the existing constructor
     *
     * @param continentName
     * @param maximumArmy
     * @param countries
     */
    public Continent(String continentName, int maximumArmy, List<Territory> countries) {
        this.continentName = continentName;
        this.maximumArmy = maximumArmy;
        this.territoryList = countries;
    }

    /**
     * This method return an ArrayList containing all the countries in a continent
     *
     * @return list of territories
     */
    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    /**
     * This method set continent territory list
     *
     * @param territoryList
     */
    public void setTerritoryList(List<Territory> territoryList) {
        this.territoryList = territoryList;
    }

    /**
     * This method returns the continent name in the map
     *
     * @return continent list
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * This method returns maximum number of armies for each continent
     *
     * @return the maximum Army value
     */
    public int getMaximumArmy() {
        return maximumArmy;
    }


//    public void setCountryNum(int countryNum) {
//        this.countryNum = countryNum;
//    }

}
