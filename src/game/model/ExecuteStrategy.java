package game.model;

import java.util.ArrayList;

public class ExecuteStrategy {
    PlayerStrategy playerStrategy;
    public void setStrategy(PlayerStrategy playerStrategy) {
        this.playerStrategy = playerStrategy;
    }
    public void executeReinforcement(GameMap gameMap) {
        playerStrategy.reinforcement(gameMap);

    }
    public void executePlaceArmyOnCountry(GameMap gameMap) {
        playerStrategy.placeArmyOnCountry(gameMap);

    }
    public void executeAttack(GameMap gameMap, ArrayList<PlayerStrategy> players) {
        playerStrategy.attack(gameMap, players);

    }
    public void executeInitFortification(GameMap gameMap) {
        playerStrategy.initFortification(gameMap);

    }

}
