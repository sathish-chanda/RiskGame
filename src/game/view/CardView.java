package game.view;

import game.model.Card;
import game.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;

public class CardView implements Observer {
    public void update (Observable obs, Object x) {
        //int cardNum = ((Card) obs).getCardNum();
        //LogHelper.printMessage("now you have " + cardNum + "cards");
    }
}
