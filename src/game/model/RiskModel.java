package game.model;


import game.utils.Constants;
import game.view.RiskView;
import javafx.stage.Stage;

/**
 * It loads map file into the game
 */
public class RiskModel {

    private Game game;

    private String message;

    /**
     * It is the constructor class, creating instance for game
     */
    public RiskModel() {
        game = new Game();
    }

    /**
     * It loading the map file into the game.
     */
    public void loadGame(RiskView view) {
        game.chooseFile(view);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
