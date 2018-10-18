package game.model;

import game.GameMap;
import game.Player;
import game.listeners.GameListener;
import game.utils.Constants;
import game.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        //
    }

    public void loadMapData(String fileName) {
        setFileName(fileName);
        gameMap.loadMap(fileName);
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
        displayListofTerrritoriesToUser();
    }

    private void displayListofTerrritoriesToUser() {
        List<Continent> continentList = gameMap.getContinentListMap();
        LogHelper.printMessage("\nSelect Territories ");
        for (int i = 0; i < continentList.size(); i++) {
            List<Territory> territoryList = continentList.get(i).getTerritoryList();
            for (int j = 0; j < territoryList.size(); j++) {
                LogHelper.printMessage(territoryList.get(j).getTerritoryName() + " --- " + (j + 1));
            }
        }
    }

    public void saveMapData() {
        gameMap.saveMap();
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
        }
    }

    private void editMapFileChoice() {
        Scanner scanner = new Scanner(System.in);
        String feedback = scanner.next();
        switch (feedback) {
            case "y":
                break;
            case "n":
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
        loadMapData(Constants.USER_MAP_FILE_NAME);
    }

    private void selectNumberOfPlayers() {
        System.out.println("please input the number of players");
        Scanner readInput = new Scanner(System.in);
        if (readInput.hasNextInt()) {
            playerNum = readInput.nextInt(); // how many player are playing the gamecomponents
        }
        players = new ArrayList<Player>();
        for (int i = 1; i <= playerNum; i++)
            players.add(new Player(playerNum * 40));

        assignCountryToPlayers();
        getArmy();
        placeArmyOnCountry();

        Scanner scanner = new Scanner(System.in);

        LogHelper.printMessage("Select source country");
        String countrySourceName = scanner.nextLine();
        LogHelper.printMessage("Selected source country = " + countrySourceName);

        LogHelper.printMessage("Select destination country");
        String countryDestinationName = scanner.nextLine();
        LogHelper.printMessage("Selected destination country = " + countryDestinationName);

        gameMap.fortification(countrySourceName, countryDestinationName, players.get(0).getPlayerID());
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
            players.get(playerSelect - 1).increaseCountryNum();
            players.get(playerSelect - 1).addCountry(gameMap.getTerritoryList().get(i));
        }
        for (int j = 0; j < players.size(); j++) {
            System.out.println("the player" + players.get(j).getPlayerID() + " has " + players.get(j).getCountryNum() + " countries");
        }
    }

    /**
     * The getArmy method print out in the console how many armies are owned by players.
     */
    private void getArmy() {

        int armyNum = 0;

        for (int i = 0; i < players.size(); i++) {

            String continentName = players.get(i).getCountry().get(0).getTerritoryName();
            boolean flag = true;
            int ownedCountrySize = players.get(i).getCountry().size();
            for (int j = 0; j < ownedCountrySize; j++) {
                if (continentName.matches(players.get(i).getCountry().get(j).getContinentName()))
                    continue;
                else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (ownedCountrySize == gameMap.searchContinent(continentName).getTerritoryList().size())
                    armyNum = gameMap.searchContinent(continentName).getMaximumArmy();
                armyNum = armyNum + (players.get(i).getCountry().size() - gameMap.searchContinent(continentName).getMaximumArmy()) / 3;

            } else {
                armyNum = players.get(i).getCountry().size() / 3;
            }
            if (armyNum < 3)
                armyNum = 3;
            players.get(i).updateArmyNum(armyNum);
            System.out.println("Player" + players.get(i).getPlayerID() + " has " + players.get(i).getArmyNum() + " armies.");
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
            int TerritoryNum = players.get(i).getCountryNum();
            for (int j = 0; j < armyNum; j++) {
                int randomChooseCountry = rand.nextInt(TerritoryNum);
                players.get(i).getCountry().get(randomChooseCountry).updateArmyNum(1);
            }
        }
        for (int j = 0; j < players.size(); j++) {
            Player player = players.get(j);
            System.out.print("player" + player.getPlayerID() + " have ");
            for (int k = 0; k < player.getCountry().size(); k++) {
                Territory country = player.getCountry().get(k);
                System.out.print(country.getArmyNum() + " on country " + country.getTerritoryName() + ", ");
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
    private int rollingDice(Territory attackingCountry, Territory defendingCountry) {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}














