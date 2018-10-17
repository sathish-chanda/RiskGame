package game.controller;

import game.utils.MapFileHelper;

public class RiskController {

    private MapFileHelper mapFileHelper;

    public RiskController() {
        //Empty RiskController Constructor needed for java fx application
    }

    public RiskController(MapFileHelper mapFileHelper) {
        this.mapFileHelper = mapFileHelper;
    }

    public void initializeRiskGame() {
        mapFileHelper.loadMapFile();
    }

}
