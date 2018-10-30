package game.controller;

import game.model.RiskModel;
import game.utils.Constants;
import game.view.MapEditorView;
import game.view.RiskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditorController implements ActionListener {


    RiskModel riskModel;
    MapEditorView mapEditorView;


    /**
     * Constructor of MapEditorController class
     *
     * @param riskModel
     * @param riskView
     */
    public MapEditorController(RiskModel riskModel, RiskView riskView) {
        this.riskModel = riskModel;
        mapEditorView = new MapEditorView(this, riskView, true);
    }

    public void openMapEditorDialog() {
        mapEditorView.createMapEditorView();
    }


    @Override
    public void actionPerformed(ActionEvent event) {

        switch (event.getActionCommand()) {
            case Constants
                    .CONTINENTS_KEY:
                break;
        }

    }


}
