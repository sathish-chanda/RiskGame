package game.model;


import game.utils.Constants;
import game.view.RiskView;

import java.io.File;

/**
 * It loads map file into the game
 */
public class RiskModel {

    private Game game;

    /**
     * It is the constructor class, creating instance for game
     */
    public RiskModel() {
        game = new Game();
    }

    /**
     * It loading the map file into the game.
     */
    public void loadGame() {
        game.loadMapData(Constants.MAP_FILE_NAME);
    }

    /**
     * This method is used to quit game
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Method to get Game instance
     *
     * @return
     */
    public Game getGame() {
        return game;
    }

}
