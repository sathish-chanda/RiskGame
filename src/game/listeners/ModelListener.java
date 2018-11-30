package game.listeners;

import java.io.File;
import java.io.Serializable;

public interface ModelListener extends Serializable {

    /**
     * This method will be called if the game save is initiated
     *
     * @param file
     */
    void onGameSaved(File file);

}
