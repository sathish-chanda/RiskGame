package game.model;

import game.model.Territory;
import game.utils.LogHelper;

import java.util.ArrayList;

/**
 * This class represents players playing the gamecomponents
 */
public class Player {

    private static int playerCounter = 0;//used to initializing players
    private int playerID;//playerID is a integer that identify a player
    private int countryNum = 0;//number of country owned by a player
    private int armyNum = 0;//number of army owned by a player
    private ArrayList<Territory> ownedCountry;// countries owned by a player, the elements in ArrayList is examples ofCountry class


    public int getReinforcementArmyNum() {
        return reinforcementArmyNum;
    }

    public void setReinforcementArmyNum(int reinforcementArmyNum) {
        this.reinforcementArmyNum = reinforcementArmyNum;
    }

    private int reinforcementArmyNum = 0;

    /**
     * Method used to setup player description
     * Assigning of Id, initial armies and owned countries
     * @param playerNum, no of players in the game
     */
    public Player(int playerNum) {
        playerID = ++playerCounter;
        int initialArmyNum = 40 - 5 * (playerNum - 2);
        armyNum = armyNum + initialArmyNum;
        ownedCountry = new ArrayList<Territory>();
        //LogHelper.printMessage("" + armyNum);
    }

    /**
     * method to increase country number depends on players count
     */
    public void increaseCountryNum() {
        countryNum = countryNum + 1;
    }

    /**
     * method get player unique Id
     * @return player Id
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Method to add player owned territory to the list
     * @param country
     */
    public void addCountry(Territory country) {
        ownedCountry.add(country);
    }

    /**
     * Method to remove player loosed territory from the list
     * @param country
     */
    public void removeCountry(Territory country) {
        ownedCountry.remove(country);
    }

    /**
     * Method to get owned territories list
     * @return list of territories
     */
    public ArrayList<Territory> getCountry() {
        return ownedCountry;
    }

    /**
     * Method to get number of countries a player owned
     * @return total country number
     */
    public int getCountryNum() {
        return ownedCountry.size();
    }

    /**
     * Method to get total number of armies a player owned
     * @return total armies count
     */
    public int getArmyNum() {
        return armyNum;
    }

    /**
     * Method to update new army count, result from reinforcement phase
     * @param armyNumber
     */
    public void updateArmyNum(int armyNumber) {
        armyNum = armyNum + armyNumber;
    }


}
