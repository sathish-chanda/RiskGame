package test.model;

import game.model.*;
import game.utils.Constants;
import game.utils.MapFileHelper;
import org.junit.Before;
import org.junit.Ignore;
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
     * This method test the major function of reinforcement
     */
    @Test
    public void testReinforcement() {
        player = new Player(2);
        player.reinforcementTest(gameMap);
        assertEquals(3, player.getReinforcementArmyNum());
    }

    /**
     * This method tests reinforcement in the case that a player owned a whole continent
     */
    @Test
    public void testReinforcementOwnedContinent() {
        player = new Player(2);
        Continent continent = gameMap.getContinentListMap().get(1);
        for (int i = 0; i < continent.getTerritoryList().size(); i++) {
            continent.getTerritoryList().get(i).setPlayer(player.getPlayerID());
            player.addCountry(continent.getTerritoryList().get(i));

        }
        player.reinforcementTest(gameMap);
        assertEquals(continent.getMaximumArmy(), player.getReinforcementArmyNum());
    }

    /**
     * This method tests number of armies could share to each player based on territories that player owned
     */
    @Test
    public void testReinforcementDividedBy3() {
        player = new Player(2);
        int count = 0;
        for (int i = 0; i < gameMap.getContinentListMap().size(); i++) {
            Continent continent = gameMap.getContinentListMap().get(i);
            for (int j = 1; j < continent.getTerritoryList().size(); j++) {
                player.addCountry(continent.getTerritoryList().get(j));
                count++;
            }
        }
        player.reinforcementTest(gameMap);
        assertEquals(count / 3, player.getReinforcementArmyNum());
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
     * */
    @Test
    public void testDeclairWin() {
        game.players = new ArrayList<PlayerStrategy>(10);
        game.players.add(player = new Player(2));
        assertEquals(true, game.roundRobinPlay(0,0,0));
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
