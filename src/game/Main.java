package game;

import game.controller.RiskController;
import game.model.Game;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.MapFileHelper;
import game.view.RiskView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class Starts the game play
 * It initialise the game model and controller
 */
public class Main {

    /**
     * Method initialize game components
     *
     */
    public static void  initGameComponents() {
        RiskModel riskModel = new RiskModel();
        RiskView riskView = new RiskView();
        RiskController riskController = new RiskController(riskModel, riskView);
        riskController.initializeRiskGame();
    }

    public static void main(String[] args) {
        initGameComponents();
    }

}
