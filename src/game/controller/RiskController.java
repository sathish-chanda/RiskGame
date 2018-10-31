package game.controller;

import game.model.Game;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.RiskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This controller class maps the user's actions to both RiskView and RiskModel
 * to handle data
 */
public class RiskController implements ActionListener {

    private RiskModel riskModel;
    private RiskView riskView;

    /**
     * Constructor of RiskController class
     *
     * @param riskModel
     * @param riskView
     */
    public RiskController(RiskModel riskModel, RiskView riskView) {
        this.riskModel = riskModel;
        this.riskView = riskView;
        addRiskViewListeners();
    }

    /**
     * Method to add risk view listeners
     */
    private void addRiskViewListeners() {
        riskView.initActionListeners(this);
    }

    /**
     * Method to initialize Risk Game
     */
    public void initializeRiskGame() {
        openMainMenu();
    }


    /**
     *
     */
    private void openMainMenu() {
        riskView.createMainMenu();
    }

    /**
     * Method to show dialog of number of players
     */
    private void openSelectPlayersDialog() {
        PlayerSelectController playerSelectController = new PlayerSelectController(riskModel, riskView);
        playerSelectController.openPlayerSelectDialog();

    }

    /**
     * Method used to create map
     */
    public void createMap() {
        LogHelper.printMessage("Creating Map");
        riskModel.createMap();
    }

    /**
     * Method to load map
     */
    public void loadMap() {
        LogHelper.printMessage("loading map file");
        riskModel.loadGame(riskView);
    }

    /**
     * Method used to quit game
     */
    public void quitGame() {
        LogHelper.printMessage("Quitting Game");
        riskModel.quitGame();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case Constants.NEW_GAME_BUTTON_LABEL:
                openSelectPlayersDialog();
                break;
            case Constants.LOAD_GAME_BUTTON_LABEL:
                loadMap();
                break;
            case Constants.QUIT_GAME_BUTTON_LABEL:
                quitGame();
                break;
            case Constants.SELECT_PLAYER_NEXT_BUTTON_lABEL:

                break;
            case Constants.SELECT_PLAYER_BACK_BUTTON_lABEL:
                openMainMenu();
                break;
        }
    }


}
