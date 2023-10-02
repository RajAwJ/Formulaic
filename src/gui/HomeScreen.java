package gui;

import javax.swing.*;
import java.awt.*;

public class HomeScreen {

    JPanel homePanel;
    JTextArea textScreen;
    JScrollPane textScroll;
    GridBagLayout setContainer;
    GridBagConstraints sortComponents;
    JButton graphDisplayButton;
    JLabel messageDisplay;
    JButton buttonPadDisplay;
    JMenuBar menuBar;

    public HomeScreen(JMenuBar menu) {
        homePanel = new JPanel();
        menuBar = menu;
        display();
    }

    public void display() {

        setContainer = new GridBagLayout();
        sortComponents = new GridBagConstraints();

        homePanel.setLayout(setContainer);

        textScreen = new JTextArea(20, 60);
        textScreen.setLineWrap(true);

        textScroll = new JScrollPane(textScreen, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setMinimumSize(new Dimension(50, 90));

        sortComponents.gridx = 1; sortComponents.gridy = 0;
        sortComponents.weightx = 1.0; sortComponents.weighty = 0.5;
        sortComponents.anchor = GridBagConstraints.NORTH;
        sortComponents.fill = GridBagConstraints.BOTH;
        sortComponents.gridwidth = 3; sortComponents.gridheight = 1;
        sortComponents.insets = new Insets(1, 1, 0, 1);
        homePanel.add(menuBar, sortComponents);

        sortComponents.gridx = 2; sortComponents.gridy = 2;
        sortComponents.gridwidth = 1; sortComponents.gridheight = 4;
        sortComponents.fill = GridBagConstraints.BOTH;
        sortComponents.weightx = 0.5; sortComponents.weighty = 1.0;
        sortComponents.anchor = GridBagConstraints.EAST;
        sortComponents.insets = new Insets(25, 40, 0, 40);
        homePanel.add(textScroll, sortComponents);

        graphDisplayButton = new JButton("Display Graph");
        graphDisplayButton.setActionCommand("GD");
        sortComponents.gridx = 2; sortComponents.gridy = 7;
        sortComponents.gridheight = 1;
        sortComponents.fill = GridBagConstraints.NONE;
        sortComponents.weighty = 0.5;
        sortComponents.insets = new Insets(0, 40, 0, 40);
        sortComponents.anchor = GridBagConstraints.WEST;
        homePanel.add(graphDisplayButton, sortComponents);

        messageDisplay = new JLabel();
        messageDisplay.setText("Ready");
        messageDisplay.setBorder(BorderFactory.createLoweredBevelBorder());
        messageDisplay.setVerticalAlignment(SwingConstants.BOTTOM);
        sortComponents.gridy = 6;
        sortComponents.fill = GridBagConstraints.HORIZONTAL;
        sortComponents.anchor = GridBagConstraints.WEST;
        homePanel.add(messageDisplay, sortComponents);

        buttonPadDisplay = new JButton("Display Button Pad");
        buttonPadDisplay.setActionCommand("BS");
        sortComponents.gridy = 7;
        sortComponents.fill = GridBagConstraints.NONE;
        sortComponents.anchor = GridBagConstraints.EAST;
        homePanel.add(buttonPadDisplay, sortComponents);
    }
}