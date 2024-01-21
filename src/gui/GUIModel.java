package gui;

import io.*;
import model.FormulaElement;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GUIModel {

    private static String path;
    private FileFilter loadFilter;
    private JFileChooser fileChooser;

    public void setScreen(final JFrame mainFrame, int width, int height, final int[] xPoints, final int[] yPoints, int rows, int cols) {
        mainFrame.setSize(width, height);
        GridLayout outerLayout = new GridLayout(rows, cols);
        mainFrame.setLayout(outerLayout);
    }

    public int displayButtonPad(JFrame mainFrame) {

        ArrayList<Integer> xPoints = new ArrayList<Integer>(), yPoints = new ArrayList<Integer>();
        int cols = 0, rows = 1;
        int allow = 0;

        if (mainFrame.getSize().height == 350) {
            mainFrame.setSize(410, 600);
            xPoints.add(0); xPoints.add(410); xPoints.add(410); xPoints.add(410); xPoints.add(0); xPoints.add(0);
            yPoints.add(0); yPoints.add(0); yPoints.add(350); yPoints.add(600); yPoints.add(600); yPoints.add(350);
            rows = 2;

            final int[] PointsX = new int[xPoints.size()];
            final int[] PointsY = new int[yPoints.size()];

            for (int i = 0; i < xPoints.size(); i++) {
                PointsX[i] =  xPoints.get(i);
                PointsY[i] =  yPoints.get(i);
            }

            setScreen(mainFrame, Collections.max(xPoints), Collections.max(yPoints), PointsX, PointsY, rows, cols);

            return (allow = 1);
        }
        else if (mainFrame.getSize().height == 600) {

            mainFrame.setSize(410, 350);
            xPoints.add(0); xPoints.add(410); xPoints.add(410); xPoints.add(0);
            yPoints.add(0); yPoints.add(0); yPoints.add(350); yPoints.add(350);

            final int[] PointsX = new int[xPoints.size()];
            final int[] PointsY = new int[yPoints.size()];

            for (int i = 0; i < xPoints.size(); i++) {
                PointsX[i] =  xPoints.get(i);
                PointsY[i] =  yPoints.get(i);
            }

            setScreen(mainFrame, Collections.max(xPoints), Collections.max(yPoints), PointsX, PointsY, rows, cols);
        }
        return allow;
    }

    public int displayGraphScreen() {
        int allow = 1;
        return allow;
    }

    public String openFile(String signal) {

        String text = "null";

        fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Open File");
        fileChooser.setFileFilter(loadFilter);
        int rVal = fileChooser.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();

            if (signal.compareTo("openFile") == 0) {
                LoadFile load = new LoadFile(path);
                text = load.returnString();
            }
            else if (signal.compareTo("openGraph") == 0) {
                try {
                    LoadGraph load = new LoadGraph(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return text;
    }

    public void saveFile(HashMap<String, FormulaElement> formulas) {
        String path;

        fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save File");
        fileChooser.setFileFilter(loadFilter);
        int rVal = fileChooser.showSaveDialog(null);

        if (rVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();

            SaveFile save = new SaveFile(formulas, path);
        }
    }

    public String parseInput(String text) {
        String[] txt; String input = "";

        txt = text.split("\\n");
        for (int i = 0; i < txt.length; i++) {
            if (i == (txt.length - 1))
                input = txt[i];
        }

        return input;
    }
}
