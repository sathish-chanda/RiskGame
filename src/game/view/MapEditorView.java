package game.view;

import game.controller.MapEditorController;
import game.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapEditorView extends JDialog {


    private ActionListener listener;

    private JPanel panel;
    private JPanel continentPanel;

    /**
     * author=Sean O'Connor
     * image=world.bmp
     * wrap=yes
     * scroll=horizontal
     * warn=yes
     */

    private GridBagLayout mainGridBagLayout;
    private GridLayout layout;
    private JTextField authorTextField;
    private JTextField imageTextField;
    private JTextField wrapTextField;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JLabel authorLabel;

    public MapEditorView(MapEditorController mapEditorController, RiskView riskView, boolean modal) {
        super(riskView, modal);

    }

    /**
     * Method to create select player menu
     */
    public void createMapEditorView() {
        setTitle("Select total number of players");
        setPreferredSize(new Dimension(300, 150));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;



        add(authorPanel());
        //add(continentPanel());
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * Method to create select player elements
     */
    private JPanel authorPanel() {
        panel = new JPanel();
        layout = new GridLayout(5, 2, 5, 5);
        panel.setLayout(layout);

        JLabel authorLabel = new JLabel("Author");
        JTextField authorTextField = new JTextField();

        JLabel imageLabel = new JLabel("image");
        JTextField imageTextField = new JTextField();


        JLabel wrapLabel = new JLabel("wrap");
        JTextField wrapTextField = new JTextField();

        JLabel scrollLabel = new JLabel("scroll");
        JTextField scrollTextField = new JTextField();

        JLabel warnLabel = new JLabel("warn");
        JTextField warnTextField = new JTextField();

        panel.add(authorLabel);
        panel.add(authorTextField);
        panel.add(imageLabel);
        panel.add(imageTextField);
        panel.add(wrapLabel);
        panel.add(wrapTextField);
        panel.add(scrollLabel);
        panel.add(scrollTextField);
        panel.add(warnLabel);
        panel.add(warnTextField);

        return panel;
    }

    private JPanel continentPanel() {

        panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);




        return continentPanel;
    }

    private JPanel selectPlayerElements() {
        continentPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(4, 1, 5, 5);
        continentPanel.setLayout(layout);

        /*selectPlayerNextButton = new JButton("Next");
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
        panel.add(selectPlayerBackButton);*/

        return panel;
    }

}
