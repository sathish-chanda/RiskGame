package game.model;

import java.util.ArrayList;

/**
 * This class called Territory is the smallest element in a map.
 * the adjacentCountry variable is an ArrayList that countries' names which this country is adjacent to.
 */
public class Territory {

    private String territoryName;//name of the country
    private String continentName;//the continent that the country belongs to
    private String latitude;
    private String longitude;
    private ArrayList<String> adjacentCountryList;//countries' names which this country is adjacent to
    private int playerID;//the player that owns the country
    private int armyNum;//the number of army a player is placed in this country
    public boolean visitedWholeMap = false;
    public boolean visitedContinentMap = false;

    public Territory() {
        armyNum = 0;
    }

    /**
     * This is the constructor method used to initialize various attributes of map file.
     * @param countryName refers primary country of the continent
     * @param latitude refers co-ordinate of country located in the map
     * @param longitude refers co-ordinate of country located in the map
     * @param continentName refers continent name
     * @param adjacentCountryList refers list of adjacent countries connected to primary country
     */
    public Territory(String countryName, String latitude, String longitude, String continentName, ArrayList<String> adjacentCountryList) {
        this.territoryName = countryName;
        this.continentName = continentName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adjacentCountryList = adjacentCountryList;
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
    public ArrayList<String> getAdjacentCountryList() {
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
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * This method updates army number of a country
     *
     * @param armyNumber
     */
    public void updateArmyNum(int armyNumber) {
        armyNum = armyNum + armyNumber;
    }

    /**
     * This method is used to get continent name
     * @return continent name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * This method sets territory names
     *
     * @param territoryName
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * This method sets continent names
     *
     * @param continentName
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * This method sets adajacent countries
     *
     * @param adjacentCountryList
     */
    public void setAdjacentCountryList(ArrayList<String> adjacentCountryList) {
        this.adjacentCountryList = adjacentCountryList;
    }

    /**
     *  get latitude position of countries in the map
     * @return latitude point
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *  get longitude position of countries in the map
     * @return longitude point
     */
    public String getLongitude() {
        return longitude;
    }
}
