package gui;

import javax.swing.*;
import java.awt.*;

public class LoadHelp extends JFrame {

    public LoadHelp(final String helpTxt) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowHelp(helpTxt);
            }
        });
    }

    private void createAndShowHelp(String text) {

        JTextArea textScreen = new JTextArea(20, 60);
        textScreen.setLineWrap(true);
        textScreen.setText(text);
        textScreen.setEditable(false);

        JScrollPane textScroll = new JScrollPane(textScreen, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setMinimumSize(new Dimension(50, 90));

        this.setPreferredSize(new Dimension(500, 600));
        this.add(textScroll);

        this.setTitle("Help");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}