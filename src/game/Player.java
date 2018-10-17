package game;

import game.model.Country;

import java.util.ArrayList;

/**
 * This class represents players playing the gamecomponents
 */
public class Player {

    private static int playerCounter = 0;//used to initializing players
    private int playerID;//playerID is a integer that identify a player
    private int countryNum = 0;//number of country owned by a player
    private int armyNum;//number of army owned by a player
    private ArrayList<Country> ownedCountry;// countries owned by a player, the elements in ArrayList is examples ofCountry class

    Player() {
        playerID = ++playerCounter;
    }

    void increaseCountryNum() {
        countryNum = countryNum + 1;
    }

    /**
     * The getArmy method returns the number of armies controled by a player
     */

    public int getPlayerID() {
        return playerID;
    }

    public void addCountry(Country country) {
        ownedCountry.add(country);
    }

    public void removeCountry(Country country) {
        ownedCountry.remove(country);
    }

    public ArrayList<Country> getCountry() {
        return ownedCountry;
    }

    public int getCountryNum() {
        return countryNum;
    }

    public int getArmyNum() {
        return armyNum;
    }

    public void setArmyNum(int armyNum) {
        this.armyNum = armyNum;
    }

    public void updateArmyNum(int armyNumber) {
        armyNum = armyNum + armyNumber;
    }


}
