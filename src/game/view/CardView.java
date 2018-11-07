package game.view;

import game.model.CardModel;
import game.utils.LogHelper;

import java.util.Observable;
import java.util.Observer;

/**
 * Class implements the observer pattern of card view
 */
public class CardView implements Observer {
    public void update (Observable obs, Object x) {
        int totalCardNum = ((CardModel) obs).getTotalCardNum();
        int infantryCard = ((CardModel) obs).getInfantryCardNum();
        int cavalryCard = ((CardModel) obs).getCavalryCardNum();
        int artilleryCard = ((CardModel) obs).getArtilleryCardNum();
        LogHelper.printMessage("now you have " + infantryCard + " infantry cards"+ cavalryCard + " cavalry cards"+ artilleryCard + " artillery cards");
    }
}
