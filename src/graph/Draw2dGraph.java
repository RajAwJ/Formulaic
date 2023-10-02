package graph;

import model.FormulaElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Ellipse2D;

public class Draw2dGraph extends JPanel {

    private static FormulaElement formulaElement;
    private static int xIncrement;   //increment of x-axis
    private static int xStart =-10; // start of x-axis
    private static int xEnd = -1;
    private static String varName;

    public Draw2dGraph(FormulaElement formula, int step, int start, int end, String vName){
        formulaElement = formula;
        xIncrement = step;
        xStart = start;
        xEnd = end;
        varName = vName;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    private int frameGap = 50;
    private int labelGap = 25;

    private Color lineColour = new Color(255, 0, 0, 180);
    private Color pointColour = new Color(0, 0, 0, 255);  // colour of point is white (invisible)
    private Color gridColour = new Color(20, 200, 200, 200);

    private static final Stroke LINE_THICKNESS = new BasicStroke(2f); //thickness of line
    private int pointWidth = 0;          //value for showing scale line
    private int numberYDivisions = 10;
    private List<Double> xyCoordinates;
    private static final double  PRECISION  = 0.1;

    public Draw2dGraph(List<Double> xyCoordinates) {
        this.xyCoordinates = xyCoordinates;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * frameGap) - labelGap) / (xyCoordinates.size() - 1);
        double yScale = ((double) getHeight() - 2 * frameGap - labelGap) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<Point>();

        //coordinate
        for (int i = 0; i < xyCoordinates.size(); i++) {
            int x1 = (int) (i * xScale + frameGap + labelGap);
            int y1 = (int) ((getMaxScore() - xyCoordinates.get(i)) * yScale + frameGap);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(frameGap + labelGap, frameGap, getWidth() - (2 * frameGap) - labelGap, getHeight() - 2 * frameGap - labelGap);
        g2.setColor(Color.WHITE);

        // Create Scale marks and draw x and y grids
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = frameGap + labelGap;
            int x1 = pointWidth + frameGap + labelGap;
            int y0 = getHeight() - ((i * (getHeight() - frameGap * 2 - labelGap)) / numberYDivisions + frameGap + labelGap);
            int y1 = y0;
            if (xyCoordinates.size() > 0) {
                g2.setColor(gridColour);
                g2.drawLine(frameGap + labelGap + 1 + pointWidth, y0, getWidth() - frameGap, y1); //x lines
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < xyCoordinates.size(); i++) {

            if (xyCoordinates.size() > 1) {
                int x0 = i * (getWidth() - frameGap * 2 - labelGap) / (xyCoordinates.size() - 1) + frameGap + labelGap;
                int x1 = x0;
                int y0 = getHeight() - frameGap - labelGap;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((xyCoordinates.size() / (xEnd - xStart)*((int)((xEnd-xStart)/20)+1))) + 0)) == 0  ) {
                    g2.setColor(gridColour);
                    g2.drawLine(x0, getHeight() - frameGap - labelGap - 1 - pointWidth, x1, frameGap);
                    g2.setColor(Color.BLACK);
                    String xLabel = xStart + i/((int) (1/PRECISION)) +   ""; //

                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);  //print scale numbers for x-axis

                }

                g2.drawLine(x0, y0, x1, y1);
            }
        }
        //draw graph
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColour);
        g2.setStroke(LINE_THICKNESS);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
        //draw points
        g2.setStroke(oldStroke);
        g2.setColor(pointColour);
        for (double i = 0; i < graphPoints.size(); i+=0.1) {
            double x = graphPoints.get((int)i).x - pointWidth / 2;
            double y = graphPoints.get((int)i).y - pointWidth / 2;
            double ovalW = pointWidth;
            double ovalH = pointWidth;

            g2.fill(new Ellipse2D.Float((float)x,(float) y,(float) ovalW, (float)ovalH));
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : xyCoordinates) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : xyCoordinates) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setXyCoordinates(List<Double> xyCoordinates) {
        this.xyCoordinates = xyCoordinates;
        invalidate();
        this.repaint();
    }

    public List<Double> getXyCoordinates() {
        return xyCoordinates;
    }

    //Integration part
    private static void createAndShowGui() {
        List<Double> scores = new ArrayList<Double>();

        for (double i = xStart; i <= xEnd; i+=PRECISION) {

            formulaElement.setVariableValue(varName, i);
            scores.add((double) formulaElement.evaluate());
        }

        Draw2dGraph mainPanel = new Draw2dGraph(scores);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}