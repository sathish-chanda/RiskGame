package game.main;

import game.controller.RiskController;
import game.model.RiskModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class Starts the game play
 * It initialise the game model and controller
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RiskModel riskModel = new RiskModel();
        riskModel.setMessage("main class");
        RiskController riskController = new RiskController(riskModel);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/main_menu.fxml"));
        fxmlLoader.setController(riskController);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
