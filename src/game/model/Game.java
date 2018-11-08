package game.model;

import game.listeners.GameListener;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.PhaseView;

import java.util.*;

/**
 * This class implements all the game components logics
 */
public class Game implements GameListener {
    private int playerNum;//the number of players playing the gamecomponents
    public ArrayList<Player> players;
    private GameMap gameMap;
    private boolean beginStartUpPhase;
    private boolean isMapValid;

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
        gameMap.loadMap(fileName);
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
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

    @Override
    public void onMapLoadSuccess() {
        gameMap.loadMapComponents();
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
        setMapValid(true);
        if (beginStartUpPhase) {
            startupPhase();
            roundRobinPlay();
        }
    }

    /**
     * This method implements the start up phase of the game.
     * In which a user selects the user saved map file,then load the map as a connected graph.
     * User chooses the number of players,then all countries are randomly assign to players.
     * In round-robin fashion,the players place one-by-one their given armies on their own countries.
     */
    public void startupPhase() {
        chooseNumberOfPlayers();
        randomAssignCountryToPlayers();
        roundRobinPlaceArmyOnCountry();
    }

    /**
     * the roundRobinPlay method is used to realize round robin logic
     */
    public boolean roundRobinPlay() {
        Scanner scanner = new Scanner(System.in);
        while (players.size() > 1) {
            for (int i = 0; i < players.size(); i++) {
                Player attacker = players.get(i);
                attacker.reinforcement(gameMap);
                attacker.placeArmyOnCountry(gameMap);
                LogHelper.printMessage("do you want to do attack in All-OUT mode : y/n ?");
                String alloutMode = "";
                alloutMode = scanner.nextLine();

                while (!alloutMode.matches("y") && !alloutMode.matches("n")) {
                    LogHelper.printMessage("wrong input! please input again.");
                    alloutMode = scanner.nextLine();
                }
                if (alloutMode.matches("y")) {
                    attacker.attackAllOut(gameMap);
                } else if (alloutMode.matches("n")) {
                    attacker.attack(gameMap);
                }

                while (attacker.getArmyNum() > 0) {
                    LogHelper.printMessage("--------------------------------------------------------------------------------");
                    LogHelper.printMessage("do you still want to attack? y for yes, n for no");
                    String input = scanner.next();
                    if (input.matches("y")) {
                        LogHelper.printMessage("do you want to do attack in All-OUT mode : y/n ?");
                        String nextAttack = "";
                        nextAttack = scanner.next();
                        while (!nextAttack.matches("y") && !nextAttack.matches("n")) {
                            LogHelper.printMessage("wrong input! please input again.");
                            nextAttack = scanner.nextLine();
                        }
                        if (nextAttack.matches("y")) {
                            attacker.attackAllOut(gameMap);
                        } else if (nextAttack.matches("n")) {
                            attacker.attack(gameMap);
                        }

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
        return true;
    }

    @Override
    public void onContinentMapInvalid() {
        setMapValid(false);
    }

    /**
     * This method allows users to select the number of players
     */
    private void chooseNumberOfPlayers() {
        LogHelper.printMessage("please input the number of players");
        players = new ArrayList<Player>();
        for (int i = 1; i <= playerNum; i++)
            players.add(new Player(playerNum));
    }

    /**
     * The assignCountrytoPlayers method is used in the constructor.
     * Its main function is to assign every country to players at the very beginning of gamecomponents
     */
    private void randomAssignCountryToPlayers() {
        int playerSelect, countrySelect;
        Random rand = new Random();
        List<Territory> countryList = new ArrayList<Territory>();
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++)
            countryList.add(gameMap.getTerritoryList().get(i));
        playerSelect = 0;
        while (countryList.size() != 0) {
            countrySelect = rand.nextInt(countryList.size());
            gameMap.getTerritoryList().get(gameMap.getTerritoryList().indexOf(countryList.get(countrySelect))).setPlayer(playerSelect + 1);
            players.get(playerSelect).addCountry(countryList.get(countrySelect));
            countryList.remove(countrySelect);
            playerSelect += 1;
            playerSelect = playerSelect % players.size();
        }

        for (int j = 0; j < players.size(); j++) {
            LogHelper.printMessage("the player" + players.get(j).getPlayerID() + " has " + players.get(j).getCountry().size() + " countries");
            float percentage = getPercentageCountriesOwnedByPlayer(players.get(j).getCountry().size());
            players.get(j).setPercentageOfCountriesOwned(percentage);

        }
        LogHelper.printMessage("--------------------------------------------------------------------------------");
    }

    /**
     * This method used to assigning countries randomly to the players
     */
    public void roundRobinPlaceArmyOnCountry() {
        Random rand = new Random();
        int[] armiesCount = new int[players.size()];
        int totalArmy = 0;
        for (int i = 0; i < players.size(); i++) {
            armiesCount[i] = players.get(i).getArmyNum();
            totalArmy += armiesCount[i];
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getCountryNum(); j++)
                players.get(i).getCountry().get(j).updateArmyNum(1);
            armiesCount[i] -= players.get(i).getCountryNum();
            totalArmy -= players.get(i).getCountryNum();
        }
        int currentPlayer = 0;
        while (totalArmy != 0) {
            currentPlayer = currentPlayer % (players.size());
            int randomChooseCountry = rand.nextInt(players.get(currentPlayer).getCountryNum());
            if (armiesCount[currentPlayer] != 0) {
                players.get(currentPlayer).getCountry().get(randomChooseCountry).updateArmyNum(1);
                armiesCount[currentPlayer] -= 1;
                totalArmy -= 1;
            }
            currentPlayer++;
        }
        for (int k = 0; k < players.size(); k++) {
            Player player = players.get(k);
            LogHelper.printMessage("Player-" + player.getPlayerID() + " has");
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
    public void declareWin(Player attacker) {
        LogHelper.printMessage("the player" + attacker.getPlayerID() + " wins the game");
        LogHelper.printMessage("press any key to exit the game");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine())
            System.exit(1);

    }

    /**
     * Method to set flag for starting a game phase
     *
     * @param beginStartUpPhase
     */
    public void setBeginStartUpPhase(boolean beginStartUpPhase) {
        this.beginStartUpPhase = beginStartUpPhase;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * Method check if map is valid
     *
     * @return
     */
    public boolean isMapValid() {
        return isMapValid;
    }

    /**
     * Method set map validity
     *
     * @param mapValid
     */
    public void setMapValid(boolean mapValid) {
        isMapValid = mapValid;
    }

    /**
     * Method to get percentage of countries owned by a player
     *
     * @param countriesOwned
     */
    public float getPercentageCountriesOwnedByPlayer(int countriesOwned) {
        float countriesOwnedFloat = countriesOwned;
        float totalTerritories = getGameMap().getTerritoryList().size();
        float result = (countriesOwnedFloat / totalTerritories) * 100;
        return result;
    }

}









