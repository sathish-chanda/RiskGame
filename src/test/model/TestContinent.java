package test.model;

import game.model.Continent;
import game.model.Territory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The class <code>{@Link TestContinent}</code> contains test cases
 * for the class <code>{@Link Continent}</code>
 */
public class TestContinent {


    private Continent continent;

    /**
     * Initialize member variables for class TestContinent prior to execution of test cases
     */
    @Before
    public void init() {
        continent = new Continent();
    }

    /**
     * perform test for continent with two parameters
     * Continent (String, int)
     */
    @Test
    public void testContinentConstructorForTwoParameters() {
        String continentName = "North America";
        int maximumArmy = 5;
        continent = new Continent(continentName, maximumArmy);
        assertEquals(continentName, continent.getContinentName());
        assertEquals(maximumArmy, continent.getMaximumArmy());
    }

    /**
     * perform test for continent with three parameters
     * Continent (String, int, List<Territory>)
     */
    @Test
    public void testContinentConstructorForThreeParameters() {
        String continentName = "North America";
        int maximumArmy = 5;
        List<Territory> territoryList = prepareTerritoryList();
        continent = new Continent(continentName, maximumArmy, territoryList);
        assertEquals(continentName, continent.getContinentName());
        assertEquals(maximumArmy, continent.getMaximumArmy());
        assertEquals(territoryList, continent.getTerritoryList());
    }

    /**
     * Method to prepare continent list
     *
     * @return
     */
    private List<Territory> prepareTerritoryList() {
        List<Territory> territoryList = new ArrayList<>();
        Territory territory = new Territory();
        territory.setTerritoryName("Canada");
        territory.setTerritoryName("USA");
        territoryList.add(territory);
        return territoryList;
    }

    /**
     * perform clean up activity, post execution of all test cases
     */
    @After
    public void cleanUp() {
        continent = null;
    }

}
