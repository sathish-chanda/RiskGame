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


    public Continent(String continentName, int maximumArmy) {
        this.continentName = continentName;
        this.maximumArmy = maximumArmy;
    }

    public Continent(String continentName, int maximumArmy, List<Territory> countries) {
        this.continentName = continentName;
        this.maximumArmy = maximumArmy;
        this.territoryList = countries;
    }

    /**
     * This method return an ArrayList containing all the countries in a continent.
     *
     * @return
     */
    public List<Territory> getcountryMap() {
        return territoryList;
    }

    public String getContinentName() {
        return continentName;
    }

    public int getMaximumArmy() {
        return maximumArmy;
    }

    public void setCountryNum(int countryNum) {
        this.countryNum = countryNum;
    }


}
