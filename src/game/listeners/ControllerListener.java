package game.listeners;

public interface ControllerListener {

    /**
     * This method will be called when user selects number of players for the game
     *
     * @param numberOfPlayer
     */
    void onPlayerSelected(int numberOfPlayer);

}
