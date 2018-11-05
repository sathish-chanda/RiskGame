package game.controller;

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

    private RiskModel riskModel;

    @FXML
    Button selectPlayerButton;
    @FXML
    ComboBox selectPlayerComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*riskModel = new RiskModel();
        clearData();*/
        loadSelectPlayerComboBox();
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
     * Populate load map data.
     */
    private void loadMapData() {
        File file = MapFileHelper.getFileFromFileChooser();
        if (file != null) {
            riskModel.loadMapData(file.getPath());
        } else {
            LogHelper.printMessage("Unable to read file or Invalid file");
        }
    }

    /**
     * Method to clear data
     */
    private void clearData() {
        riskModel.getGame().getGameMap().getMapFileHelper().cleanUp();
    }
}
