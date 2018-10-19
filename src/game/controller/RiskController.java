package game.controller;

import game.model.Game;
import game.model.RiskModel;
import game.utils.MapFileHelper;

/**
 * This controller class maps the user's actions to both RiskView and RiskModel
 * to handle data
 */
public class RiskController {

    private RiskModel riskModel;

    /**
     * Constructor of RiskController Class
     */
    public RiskController() {
        //Empty RiskController Constructor needed for java fx application
    }

    /**
     * Constructor of RiskController class
     *
     * @param riskModel
     */
    public RiskController(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * Constructor of RiskController Class
     */
    public void initializeRiskGame() {
        riskModel.loadGame();
    }

}
