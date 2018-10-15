package game.model;

import game.utils.MapFileHelper;

public class RiskModel {

    private MapFileHelper mapFileHelper;
    private Game game;

    public void loadGame() {
        game = new Game();
        game.loadMapData();
    }

}
