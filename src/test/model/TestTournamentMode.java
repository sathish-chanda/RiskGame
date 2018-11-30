package test.model;

import game.model.*;
import game.utils.MapFileHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestTournamentMode {

    private TournamentMode tournamentMode;


    @Test
    public void TestSetTheStrategyOfPlayers(){
        String strategyAgg = "aggressive";
        String strategyBen = "benevolent";
        int P = 2;
        ArrayList<PlayerStrategy> players = new ArrayList<PlayerStrategy>();
        assertTrue(strategyAgg, players.add(new AggressiveComputerPlayer(P)));
        assertTrue(strategyBen, players.add(new BenevolentComputerPlayer(P)));
    }

}
