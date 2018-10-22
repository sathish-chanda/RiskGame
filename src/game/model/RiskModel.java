package game.model;


import game.utils.Constants;

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

}
