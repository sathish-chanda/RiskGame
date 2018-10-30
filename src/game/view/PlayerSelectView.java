package game.view;

import game.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerSelectView extends JDialog {

    private ActionListener listener;

    private JPanel panel;
    private JButton selectPlayerNextButton;
    private JButton selectPlayerBackButton;

    private JComboBox playerSelectComboBox;
    private GridLayout layout;

    /**
     * Constructor of class PlayerSelectView
     *
     * @param listener
     * @param riskView
     */
    public PlayerSelectView(ActionListener listener, RiskView riskView, boolean modal) {
        super(riskView, modal);
        this.listener = listener;
    }

    /**
     * Method to create select player menu
     */
    public void createSelectPlayerMenu() {
        setTitle("Select total number of players");
        setPreferredSize(new Dimension(300, 150));
        add(selectPlayerElements());
        pack();
        setVisible(true);
        toFront();
    }


    /**
     * Method to create select player elements
     */
    private JPanel selectPlayerElements() {
        panel = new JPanel();
        layout = new GridLayout(4, 1, 5, 5);
        panel.setLayout(layout);

        selectPlayerNextButton = new JButton("Next");
        selectPlayerBackButton = new JButton("Back");

        selectPlayerNextButton.setActionCommand(Constants.SELECT_PLAYER_NEXT_BUTTON_lABEL);
        selectPlayerBackButton.setActionCommand(Constants.SELECT_PLAYER_BACK_BUTTON_lABEL);

        selectPlayerNextButton.addActionListener(listener);
        selectPlayerBackButton.addActionListener(listener);

        String playerList[] = {"2", "3", "4", "5", "6"};
        playerSelectComboBox = new JComboBox(playerList);
        JLabel label = new JLabel("Please select number of players");

        panel.add(label);
        panel.add(playerSelectComboBox);
        panel.add(selectPlayerNextButton);
        panel.add(selectPlayerBackButton);

        return panel;
    }

}
