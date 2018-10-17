package game;

import game.controller.RiskController;
import game.utils.Constants;
import game.utils.MapFileHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        initGameComponents();
    }

    private void initGameComponents() {
        MapFileHelper mapFileHelper = MapFileHelper.getInstance();
        RiskController riskController = new RiskController(mapFileHelper);
        riskController.initializeRiskGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
