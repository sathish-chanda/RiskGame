package game.model;


import game.utils.Constants;
import game.utils.LogHelper;
import game.utils.MapFileHelper;

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
     * Method to open new game
     */
    public void newGame() {
        LogHelper.printMessage("Initializing new Game");
        initNewGame();
    }

    /**
     * It loading the map file into the game.
     */
    public void loadGame() {
        game.loadMapData(Constants.MAP_FILE_NAME);
    }

    /**
     * Method to load Map Data
     *
     * @param file
     */
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

    /**
     * Method to initialize new Game
     */
    private void initNewGame() {
        File file = MapFileHelper.getFileFromFileChooser();
        if (file != null) {
            loadMapData(file.getPath());
        } else {
            LogHelper.printMessage("Unable to read file or Invalid file");
        }
    }

}
