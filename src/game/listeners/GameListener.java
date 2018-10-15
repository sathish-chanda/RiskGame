package game.listeners;

public interface GameListener {

    void onMapLoadSuccess();

    void onMapLoadFailure(String message);

}
