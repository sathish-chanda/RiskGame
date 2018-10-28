package game.view;

import game.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RiskView extends JFrame {

    private ActionListener listener;
    private JPanel primaryPanel;
    private GridLayout primaryGridLayout;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton quitGameButton;


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
        primaryPanel = new JPanel();
        primaryGridLayout = new GridLayout(3, 1, 0, 0);
        primaryPanel.setLayout(primaryGridLayout);

        newGameButton = new JButton(Constants.NEW_GAME_BUTTON_LABEL);
        loadGameButton = new JButton(Constants.LOAD_GAME_BUTTON_LABEL);
        quitGameButton = new JButton(Constants.QUIT_GAME_BUTTON_LABEL);

        newGameButton.setActionCommand(Constants.NEW_GAME_BUTTON_LABEL);
        loadGameButton.setActionCommand(Constants.LOAD_GAME_BUTTON_LABEL);
        quitGameButton.setActionCommand(Constants.QUIT_GAME_BUTTON_LABEL);

        newGameButton.addActionListener(listener);
        loadGameButton.addActionListener(listener);
        quitGameButton.addActionListener(listener);

        primaryPanel.add(newGameButton);
        primaryPanel.add(loadGameButton);
        primaryPanel.add(quitGameButton);

        return primaryPanel;
    }

}
