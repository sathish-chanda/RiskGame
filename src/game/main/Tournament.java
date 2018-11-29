package game.main;

import game.model.TournamentMode;
import game.utils.LogHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Scanner;

public class Tournament implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        Scanner scanner = new Scanner(System.in);
        LogHelper.printMessage("choose the value of M from 1 to 5");
        int M = scanner.nextInt();
        LogHelper.printMessage("choose the value of P from 2 to 4");
        int P = scanner.nextInt();
        LogHelper.printMessage("choose the value of G from 1 to 5");
        int G = scanner.nextInt();
        LogHelper.printMessage("choose the value of D from 10 to 50");
        int D = scanner.nextInt();
        TournamentMode tournament = new TournamentMode();
        tournament.startTournament(M, P, G, D);
    }
}
