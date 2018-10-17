package game.controller;

import game.model.Game;
import game.model.RiskModel;
import game.utils.MapFileHelper;

public class RiskController {

    private RiskModel riskModel;

    public RiskController() {
        //Empty RiskController Constructor needed for java fx application
    }

    public RiskController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    public void initializeRiskGame() {
        riskModel.loadGame();
    }

}
