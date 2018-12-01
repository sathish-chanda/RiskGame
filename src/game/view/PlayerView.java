package game.view;

import game.model.Continent;
import game.model.Player;
import game.utils.LogHelper;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * This class implements observer view
 */
public class PlayerView implements Observer {

    /**
     * This method observers any change in the observable
     * @param obs , observable class
     * @param arg , class which integrates observer and observable
     */
    public void update(Observable obs, Object arg) {
        int playerId = ((Player) obs).getPlayerID();
        int armyNum = ((Player) obs).getArmyNum();
        List<Continent> continentList = ((Player) obs).getListOfContinentsOwned();

        StringBuilder stringBuilder = new StringBuilder();

        String ownedContinents = "";

        if (continentList.size() > 0) {
            for (int i = 0; i < continentList.size(); i++) {
                stringBuilder.append(continentList.get(i).getContinentName());
            }

            ownedContinents = "owned continents = " + stringBuilder.toString();
        }

        float percentageOfCountryOwned = ((Player) obs).getPercentageOfCountriesOwned();
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

}
