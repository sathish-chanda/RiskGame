package test.model;

import game.model.Player;
import org.junit.Before;
import org.junit.Test;
import game.model.Territory;

import static org.junit.Assert.assertEquals;


public class TestPlayer {
    public Player player;
    public Territory territory;

    @Before
    public void init() {
        territory = new Territory();

    }

    @Test
    public void testPlayerId() {
        assertEquals(1, player.getPlayerID());
    }

    @Test
    public void testArmiesForPlayernum2() {
    player = new Player(2);
        assertEquals(40, player.getArmyNum());
    }
    @Test
    public void testArmiesForPlayernum3() {
     player = new Player(3);
        assertEquals(35, player.getArmyNum());
    }
    @Test
    public void testArmiesForPlayernum4() {
     player = new Player(4);
        assertEquals(30, player.getArmyNum());
    }
    @Test
    public void testArmiesForPlayernum5() {
     player = new Player(5);
        assertEquals(25, player.getArmyNum());
    }
    @Test
    public void testArmiesForPlayernum6() {
     player = new Player(6);
        assertEquals(20, player.getArmyNum());
    }

}
