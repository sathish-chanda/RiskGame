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

        LogHelper.printMessage("Player  " + playerId + " total army of " + armyNum + " and he owns " + percentageOfCountryOwned + " of total territories");

    }

}
