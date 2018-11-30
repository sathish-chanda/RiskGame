package test.model;

import game.model.Game;
import game.model.GameMap;
import game.model.RiskModel;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestRiskModel {


    /**
     * Method to test load functionality of invalid file.
     */
    @Test
    public void testInvalidLoadFile() {
        RiskModel riskModel = new RiskModel();
        riskModel.loadFile(null, false);
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
        String path = "D:\\test save and load files\\test save\\TestSaveFile.ser";
        File file = new File(path);
        RiskModel riskModel = new RiskModel();
        riskModel.saveFile(file);
        assertTrue(riskModel.isSavedFileValid());
    }

    /**
     * Method to test load functionality of valid file
     */
    @Test
    public void testLoadValidFile(){
        String path = "D:\\test save and load files\\test load\\TestLoadValidFile.ser";
        File fileSave = new File(path);
        RiskModel riskModel = new RiskModel();
        riskModel.saveFile(fileSave);
        File fileLoad = new File(path);
        riskModel.loadFile(fileLoad, false);
        assertTrue(riskModel.isLoadFileValid());
    }

    /**
     * Method to test saved pla
     */
    @Test
    public void testSavedTotalPlayers() {
        String path = "D:\\test save and load files\\test load\\TestLoadTotalPlayersFile.ser";
        File fileSave = new File(path);
        RiskModel riskModel = new RiskModel();
        riskModel.getGame().setPlayerNum(3);
        riskModel.saveFile(fileSave);
        File fileLoad = new File(path);
        riskModel.loadFile(fileLoad, false);
        int totalPlayers = riskModel.getGame().getPlayerNum();
        assertEquals(3, totalPlayers);
    }

    /**
     * Method to test saved total number of players in risk game
     */
    @Test
    public void testTotalPlayersInGame() {
        String path = "D:\\test save and load files\\test load\\TestLoadTotalPlayersFile.ser";
        File fileSave = new File(path);
        RiskModel riskModel = new RiskModel();
        riskModel.getGame().setPlayerNum(3);
        riskModel.saveFile(fileSave);
        File fileLoad = new File(path);
        riskModel.loadFile(fileLoad, false);
        int totalPlayers = riskModel.getGame().getPlayerNum();
        assertEquals(3, totalPlayers);
    }

    /**
     * Method to test saved game map
     */
    @Test
    public void testGameMap() {
        String path = "D:\\test save and load files\\test load\\TestGameMap.ser";
        File fileSave = new File(path);
        RiskModel riskModel = new RiskModel();
        riskModel.saveFile(fileSave);
        File fileLoad = new File(path);
        riskModel.loadFile(fileLoad, false);
        GameMap gameMap = riskModel.getGame().getGameMap();
        assertNotEquals(null, gameMap);
    }

}
