
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

public class TestCheaterPlayer {
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
     * This method test the major function of reinforcement
     */
    @Test
    public void testAttack() {

        PlayerStrategy cheaterPlayer1 = new CheaterComputerPlayer(2);
        PlayerStrategy cheaterPlayer2 = new CheaterComputerPlayer(2);
        ArrayList<PlayerStrategy> cheaterPlayerList = new ArrayList<PlayerStrategy>();
        cheaterPlayerList.add(cheaterPlayer1);
        cheaterPlayerList.add(cheaterPlayer2);
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++) {
            gameMap.getTerritoryList().get(i).setPlayer(cheaterPlayer1.getPlayerID());
            cheaterPlayer1.addCountry(gameMap.getTerritoryList().get(i));
        }
        for (int i = 0; i < gameMap.getTerritoryList().get(0).getAdjacentCountryList().size(); i++) {
            gameMap.searchCountry(gameMap.getTerritoryList().get(0).getAdjacentCountryList().get(i)).setPlayer(cheaterPlayer2.getPlayerID());
            cheaterPlayer2.addCountry(gameMap.searchCountry(gameMap.getTerritoryList().get(0).getAdjacentCountryList().get(i)));
            cheaterPlayer1.removeCountry(gameMap.searchCountry(gameMap.getTerritoryList().get(0).getAdjacentCountryList().get(i)));
        }
        cheaterPlayer1.attack(gameMap,cheaterPlayerList);
        for (int i = 0; i < gameMap.getTerritoryList().get(0).getAdjacentCountryList().size(); i++) {
            assertEquals(cheaterPlayer2.getPlayerID(), gameMap.searchCountry(gameMap.getTerritoryList().get(0).getAdjacentCountryList().get(i)).getPlayerID());
        }
    }

}