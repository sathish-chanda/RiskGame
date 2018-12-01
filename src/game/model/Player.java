package game.model;

import game.view.PlayerView;
import game.controller.CardController;
import game.utils.LogHelper;
import game.view.PhaseView;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents players playing the gamecomponents
 */
public class Player extends PlayerStrategy{


    private int playerID;//playerID is a integer that identify a player
    private int countryNum = 0;//number of country owned by a player
    private int armyNum = 0;//number of army owned by a player
    private ArrayList<Territory> ownedCountry;// countries owned by a player, the elements in ArrayList is examples ofCountry class
    private CardModel card;
    private String message;
    private ArrayList<String> actions;
    private GameMap gameMap;
    private float percentageOfCountriesOwned;
    public String classType = "human";

    /**
     * Method used to get reinforcement army count
     *
     * @return reinforcement army count
     */
    public int getReinforcementArmyNum() {
        return reinforcementArmyNum;
    }

    /**
     * Method used to set reinforcement army count
     *
     * @param reinforcementArmyNum
     */
    public void setReinforcementArmyNum(int reinforcementArmyNum) {
        this.reinforcementArmyNum = reinforcementArmyNum;
    }

    /**
     * Method used to update reinforcement army count
     *
     * @param reinforcementArmyNum
     */
    public void updateReinforcementArmyNum(int reinforcementArmyNum) {
        this.reinforcementArmyNum = this.reinforcementArmyNum + reinforcementArmyNum;
    }

    private int reinforcementArmyNum = 0;

    /**
     * Method used to setup player description
     * Assigning of Id, initial armies and owned countries
     *
     * @param playerNum, no of players in the game
     */
    public Player(int playerNum) {
        playerID = ++playerCounter;
        int initialArmyNum = 40 - 5 * (playerNum - 2);
        armyNum = armyNum + initialArmyNum;
        ownedCountry = new ArrayList<Territory>();
        actions = new ArrayList<String>();
        card = new CardModel();
        PhaseView phaseView = new PhaseView();
        addObserver(phaseView);
        PlayerView playerView = new PlayerView();
        addObserver(playerView);
        message = "Start up Phase";
        actions.clear();
        actions.add("Choosing the Number of Player");
        actions.add("Random Assignment of Countries");
        actions.add("Round Robin Placing of Armies in Countries");
    }

    /**
     * method to increase country number depends on players count
     */
    public void increaseCountryNum() {
        countryNum = countryNum + 1;
    }

    /**
     * method get player unique Id
     *
     * @return player Id
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Method to add player owned territory to the list
     *
     * @param country
     */
    public void addCountry(Territory country) {
        ownedCountry.add(country);
    }

    /**
     * Method to remove player loosed territory from the list
     *
     * @param country
     */
    public void removeCountry(Territory country) {
        ownedCountry.remove(country);
    }

    /**
     * Method to get owned territories list
     *
     * @return list of territories
     */
    public ArrayList<Territory> getCountry() {
        return ownedCountry;
    }

    /**
     * Method to get number of countries a player owned
     *
     * @return total country number
     */
    public int getCountryNum() {
        return ownedCountry.size();
    }

    /**
     * Method to get total number of armies a player owned
     *
     * @return total armies count
     */
    public int getArmyNum() {
        return armyNum;
    }

    /**
     * Method to update new army count, result from reinforcement phase
     *
     * @param armyNumber
     */
    public void updateArmyNum(int armyNumber) {
        armyNum = armyNum + armyNumber;
    }


    /**
     * The getArmy method print out in the console how many armies are owned by players.
     */
    public void reinforcement(GameMap gameMap) {
        int reinforcementArmyNum = 0;
        int deleteTerritoryNum = 0;
        for (int j = 0; j < gameMap.getContinentListMap().size(); j++) {
            Continent continent = gameMap.getContinentListMap().get(j);
            boolean flag = true;
            for (int k = 0; k < continent.getTerritoryList().size(); k++) {
                Territory territory = continent.getTerritoryList().get(k);
                if (territory.getPlayerID() == playerID)
                    continue;
                else
                    flag = false;
            }
            if (flag) {
                reinforcementArmyNum = reinforcementArmyNum + continent.getMaximumArmy();
                deleteTerritoryNum = deleteTerritoryNum + continent.getTerritoryList().size();
            }
        }
        reinforcementArmyNum = reinforcementArmyNum + (getCountry().size() - deleteTerritoryNum) / 3;
        if (reinforcementArmyNum < 3)
            reinforcementArmyNum = 3;
        updateArmyNum(reinforcementArmyNum);
        setReinforcementArmyNum(reinforcementArmyNum);
        actions.clear();
        actions.add("Allocate Armies");
        actions.add("Exchanging Card");
        phaseChanged("Reinforcement Phase");
        LogHelper.printMessage("Player" + playerID + " has " + reinforcementArmyNum + " reinforcement armies.");
        CardController cardController = new CardController();
        cardController.exchangeCardsForArmies(this);
    }

    public void reinforcementTest(GameMap gameMap) {
        int reinforcementArmyNum = 0;
        int deleteTerritoryNum = 0;
        for (int j = 0; j < gameMap.getContinentListMap().size(); j++) {
            Continent continent = gameMap.getContinentListMap().get(j);
            boolean flag = true;
            for (int k = 0; k < continent.getTerritoryList().size(); k++) {
                Territory territory = continent.getTerritoryList().get(k);
                if (territory.getPlayerID() == playerID)
                    continue;
                else
                    flag = false;
            }
            if (flag) {
                reinforcementArmyNum = reinforcementArmyNum + continent.getMaximumArmy();
                deleteTerritoryNum = deleteTerritoryNum + continent.getTerritoryList().size();
            }
        }
        reinforcementArmyNum = reinforcementArmyNum + (getCountry().size() - deleteTerritoryNum) / 3;
        if (reinforcementArmyNum < 3)
            reinforcementArmyNum = 3;
        updateArmyNum(reinforcementArmyNum);
        setReinforcementArmyNum(reinforcementArmyNum);
    }

    /**
     * The placeArmynonCountry method is used at the beginning of each round of play.
     * It assign a player's army to the countries owned by that player.
     */
    public void placeArmyOnCountry(GameMap gameMap) {
        Scanner scanner = new Scanner(System.in);
        int armyNumToAllocate = getReinforcementArmyNum();
        LogHelper.printMessage("player" + getPlayerID() + " has " + armyNumToAllocate + " number of army to allocate");
        for (int j = 0; j < getCountry().size(); j++) {
            Territory territory = getCountry().get(j);
            LogHelper.printMessage("There are " + territory.getArmyNum() + " armies" + " on " + territory.getTerritoryName());
        }
        int inputArmyNum;
        for (int j = 0; j < getCountry().size(); j++) {
            Territory territory = getCountry().get(j);
            if (armyNumToAllocate > 0) {
                LogHelper.printMessage("you can place " + armyNumToAllocate + " on " + territory.getTerritoryName());
                LogHelper.printMessage("input how many army you want to place on " + territory.getTerritoryName());
                inputArmyNum = scanner.nextInt();
                while ((inputArmyNum > armyNumToAllocate) || (inputArmyNum < 0)) {
                    LogHelper.printMessage("wrong input number, reinput");
                    inputArmyNum = scanner.nextInt();
                }

                territory.updateArmyNum(inputArmyNum);
                armyNumToAllocate = armyNumToAllocate - inputArmyNum;
                LogHelper.printMessage("you assign " + inputArmyNum + " armies to " + territory.getTerritoryName());
            } else
                break;
        }
        LogHelper.printMessage("--------------------------------------------------------------------------------");
        setReinforcementArmyNum(0);
        for (int j = 0; j < getCountry().size(); j++) {
            Territory territory = getCountry().get(j);
            LogHelper.printMessage("now there are " + territory.getArmyNum() + "armies" + " on " + territory.getTerritoryName());
        }
        LogHelper.printMessage("--------------------------------------------------------------------------------");
    }

    /**
     * Method relaise the attack phase of the game
     *
     * @param gameMap, map List
     */
    public void attack(GameMap gameMap, ArrayList<PlayerStrategy> players) {
        ArrayList<Territory> attackingTerritoryList = new ArrayList<Territory>();
        ArrayList<Territory> defendingTerritoryList = new ArrayList<Territory>();
        Territory attackingTerritory = null;
        Territory defendingTerritory = null;
        PlayerStrategy defender = null;
        Scanner scanner = new Scanner(System.in);
        actions.clear();
        actions.add("Attacking mode");
        actions.add("Rolling Dice");
        phaseChanged("Attack Phase");
        for (int j = 0; j < getCountry().size(); j++) {
            Territory territory = getCountry().get(j);
            boolean flag = true;
            if (territory.getArmyNum() > 2) {
                for (int k = 0; k < territory.getAdjacentCountryList().size(); k++) {
                    String adjacentTerritoryName = territory.getAdjacentCountryList().get(k);
                    Territory adjacentTerritory = gameMap.searchCountry(adjacentTerritoryName);
                    if (adjacentTerritory.getPlayerID() != getPlayerID()) {
                        attackingTerritoryList.add(territory);
                        if (flag) {
                            LogHelper.printMessage("you can choose " + territory.getTerritoryName() + " as attacking country, it has " + territory.getArmyNum() + " armies");
                            flag = false;
                        }
                        LogHelper.printMessage("you can choose " + adjacentTerritoryName + " to attack, it has " + adjacentTerritory.getArmyNum() + " armies");
                    }
                }
                LogHelper.printMessage("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }
        if (attackingTerritoryList.isEmpty()) {
            LogHelper.printMessage("player" + getPlayerID() + " cannot attack other country because no country has 2 or more armies");
            return;
        } else {
            LogHelper.printMessage("please choose a country from above as attacker country");
            String inputAttackingTerritoryName = scanner.nextLine();
            attackingTerritory = gameMap.searchCountry(inputAttackingTerritoryName);
            while ((attackingTerritory == null) || !attackingTerritoryList.contains(attackingTerritory)) {
                LogHelper.printMessage("the country name you just input doesn't exist, please reinput");
                inputAttackingTerritoryName = scanner.nextLine();
                attackingTerritory = gameMap.searchCountry(inputAttackingTerritoryName);
            }
            LogHelper.printMessage("please choose a country to attack");
            String inputDefendingTerritoryName = scanner.nextLine();
            defendingTerritory = gameMap.searchCountry(inputDefendingTerritoryName);
            while ((defendingTerritory == null) || !attackingTerritory.getAdjacentCountryList().contains(defendingTerritory.getTerritoryName())) {
                LogHelper.printMessage("the country name you just input doesn't exist or is not adjacent to attacking territory, please reinput");
                inputDefendingTerritoryName = scanner.nextLine();
                defendingTerritory = gameMap.searchCountry(inputDefendingTerritoryName);
            }
            int defenderID = defendingTerritory.getPlayerID();
            for (int x = 0; x < players.size(); x++) {
                if (players.get(x).getPlayerID() == defenderID) {
                    defender = players.get(x);
                    break;
                }
            }

        }

        int result = rollingDice(attackingTerritory, defendingTerritory);
        if (result == 1) { //result == 1 means attacker wins
            LogHelper.printMessage("attacker wins this attack phase");
            card.increaseCard();
            defender.removeCountry(gameMap.searchCountry(defendingTerritory.getTerritoryName()));
            defendingTerritory.setPlayer(getPlayerID());
            addCountry(defendingTerritory);
            LogHelper.printMessage("How many armies do you want to share from " +attackingTerritory.getTerritoryName() +" to " +defendingTerritory.getTerritoryName());
            int maxArmyShare = attackingTerritory.getArmyNum()-1;
            LogHelper.printMessage("you can move up to " + maxArmyShare +" armies");
            int armyShare = scanner.nextInt();
            defendingTerritory.updateArmyNum(armyShare);
            attackingTerritory.updateArmyNum(0 - armyShare);
            LogHelper.printMessage("attacker conquer " + defendingTerritory.getTerritoryName());
        } else if (result == -1) {
            LogHelper.printMessage("attacker loses this attack phase");
        }

    }

    /**
     * This method implements the All-Out mode in the attack Phase
     *
     * @param gameMap map List
     */
    public void attackAllOut(GameMap gameMap, ArrayList<PlayerStrategy> players ){
        ArrayList<Territory> attackingTerritoryList = new ArrayList<Territory>();
        ArrayList<Territory> defendingTerritoryList = new ArrayList<Territory>();
        Territory attackingTerritory = null;
        Territory defendingTerritory = null;
        actions.clear();
        actions.add("Allout-Attacking mode");
        actions.add("Rolling dice");
        phaseChanged("Attack Phase");
        PlayerStrategy defender = null;
        Scanner scanner = new Scanner(System.in);
        for (int j = 0; j < getCountry().size(); j++) {
            Territory territory = getCountry().get(j);
            boolean flag = true;
            if (territory.getArmyNum() > 2) {
                for (int k = 0; k < territory.getAdjacentCountryList().size(); k++) {
                    String adjacentTerritoryName = territory.getAdjacentCountryList().get(k);
                    Territory adjacentTerritory = gameMap.searchCountry(adjacentTerritoryName);
                    if (adjacentTerritory.getPlayerID() != getPlayerID()) {
                        attackingTerritoryList.add(territory);
                        if (flag) {
                            LogHelper.printMessage("you can choose " + territory.getTerritoryName() + " as attacking country, it has " + territory.getArmyNum() + " armies");
                            flag = false;
                        }
                        LogHelper.printMessage("you can choose " + adjacentTerritoryName + " to attack, it has " + adjacentTerritory.getArmyNum() + " armies");
                    }
                }
                LogHelper.printMessage("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }
        if (attackingTerritoryList.isEmpty()) {
            LogHelper.printMessage("player" + getPlayerID() + " cannot attack other country because no country has 2 or more armies");
            return;
        } else {
            LogHelper.printMessage("please choose a country from above as attacker country");
            String inputAttackingTerritoryName = scanner.nextLine();
            attackingTerritory = gameMap.searchCountry(inputAttackingTerritoryName);
            while ((attackingTerritory == null) || !attackingTerritoryList.contains(attackingTerritory)) {
                LogHelper.printMessage("the country name you just input doesn't exist, please reinput");
                inputAttackingTerritoryName = scanner.nextLine();
                attackingTerritory = gameMap.searchCountry(inputAttackingTerritoryName);
            }

            LogHelper.printMessage("please choose a country to attack");
            String inputDefendingTerritoryName = scanner.nextLine();
            defendingTerritory = gameMap.searchCountry(inputDefendingTerritoryName);
            while ((defendingTerritory == null) || !attackingTerritory.getAdjacentCountryList().contains(defendingTerritory.getTerritoryName())) {
                LogHelper.printMessage("the country name you just input doesn't exist or is not adjacent to attacking territory, please reinput");
                inputDefendingTerritoryName = scanner.nextLine();
                defendingTerritory = gameMap.searchCountry(inputDefendingTerritoryName);
            }
        }
        int defenderID = defendingTerritory.getPlayerID();
        for (int x = 0; x < players.size(); x++) {
            if (players.get(x).getPlayerID() == defenderID) {
                defender = players.get(x);
                break;
            }

        }

        int result = rollingDiceAllOut(attackingTerritory, defendingTerritory);
        if (result == 1) { //result == 1 means attacker wins
            LogHelper.printMessage("attacker wins this attack phase");
            card.increaseCard();
            defender.removeCountry(gameMap.searchCountry(defendingTerritory.getTerritoryName()));
            defendingTerritory.setPlayer(getPlayerID());
            addCountry(defendingTerritory);
            LogHelper.printMessage("How many armies do you want to share from " +attackingTerritory.getTerritoryName() +" to " +defendingTerritory.getTerritoryName());
            int maxArmyShare = attackingTerritory.getArmyNum()-1;
            LogHelper.printMessage("you can move up to " + maxArmyShare + " armies");
            int armyShare = scanner.nextInt();
            defendingTerritory.updateArmyNum(armyShare);
            attackingTerritory.updateArmyNum(0 - armyShare);
            LogHelper.printMessage("attacker conquer " + defendingTerritory.getTerritoryName());
        } else if (result == -1) {
            LogHelper.printMessage("attacker loses this attack phase");
        }

    }

    /**
     * The rollingDice method realize the dice rolling logic
     *
     * @param attackingTerritory the territory which want to attack other territories
     * @param defendingTerritory the territory which is being attacked
     * @return
     */
    public int rollingDice(Territory attackingTerritory, Territory defendingTerritory) {
        int attackingTerritoryArmyNum = 0;
        int defendingTerritoryArmyNum = 0;
        int maxAttackingDiceNum = 0;
        int maxDefendingDiceNum = 0;
        int inputAttackingDiceNum = 0;
        int inputDefendingDiceNum = 0;
        Scanner scanner = new Scanner(System.in);
        while ((attackingTerritory.getArmyNum() > 1) && (defendingTerritory.getArmyNum() > 0)) {
            attackingTerritoryArmyNum = attackingTerritory.getArmyNum();
            if (attackingTerritoryArmyNum > 3) {
                maxAttackingDiceNum = 3;
            } else if (attackingTerritoryArmyNum == 3) {
                maxAttackingDiceNum = 2;
            } else if (attackingTerritoryArmyNum == 2) {
                maxAttackingDiceNum = 1;
            }
            LogHelper.printMessage("attacker, you can roll up to " + maxAttackingDiceNum + " dice, how many dice you want to roll?");
            inputAttackingDiceNum = scanner.nextInt();
            while ((inputAttackingDiceNum > maxAttackingDiceNum) || (inputAttackingDiceNum <= 0)) {
                LogHelper.printMessage("your input exceeds maximum or is below or equal to zero, please reinput");
                inputAttackingDiceNum = scanner.nextInt();
            }

            defendingTerritoryArmyNum = defendingTerritory.getArmyNum();
            if (defendingTerritoryArmyNum >= 2) {
                maxDefendingDiceNum = 2;
            } else {
                maxDefendingDiceNum = 1;
            }
            LogHelper.printMessage("defender, you can roll up to " + maxDefendingDiceNum + " dice, how many dice you want to roll?");
            inputDefendingDiceNum = scanner.nextInt();
            while ((inputDefendingDiceNum > maxDefendingDiceNum) || (inputDefendingDiceNum == 0)) {
                LogHelper.printMessage("your input exceeds maximum or is zero, please reinput");
                inputDefendingDiceNum = scanner.nextInt();
            }
            Random random = new Random();
            PriorityQueue<Integer> attackingDiceQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            for (int i = 0; i < inputAttackingDiceNum; i++) {
                attackingDiceQueue.add(random.nextInt(5) + 1);
            }
            PriorityQueue<Integer> defendingDiceQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            for (int i = 0; i < inputDefendingDiceNum; i++) {
                defendingDiceQueue.add(random.nextInt(5) + 1);
            }
            int minDiceNum = Math.min(inputAttackingDiceNum, inputDefendingDiceNum);
            for (int i = 0; i < minDiceNum; i++) {
                int attackingDice = attackingDiceQueue.poll();
                System.out.print("the attacker's dice number is " + attackingDice + "    ");
                int defendingDice = defendingDiceQueue.poll();
                System.out.println("the defender's dice number is " + defendingDice + "    ");
                if (attackingDice > defendingDice) {
                    LogHelper.printMessage("attacker wins");
                    defendingTerritory.updateArmyNum(-1);
                } else {
                    LogHelper.printMessage("defender wins");
                    attackingTerritory.updateArmyNum(-1);
                }
            }

            LogHelper.printMessage("do you want to continue this attack phase: y/n?");
            String choice = "";
            choice = scanner.next();
            if (choice.matches("y"))
                continue;
            else if (choice.matches("n"))
                break;
            else {
                LogHelper.printMessage("wrong input format! please reinput");
                choice = "";
            }


        }
        if (attackingTerritory.getArmyNum() == 0) {
            return -1;
        } else if (defendingTerritory.getArmyNum() == 0) {
            return 1;
        } else
            return 0;
    }

    /**
     * The rollingDiceAllOut method realize the dice rolling logic for All-Out mode
     *
     * @param attackingTerritory the territory which want to attack other territories
     * @param defendingTerritory the territory which is being attacked
     * @return
     */
    public int rollingDiceAllOut(Territory attackingTerritory, Territory defendingTerritory) {
        int attackingTerritoryArmyNum = 0;
        int defendingTerritoryArmyNum = 0;
        int maxAttackingDiceNum = 0;
        int maxDefendingDiceNum = 0;
        Scanner scanner = new Scanner(System.in);
        while ((attackingTerritory.getArmyNum() > 1) && (defendingTerritory.getArmyNum() > 0)) {
            attackingTerritoryArmyNum = attackingTerritory.getArmyNum();
            if (attackingTerritoryArmyNum > 3) {
                maxAttackingDiceNum = 3;
            } else if (attackingTerritoryArmyNum == 3) {
                maxAttackingDiceNum = 2;
            } else if (attackingTerritoryArmyNum == 2) {
                maxAttackingDiceNum = 1;
            }

            defendingTerritoryArmyNum = defendingTerritory.getArmyNum();
            if (defendingTerritoryArmyNum >= 2) {
                maxDefendingDiceNum = 2;
            } else {
                maxDefendingDiceNum = 1;
            }

            LogHelper.printMessage("the attacker rolls " + maxAttackingDiceNum + " dice");
            LogHelper.printMessage("the defender rolls " + maxDefendingDiceNum + " dice");
            Random random = new Random();
            PriorityQueue<Integer> attackingDiceQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            for (int i = 0; i < maxAttackingDiceNum; i++) {
                attackingDiceQueue.add(random.nextInt(5) + 1);
            }
            PriorityQueue<Integer> defendingDiceQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });
            for (int i = 0; i < maxDefendingDiceNum; i++) {
                defendingDiceQueue.add(random.nextInt(5) + 1);
            }
            int minDiceNum = Math.min(maxAttackingDiceNum, maxDefendingDiceNum);
            for (int i = 0; i < minDiceNum; i++) {
                int attackingDice = attackingDiceQueue.poll();
                System.out.print("the attacker's dice number is " + attackingDice + "    ");
                int defendingDice = defendingDiceQueue.poll();
                System.out.println("the defender's dice number is " + defendingDice + "    ");
                if (attackingDice > defendingDice) {
                    LogHelper.printMessage("attacker wins");
                    defendingTerritory.updateArmyNum(-1);
                } else {
                    LogHelper.printMessage("defender wins");
                    attackingTerritory.updateArmyNum(-1);
                }
            }
        }
        if (attackingTerritory.getArmyNum() == 0) {
            return -1;
        } else if (defendingTerritory.getArmyNum() == 0) {
            return 1;
        } else
            return 0;
    }

    /**
     * Method initites the fortification Phase
     *
     * @param gameMap
     */
    public void initFortification(GameMap gameMap) {
        Scanner scanner = new Scanner(System.in);
        actions.clear();
        actions.add("Moving Armies between countries");
        phaseChanged("Fortification Phase");
        LogHelper.printMessage("now it's the player" + getPlayerID() + " term to do fortification");
        LogHelper.printMessage("Select source country");
        String territorySourceName = scanner.nextLine();
        LogHelper.printMessage("Selected source country = " + territorySourceName);

        LogHelper.printMessage("Select destination country");
        String territoryDestinationName = scanner.nextLine();
        LogHelper.printMessage("Selected destination country = " + territoryDestinationName);

        fortification(territorySourceName, territoryDestinationName, getPlayerID());
    }

    public void fortification(String countrySourceName, String countryDestinationName, int playerID) {
        Territory t1 = gameMap.searchCountry(countrySourceName);
        Territory t2 = gameMap.searchCountry(countryDestinationName);
        Queue<Territory> q = new LinkedList<Territory>();
        boolean flag = false;
        Territory startCountry = t1;
        q.offer(startCountry);
        ArrayList<Territory> searchedTerritory = new ArrayList<Territory>();

        while (!q.isEmpty()) {
            Territory c = q.poll();
            searchedTerritory.add(c);
            for (int i = 0; i < c.getAdjacentCountryList().size(); i++) {
                Territory adjacentCountry = gameMap.searchCountry(c.getAdjacentCountryList().get(i));
                if (adjacentCountry.getTerritoryName().matches(countryDestinationName) && (adjacentCountry.getPlayerID() == playerID)) {
                    flag = true;
                    break;
                } else if ((adjacentCountry.getPlayerID() == playerID) && !searchedTerritory.contains(adjacentCountry)) {
                    q.offer(adjacentCountry);
                } else
                    continue;
            }
        }
        if (flag) {
            int moveArmyNum = 0;
            LogHelper.printMessage("please input the number of armys you want to move");
            LogHelper.printMessage("you can move up to " + gameMap.searchCountry(countrySourceName).getArmyNum() + " armies");
            Scanner readInput = new Scanner(System.in);
            if (readInput.hasNextInt())
                moveArmyNum = readInput.nextInt();
            while (moveArmyNum > gameMap.searchCountry(countrySourceName).getArmyNum()) {
                LogHelper.printMessage("exceed maximum number you can move");
                LogHelper.printMessage("please input the number of armys you want to move");
                LogHelper.printMessage("you can move up to " + gameMap.searchCountry(countrySourceName).getArmyNum() + " armies");
                if (readInput.hasNextInt())
                    moveArmyNum = readInput.nextInt();
            }
            t1.updateArmyNum(0 - moveArmyNum);
            LogHelper.printMessage("now " + countrySourceName + " has " + t1.getArmyNum() + " armies");
            t2.updateArmyNum(moveArmyNum);
            LogHelper.printMessage("now " + countryDestinationName + " has " + t2.getArmyNum() + " armies");

        } else
            LogHelper.printMessage("cant move army");
        return;
    }

    public void phaseChanged(String message) {
        this.message = message;
        setChanged();
        notifyObservers(this);
    }

    public String getMessage() {
        return message;
    }

    /**
     * Returns percentage of countries owned player
     */
    public float getPercentageOfCountriesOwned() {
        return percentageOfCountriesOwned;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    /**
     * Sets percentage of countries owned player
     *
     * @param percentageOfCountriesOwned
     */
    public void setPercentageOfCountriesOwned(float percentageOfCountriesOwned) {
        this.percentageOfCountriesOwned = percentageOfCountriesOwned;
        invokePlayerObserver();
    }

    /**
     * Method to get card model
     *
     * @return
     */
    public CardModel getCard() {
        return card;
    }

    /**
     * Method to invoke player observer
     */
    public void invokePlayerObserver() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Method to initialize gameMap in player class
     *
     * @param gameMap
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Method to get list of continent owned
     */
    public List<Continent> getListOfContinentsOwned() {
        List<String> tempPlayerContinentList = new ArrayList<>();
        List<Continent> playerContinentList = new ArrayList<>();
        List<Continent> continentListMap = gameMap.getContinentListMap();
        List<Continent> playerOwnedContinentList = new ArrayList<>();
        for (Territory territory : getCountry()) {
            //Continent continent = new Continent(territory.getContinentName(), 0);
            tempPlayerContinentList.add(territory.getContinentName());
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(tempPlayerContinentList);
        tempPlayerContinentList.clear();
        tempPlayerContinentList.addAll(hashSet);

        for (int i = 0; i < tempPlayerContinentList.size(); i++) {
            Continent continent = new Continent(tempPlayerContinentList.get(i), 0);
            playerContinentList.add(continent);
        }

        for (int i = 0; i < playerContinentList.size(); i++) {
            for (int j = 0; j < getCountry().size(); j++) {
                if (playerContinentList.get(i).getContinentName().equals(getCountry().get(j).getContinentName())) {
                    playerContinentList.get(i).addTerritoryList(getCountry().get(j));
                }
            }
        }

        for (int i = 0; i < continentListMap.size(); i++) {
            for (int j = 0; j < playerContinentList.size(); j++) {
                if (continentListMap.get(i).getContinentName().equals(playerContinentList.get(j).getContinentName())) {
                    if (continentListMap.get(i).getTerritoryList().size() == playerContinentList.get(j).getTerritoryList().size()) {
                        playerOwnedContinentList.add(continentListMap.get(i));
                    }
                }
            }
        }
        return playerOwnedContinentList;
    }

}
