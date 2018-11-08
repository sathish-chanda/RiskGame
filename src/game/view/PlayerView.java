package game.view;

import game.model.Player;
import game.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;

public class PlayerView implements Observer {

    @Override
    public void update(Observable obs, Object arg) {
        int playerId = ((Player) obs).getPlayerID();
        int armyNum = ((Player) obs).getArmyNum();
        float percentageOfCountryOwned = ((Player) obs).getPercentageOfCountriesOwned();
        LogHelper.printMessage("========================================================================================\n");
        LogHelper.printMessage("PLAYERS WORLD DOMINATION VIEW\n");
        LogHelper.printMessage("Player " + playerId + " have total army of " + armyNum + " and he owns " + percentageOfCountryOwned + " percent of total territories");
        LogHelper.printMessage("========================================================================================\n");

    }

}
