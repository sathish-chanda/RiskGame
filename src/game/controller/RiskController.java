package game.controller;

import game.main.MapEditor;
import game.main.MapEditorOptions;
import game.model.Game;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import game.view.RiskView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    @FXML
    private Button mapEditorButton;
    @FXML
    private Button quitGameButton;

    /**
     * Constructor of class RiskController
     *
     * @param riskModel
     */
    public RiskController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtonActions();
    }

    /**
     * Method to initialize button  actions
     */
    private void initButtonActions() {
        newGameButton.setOnAction(null);
        mapEditorButton.setOnAction(openMapEditorOptionsDialog());
        quitGameButton.setOnAction(event -> quitGame());
    }

    /**
     * Method to open map editor options dialog
     */
    private MapEditorOptions openMapEditorOptionsDialog() {
        MapEditorOptions mapEditorOptions = new MapEditorOptions(riskModel);
        return mapEditorOptions;
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


}
