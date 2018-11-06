package game.controller;

import game.model.CardModel;
import game.view.CardView;
import game.model.Player;

import java.util.Observer;

public class CardController extends Object {
    private CardModel cardModel;
    private CardView cardView;

    public CardController() {
        cardModel = new CardModel();
        cardView = new CardView();
        cardModel.addObserver(cardView);
    }

    public void exchangeCardsForArmies(Player player) {
        cardModel.exchangeCardForArmy(player);
    }
}
