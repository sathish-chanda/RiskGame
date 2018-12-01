package game.utils;

import game.model.Continent;
import game.model.PlayerStrategy;

import java.util.ArrayList;
import java.util.List;

public class SavedObserver {

    /**
     * Method to load saved world domination view of player
     *
     * @param savedPLayer
     */
    public static void loadWorldDominationView(PlayerStrategy savedPLayer) {
        int playerId = savedPLayer.getPlayerID();
        int armyNum = savedPLayer.getArmyNum();
        List<Continent> continentList = savedPLayer.getListOfContinentsOwned();

        StringBuilder stringBuilder = new StringBuilder();

        String ownedContinents = "";

        if (continentList.size() > 0) {
            for (int i = 0; i < continentList.size(); i++) {
                stringBuilder.append(continentList.get(i).getContinentName());
            }
            ownedContinents = "owned continents = " + stringBuilder.toString();
        }

        float percentageOfCountryOwned = savedPLayer.getPercentageOfCountriesOwned();

        if (savedPLayer.getGameMap() != null) {
            float totalCountries = savedPLayer.getGameMap().getTerritoryList().size();
            float playerOwnedCountries = savedPLayer.getCountryNum();
            percentageOfCountryOwned = (playerOwnedCountries / totalCountries) * 100;
        }



        LogHelper.printMessage("========================================================================================\n");
        LogHelper.printMessage("PLAYERS WORLD DOMINATION VIEW\n");
        LogHelper.printMessage("Player " + playerId + " have total army of " +
                armyNum + " and he owns " +
                percentageOfCountryOwned + " percent of total territories" +
                " total continents owned by a player =  " + continentList.size() +
                ownedContinents
        );
        LogHelper.printMessage("");
        LogHelper.printMessage("========================================================================================\n");

    }

    /**
     * Method to load saved world phase view of player
     *
     * @param savedPLayer
     */
    public static void loadPhaseView(PlayerStrategy savedPLayer) {
        String message = savedPLayer.getMessage();
        ArrayList<String> actions = savedPLayer.getActions();
        int playerID = savedPLayer.getPlayerID();

        if (message.equals("Start up Phase")) {
            message = "Reinforcement Phase";
            actions.clear();
            actions.add("Allocate Armies");
            actions.add("Exchanging Card");
        }

        LogHelper.printMessage("=============================================================================================\n");
        LogHelper.printMessage("\t\t\tPHASE VIEW\n");
        LogHelper.printMessage("Current Phase : " + message);
        LogHelper.printMessage("Player Name   : " + playerID);
        String actionsMessage = "";
        for (int i = 0; i < actions.size() - 1; i++)
            actionsMessage += actions.get(i) + ",";
        if (actions.size() != 0)
            actionsMessage += actions.get(actions.size() - 1);
        LogHelper.printMessage("Actions       : " + actionsMessage);
        LogHelper.printMessage("=============================================================================================\n");
    }

}
