package game.controller;

import game.model.Game;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.RiskView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This controller class maps the user's actions to both RiskView and RiskModel
 * to handle data
 */
public class RiskController implements Initializable {

    private RiskModel riskModel;
    private RiskView riskView;

    @FXML
    private Button newGameButton;

    /**
     * Constructor of class RiskController
     */
    public RiskController() {
        //Empty constructor required
    }

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
        //riskView.initActionListeners(this);
    }

    /**
     * Method to initialize Risk Game
     */
    public void initializeRiskGame() {

    }


    /**
     *
     */
    @FXML
    private void openMainMenu() {
        System.out.println("open main menu "+riskModel.getMessage());
    }

    /**
     * Method to show dialog of number of players
     */
    private void openSelectPlayersDialog() {
        PlayerSelectController playerSelectController = new PlayerSelectController(riskModel, riskView);
        playerSelectController.openPlayerSelectDialog();

    }

    /**
     * Method to load map
     */
    public void loadMap() {
        LogHelper.printMessage("loading map file");
        riskModel.loadGame(riskView);
    }

    /**
     * Method to open Map Editor page
     */
    private void openMapEditorDialog() {
        LogHelper.printMessage("opening map file editor");
        MapEditorController mapEditorController = new MapEditorController(riskModel, riskView);
        mapEditorController.openMapEditorDialog();

    }

    /**
     * Method used to quit game
     */
    public void quitGame() {
        LogHelper.printMessage("Quitting Game");
        riskModel.quitGame();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        riskModel = new RiskModel();
        riskModel.setMessage("message is set");
    }

}
