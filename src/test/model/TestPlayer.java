package test.model;

import game.model.*;
import game.utils.Constants;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * The class <code>{@Link TestPlayer}</code> contains test cases
 * for the class <code>{@Link TestPlayer}</code>
 */
public class TestPlayer {
    public Player player;
    public Territory territory;
    public GameMap gameMap;
    public Game game;


    @Test
    public void testReinforcement() {

        gameMap.loadMap(Constants.USER_MAP_FILE_NAME);
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();

        gameMap = new GameMap(game);
        gameMap.loadMap(Constants.FILE_DOMAIN_PATH + Constants.USER_MAP_FILE_NAME);
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();
        player = new Player(2);
        player.reinforcement(gameMap);
        assertEquals(0, player.getReinforcementArmyNum());
    }

    /**
     * Initialize member variables for class TestPlayer prior to execution of test cases
     */
    @Before
    public void init() {
        territory = new Territory();
    }

    /**
     * perform test for player ID
     * Test whether player ID generated
     */
    @Test
    public void testPlayerId() {
        player = new Player(2);
        assertEquals(1, player.getPlayerID());
    }

    @Test
    public void testFotification2() {
        player = new Player(2);
        Territory sourceTerritory = gameMap.getTerritoryListMap().get(0);
        sourceTerritory.setPlayer(player.getPlayerID());
        sourceTerritory.updateArmyNum(5);
        Territory destineTerritory = gameMap.searchCountry(sourceTerritory.getAdjacentCountryList().get(0));
        destineTerritory.setPlayer(player.getPlayerID() + 1);

        boolean result = gameMap.fortificationTest(sourceTerritory.getTerritoryName(), destineTerritory.getTerritoryName(), 1, player.getPlayerID());
        assertEquals(false, result);
    }


    /**
     * perform test for randomly generated armies when player count = 2
     */
    @Test
    public void testArmiesForPlayernum2() {
    player = new Player(2);
        assertEquals(40, player.getArmyNum());
    }

    /**
     * perform test for randomly generated armies when player count = 3
     */
    @Test
    public void testArmiesForPlayernum3() {
     player = new Player(3);
        assertEquals(35, player.getArmyNum());
    }

    /**
     * perform test for randomly generated armies when player count = 4
     */
    @Test
    public void testArmiesForPlayernum4() {
     player = new Player(4);
        assertEquals(30, player.getArmyNum());
    }

    /**
     * perform test for randomly generated armies when player count = 5
     */
    @Test
    public void testArmiesForPlayernum5() {
     player = new Player(5);
        assertEquals(25, player.getArmyNum());
    }

    /**
     * perform test for randomly generated armies when player count = 6
     */
    @Test
    public void testArmiesForPlayernum6() {
     player = new Player(6);
        assertEquals(20, player.getArmyNum());
    }

}
