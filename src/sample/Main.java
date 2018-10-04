package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public List<String> mapDataList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        validateMapData();
    }

    /**
     * This method is used to validate map for its correctness
     */
    private void validateMapData() {
        try {
            FileReader fileReader = new FileReader(Constants.FILE_DOMAIN_PATH + Constants.MAP_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                mapDataList.add(line);
            }
        } catch (IOException e) {
            // TODO show dialog if file not found
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
