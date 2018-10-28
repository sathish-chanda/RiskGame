package game.view;

import game.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RiskView extends JFrame {

    private ActionListener listener;

    private JPanel panel;
    private GridLayout layout;

    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton quitGameButton;

    private JButton selectPlayerNextButton;
    private JButton selectPlayerBackButton;

    private JComboBox playerSelectComboBox;

    /**
     * Constructor of RiskView class
     */
    public RiskView() {
        //Empty constructor required
    }

    /**
     * Method to initialize listeners
     */
    public void initActionListeners(ActionListener listener) {
        this.listener = listener;
    }

    /**
     * Method to create main menu
     */
    public void createMainMenu() {
        if (panel != null) {
            remove(panel);
        }
        setTitle("Main Menu");
        setPreferredSize(new Dimension(300, 150));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainMenuElements());
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * Method to create main menu elements
     *
     * @return
     */
    private JPanel mainMenuElements() {
        panel = new JPanel();
        layout = new GridLayout(3, 1, 5, 5);
        panel.setLayout(layout);

        newGameButton = new JButton(Constants.NEW_GAME_BUTTON_LABEL);
        loadGameButton = new JButton(Constants.LOAD_GAME_BUTTON_LABEL);
        quitGameButton = new JButton(Constants.QUIT_GAME_BUTTON_LABEL);

        newGameButton.setActionCommand(Constants.NEW_GAME_BUTTON_LABEL);
        loadGameButton.setActionCommand(Constants.LOAD_GAME_BUTTON_LABEL);
        quitGameButton.setActionCommand(Constants.QUIT_GAME_BUTTON_LABEL);

        newGameButton.addActionListener(listener);
        loadGameButton.addActionListener(listener);
        quitGameButton.addActionListener(listener);

        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(quitGameButton);

        return panel;
    }


    /**
     * Method to create select player menu
     */
    public void createSelectPlayerMenu() {
        remove(panel);
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
