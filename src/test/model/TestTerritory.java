package test.model;

import game.model.Territory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * The class <code>{@Link TestTerritory}</code> contains test cases
 * for the class <code>{@Link Territory}</code>
 */
public class TestTerritory {

    private Territory territory;

    /**
     * Initialize member variables for class TestTerritory prior to execution of test cases
     */
    @Before
    public void init() {
        territory = new Territory();
    }

    /**
     * perform test for territory with two parameters
     * Territory (String, String, String,
     * String, ArrayList<String> )
     */
    @Test
    public void testParameterizedConstructor() {
        String territoryName = "Mexico";
        String latitude = "160";
        String longitude = "150";
        String continentName = "North America";
        territory = new Territory(territoryName, latitude, longitude, continentName, adjacentTerritoryList());
        assertEquals(territoryName, territory.getTerritoryName());
        assertEquals(latitude, territory.getLatitude());
        assertEquals(longitude, territory.getLongitude());
        assertEquals(continentName, territory.getContinentName());
        assertEquals(adjacentTerritoryList(), territory.getAdjacentCountryList());
    }

    /**
     * Method to prepare territory list
     *
     * @return
     */
    private ArrayList<String> adjacentTerritoryList() {
        ArrayList<String> adjacentTerritoryList = new ArrayList<>();
        String canada = "Canada";
        String usa = "USA";
        adjacentTerritoryList.add(canada);
        adjacentTerritoryList.add(usa);
        return adjacentTerritoryList;

    }

    /**
     * perform clean up activity, post execution of all test cases
     */
    @After
    public void cleanUp() {
        territory = null;
    }

}
