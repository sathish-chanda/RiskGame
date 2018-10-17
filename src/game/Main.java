package game;

import game.controller.RiskController;
import game.model.Game;
import game.model.RiskModel;
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
        /*Parent root = FXMLLoader.load(getClass().getResource("/game/main.fxml"));
        primaryStage.setTitle(Constants.RISK_GAME_NAME);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        initGameComponents();
    }

    private void initGameComponents() {
        RiskModel riskModel = new RiskModel();
        RiskController riskController = new RiskController(riskModel);
        riskController.initializeRiskGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
