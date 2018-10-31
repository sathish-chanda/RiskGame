package game.controller;

import game.model.RiskModel;
import game.utils.Constants;
import game.view.MapEditorView;
import game.view.RiskView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class MapEditorController implements EventHandler<ActionEvent> {

    private RiskModel riskModel;

    public MapEditorController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    @Override
    public void handle(ActionEvent event) {

    }

}
