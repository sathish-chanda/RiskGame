package game.controller;

import game.model.CardModel;
import game.utils.LogHelper;
import game.model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * This class creates the observable pattern of players card
 */
public class CardController {
    private CardModel cardModel;

    /**
     * constructor class where initializes the observer and observable pattern
     */
    public CardController() {
        cardModel = new CardModel();
    }

    /**
     * This method initiates the exchange card for the players
     *
     * @param player
     */
    public void exchangeCardsForArmies(Player player) {
        cardModel.exchangeCardForArmy(player);
    }
}
