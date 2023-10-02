package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GUIView extends JFrame {

    JPanel mainPanel;
    HomeScreen main;
    ButtonScreen buttonPad;
    GUIMenuBar menus;
    JMenu FileMenu, ViewMenu, HelpMenu;
    boolean buttonPadVisible;

    public GUIView(String title) {

        this.setTitle(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(50, 0);
        this.setResizable(false);
        this.setSize(410, 350);

        FileMenu = new JMenu("File");
        ViewMenu = new JMenu("View");
        HelpMenu = new JMenu("Help");

        menus = new GUIMenuBar(FileMenu, ViewMenu, HelpMenu);
        mainPanel = new JPanel();
        this.setContentPane(mainPanel);
        GridLayout outerLayout = new GridLayout(1, 1);
        mainPanel.setLayout(outerLayout);

        this.setVisible(true);

        main = new HomeScreen(menus);
        mainPanel.add(main.homePanel);
        buttonPad = new ButtonScreen();
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    public void displayButtonScreen(int allow) {

        if (allow == 1) {
            mainPanel.add(buttonPad.buttonPanel);
            main.buttonPadDisplay.setText("Hide Button Pad");
            buttonPadVisible = true;
        }
        else {
            mainPanel.remove(buttonPad.buttonPanel);
            main.buttonPadDisplay.setText("Display Button Pad");
            buttonPadVisible = false;
        }
    }

    public void buttonActionListeners(ActionListener al) {

        main.textScreen.addKeyListener((KeyListener) al);
        main.graphDisplayButton.addActionListener(al);
        main.buttonPadDisplay.addActionListener(al);

        for (JButton b : buttonPad.button) {
            b.addActionListener(al);
        }

        for (int i = 0; i < 5; i++) {
            if (i != 3) {
                FileMenu.getItem(i).addActionListener(al);
            }

            if (i == 0) {
                ViewMenu.getItem(i).addActionListener(al);
            }
            if (i < 2) {
                HelpMenu.getItem(i).addActionListener(al);
            }
        }
    }
}