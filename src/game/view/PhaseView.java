package game.view;

import game.model.Player;
import game.utils.LogHelper;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PhaseView implements Observer {
    public void update(Observable observable, Object object) {
        String message = ((Player) observable).getMessage();
        ArrayList<String> actions = ((Player) observable).getActions();
        int playerID = ((Player) observable).getPlayerID();
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