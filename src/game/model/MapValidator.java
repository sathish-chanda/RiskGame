package game.model;

/**
 * This class performs Map validation
 */
public class MapValidator {

    private boolean isMapKeyValid;
    private boolean isContinentKeyValid;
    private boolean isTerrtitoryKeyValid;
    private String currentKey;
    private boolean fileSaveSuccess;

    /**
     * check key string in the map for validity
     *
     * @return boolean
     */
    public boolean isMapKeyValid() {
        return isMapKeyValid;
    }

    /**
     * setting map validity key to validate map file
     *
     * @param mapKeyValid
     */
    public void setMapKeyValid(boolean mapKeyValid) {
        isMapKeyValid = mapKeyValid;
    }

    /**
     * this method is used to check continent validity in map
     *
     * @return boolean
     */
    public boolean isContinentKeyValid() {
        return isContinentKeyValid;
    }

    /**
     * set continent validity key
     *
     * @param continentKeyValid
     */
    public void setContinentKeyValid(boolean continentKeyValid) {
        isContinentKeyValid = continentKeyValid;
    }

    /**
     * This method is used to check territory validity in map
     *
     * @return boolean
     */
    public boolean isTerrtitoryKeyValid() {
        return isTerrtitoryKeyValid;
    }

    /**
     * set territory validity key
     *
     * @param terrtitoryKeyValid
     */
    public void setTerrtitoryKeyValid(boolean terrtitoryKeyValid) {
        isTerrtitoryKeyValid = terrtitoryKeyValid;
    }

    /**
     * getting current key status
     *
     * @return current key
     */
    public String getCurrentKey() {
        return currentKey;
    }

    /**
     * set current key
     *
     * @param currentKey
     */
    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }

    /**
     * check whether file saved successfully
     *
     * @return boolean
     */
    public boolean isFileSaveSuccess() {
        return fileSaveSuccess;
    }

    /**
     * set file saved status
     *
     * @param fileSaveSuccess
     */
    public void setFileSaveSuccess(boolean fileSaveSuccess) {
        this.fileSaveSuccess = fileSaveSuccess;
    }
}
