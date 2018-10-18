package game.listeners;

public interface GameListener {

    void onMapLoadSuccess();

    void onMapLoadFailure(String message);

    void onTerritoryMapValid();

    void onTerritoryMapInvalid(String message);

    void onContinentMapValid();

    void onContinentMapInvalid(String message);

    void onUserMapSaveSuccess();

}
