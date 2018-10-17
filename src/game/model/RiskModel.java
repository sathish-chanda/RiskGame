package game.model;


public class RiskModel {

    private Game game;

    public void loadGame() {
        game = new Game();
        game.loadMapData();
    }

}
