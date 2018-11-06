package game.model;


import game.utils.Constants;
import game.utils.LogHelper;

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
     * Method to open new game
     * @param numberOfPlayer
     */
    public void newGame(int numberOfPlayer) {
        LogHelper.printMessage("new game loading in progress......... players = "+numberOfPlayer);
    }

    /**
     * It loading the map file into the game.
     */
    public void loadGame() {
        game.loadMapData(Constants.MAP_FILE_NAME);
    }

    public void loadMapData(String file) {
        game.loadMapData(file);
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
