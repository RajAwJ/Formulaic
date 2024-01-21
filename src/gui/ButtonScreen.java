package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonScreen {

    JPanel buttonPanel;
    GridBagLayout buttonLayout;
    GridBagConstraints buttonConstraints;
    JButton[] button;

    public ButtonScreen() {
        buttonPanel = new JPanel();

        buttonLayout = new GridBagLayout();
        buttonConstraints = new GridBagConstraints();

        buttonPanel.setLayout(buttonLayout);
        button = new JButton[24];
        createNumberButtons();
    }

    private void createNumberButtons() {

        for (int num = 0; num < 24; num++) {

            button[num] = new JButton();

            buttonConstraints.weightx = 0.5; buttonConstraints.weighty = 0.5;
            buttonConstraints.fill = GridBagConstraints.BOTH;
            buttonConstraints.anchor = GridBagConstraints.CENTER;

            switch (num) {

                case (0):
                    button[num].setText("" + num);
                    button[num].setActionCommand("0");

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 5;
                    buttonConstraints.gridwidth = 2; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 40, 3, 3);
                    break;

                case (1):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 4;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 40, 3, 3);
                    break;

                case (2):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 3; buttonConstraints.gridy = 4;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (3):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 4;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (4):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 3;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 40, 3, 3);
                    break;

                case (5):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 3; buttonConstraints.gridy = 3;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (6):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 3;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (7):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 2;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(25, 40, 3, 3);
                    break;

                case (8):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 3; buttonConstraints.gridy = 2;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(25, 3, 3, 3);
                    break;

                case (9):
                    button[num].setText("" + num);
                    button[num].setActionCommand("" + num);

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 2;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(25, 3, 3, 3);
                    break;

                case (10):
                    button[num].setText(".");
                    button[num].setActionCommand(".");

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 5;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (11):
                    button[num].setText("(");
                    button[num].setActionCommand("(");

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 6;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 40, 25, 3);
                    break;

                case (12):
                    button[num].setText(")");
                    button[num].setActionCommand(")");

                    buttonConstraints.gridx = 3; buttonConstraints.gridy = 6;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 25, 3);
                    break;

                case (13):
                    button[num].setText("/");
                    button[num].setActionCommand("/");

                    buttonConstraints.gridx = 5; buttonConstraints.gridy = 2;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(25, 3, 3, 3);
                    break;

                case (14):
                    button[num].setText("*");
                    button[num].setActionCommand("*");

                    buttonConstraints.gridx = 5; buttonConstraints.gridy = 3;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (15):
                    button[num].setText("-");
                    button[num].setActionCommand("-");

                    buttonConstraints.gridx = 5; buttonConstraints.gridy = 4;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (16):
                    button[num].setText("+");
                    button[num].setActionCommand("+");

                    buttonConstraints.gridx = 5; buttonConstraints.gridy = 5;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 3, 3);
                    break;

                case (17):
                    button[num].setText("^");
                    button[num].setActionCommand("^");

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 6;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 25, 3);
                    break;

                case (18):
                    button[num].setText("sin");
                    button[num].setActionCommand("sin");

                    buttonConstraints.gridx = 6; buttonConstraints.gridy = 2;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 2;
                    buttonConstraints.insets = new Insets(25, 3, 3, 40);
                    break;

                case (19):
                    button[num].setText("cos");
                    button[num].setActionCommand("cos");

                    buttonConstraints.gridx = 6; buttonConstraints.gridy = 4;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 2;
                    buttonConstraints.insets = new Insets(3, 3, 3, 40);
                    break;

                case (20):
                    button[num].setText("=");
                    button[num].setActionCommand("=");

                    buttonConstraints.gridx = 5; buttonConstraints.gridy = 6;
                    buttonConstraints.gridwidth = 2; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(3, 3, 25, 40);
                    break;

                case (21):
                    button[num].setText("f'");
                    button[num].setActionCommand("f'");

                    buttonConstraints.gridx = 2; buttonConstraints.gridy = 1;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(15, 40, 3, 3);
                    break;

                case (22):
                    button[num].setText("f''");
                    button[num].setActionCommand("f''");

                    buttonConstraints.gridx = 3; buttonConstraints.gridy = 1;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(15, 3, 3, 3);
                    break;

                case (23):
                    button[num].setText("Clear");
                    button[num].setActionCommand("Clear");

                    buttonConstraints.gridx = 4; buttonConstraints.gridy = 1;
                    buttonConstraints.gridwidth = 1; buttonConstraints.gridheight = 1;
                    buttonConstraints.insets = new Insets(15, 3, 3, 3);
            }
            buttonPanel.add(button[num], buttonConstraints);
        }
    }
}
