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
        }
    }

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

    private void selectNumberOfPlayers() {
        LogHelper.printMessage("please input the number of players");
        Scanner readInput = new Scanner(System.in);
        if (readInput.hasNextInt()) {
            playerNum = readInput.nextInt(); // how many player are playing the gamecomponents
        }
        players = new ArrayList<Player>();
        for (int i = 1; i <= playerNum; i++)
            players.add(new Player(playerNum));

        assignCountryToPlayers();
        reinforcement();
        placeArmyOnCountry();
        initFortification();
    }

    /**
     * Method to inititalize fortification process
     */
    private void initFortification() {
        Scanner scanner = new Scanner(System.in);
        for(int j = 0; j < playerNum; j++) {

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
            players.get(playerSelect - 1).increaseCountryNum();
            players.get(playerSelect - 1).addCountry(gameMap.getTerritoryList().get(i));
        }
        for (int j = 0; j < players.size(); j++) {
            LogHelper.printMessage("the player" + players.get(j).getPlayerID() + " has " + players.get(j).getCountryNum() + " countries");
        }
    }

    /**
     * The getArmy method print out in the console how many armies are owned by players.
     */
    private void reinforcement() {

        int armyNum = 0;
        int deleteTerritoryNum = 0;
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < gameMap.getContinentListMap().size(); j++) {
                Continent c = gameMap.getContinentListMap().get(j);
                int playerID = players.get(i).getPlayerID();
                boolean flag = true;
                for (int k = 0; k < c.getTerritoryList().size(); k++) {
                    Territory t = c.getTerritoryList().get(k);
                    if (t.getPlayerID() == playerID)
                        continue;
                    else
                        flag = false;
                }
                if (flag) {
                    armyNum = armyNum + c.getMaximumArmy();
                    deleteTerritoryNum = deleteTerritoryNum + c.getTerritoryList().size();
                }
            }
            armyNum = armyNum + (players.get(i).getCountry().size() - deleteTerritoryNum) / 3;
            if (armyNum < 3)
                armyNum = 3;
            players.get(i).updateArmyNum(armyNum);
            LogHelper.printMessage("Player" + players.get(i).getPlayerID() + " has " + players.get(i).getArmyNum() + " armies.");
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
            LogHelper.printMessage("player" + player.getPlayerID() + " have ");
            for (int k = 0; k < player.getCountry().size(); k++) {
                Territory country = player.getCountry().get(k);
                LogHelper.printMessage(country.getArmyNum() + " on country " + country.getTerritoryName() + ", ");
            }
            LogHelper.printMessage(" ");
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}














