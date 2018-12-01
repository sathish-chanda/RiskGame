package game.model;

import game.listeners.GameListener;
import game.listeners.ModelListener;
import game.utils.Constants;
import game.utils.LogHelper;
import game.utils.SavedObserver;
import game.view.PhaseView;

import java.io.*;
import java.util.*;

/**
 * This class implements all the game components logics
 */
public class Game implements GameListener, Externalizable {
    public int playerNum;//the number of players playing the game components
    public ArrayList<PlayerStrategy> players;
    public GameMap gameMap;
    public boolean beginStartUpPhase;
    public boolean isMapValid;
    private String filePath = "d:\\risk saved file.ser";
    private ModelListener listener;
    int round = 0;
    boolean loadObserver;

    /**
     * In the constructor, the first input is the number of players.
     * The second input is the file name of .txt file which contains the information of a map
     * After the initialization of player and map, the next process is assign countries to players.
     * After the initial assignment of countries, the main gamecomponents, roundRobinPlay begins.
     */
    public Game() {
        LogHelper.printMessage("Loading game");
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

    /**
     * Method to get game map
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Method to set game map
     *
     * @param gameMap
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
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
        boolean flag = true;
        setMapValid(true);
        if (beginStartUpPhase) {
            startupPhase();
            for (int i = 0; i < playerNum; i++) {
                if (players.get(i).classType == "human") {
                    roundRobinPlay(0, 0, 0);
                    flag = false;
                    break;
                }

            }
            if(flag)
                roundRobinPlayAuto(0, 0, 0);
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
     * the roundRobinPlay method is used to implement round robin logic
     *
     * @param M Number of Map of the game
     * @param G Number of Games to be played
     * @param D is used to declare draw
     * @return
     */
    public boolean roundRobinPlay(int M, int G, int D) {
        Scanner scanner = new Scanner(System.in);
        while (players.size() > 1) {
            for (int i = 0; i < players.size(); i++) {
                //round = i;
                //saveFile();
                PlayerStrategy attacker = players.get(i);
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
                    attacker.attackAllOut(gameMap, players);
                } else if (alloutMode.matches("n")) {
                    attacker.attack(gameMap, players);
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
                            attacker.attackAllOut(gameMap, players);
                        } else if (nextAttack.matches("n")) {
                            attacker.attack(gameMap, players);
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
                PlayerStrategy player = players.get(j);
                if (player.getCountryNum() == 0) {
                    players.remove(player);
                    j--;
                }
            }
        }
        declareWin(players.get(0));
        return true;
    }

    /**
     * Method to continue round robin play
     */
    public boolean continueRoundRobinPlay(int M, int G, int D) {
        ExecuteStrategy executeStrategy = new ExecuteStrategy();
        LogHelper.printMessage("in round robin play");
        int count = 0;
        boolean alreadyLoaded = true;
        while (count <= D) {
            while (players.size() > 1) {
                for (int i = round; i < players.size(); i++) {
                    if (alreadyLoaded) {
                        alreadyLoaded = false;
                        initSavedObserver();
                    }
                    round = i;
                    saveFile();
                    PlayerStrategy attacker = players.get(i);
                    executeStrategy.setStrategy(attacker);
                    executeStrategy.executeReinforcement(gameMap);
                    executeStrategy.executePlaceArmyOnCountry(gameMap);
                    executeStrategy.executeAttack(gameMap, players);
                    executeStrategy.executeInitFortification(gameMap);

                    for (int j = 0; j < players.size(); j++) {
                        PlayerStrategy player = players.get(j);
                        if (player.getCountryNum() == 0) {
                            players.remove(player);
                            j--;
                        }
                    }
                }
                count++;
            }
            if (players.size() == 1) {
                LogHelper.printMessage("Map" + M + " Game" + G);
                declareWin(players.get(0));
                System.exit(1);
                return true;
            }
        }
        return true;
    }

    /**
     * Method to initialize saved Observer
     */
    private void initSavedObserver() {
        SavedObserver.loadWorldDominationView(players.get(round));
        SavedObserver.loadPhaseView(players.get(round));
    }

    /**
     * This method is used for computer player
     *
     * @param M Number of Map of the game
     * @param G Number of Games to be played
     * @param D is used to declare draw
     * @return
     */
    public boolean roundRobinPlayAuto(int M, int G, int D) {
        ExecuteStrategy executeStrategy = new ExecuteStrategy();
        LogHelper.printMessage("in round robin play");
        int count = 0;
        while (count <= D) {
            while (players.size() > 1) {
                for (int i = 0; i < players.size(); i++) {

                    LogHelper.printMessage("Testing phase get getActions "+players.get(i).getActions().get(0));
                    LogHelper.printMessage("Testing phase get message "+players.get(i).getMessage());
                    LogHelper.printMessage("Testing phase get player "+players.get(i).getPlayerID());

                    round = i;
                    saveFile();
                    PlayerStrategy attacker = players.get(i);
                    executeStrategy.setStrategy(attacker);
                    executeStrategy.executeReinforcement(gameMap);
                    executeStrategy.executePlaceArmyOnCountry(gameMap);
                    executeStrategy.executeAttack(gameMap, players);
                    executeStrategy.executeInitFortification(gameMap);

                    for (int j = 0; j < players.size(); j++) {
                        PlayerStrategy player = players.get(j);
                        if (player.getCountryNum() == 0) {
                            players.remove(player);
                            j--;
                        }
                    }



                }
                count++;
            }
            if (players.size() == 1) {
                LogHelper.printMessage("Map" + M + " Game" + G);
                declareWin(players.get(0));
                System.exit(1);
                return true;
            }
        }
        return true;
    }


    @Override
    public void onContinentMapInvalid() {
        setMapValid(false);
    }

    /**
     * This method allows users to select the number of players
     */
    public void chooseNumberOfPlayers() {
        Scanner scanner = new Scanner(System.in);
        players = new ArrayList<PlayerStrategy>();
        String typeOfPlayer;
        for (int i = 1; i <= playerNum; i++) {
            LogHelper.printMessage("set the type of player" + i);
            typeOfPlayer = scanner.nextLine();
            if (typeOfPlayer.equalsIgnoreCase("aggressive")) {
                players.add(new AggressiveComputerPlayer(playerNum));
            } else if (typeOfPlayer.equalsIgnoreCase("benevolent")) {
                players.add(new BenevolentComputerPlayer(playerNum));
            } else if (typeOfPlayer.equalsIgnoreCase("cheater")) {
                players.add(new CheaterComputerPlayer(playerNum));
            } else if (typeOfPlayer.equalsIgnoreCase("random")) {
                players.add(new RandomComputerPlayer(playerNum));
            } else if (typeOfPlayer.equalsIgnoreCase("player")) {
                players.add(new Player(playerNum));
            }
        }
    }

    /**
     * The assignCountrytoPlayers method is used in the constructor.
     * Its main function is to assign every country to players at the very beginning of gamecomponents
     */
    public void randomAssignCountryToPlayers() {
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
            players.get(j).setGameMap(gameMap);
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
            PlayerStrategy player = players.get(k);
            LogHelper.printMessage("Player-" + player.getPlayerID() + " has");
            for (int m = 0; m < player.getCountryNum(); m++) {
                Territory territory = player.getCountry().get(m);
                LogHelper.printMessage(territory.getArmyNum() + " armies on territroy " + territory.getTerritoryName());
            }
            LogHelper.printMessage("--------------------------------------------------------------------------------");
        }
    }

    /**
     * The declairWin method is used when a player eliminate all other players and win the game
     *
     * @param attacker the player who wins the game
     */
    public void declareWin(PlayerStrategy attacker) {
        LogHelper.printMessage("the player" + attacker.getPlayerID() + " wins the game");
        LogHelper.printMessage("Game Finished");
        //System.exit(1);

    }

    /**
     * Method to set flag for starting a game phase
     *
     * @param beginStartUpPhase
     */
    public void setBeginStartUpPhase(boolean beginStartUpPhase) {
        this.beginStartUpPhase = beginStartUpPhase;
    }

    /**
     * Method to set number of players
     *
     * @param playerNum
     */
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * Method to get number of players
     *
     * @return
     */
    public int getPlayerNum() {
        return playerNum;
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

    /**
     * Method to save file
     */
    private void saveFile() {
        try {
            File saveFile = new File(filePath);
            listener.onGameSaved(saveFile);
        } catch (Exception exception) {
            LogHelper.printMessage("Game Error Message " + exception);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(playerNum);
        out.writeObject(players);
        out.writeObject(gameMap);
        out.writeBoolean(beginStartUpPhase);
        out.writeBoolean(isMapValid);
        out.writeInt(round);
        out.writeObject(listener);
        LogHelper.printMessage("Game Save Successful");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        playerNum = in.readInt();
        players = (ArrayList<PlayerStrategy>) in.readObject();
        gameMap = (GameMap) in.readObject();
        beginStartUpPhase = in.readBoolean();
        isMapValid = in.readBoolean();
        round = in.readInt();
        listener = (ModelListener) in.readObject();
        LogHelper.printMessage("Game Load Successful");
    }

    /**
     * Method to set listeners
     *
     * @param modelListener
     */
    public void setListeners(ModelListener modelListener) {
        this.listener = modelListener;
    }
}









