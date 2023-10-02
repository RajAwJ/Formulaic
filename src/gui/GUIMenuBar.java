package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUIMenuBar extends JMenuBar {

    String FileMenuItems[] = new String[] {"New", "Save", "Load", "" , "Exit"};
    String ViewMenuItems[] = new String[] {"History"};
    String HelpMenuItems[] = new String[] {"Help Topics", "About Calculator"};

    JMenu FileM;
    JMenu ViewM;
    JMenu HelpM;

    public GUIMenuBar(JMenu FileMenu, JMenu ViewMenu, JMenu HelpMenu) {

        ViewM = ViewMenu;
        FileM = FileMenu;
        HelpM = HelpMenu;

        for (int i = 0; i < FileMenuItems.length; i++) {
            if (i != 3) {
                JMenuItem item = new JMenuItem(FileMenuItems[i]);
                item.setActionCommand("File" + i);
                FileM.add(item);
            }
        }
        FileM.insertSeparator(3);

        for (int i = 0; i < HelpMenuItems.length; i++) {
            JMenuItem item = new JMenuItem(HelpMenuItems[i]);
            item.setActionCommand("Help" + i);
            HelpM.add(item);
        }

        JMenuItem item = new JMenuItem(ViewMenuItems[0]);
        item.setActionCommand("View0");
        ViewM.add(item);

        add(FileM);
        add(ViewM);
        add(HelpM);
    }
}