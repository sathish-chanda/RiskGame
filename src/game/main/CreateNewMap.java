package game.main;

import game.controller.MapEditorController;
import game.model.RiskModel;
import game.utils.Constants;
import game.utils.LogHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateNewMap implements EventHandler<ActionEvent> {

    RiskModel riskModel;

    /**
     * Constructor of class {@link CreateNewMap}
     */
    public CreateNewMap() {
        riskModel = new RiskModel();
    }

    @Override
    public void handle(ActionEvent event) {
        LogHelper.printMessage("Opening create new map dialog");
        openMapCreatorLayout();
    }

    /**
     * Method to create map creator layout
     *
     * @throws Exception
     */
    private void createMapCreatorLayout() throws Exception {
        Stage mapEditorStage = new Stage();
        mapEditorStage.setTitle("Edit Map");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/map_viewer.fxml"));
        MapEditorController mapEditorController = new MapEditorController(riskModel);
        fxmlLoader.setController(mapEditorController);
        Parent root = fxmlLoader.load();
        mapEditorStage.setScene(new Scene(root, 950, 500));
        mapEditorStage.show();
    }

    /**
     * Method to open map creator layout
     */
    private void openMapCreatorLayout() {
        try {
            createMapCreatorLayout();
        } catch (Exception exception) {
            LogHelper.printMessage(MapEditor.class.getName() + Constants.SPACE + exception.getMessage());
        }
    }

}
