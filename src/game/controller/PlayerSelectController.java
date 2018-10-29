package game.controller;

import game.model.RiskModel;
import game.view.PlayerSelectView;
import game.view.RiskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelectController implements ActionListener {

    RiskModel riskModel;
    PlayerSelectView playerSelectView;

    public PlayerSelectController(RiskModel riskModel, RiskView riskView) {
        this.riskModel = riskModel;
        playerSelectView = new PlayerSelectView(this, riskView, true);
    }

    public void openPlayerSelectDialog() {
        playerSelectView.createSelectPlayerMenu();
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }
}
