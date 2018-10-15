package game;

import game.model.Country;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class implements all the game components logics
 */
public class Game {

    private int playerNum;//the number of players playing the gamecomponents
    private ArrayList<Player> players;
    private Map map;

    /**
     * In the constructor, the first input is the number of players.
     * The second input is the file name of .txt file which contains the information of a map
     * After the initialization of player and map, the next process is assign countries to players.
     * After the initial assignment of countries, the main gamecomponents, roundRobinPlay begins.
     */
    Game() {
        System.out.println("please input the number of players");
        Scanner readInput = new Scanner(System.in);
        if (readInput.hasNextInt())
            playerNum = readInput.nextInt(); // how many player are playing the gamecomponents
        players = new ArrayList<Player>();
        for (int i = 1; i <= playerNum; i++)
            players.add(new Player());
        System.out.println("please input the map name");
        if (readInput.hasNext()) {
            String mapName = readInput.next();
            map = new Map(mapName);
        }
        assignCountryToPlayers();
        getArmy();
        placeArmyOnCountry();
        //roundRobinPlay();
    }


    /**
     * The roundRobinPlay method realize the gamecomponents logic.
     */
/*	void roundRobinPlay() {
		while (players.size() > 1) {
			placeArmyOnCountry();//one country must have 2 or more than 2 army
			for (int i = 0; i < players.size(); i++) {
				Player attacker = players.get(i);
				Country attackingCountry;
				Random rand = new Random();
				while (true) {
					int k = rand.nextInt(attacker.getCountry().size());
					if (attacker.getCountry().get(k).getArmyNum() >= 2) {
						attackingCountry = attacker.getCountry().get(k);
						break;
					}
				}
				int m = rand.nextInt(attackingCountry.getAdjacentCountry().size());
				String defendingCountryName = attackingCountry.getAdjacentCountry().get(m);
			//	Country defendingCountry = map.searchCountry(defendingCountryName);
			//	Player defender = searchPlayerByCountryName(defendingCountry.getCountryName());
				if (defendingCountry.getArmyNum() == 0) {
					//which means the defending country has 0 army, bound to lose
					//battleResult(attackingCountry, defendingCountry, attacker, defender);
				}
				else {
					while ((attackingCountry.getArmyNum()>0) && (defendingCountry.getArmyNum()>0)) {
						int result = rollingDice(attackingCountry, defendingCountry);
						if (result > 0) //attacker wins
							defendingCountry.updateArmyNum(0 - result);
						else 
							attackingCountry.updateArmyNum(result);
					}
					//battleResult(attackingCountry, defendingCountry, attacker, defender);
				}
			}
		}
	}
	*/

    /**
     * The assignCountrytoPlayers method is used in the constructor.
     * Its main function is to assign every country to players at the very beginning of gamecomponents
     */
    private void assignCountryToPlayers() {
        int playerSelect;
        Random rand = new Random();
        for (int i = 0; i < map.countryMap.size(); i++) {
            playerSelect = rand.nextInt(playerNum) + 1;
            map.countryMap.get(i).setPlayer(playerSelect);
            players.get(playerSelect - 1).increaseCountryNum();
            players.get(playerSelect - 1).addCountry(map.countryMap.get(i));
        }
        for (int j = 0; j < players.size(); j++) {
            System.out.println("the player" + players.get(j).getCountryNum());
        }
    }

    /**
     * The getArmy method print out in the console how many armies are owned by players.
     */
    void getArmy() {

        int armyNum = 0;

        for (int i = 0; i < players.size(); i++) {

            String continentName = players.get(i).getCountry().get(0).getCountryName();
            boolean flag = true;
            int ownedCountrySize = players.get(i).getCountry().size();
            for (int j = 0; j < ownedCountrySize; j++) {
                if (continentName == players.get(i).getCountry().get(i).getContinentName())
                    continue;
                else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (ownedCountrySize == map.searchContinent(continentName).getcountryMap().size())
                    armyNum = map.searchContinent(continentName).getMaximumArmy();
                armyNum = armyNum + (players.get(i).getCountry().size() - map.searchContinent(continentName).getMaximumArmy()) / 3;

            } else {
                armyNum = players.get(i).getCountry().size() / 3;
            }
            if (armyNum < 3)
                armyNum = 3;
            players.get(i).setArmyNum(armyNum);
            System.out.println("Player" + players.get(i).getPlayerID() + " has " + armyNum + " armies.");
        }
    }

    /**
     * The placeArmynonCountry method is used at the beginning of each round of play.
     * It assign a player's army to the countries owned by that player.
     */
    private void placeArmyOnCountry() {
        Random rand = new Random();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int armyNum = players.get(i).getArmyNum();
            int allocatedArmyNum = 0;
            for (int j = 0; j < player.getCountry().size(); j++) {
                if (armyNum == 0) {
                    allocatedArmyNum = 0;
                } else if (armyNum == 1) {
                    allocatedArmyNum = 1;
                } else {
                    allocatedArmyNum = rand.nextInt(armyNum);
                }
                player.getCountry().get(j).updateArmyNum(allocatedArmyNum);
                armyNum = armyNum - allocatedArmyNum;
            }
        }
        for (int j = 0; j < players.size(); j++) {
            Player player = players.get(j);
            System.out.print("player" + player.getPlayerID() + " have");
            for (int k = 0; k < player.getCountry().size(); k++) {
                Country country = player.getCountry().get(k);
                System.out.print(country.getArmyNum() + " on country " + country.getCountryName() + ", ");
            }
            System.out.println(" ");
        }
    }

    /**
     * The searchPlayerByCountryName use a
     * }
     * return null;
     * }
     * <p>
     * /**
     * This method is an imitation of rolling dice.
     *
     * @param attackingCountry
     * @param defendingCountry
     * @return
     */
    int rollingDice(Country attackingCountry, Country defendingCountry) {
        Scanner readInput = new Scanner(System.in);
        System.out.println("attacker choose how many dice you want to roll");
        int attackerDice = 1;
        if (readInput.hasNext()) {
            attackerDice = readInput.nextInt();
        }
        System.out.println("defender choose how many dice you want to roll");
        int defenderDice = 1;
        if (readInput.hasNext()) {
            defenderDice = readInput.nextInt();
        }

        Random rand = new Random();
        int dice;
        int attackerBestDice = 0;
        int attackerSecondBestDice = 0;
        for (int i = 0; i < attackerDice; i++) {
            dice = rand.nextInt(6) + 1;
            if (attackerBestDice < dice)
                attackerBestDice = dice;
        }

        int defenderBestDice = 0;
        int defenderSecondBestDice = 0;
        for (int i = 0; i < defenderDice; i++) {
            dice = rand.nextInt(6) + 1;
            if (defenderBestDice < dice)
                defenderBestDice = dice;
        }

        if (attackerBestDice > defenderBestDice)//if positive attacker wins, if negative defender wins
            return 1;
        else return -1;
    }

    /**
     * The battleResult method check the result of each battle between two players.
     *
     * @param attackingCountry
     * @param defendingCountry
     * @param attacker
     * @param defender
     */
    void battleResult(Country attackingCountry, Country defendingCountry, Player attacker, Player defender) {
        if (attackingCountry.getArmyNum() <= 0) {
            attackingCountry.setPlayer(defendingCountry.getPlayerID());
            attacker.removeCountry(attackingCountry);

        } else if (defendingCountry.getArmyNum() <= 0) {
            defendingCountry.setPlayer(attackingCountry.getPlayerID());
            defender.removeCountry(defendingCountry);
        }

        for (int i = 0; i < map.continentMap.size(); i++) {
			/*int firstPlayerID = map.continentMap.get(i).countryMap.get(0).getPlayerID();
			int otherPlayerID; 
			boolean flag = true;
			for (int j = 1; j < map.continentMap.get(i).countryMap.size(); j++) {
				otherPlayerID = map.continentMap.get(i).countryMap.get(j).getPlayerID();
				if (firstPlayerID != otherPlayerID) {
					flag = false;
				}
			}
			if (flag) {
				int k = 0;
				while(players.get(k).getPlayerID() != firstPlayerID)
					k++;
			}*/
            //players.get(i).updateArmyNum(map.continentMap.get(i).maximumArmy);
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}














