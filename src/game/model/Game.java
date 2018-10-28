package game.model;

import game.listeners.GameListener;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.RiskView;

import java.util.*;

/**
 * This class implements all the game components logics
 */
public class Game implements GameListener {

    private int playerNum;//the number of players playing the gamecomponents
    private ArrayList<Player> players;
    private GameMap gameMap;
    private String fileName;

    /**
     * In the constructor, the first input is the number of players.
     * The second input is the file name of .txt file which contains the information of a map
     * After the initialization of player and map, the next process is assign countries to players.
     * After the initial assignment of countries, the main gamecomponents, roundRobinPlay begins.
     */
    public Game() {
        gameMap = new GameMap(this);
    }

    /**
     * This method set and load the map file
     *
     * @param fileName, send map file name.
     */
    public void loadMapData(String fileName) {
        setFileName(fileName);
        gameMap.loadMap(fileName);
    }

    /**
     * Method to open file chooser
     */
    public void chooseFile(RiskView view) {
        gameMap.chooseFile(view);
    }

    /**
     * This method displays list of continents to user
     */
    private void displayListOfContinentsToUser() {
        List<Continent> continentList = gameMap.getContinentListMap();
        LogHelper.printMessage("\nSelect Continents ");
        for (int i = 0; i < continentList.size(); i++) {
            LogHelper.printMessage(continentList.get(i).getContinentName() + " --- " + (i + 1));
        }
        displayListOfTerrritoriesToUser();
    }

    /**
     * This method displays list of the territories to  user
     */
    private void displayListOfTerrritoriesToUser() {
        List<Continent> continentList = gameMap.getContinentListMap();
        LogHelper.printMessage("\nSelect Territories ");
        for (int i = 0; i < continentList.size(); i++) {
            List<Territory> territoryList = continentList.get(i).getTerritoryList();
            for (int j = 0; j < territoryList.size(); j++) {
                LogHelper.printMessage(territoryList.get(j).getTerritoryName() + " --- " + (j + 1));
            }
        }
    }

    /**
     * Method to save map data
     */
    public void saveMapData() {
        gameMap.saveMap();
    }

    /**
     * Method to save map data
     */
    public void editMapData() {
        gameMap.initEditMap();
    }

    @Override
    public void onMapLoadSuccess() {
        gameMap.loadContinents();
        gameMap.loadTerritories();
        gameMap.syncContinentsAndTerritories();
        gameMap.verifyTerritoryMap();
    }

    @Override
    public void onMapLoadFailure(String message) {
        LogHelper.printMessage(message);
    }

    @Override
    public void onTerritoryMapValid() {
        gameMap.verifyContinentMap();
    }

    @Override
    public void onTerritoryMapInvalid(String message) {
        LogHelper.printMessage(message);
    }

    @Override
    public void onContinentMapValid() {
        if (getFileName().equals(Constants.MAP_FILE_NAME)) {
            LogHelper.printMessage("Do you want to edit map? y/n");
            editMapFileChoice();
        } else {
            selectNumberOfPlayers();
            assignCountryToPlayers();
            randomPlaceArmyOnCountry();
            roundRobinPlay();
        }
    }

    /**
     * the roundRobinPlay method is used to realize round robin logic
     */
    private void roundRobinPlay() {
        Scanner scanner = new Scanner(System.in);
        while (players.size() > 1) {
            for (int i = 0; i < players.size(); i++) {
                Player attacker = players.get(i);
                reinforcement(attacker);
                placeArmyOnCountry(attacker);
                attack(attacker);
                while (attacker.getArmyNum() > 0) {
                    LogHelper.printMessage("do you still want to attack? y for yes, n for no");
                    String input  = scanner.next();
                    if (input.matches("y")){
                        attack(attacker);
                    }
                    else if (input.matches("n")) {
                        break;
                    }
                    else {
                        LogHelper.printMessage("wrong input format! please reinput");
                    }
                }
            }
            for (int j = 0; j < players.size(); j++) {
                Player player = players.get(j);
                if (player.getCountryNum() == 0) {
                    players.remove(player);
                    j--;
                }
            }
        }
        declareWin(players.get(0));
    }

    /**
     * This method allows user to edit the map
     */
    private void editMapFileChoice() {
        Scanner scanner = new Scanner(System.in);
        String feedback = scanner.next();
        switch (feedback) {
            case Constants.YES:
                editMapData();
                break;
            case Constants.NO:
                saveMapData();
                break;
            default:
                LogHelper.printMessage("please select y or n");
                editMapFileChoice();
                break;
        }
    }

    @Override
    public void onContinentMapInvalid(String message) {
        LogHelper.printMessage(message);
    }

    @Override
    public void onUserMapSaveSuccess() {
        gameMap.cleanUp();
        loadMapData(Constants.USER_MAP_FILE_NAME);
    }

    @Override
    public void onUserMapEditSuccess() {
        saveMapData();
    }


    /**
     * This method allows users to select the number of players
     */
    private void selectNumberOfPlayers() {
        LogHelper.printMessage("please input the number of players");
        Scanner readInput = new Scanner(System.in);
        if (readInput.hasNextInt()) {
            playerNum = readInput.nextInt(); // how many player are playing the gamecomponents
        }
        players = new ArrayList<Player>();
        for (int i = 1; i <= playerNum; i++)
            players.add(new Player(playerNum));
    }

    /**
     * Method to inititalize fortification process
     */
    private void initFortification() {
        Scanner scanner = new Scanner(System.in);
        for (int j = 0; j < playerNum; j++) {

            LogHelper.printMessage("now it's the player" + players.get(j).getPlayerID() + " term to do fortification");
            LogHelper.printMessage("Select source country");
            String countrySourceName = scanner.nextLine();
            LogHelper.printMessage("Selected source country = " + countrySourceName);

            LogHelper.printMessage("Select destination country");
            String countryDestinationName = scanner.nextLine();
            LogHelper.printMessage("Selected destination country = " + countryDestinationName);

            gameMap.fortification(countrySourceName, countryDestinationName, players.get(j).getPlayerID());
        }
    }

    /**
     * The assignCountrytoPlayers method is used in the constructor.
     * Its main function is to assign every country to players at the very beginning of gamecomponents
     */
    private void assignCountryToPlayers() {
        int playerSelect;
        Random rand = new Random();
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++) {
            playerSelect = rand.nextInt(playerNum) + 1;
            gameMap.getTerritoryList().get(i).setPlayer(playerSelect);
            players.get(playerSelect - 1).updateArmyNum(1);
            players.get(playerSelect - 1).addCountry(gameMap.getTerritoryList().get(i));
        }
        for (int j = 0; j < players.size(); j++) {
            LogHelper.printMessage("the player" + players.get(j).getPlayerID() + " has " + players.get(j).getCountry().size() + " countries");
        }
        LogHelper.printMessage("--------------------------------------------------------------------------------");
    }

    /**
     * The getArmy method print out in the console how many armies are owned by players.
     */
    private void reinforcement(Player attacker) {

        int reinforcementArmyNum = 0;
        int deleteTerritoryNum = 0;
        int playerID = attacker.getPlayerID();
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
        reinforcementArmyNum = reinforcementArmyNum + (attacker.getCountry().size() - deleteTerritoryNum) / 3;
        if (reinforcementArmyNum < 3)
            reinforcementArmyNum = 3;
        attacker.updateArmyNum(reinforcementArmyNum);
        attacker.setReinforcementArmyNum(reinforcementArmyNum);
        LogHelper.printMessage("Player" + attacker.getPlayerID() + " has " + reinforcementArmyNum + " reinforcement armies.");
        //placeArmyOnCountry(armyNum);
    }

    /**
     * The randomPlaceArmyOnCountry is used to initialize the game.
     */
    private void randomPlaceArmyOnCountry() {
        Random rand = new Random();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int armyNum = player.getArmyNum();
            int TerritoryNum = player.getCountryNum();
            for (int j = 0; j < armyNum; j++) {
                int randomChooseCountry = rand.nextInt(TerritoryNum);
                players.get(i).getCountry().get(randomChooseCountry).updateArmyNum(1);
            }
        }
        for (int k = 0; k < players.size(); k++) {
            Player player = players.get(k);
            LogHelper.printMessage("player" + player.getPlayerID() + " has");
            for (int m = 0; m < player.getCountryNum(); m++) {
                Territory territory = player.getCountry().get(m);
                LogHelper.printMessage(territory.getArmyNum() + " armies on territroy " + territory.getTerritoryName() );
            }
            LogHelper.printMessage("--------------------------------------------------------------------------------");
        }
    }

    /**
     * The placeArmynonCountry method is used at the beginning of each round of play.
     * It assign a player's army to the countries owned by that player.
     */
    private void placeArmyOnCountry(Player attacker) {
        Scanner scanner = new Scanner(System.in);
        int armyNumToAllocate = attacker.getReinforcementArmyNum();
        LogHelper.printMessage("player" + attacker.getPlayerID() + " has " + armyNumToAllocate + " number of army to allocate");
        for (int j = 0; j < attacker.getCountry().size(); j++) {
            Territory territory = attacker.getCountry().get(j);
            LogHelper.printMessage("There are " + territory.getArmyNum() + " armies" + " on " + territory.getTerritoryName());
        }
        int inputArmyNum;
        for (int j = 0; j < attacker.getCountry().size(); j++) {
            Territory territory = attacker.getCountry().get(j);
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
        attacker.setReinforcementArmyNum(0);
        for (int j = 0; j < attacker.getCountry().size(); j++) {
            Territory territory = attacker.getCountry().get(j);
            LogHelper.printMessage("now there are " + territory.getArmyNum() + "armies" + " on " + territory.getTerritoryName());
        }
        LogHelper.printMessage("--------------------------------------------------------------------------------");
    }


    /**
     * The attack method realize the attack logic
     * @param attacker the player who wants to attack
     */
    private void attack(Player attacker) {
        ArrayList<Territory> attackingTerritoryList = new ArrayList<Territory>();
        ArrayList<Territory> defendingTerritoryList = new ArrayList<Territory>();
        Territory attackingTerritory = null;
        Territory defendingTerritory = null;
        Player defender = null;
        Scanner scanner = new Scanner(System.in);
        for (int j = 0; j < attacker.getCountry().size(); j++) {
            Territory territory = attacker.getCountry().get(j);
            boolean flag = true;
            if (territory.getArmyNum() > 2) {
                for (int k = 0; k < territory.getAdjacentCountryList().size(); k++) {
                    String adjacentTerritoryName = territory.getAdjacentCountryList().get(k);
                    Territory adjacentTerritory = gameMap.searchCountry(adjacentTerritoryName);
                    if (adjacentTerritory.getPlayerID() != attacker.getPlayerID()) {
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
            LogHelper.printMessage("player" + attacker.getPlayerID() + " cannot attack other country because no country has 2 or more armies");
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

        int result = rollingDice(attackingTerritory, defendingTerritory);
        if (result == 1) { //result == 1 means attacker wins
            LogHelper.printMessage("attacker wins");
            defender.removeCountry(defendingTerritory);
            defendingTerritory.setPlayer(attacker.getPlayerID());
            attacker.addCountry(defendingTerritory);
        } else if (result == -1) {
            LogHelper.printMessage("defender wins");
        }

    }


    /**
     * the attackAllOut method realize the all-out requirements in the grading sheet
     * @param attacker
     */
    private void attackAllOut(Player attacker) {
        ArrayList<Territory> attackingTerritoryList = new ArrayList<Territory>();
        ArrayList<Territory> defendingTerritoryList = new ArrayList<Territory>();
        Territory attackingTerritory = null;
        Territory defendingTerritory = null;
        Player defender = null;
        Scanner scanner = new Scanner(System.in);
        for (int j = 0; j < attacker.getCountry().size(); j++) {
            Territory territory = attacker.getCountry().get(j);
            boolean flag = true;
            if (territory.getArmyNum() > 2) {
                for (int k = 0; k < territory.getAdjacentCountryList().size(); k++) {
                    String adjacentTerritoryName = territory.getAdjacentCountryList().get(k);
                    Territory adjacentTerritory = gameMap.searchCountry(adjacentTerritoryName);
                    if (adjacentTerritory.getPlayerID() != attacker.getPlayerID()) {
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
            LogHelper.printMessage("player" + attacker.getPlayerID() + " cannot attack other country because no country has 2 or more armies");
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

        int result = rollingDice(attackingTerritory, defendingTerritory);
        if (result == 1) { //result == 1 means attacker wins
            LogHelper.printMessage("attacker wins");
            defender.removeCountry(defendingTerritory);
            defendingTerritory.setPlayer(attacker.getPlayerID());
            attacker.addCountry(defendingTerritory);
        } else if (result == -1) {
            LogHelper.printMessage("defender wins");
        }

    }


    /**
     * The rollingDice method realize the dice rolling logic
     * @param attackingTerritory the territory which want to attack other territories
     * @param defendingTerritory the territory which is being attacked
     * @return
     */
    int rollingDice(Territory attackingTerritory, Territory defendingTerritory) {
        int attackingTerritoryArmyNum = 0;
        int defendingTerritoryArmyNum = 0;
        int maxAttackingDiceNum = 0;
        int maxDefendingDiceNum = 0;
        int inputAttackingDiceNum = 0;
        int inputDefendingDiceNum = 0;
        Scanner scanner = new Scanner(System.in);
        while ((attackingTerritory.getArmyNum() > 0) && (defendingTerritory.getArmyNum() > 0)) {
            attackingTerritoryArmyNum = attackingTerritory.getArmyNum();
            if (attackingTerritoryArmyNum >= 3) {
                maxAttackingDiceNum = 3;
            } else if (attackingTerritoryArmyNum == 2) {
                maxAttackingDiceNum = 2;
            } else if (attackingTerritoryArmyNum == 1) {
                maxAttackingDiceNum = 1;
            }
            LogHelper.printMessage("attacker, you can roll up to " + maxAttackingDiceNum + " dice, how many dice you want to roll?");
            inputAttackingDiceNum = scanner.nextInt();
            while ((inputAttackingDiceNum > maxAttackingDiceNum) || (inputAttackingDiceNum <= 0)) {
                LogHelper.printMessage("your input exceeds maximum or is below or equal to zero, please reinput");
                inputAttackingDiceNum = scanner.nextInt();
            }

            defendingTerritoryArmyNum = defendingTerritory.getArmyNum();
            if ((defendingTerritoryArmyNum >= 2) && (attackingTerritoryArmyNum >= 2)) {
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
                }
                else {
                    LogHelper.printMessage("defender wins");
                    attackingTerritory.updateArmyNum(-1);
                }
            }
            LogHelper.printMessage("do you want to continue to attack?");
            String choice = null;
            while (choice == null) {
                choice = scanner.nextLine();
                if (choice.matches("y"))
                    break;
                else if (choice.matches("n"))
                    return 0;
                else
                    LogHelper.printMessage("wrong input format! please reinput");
                    choice = null;
            }
        }
        if (attackingTerritory.getArmyNum() == 0) {
            return -1;
        }
        else if (defendingTerritory.getArmyNum() == 0){
            return 1;
        }
        else
            return 0;
    }

    /**
     * The declairWin method is used when a player enlimate all other players and win the game
     * @param attacker the player who wins the game
     */
    void declareWin(Player attacker) {
        LogHelper.printMessage("the player" + attacker.getPlayerID() + " wins the game");
        LogHelper.printMessage("press any key to exit the game");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine())
            System.exit(1);

    }


    /**
     * This method is used to get Map file name
     *
     * @return filename
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * This method is used set file name
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}









