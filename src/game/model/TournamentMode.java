package game.model;

import game.main.Tournament;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Scanner;

public class TournamentMode extends Game {

    /**
     * Method to start tournament mode
     *
     * @param M number of maps
     * @param P number of player
     * @param G number of games
     * @param D declare draw
     */
    public void startTournament(int M, int P, int G, int D) {
        ArrayList<String> fileName = new ArrayList<String>();
        fileName.add("src\\world.map");
        fileName.add("src\\3D Cliff.map");
        fileName.add("src\\world.map");
        fileName.add("src\\world.map");
        fileName.add("src\\world.map");
        for (int k = 0; k < M; k++) {
            for (int i = 0; i < G; i++) {
                setPlayerNum(P);
                gameMap = new GameMap(this);
                MapFileHelper mapFileHelper = MapFileHelper.getInstance();
                gameMap.mapFileHelper = mapFileHelper;
                setBeginStartUpPhase(true);
                loadMapData(fileName.get(k));
            }
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
                declareWin(players.get(0));
                FileHelper.writeFileHepler("the winner is a " + players.get(0));
                return true;
            }
        }
        return true;
    }
}