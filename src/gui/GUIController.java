package gui;

import controller.Controller;
import graph.Draw2dGraph;
import io.LoadFile;
//import io.SaveGraph;
import model.FormulaElement;

import java.awt.event.*;
import java.util.Vector;

public class GUIController implements ActionListener, KeyListener {
    private String input;
    public GUIModel model;
    GUIView view;
    private Controller contr;

    public GUIController(Controller controller) {
        view = new GUIView("Formulaic");
        model = new GUIModel();
        input = "";
        contr = controller;

        view.buttonActionListeners(this);
    }

    public void actionPerformed(ActionEvent evt) {

        String event = evt.getActionCommand();

        if (event.compareTo("BS") == 0) {
            view.displayButtonScreen(model.displayButtonPad(view));

        } else if (event.compareTo("GD") == 0) {
            input = view.main.textScreen.getText();
            input = model.parseInput(input);
            contr.graph(input);

        } else if (event.compareTo("0") == 0) {
            view.main.textScreen.append("0");
        } else if (event.compareTo("1") == 0) {
            view.main.textScreen.append("1");
        } else if (event.compareTo("2") == 0) {
            view.main.textScreen.append("2");
        } else if (event.compareTo("3") == 0) {
            view.main.textScreen.append("3");
        } else if (event.compareTo("4") == 0) {
            view.main.textScreen.append("4");
        } else if (event.compareTo("5") == 0) {
            view.main.textScreen.append("5");
        } else if (event.compareTo("6") == 0) {
            view.main.textScreen.append("6");
        } else if (event.compareTo("7") == 0) {
            view.main.textScreen.append("7");
        } else if (event.compareTo("8") == 0) {
            view.main.textScreen.append("8");
        } else if (event.compareTo("9") == 0) {
            view.main.textScreen.append("9");
        } else if (event.compareTo(".") == 0) {
            view.main.textScreen.append(".");
        } else if (event.compareTo("(") == 0) {
            view.main.textScreen.append("(");
        } else if (event.compareTo(")") == 0) {
            view.main.textScreen.append(")");
        } else if (event.compareTo("/") == 0) {
            view.main.textScreen.append("/");
        } else if (event.compareTo("*") == 0) {
            view.main.textScreen.append("*");
        } else if (event.compareTo("-") == 0) {
            view.main.textScreen.append("-");
        } else if (event.compareTo("+") == 0) {
            view.main.textScreen.append("+");
        } else if (event.compareTo("^") == 0) {
            view.main.textScreen.append("^");
        } else if (event.compareTo("sin") == 0) {
            view.main.textScreen.append("sin(");
        } else if (event.compareTo("cos") == 0) {
            view.main.textScreen.append("cos(");
        } else if (event.compareTo("=") == 0) {

            input = view.main.textScreen.getText();
            input = model.parseInput(input);
            contr.enter(input);
            view.main.textScreen.append("\n");

            if (contr.isResultAssigned) {
                display((contr.getMessage() + "\n"), " ");
            }
            else {
                display("", contr.getMessage());
            }
        } else if (event.compareTo("f'") == 0) {

            String output = accessInput();
            output = model.parseInput(output);
            output = contr.differentiate(output);
            display(("\n" + output + "\n"), " ");
        } else if (event.compareTo("f''") == 0) {

            String output = accessInput();
            output = model.parseInput(output);
            output = contr.differentiate2(output);
            display(("\n" + output + "\n"), " ");
        } else if (event.compareTo("Clear") == 0) {

            view.main.textScreen.setText("");
            display("", "Cleared");
        } else if (event.compareTo("File0") == 0) {

            view.main.textScreen.setText("");
        } else if (event.compareTo("File1") == 0) {

            contr.save();
        } else if (event.compareTo("File2") == 0) {

            contr.load();
        } else if (event.compareTo("File4") == 0) {

            System.exit(0);
        } else if (event.compareTo("View0") == 0) {

            ViewHistory showHistory = new ViewHistory(accessInput());
        } else if (event.compareTo("Help0") == 0) {

           load("Help.txt");
        } else if (event.compareTo("Help1") == 0) {

            load("About.txt");
        } else if (event.compareTo("saveGraph") == 0) {

        }
    }

    public void display(String output, String msg) {
        view.main.textScreen.append(output);
        view.main.messageDisplay.setText(msg);
    }

    public void load(String fileName) {
        LoadFile file = new LoadFile(fileName);
        LoadHelp help = new LoadHelp(file.returnString());
    }

    public String accessInput() {
        input = view.main.textScreen.getText();
        return input;
    }

    public void displayGraph(FormulaElement formula, Vector<String> params, String varName) {

        int limitStart = Integer.parseInt(params.get(0));
        int limitEnd = Integer.parseInt(params.get(1));
        int step = 0;

        Draw2dGraph drawGraph;

        if (params.size() == 3) {
            step = Integer.parseInt(params.get(2));
            drawGraph = new Draw2dGraph(formula, step, limitStart, limitEnd, varName);
        }
        else {
            drawGraph = new Draw2dGraph(formula, step, limitStart, limitEnd, varName);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
            input = view.main.textScreen.getText();
            input = model.parseInput(input);

            contr.enter(input);

            if (contr.isResultAssigned) {
                display((contr.getMessage() + "\n"), " ");
            }
            else {
                display("", contr.getMessage());
            }
        }
    }
}
