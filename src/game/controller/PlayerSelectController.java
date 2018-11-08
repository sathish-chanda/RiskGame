package game.controller;

import game.listeners.ControllerListener;
import game.model.Player;
import game.model.RiskModel;
import game.utils.LogHelper;
import game.utils.MapFileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Class implements Java FX for select the number of players
 */
public class PlayerSelectController implements Initializable {

    private ControllerListener controllerListener;

    @FXML
    Button selectPlayerButton;
    @FXML
    ComboBox selectPlayerComboBox;

    /**
     * Constructor used to listen player selection layout
     *
     * @param controllerListener
     */
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
        selectPlayerComboBox.getSelectionModel().select(0);
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
     * @return player count
     */
    private int getTotalNumberOfPlayers() {
        try {
            int totalPlayers = (int) selectPlayerComboBox.getSelectionModel().getSelectedItem();
            return totalPlayers;
        } catch (Exception exception) {
            return 0;
        }
    }

}
