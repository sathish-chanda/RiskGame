package game.model;


import game.listeners.ControllerListener;
import game.listeners.ModelListener;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import javafx.stage.FileChooser;

import java.io.*;

/**
 * It loads map file into the game
 */
public class RiskModel implements ModelListener {

    private Game game;
    private boolean isSavedFileValid;
    private boolean isLoadFileValid;

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
     * Method to initialize save game
     */
    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Saved files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setInitialFileName("risk game saved file");
        File file = fileChooser.showOpenDialog(null);
        LogHelper.printMessage("file path == " + file.getPath());
        saveFile(file);
    }

    /**
     * Method to save file
     *
     * @param file
     */
    public void saveFile(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
            fileOutputStream.close();
            LogHelper.printMessage("Game saved to file " + file.getPath());
            setSavedFileValid(true);
        } catch (Exception exception) {
            LogHelper.printMessage("Risk model Error Message " + exception);
            setSavedFileValid(false);
        }
    }

    /**
     * It loads the saved file.
     */
    public void loadGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Saved files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        LogHelper.printMessage("file path == " + file.getPath());
        loadFile(file, true);
    }

    /**
     * Method to load file
     */
    public void loadFile(File file, boolean isDevelop) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            game = (Game) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            setLoadFileValid(true);
            if (isDevelop) {
                game.continueRoundRobinPlay(0, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setLoadFileValid(false);
        }
    }

    /**
     * Method to load Map Data
     *
     * @param file
     */
    public void loadMapData(String file) {
        game.setListeners(this);
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

    /**
     * Condition to check if the saved file is valid
     *
     * @return
     */
    public boolean isSavedFileValid() {
        return isSavedFileValid;
    }

    /**
     * Set the validity of saved file.
     *
     * @return
     */
    public void setSavedFileValid(boolean savedFileValid) {
        isSavedFileValid = savedFileValid;
    }

    /**
     * Condition to check whether the load file is valid
     *
     * @return
     */
    public boolean isLoadFileValid() {
        return isLoadFileValid;
    }

    /**
     * Set the validity of the load file
     *
     * @param loadFileValid
     */
    public void setLoadFileValid(boolean loadFileValid) {
        isLoadFileValid = loadFileValid;
    }

    @Override
    public void onGameSaved(File file) {
        saveFile(file);
    }
}
