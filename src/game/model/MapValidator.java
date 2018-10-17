package game.model;

public class MapValidator {

    private boolean isMapKeyValid;
    private boolean isContinentKeyValid;
    private boolean isTerrtitoryKeyValid;
    private String currentKey;

    public boolean isMapKeyValid() {
        return isMapKeyValid;
    }

    public void setMapKeyValid(boolean mapKeyValid) {
        isMapKeyValid = mapKeyValid;
    }

    public boolean isContinentKeyValid() {
        return isContinentKeyValid;
    }

    public void setContinentKeyValid(boolean continentKeyValid) {
        isContinentKeyValid = continentKeyValid;
    }

    public boolean isTerrtitoryKeyValid() {
        return isTerrtitoryKeyValid;
    }

    public void setTerrtitoryKeyValid(boolean terrtitoryKeyValid) {
        isTerrtitoryKeyValid = terrtitoryKeyValid;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }
}
