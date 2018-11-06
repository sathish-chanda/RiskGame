package game.controller;

import game.listeners.ControllerListener;
import game.model.RiskModel;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerSelectController implements Initializable {

    private ControllerListener controllerListener;

    @FXML
    Button selectPlayerButton;
    @FXML
    ComboBox selectPlayerComboBox;

    public PlayerSelectController(ControllerListener controllerListener) {
        this.controllerListener = controllerListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSelectPlayerComboBox();
        initListeners();
    }

    /**
     * Method to load select player combo box
     */
    private void loadSelectPlayerComboBox() {
        selectPlayerComboBox.getItems().clear();
        for (int i = 2; i <= 6; i++) {
            selectPlayerComboBox.getItems().add(i);
        }
    }

    /**
     * Method to initialize UI component listeners
     */
    private void initListeners() {
        selectPlayerButton.setOnAction(event -> controllerListener.onPlayerSelected(getTotalNumberOfPlayers()));
    }

    /**
     * Method to get total number of players from select player combo box
     *
     * @return
     */
    private int getTotalNumberOfPlayers() {
        return (int) selectPlayerComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * Populate load map data.
     */
    /*private void loadMapData() {
        File file = MapFileHelper.getFileFromFileChooser();
        if (file != null) {
            riskModel.loadMapData(file.getPath());
        } else {
            LogHelper.printMessage("Unable to read file or Invalid file");
        }
    }*/

    /**
     * Method to clear data
     */
    /*private void clearData() {
        riskModel.getGame().getGameMap().getMapFileHelper().cleanUp();
    }*/
}
