package test;

import game.GameMap;
import game.model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * The class <code>{@Link TestGameMap}</code> contains test cases
 * for the class <code>{@Link GameMap}</code>
 */
public class TestGameMap {

    private GameMap gameMap;

    /**
     * Initialize member variables for class TestGameMap prior to execution of test cases
     */
    @Before
    public void init() {
        gameMap = new GameMap(new Game());
    }

    /**
     * perform load continents test
     */
    @Test
    public void testLoadContinents() {
        gameMap.loadMap();
        gameMap.loadContinents();
        assertEquals(getContinentComponentList().size(),
                gameMap.getContinentList().size());
    }

    /**
     * perform load territories test
     */
    @Test
    public void testLoadTerritories() {
        gameMap.loadMap();
        gameMap.loadContinents();
        gameMap.loadTerritories();
        assertEquals(getTerritoryComponentList().size(),
                gameMap.getTerritoryList().size());
    }

    /**
     * perform synchronization of continents and territories test
     */
    @Test
    public void testSyncContinentsAndTerritories() {
        gameMap.loadMap();
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();
        assertEquals(gameMap.getContinentList().size(), gameMap.getContinentListMap().size());
    }

    /**
     * Method to get territory component list
     *
     * @return
     */
    private List<String> getTerritoryComponentList() {
        List<String> territoriesComponentList = gameMap.getMapFileHelper().getTerritoriesComponentList();
        territoriesComponentList.removeAll(Arrays.asList("", null));
        return territoriesComponentList;
    }

    /**
     * Method to get continent component list
     *
     * @return
     */
    private List<String> getContinentComponentList() {
        List<String> continentComponentList = gameMap.getMapFileHelper().getContinentsComponentList();
        continentComponentList.removeAll(Arrays.asList("", null));
        return continentComponentList;
    }

    /**
     * perform clean up activity, post execution of all test cases
     */
    @After
    public void cleanUp() {
        gameMap = null;
    }

}
