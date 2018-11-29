package game.model;

import game.main.Tournament;
import game.utils.LogHelper;
import game.utils.MapFileHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TournamentMode extends Game {
    public GameMap gameMap;

    public void startTournament(int M, int P, int G, int D) {
        MapFileHelper mapFileHelper = MapFileHelper.getInstance();
        switch (M) {
            case 1:
                mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
            case 2:
                mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
            case 3:
                mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
            case 4:
                mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
            case 5:
                mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
        }
        for (int i = 0; i < G; i++) {
            gameMap = new GameMap(this);
            gameMap.mapFileHelper = mapFileHelper;
            gameMap.loadMapComponents();
            gameMap.loadContinents();
            gameMap.loadTerritories();
            gameMap.syncContinentsAndTerritories();

            setTheStrategyOfPlayers(P);
            randomAssignCountryToPlayers();
            roundRobinPlaceArmyOnCountry();
            roundRobinPlay(M, G, D);
        }
    }

    public boolean roundRobinPlay(int M, int G, int D) {
        int count = 0;
        while (count <= D) {
            while (players.size() > 1) {
                for (int i = 0; i < players.size(); i++) {
                    PlayerStrategy attacker = players.get(i);
                    attacker.reinforcement(gameMap);
                    attacker.placeArmyOnCountry(gameMap);
                    while (attacker.getArmyNum() > 0) {
                        attacker.attack(gameMap, players);
                        attacker.initFortification(gameMap);
                    }
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
                LogHelper.printMessage("Map" + M + " Game" + G);
                declareWin(players.get(0));
                LogHelper.printMessage("the player is a " + players.get(0));
                return true;
            }
        }
        return true;
    }

    public void setTheStrategyOfPlayers(int P) {
        Scanner scanner = new Scanner(System.in);
        players = new ArrayList<PlayerStrategy>();
        String typeOfPlayer;
        for (int i = 1; i <= P; i++) {
            LogHelper.printMessage("set the type of player" + i);
            typeOfPlayer = scanner.nextLine();
            if (typeOfPlayer.equalsIgnoreCase("aggressive")) {
                players.add(new AggressiveComputerPlayer(P));
            }
            else if (typeOfPlayer.equalsIgnoreCase("benevolent")) {
                players.add(new BenevolentComputerPlayer(P));
            }
            else if (typeOfPlayer.equalsIgnoreCase("cheater")) {
                players.add(new CheaterComputerPlayer(P));
            }
            else if (typeOfPlayer.equalsIgnoreCase("random")) {
                players.add(new RandomComputerPlayer(P));
            }
        }
    }
}