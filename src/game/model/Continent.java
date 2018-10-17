package game.model;

import java.util.ArrayList;

/**
 * This class represents a continent in a map, it's a sub-graph of a map.
 * It has an ArrayList variable called countryMap which represents countries in a continent.
 * The elements in countryMap are exmples of Country class.
 */
public class Continent {

    private ArrayList<Country> countryMap;// all the countries in a continent
    private int maximumArmy = 0;//army number on a continent if the continent is owned by one player
    private String continentName;//name of continent
    private int countryNum;

    Continent(String continentName, int maximumArmy) {
        this.continentName = continentName;
        this.maximumArmy = maximumArmy;
    }

    /**
     * This method return an ArrayList containing all the countries in a continent.
     *
     * @return
     */
    public ArrayList<Country> getcountryMap() {
        return countryMap;
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
