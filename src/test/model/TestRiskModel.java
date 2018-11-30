package test.model;

import game.model.Game;
import game.model.RiskModel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRiskModel {


    /**
     * Method to test load functionality of invalid file.
     */
    @Test
    public void testInvalidLoadFile() {
        RiskModel riskModel = new RiskModel();
        riskModel.loadFile(null);
        assertFalse(riskModel.isLoadFileValid());
    }

    /**
     * Method to test save functionality of invalid file.
     */
    @Test
    public void testSaveFileInvalid() {
        RiskModel riskModel = new RiskModel();
        riskModel.saveFile(null);
        assertFalse(riskModel.isSavedFileValid());
    }

    /**
     * Method to test save functionality of valid file.
     */
    @Test
    public void testSaveValidFile() {
        RiskModel riskModel = new RiskModel();
    }

}
