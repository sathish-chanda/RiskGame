package test.model;

import game.model.*;
import game.utils.MapFileHelper;

import game.model.*;
import game.utils.Constants;
import game.utils.MapFileHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestAggressPlayer {
    /**
     * The class <code>{@Link TestPlayer}</code> contains test cases
     * for the class <code>{@Link TestPlayer}</code>
     */
    public PlayerStrategy player;
    public Territory territory;
    public GameMap gameMap;
    public Game game;


    /**
     * The init method is for the initilization of the test data
     */
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



    /**
     * This method tests reinforcement in the case that a player owned a whole continent
     */
    @Ignore
    public void testReinforcementOwnedContinent() {
        player = new Player(2);
        Continent continent = gameMap.getContinentListMap().get(1);
        for (int i = 0; i < continent.getTerritoryList().size(); i++) {
            continent.getTerritoryList().get(i).setPlayer(player.getPlayerID());
            player.addCountry(continent.getTerritoryList().get(i));

        }
        player.reinforcement(gameMap);
        assertEquals(continent.getMaximumArmy(), player.getReinforcementArmyNum());
    }

    /**
     * the
     */
    @Test
    public void testPlaceArmyOnCountry() {
        player = new AggressiveComputerPlayer(2);
        player.setReinforcementArmyNum(10);
        player.setGameMap(gameMap);
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++) {
            gameMap.getTerritoryList().get(i).updateArmyNum(1);
            gameMap.getTerritoryList().get(i).setPlayer(player.getPlayerID());
            player.addCountry(gameMap.getTerritoryList().get(i));
        }
        gameMap.getTerritoryList().get(0).updateArmyNum(10);
        gameMap.getTerritoryList().get(1).updateArmyNum(100);
        gameMap.searchCountry(gameMap.getTerritoryList().get(0).getAdjacentCountryList().get(0)).setPlayer(5);
        player.placeArmyOnCountry(gameMap);
        assertEquals(11, gameMap.getTerritoryList().get(0).getArmyNum());
        assertEquals(111, gameMap.getTerritoryList().get(1).getArmyNum());

    }

    @Test
    public void testRollingDice() {
        player = new AggressiveComputerPlayer(1);
        Territory attackingTerritory = new Territory();
        attackingTerritory.updateArmyNum(10);
        Territory defendingTerritory = new Territory();
        defendingTerritory.updateArmyNum(1);
        int result = player.rollingDice(attackingTerritory, defendingTerritory);
        assertEquals(1, result);
    }


    /**
     * This test case tests the attacker
     */
    @Test
    public void testAttackRollingDiceAttacker() {
        player = new Player(2);
        Territory attackTerritory = gameMap.getTerritoryList().get(0);
        Territory defendTerritory = gameMap.searchCountry(attackTerritory.getAdjacentCountryList().get(0));
        attackTerritory.updateArmyNum(50);
        defendTerritory.updateArmyNum(0);
        assertEquals(1, player.rollingDiceAllOut(attackTerritory, defendTerritory));
    }

    /**
     * This test case tests the defender
     */
    @Test
    public void testAttackRollingDiceDefender() {
        player = new Player(2);
        Territory attackTerritory = gameMap.getTerritoryListMap().get(0);
        Territory defendTerritory = gameMap.searchCountry(attackTerritory.getAdjacentCountryList().get(0));
        attackTerritory.updateArmyNum(0);
        defendTerritory.updateArmyNum(50);
        assertEquals(-1, player.rollingDiceAllOut(attackTerritory, defendTerritory));
    }


    /**
     * This test case tests the end of game
     */
    @Test
    public void testDeclairWin() {
        game.players = new ArrayList<PlayerStrategy>(10);
        game.players.add(player = new Player(2));
        assertEquals(true, game.roundRobinPlay(0, 0, 0));
    }

    /**
     * This test case test fortification
     */
    @Test
    public void testFotification() {
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
     * This test tests fortification
     */
    @Test
    public void testFotification2() {
        player = new Player(2);
        Territory sourceTerritory = gameMap.getTerritoryListMap().get(0);
        sourceTerritory.setPlayer(player.getPlayerID());
        sourceTerritory.updateArmyNum(5);
        Territory destineTerritory = gameMap.searchCountry(sourceTerritory.getAdjacentCountryList().get(0));
        destineTerritory.setPlayer(player.getPlayerID());

        boolean result = gameMap.fortificationTest(sourceTerritory.getTerritoryName(), destineTerritory.getTerritoryName(), 1, player.getPlayerID());
        assertEquals(true, result);
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
