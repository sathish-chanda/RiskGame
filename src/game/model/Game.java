package game.model;

import game.listeners.GameListener;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.RiskView;

import java.io.File;
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
        //ToDo modify this part as per GUI feature
        /*if (getFileName().equals(Constants.MAP_FILE_NAME)) {
            LogHelper.printMessage("Do you want to edit map? y/n");
            editMapFileChoice();
        } else {

        }*/

        selectNumberOfPlayers();
        assignCountryToPlayers();
        randomPlaceArmyOnCountry();
        roundRobinPlay();

    }

    /**
     * the roundRobinPlay method is used to realize round robin logic
     */
    private void roundRobinPlay() {
        Scanner scanner = new Scanner(System.in);
        while (players.size() > 1) {
            for (int i = 0; i < players.size(); i++) {
                Player attacker = players.get(i);
                attacker.reinforcement(gameMap);
                attacker.placeArmyOnCountry(gameMap);
                attacker.attack(gameMap);
                while (attacker.getArmyNum() > 0) {
                    LogHelper.printMessage("do you still want to attack? y for yes, n for no");
                    String input = scanner.next();
                    if (input.matches("y")) {
                        attacker.attack(gameMap);
                    } else if (input.matches("n")) {
                        break;
                    } else {
                        LogHelper.printMessage("wrong input format! please reinput");
                    }
                }
                attacker.initFortification(gameMap);
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
                LogHelper.printMessage(territory.getArmyNum() + " armies on territroy " + territory.getTerritoryName());
            }
            LogHelper.printMessage("--------------------------------------------------------------------------------");
        }
    }


    /**
     * The declairWin method is used when a player enlimate all other players and win the game
     *
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









