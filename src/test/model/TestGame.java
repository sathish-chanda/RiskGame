package test.model;

import game.model.Game;
import game.model.GameMap;
import game.model.Player;
import game.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import game.model.Territory;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TestGame {
    public Player player;
    public Territory territory;
    public GameMap gameMap;
    public ArrayList<Player> players;
    public Game game;

    @Before
    public void init() {
        territory = new Territory();
        game = new Game();
        gameMap = new GameMap(game);
        gameMap.loadMap(Constants.USER_MAP_FILE_NAME);
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();


    }

}