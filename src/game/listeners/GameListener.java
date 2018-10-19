package game.listeners;

/**
 * Listen the various method in the game
 * interface between various classes
 */
public interface GameListener {

    /**
     * Listen the mapValidity
     */
    void onMapLoadSuccess();

    /**
     * Listen when the loading map is invalid
     * @param message, error message for failure
     */
    void onMapLoadFailure(String message);

    /**
     * Listen when territory list is valid in map file
     */
    void onTerritoryMapValid();

    /**
     * Listen when territory data in invalid
     * @param message, error message for failure
     */
    void onTerritoryMapInvalid(String message);

    /**
     * Listen when continent list is valid in map file
     */
    void onContinentMapValid();

    /**
     * triggers when continent data is invalid
     * @param message, error message for failure
     */
    void onContinentMapInvalid(String message);

    /**
     * Listen when map is saved successfully
     */
    void onUserMapSaveSuccess();

}
