package game.controller;

import game.model.CardModel;
import game.model.PlayerStrategy;
import game.utils.LogHelper;
import game.model.Player;
import game.view.CardView;

import java.util.Observable;
import java.util.Observer;

/**
 * This class creates the observable pattern of players card
 */
public class CardController extends Object {
    private CardModel cardModel;
    private CardView cardView;

    /**
     * constructor class where initializes the observer and observable pattern
     */
    public CardController() {
        cardModel = new CardModel();
        cardView = new CardView();
        cardModel.addObserver(cardView);
    }

    /**
     * This method initiates the exchange card for the players
     *
     * @param player
     */
    public void exchangeCardsForArmies(PlayerStrategy player) {
        cardModel.exchangeCardForArmy(player);
    }
}
