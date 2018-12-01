package game.view;

import game.model.Player;
import game.model.PlayerStrategy;
import game.utils.LogHelper;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * This class implements observer view
 */
public class PhaseView implements Observer {

    @Override
    public void update(Observable observable, Object object) {
        String message = ((PlayerStrategy) observable).getMessage();
        ArrayList<String> actions = ((PlayerStrategy) observable).getActions();
        int playerID = ((PlayerStrategy) observable).getPlayerID();
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
