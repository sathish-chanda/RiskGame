package game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface PlayerStrategy extends Serializable {
    public void reinforcement(GameMap gameMap);
    public void placeArmyOnCountry(GameMap gameMap);
    public void attack(GameMap gameMap, ArrayList<PlayerStrategy> players);
    public void attackAllOut(GameMap gameMap, ArrayList<PlayerStrategy> players);
    public int rollingDice(Territory attackingTerritory, Territory defendingTerritory);
    public int rollingDiceAllOut(Territory attackingTerritory, Territory defendingTerritory);
    public void initFortification(GameMap gameMap);

    public int getReinforcementArmyNum();
    public void setReinforcementArmyNum(int reinforcementArmyNum);
    public void updateReinforcementArmyNum(int reinforcementArmyNum);
    public void increaseCountryNum();
    public int getPlayerID();
    public void addCountry(Territory country);
    public void removeCountry(Territory country);
    public ArrayList<Territory> getCountry();
    public int getCountryNum();
    public int getArmyNum();
    public void updateArmyNum(int armyNumber);
    public void fortification(String territorySourceName, String territoryDestinationName, int PlayerID);
    public CardModel getCard();
    public void setGameMap(GameMap gameMap);
    public List<Continent> getListOfContinentsOwned();
    public void setPercentageOfCountriesOwned(float percentageOfCountriesOwned);
}
