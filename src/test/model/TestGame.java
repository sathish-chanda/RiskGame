package test.model;

import game.model.Game;
import game.model.GameMap;
import game.model.Player;
import game.utils.Constants;
import game.utils.MapFileHelper;
import org.junit.Before;
import org.junit.Test;
import game.model.Territory;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class TestGame {
    public Player player;
    public Territory territory;
    public GameMap gameMap;
    public ArrayList<Player> players;
    public Game game;

    @Before
    public void init() {

        MapFileHelper mapFileHelper = MapFileHelper.getInstance();
        mapFileHelper.readMapFile("C:\\Users\\jiaquanyu\\Documents\\Risk Game Files\\world.map");
        game = new Game();
        gameMap = new GameMap(game);
        gameMap.mapFileHelper = mapFileHelper;
        gameMap.loadMapComponents();
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();

    }

    @Test
    public void testStartUp() {
        game.gameMap = gameMap;
        game.chooseNumberOfPlayersTest(4);
        game.randomAssignCountryToPlayers();
        game.roundRobinPlaceArmyOnCountry();
        for (int i = 0; i < game.players.size(); i++) {
            assertNotNull(game.players.get(i));
            assertNotNull(game.players.get(i).getCountry());
        }
        for (int j = 0; j < gameMap.getTerritoryList().size(); j++) {
            assertNotEquals(0, gameMap.getTerritoryList().get(j).getPlayerID());
        }
    }

}