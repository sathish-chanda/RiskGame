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
        //int ContinentList
        float percentageOfCountryOwned = ((Player) obs).getPercentageOfCountriesOwned();
        LogHelper.printMessage("======================================================\n");
        LogHelper.printMessage("PLAYERS WORLD DOMINATION VIEW");
        LogHelper.printMessage("Player Id: " + playerId + "\n Army Number: " + armyNum + "\n Percentage of countries he own: " + percentageOfCountryOwned );
        LogHelper.printMessage("PLAYERS WORLD DOMINATION VIEW");
        LogHelper.printMessage("======================================================\n");
    }

}
