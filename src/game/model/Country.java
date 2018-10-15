package game.model;

import java.util.ArrayList;

/**
 * This class called Country is the smallest element in a map.
 * the adjacentCountry variable is an ArrayList that countries' names which this country is adjacent to.
 */
public class Country {

    private String countryName;//name of the country
    private String continentName;//the continent that the country belongs to
    private int playerID;//the player that owns the country
    private int armyNum;//the number of army a player is placed in this country
    private ArrayList<String> adjacentCountryList;//countries' names which this country is adjacent to
    public boolean visitedWholeMap = false;
    public boolean visitedContinentMap = false;


    Country() {
        armyNum = 0;
    }

    /**
     * This method is used to allocate a country to a player
     *
     * @param playerID
     */
    public void setPlayer(int playerID) {
        this.playerID = playerID;
    }

    /**
     * This method returns the number of army place on a country
     *
     * @return
     */
    public int getArmyNum() {
        return armyNum;
    }

    /**
     * This method returns an ArrayList which has the names of a country's adjacent country
     *
     * @return
     */
    public ArrayList<String> getAdjacentCountry() {
        return adjacentCountryList;
    }

    /**
     * This method returns name of the player owning the country
     *
     * @return
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * This method returns the country name
     *
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method updates army number of a country
     *
     * @param armyNumber
     */
    public void updateArmyNum(int armyNumber) {
        armyNum = armyNum + armyNumber;
    }

    public String getContinentName() {
        return continentName;
    }

}
