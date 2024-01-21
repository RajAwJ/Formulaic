package gui;

import javax.swing.*;
import java.awt.*;

public class ViewHistory extends JFrame {

    public ViewHistory(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowHistory(text);
            }
        });
    }

    private void createAndShowHistory(String text) {

        JTextArea textScreen = new JTextArea(20, 60);
        textScreen.setLineWrap(true);
        textScreen.setText(text);
        textScreen.setEditable(false);

        JScrollPane textScroll = new JScrollPane(textScreen, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setMinimumSize(new Dimension(50, 90));

        this.setPreferredSize(new Dimension(300, 300));
        this.add(textScroll);

        this.setTitle("History");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}