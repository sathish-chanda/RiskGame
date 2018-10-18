package game.model;


import game.utils.Constants;

public class RiskModel {

    private Game game;

    public RiskModel() {
        game = new Game();
    }
    public void loadGame() {
        game.loadMapData(Constants.MAP_FILE_NAME);
    }

}
