package game.model;

import game.main.Tournament;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Scanner;

public class TournamentMode extends Game {
    public void startTournament(int M, int P, int G, int D) {
        String fileName = null;
        switch (M) {
            case 1:
                fileName = "C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map";
            case 2:
                fileName = "C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map";
            case 3:
                fileName = "C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map";
            case 4:
                fileName = "C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map";
            case 5:
                fileName = "C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map";
        }
        for (int i = 0; i < G; i++) {
            setPlayerNum(P);
            gameMap = new GameMap(this);
            MapFileHelper mapFileHelper = MapFileHelper.getInstance();
            gameMap.mapFileHelper = mapFileHelper;
            setBeginStartUpPhase(true);
            loadMapData(fileName);
            // roundRobinPlay(M, G, D);
        }
    }

    @Override
    public boolean roundRobinPlay(int M, int G, int D) {
        ExecuteStrategy executeStrategy = new ExecuteStrategy();
        LogHelper.printMessage("in round robin play");
        int count = 0;
        while (count <= D) {
            while (players.size() > 1) {
                for (int i = 0; i < players.size(); i++) {
                    PlayerStrategy attacker = players.get(i);

                    executeStrategy.setStrategy(attacker);
                    executeStrategy.executeReinforcement(gameMap);
                    executeStrategy.executePlaceArmyOnCountry(gameMap);
                    executeStrategy.executeAttack(gameMap, players);
                    executeStrategy.executeInitFortification(gameMap);

                    for (int j = 0; j < players.size(); j++) {
                        PlayerStrategy player = players.get(j);
                        if (player.getCountryNum() == 0) {
                            players.remove(player);
                            j--;
                        }
                    }
                }
                count++;
            }
            if (players.size() == 1) {
                //LogHelper.printMessage("Map" + M + " Game" + G);
                declareWin(players.get(0));
                LogHelper.printMessage("the winner is a " + players.get(0));
                System.exit(1);
                return true;
            }
        }
        return true;
    }
}