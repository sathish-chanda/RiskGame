package game.model;

import game.utils.LogHelper;

import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

public class CardModel extends Observable {

    private int totalCardNum = 4;
    private int infantryCard = 4;
    private int cavalryCard = 0;
    private int artilleryCard = 0;
    private static int cardUsedTimes = 1;
    private String action;

    public int getTotalCardNum() {
        return totalCardNum;
    }

    public int getInfantryCardNum() {
        return infantryCard;
    }

    public int getCavalryCardNum() {
        return cavalryCard;
    }

    public int getArtilleryCardNum() {
        return artilleryCard;
    }


    /**
     * The receiveCard method give the player one card if he concour a territory
     */
    public void increaseCard() {
        totalCardNum++;
        Random random = new Random();
        int card = random.nextInt(2);
        switch (card) {
            case 0:
                infantryCard++;
                break;
            case 1:
                cavalryCard++;
                break;
            case 2:
                artilleryCard++;
                break;
        }
    }

    public void receiveCard(CardModel defenderCard) {
        infantryCard = defenderCard.getInfantryCardNum() + infantryCard;
        cavalryCard = defenderCard.cavalryCard + cavalryCard;
        artilleryCard = defenderCard.artilleryCard + artilleryCard;
    }

    public void exchangeCardForArmy(PlayerStrategy player) {
        if (player != null) {
            totalCardNum = player.getCard().getTotalCardNum();
            infantryCard = player.getCard().getInfantryCardNum();
            cavalryCard = player.getCard().getCavalryCardNum();
            artilleryCard = player.getCard().getArtilleryCardNum();
        }

        Scanner scanner = new Scanner(System.in);
        if (totalCardNum >= 5) {
            LogHelper.printMessage("you have 5 or more than 5 cards, you must exchange some of them for armies");
            if (infantryCard >= 3)
                LogHelper.printMessage("you have " + infantryCard + " infantry cards, you can exchange 3 of them for armies");
            else if (cavalryCard >= 3)
                LogHelper.printMessage("you have " + cavalryCard + " cavalry cards, you can exchange 3 of them for armies");
            else if (artilleryCard >= 3)
                LogHelper.printMessage("you have " + artilleryCard + " artillery cards, you can exchange 3 of them for armies");
            else
                LogHelper.printMessage("you have " + infantryCard + " infantry cards, " + cavalryCard + " cavalry cards, " + artilleryCard + " artillery cards, you can exchange 3 cards of each kind for army");
            LogHelper.printMessage("type infantry, cavalry, artillery or each to choose the cards you want to exchange for army:");
            action = "card is being exchanged";
            String choice = scanner.nextLine();
            switch (choice) {
                case "infantry":
                    infantryCard = infantryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                    break;
                case "cavalry":
                    cavalryCard = cavalryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                    break;
                case "artillery":
                    artilleryCard = artilleryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                    break;
                case "each":
                    infantryCard = infantryCard - 1;
                    cavalryCard = cavalryCard - 1;
                    artilleryCard = artilleryCard - 1;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                    break;
            }
            cardUsedTimes++;
            setChanged();
            notifyObservers(this);
            return;
        } else {
            LogHelper.printMessage("you have " + infantryCard + " infantry cards, " + cavalryCard + " cavalry cards, " + artilleryCard + " artillery cards");
            LogHelper.printMessage("do you want to exchange cards for armies? input y for yes, n for no");
            String input = scanner.nextLine();
            while (!input.matches("y") && !input.matches("n")) {
                LogHelper.printMessage("wrong input! please input again.");
                input = scanner.nextLine();
            }
            if (input.matches("n")) {
                LogHelper.printMessage("exit card exchange");
                return;
            } else if (input.matches("y") && infantryCard < 3 && cavalryCard < 3 && artilleryCard < 3 && (infantryCard == 0 || cavalryCard == 0 || artilleryCard == 0)) {
                LogHelper.printMessage("you don't have enough cards to exchange for armies");
                return;
            } else {
                LogHelper.printMessage("you have " + infantryCard + " infantry cards, " + cavalryCard + " cavalry cards, " + artilleryCard + " artillery cards");
                if (infantryCard >= 3) {
                    LogHelper.printMessage("you have more than 3 of infantry cards, you exchange 3 of them for armies");
                    infantryCard = infantryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                } else if (cavalryCard >= 3) {
                    LogHelper.printMessage("you have more than 3 of cavalry cards, you exchange 3 of them for armies");
                    cavalryCard = cavalryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                } else if (artilleryCard >= 3) {
                    LogHelper.printMessage("you have more than 3 of artillery cards, you exchange 3 of them for armies");
                    artilleryCard = artilleryCard - 3;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);
                } else if ((infantryCard >= 1) && (cavalryCard >= 1) && (artilleryCard >= 1)) {
                    LogHelper.printMessage("you have one of each cards, you can exchange them for armies");
                    infantryCard = infantryCard - 1;
                    cavalryCard = cavalryCard - 1;
                    artilleryCard = artilleryCard - 1;
                    player.updateReinforcementArmyNum(cardUsedTimes * 5);


                }
                cardUsedTimes++;
                setChanged();
                notifyObservers(this);
                return;
            }
        }
    }

    /**
     * Method to get current action
     *
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * Method to set current action
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }
}
