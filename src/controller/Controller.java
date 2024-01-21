
package controller;

import gui.GUIController;
import interfaces.ControllerInterface;

import java.util.*;
import javax.swing.JOptionPane;
import model.FormulaElement;
import model.FormulaValidator;

public class Controller implements ControllerInterface{

    private HashMap<String,FormulaElement> formulas;
    private String message = "";
    private Double result = 0.0;
    GUIController controller;
    public boolean isResultAssigned;

    public Controller(){
        formulas = new HashMap<String,FormulaElement>();
        controller = new GUIController(this);
        isResultAssigned = false;
    }

    public static void main(String[] args){
        Controller calculator = new Controller();
    }

    public void graph(String input) {
        String inputCopy = input.trim();
        // remove word graph from the beginning if it is one
        if(inputCopy.matches("graph .*")){
            inputCopy = inputCopy.substring(5,input.length()).trim();
        }
        //System.out.println("input after removing \"graph\": " + inputCopy);
        if(FormulaValidator.isGraphFormulaValid(input)){
            // get formula name
            String formulaName = inputCopy.substring(0,1);
            // formula with given name does not exist
            if(!formulas.containsKey(formulaName)){
                message = "Formula \'" + input.charAt(0) + "\' does not exist!";
                return;
            }
            else{
                // get formula with given name
                FormulaElement formula = formulas.get(formulaName);
                FormulaElement copyFormula = formula.clone();
                // store variables and they values from the input
                HashMap<String,Vector> namesAndValues = FormulaValidator.getVarNamesAndValuesForGraph(inputCopy);
                //**** test display the content of hash
                if(namesAndValues.keySet().contains("error")){
                    message = "Formula contains the same variables";
                    return;
                }
                // check if the variable of the input are the same as in the formula
                String[] varSet = namesAndValues.keySet().toArray(new String[namesAndValues.size()]).clone();
                if(!areVariablesTheSame(formula,varSet)){
                    message = "Formula contains different set of variable than entered!";
                    return;
                }
                // store begin, end and step
                Vector params = new Vector();
                String varName = "";
                // set variable values
                Iterator<Map.Entry<String,Vector>> it = namesAndValues.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String,Vector> entry = it.next();
                    if(entry.getValue().size() == 1){
                        copyFormula.setVariableValue(entry.getKey(), Integer.parseInt(entry.getValue().firstElement().toString()));
                    }
                    else{
                        params = entry.getValue();
                        varName = entry.getKey();
                    }
                }

                // check if needs to be differentiated
                int diffLevel = getDifferLevel(inputCopy.substring(0,3));
                switch(diffLevel){
                    case 0:
                        controller.displayGraph(copyFormula,params,varName);
                        break;
                    case 1:
                        FormulaElement copyFormula2 = FormulaElement.numericDifferentiation(copyFormula, varName).clone();

                        controller.displayGraph(copyFormula2,params,varName);
                        break;
                    case 2:
                        FormulaElement copyFormula3 = FormulaElement.numericDifferentiation(copyFormula, varName).clone();
                        FormulaElement copyFormula4 = FormulaElement.numericDifferentiation(copyFormula3, varName).clone();

                        controller.displayGraph(copyFormula4,params,varName);
                        break;
                }
            }
        }
        else{
            message = "Incorrect formula for graph!";
            return;
        }
        message = "formula OK";
    }

    // checks differentiation level: 0 - not differentiation, 1 - first differentiation, 2 - second differentiation
    private int getDifferLevel(String substring) {
        int diffLevel = 0;
        for(int i = 0; i < substring.length(); i++){
            if(substring.charAt(i) == '\'')
                diffLevel++;
        }
        return diffLevel;
    }

    // compares formula with input 
    private boolean areVariablesTheSame(FormulaElement formula, String[] varSet) {
        ArrayList<String> var = formula.getVariables();

        ArrayList<String> var2 = new ArrayList(Arrays.asList(varSet));

        return var.containsAll(var2)&&var2.containsAll(var);
    }

    public void enter(String input) {
        // deciding what to execute next
        // trim and copy the first 11 characters to string
        String testString = input.trim();
        String testGraph = "";
        if(testString.matches("save")){
            save();
            return ;
        }
        if(testString.matches("load")){
            load();
            return ;
        }
        // trim to remove the last empty space - for 'save graph' and 'load graph'
        testString = testString.trim();
        if(testString.matches("save graph")){

            controller.display("", "Cannot Save Graph");
            return ;
        }
        if(testString.matches("load graph")){
            controller.model.openFile("openGraph");
            return ;
        }
        if (testString.length() != 0) {
            if ( FormulaValidator.isSimpleExpression(testString) ) {
                try{
                    FormulaElement result = FormulaElement.parseFormula(testString);
                    if (result.isFullyGrounded()) {
                        message = String.valueOf(result.evaluate());
                        isResultAssigned = true;
                        return;
                    }
                }
                catch(ClassCastException e){
                    message = "Wrong input";
                    isResultAssigned = false;
                    return;
                }
            }
        }
        // take the first 6 character - expecting 'graph '
        if(testString.length() > 5){
            testGraph = testString.substring(0,5);
            // check if word 'graph ' or valid syntax for graph like : f(x=3,4) or f'(x=3,4) or f''(x=3,4)
            if(testGraph.matches("graph ") || FormulaValidator.isGraphFormulaValid(input)){
                graph(input);
                return ;
            }
        }
        if(testString.length() < 4){
            message = "incorrect input - inside enter()";
            return;
        }
        // if does not match the one of above
        assignOrExecute(testString);
    }

    public void assignOrExecute(String i) {
        String input = (String) i;
        // try with assigning
        if(FormulaValidator.isAssignmentFormulaValid(input)){
            // if assigning formula is not valid
            if(!assign(input)){
                message = "formula cannot be assigned!";
            }

        }
        // not assigning - try evaluate
        else{
            if(FormulaValidator.isEvaluateFormulaValid(input))
                execute(input);
            else
                message = "Wrong input! - can not execute";
        }
    }

    private boolean assign(String input){
        input = input.trim();
        // get name of formula from input
        String formulaName = input.substring(0,1);
        // get only formula from input
        String copyInput = input.substring(3);
        // if formula valid then proceed to assign  
        if(FormulaValidator.areBracketsBalanced(copyInput)){
            // catch exceptions - invalid formula syntax
            try{
                FormulaElement formula = FormulaElement.parseFormula(copyInput);
                if(formulas.containsKey(formulaName)){
                    // display message window
                    int n = JOptionPane.showConfirmDialog(
                            null,
                            "Override formula " + formulaName + ": " + formulas.get(formulaName) + " with\n"
                                    + formulaName + ": " + formula + " ?",
                            "WARNING",
                            JOptionPane.YES_NO_OPTION);
                    if(n == 0){
                        formulas.put(formulaName, formula);
                        message = "formula overridden";
                    }
                    else
                        message = "formula not overridden";

                }
                else{
                    formulas.put(formulaName, formula);
                    message = "formula assigned";
                }
                return true;
            }
            catch(ClassCastException e){
                message = "wrong formula syntax";
            }
        }
        else{
            message = "invalid formula - general";
            return false;
        }

        // assignment success
        return true;
    }

    private void execute(String input) {
        String inputCopy = input.trim();
        if(inputCopy.length() < 4){
            message = "incorrect input";
            return;
        }
        // get formula name from input
        String formulaName = inputCopy.substring(0,1);
        FormulaElement formula = formulas.get(formulaName);
        // formula with given name does not exists
        if(!formulas.containsKey(formulaName)){
            message = "Formula \'" + input.charAt(0) + "\' does not exist!";
            return;
        }
        //remove formula name from the string
        inputCopy = inputCopy.substring(1,inputCopy.length());
        // store variables and they values from the input
        HashMap<String,String> namesAndValues = FormulaValidator.getVarNamesAndValuesForEvaluate(inputCopy);
        // check if it contains only integer like: f(43)
        if(FormulaValidator.containsOnlyInteger(inputCopy)){
            // get integer from input
            int integer = FormulaValidator.getInteger(inputCopy);
            if(namesAndValues.size() == 1){
                isResultAssigned = true;
                // variable names
                ArrayList<String> names = formulas.get(formulaName).getVariables();
                String name = names.get(0);
                message = "... before calculating result";

                //*** test formula content
                formula.setVariableValue(name, integer);
                Double r = formula.evaluate();
                getResult();
                message = r.toString();
            }
        }
        // with variable(s) assigned
        else{
            message = "evaluating with variable(s) and assigned value(s)";
            //**** test display the content of hash
            if(namesAndValues.keySet().contains("error")){
                message = "Formula contains the same variables";
                return;
            }
            // check if the variable of the input are the same as in the formula
            String[] varSet = namesAndValues.keySet().toArray(new String[namesAndValues.size()]).clone();
            if(!areVariablesTheSame(formula,varSet)){
                message = "Formula contains different set of variable than entered!";
                return;
            }
            // set variable values
            Iterator<Map.Entry<String,String>> it = namesAndValues.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                formula.setVariableValue(entry.getKey(), Integer.parseInt(entry.getValue()));
            }
            isResultAssigned = true;
            // display result
            Double r = formula.evaluate();
            getResult();
            message = r.toString();

        }
    }

    public Double getResult() {
        return result;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String differentiate(String input) {

        String ans;
        ans = FormulaElement.symbolicDifferentiation(input);
        return ans;
    }

    @Override
    public String differentiate2(String input) {

        String ans;
        ans = FormulaElement.symbolicDifferentiation(FormulaElement.symbolicDifferentiation(input));
        return ans;
    }

    public void save() {
        controller.model.saveFile(formulas);
        controller.display("", "Formula Saved");
    }

    public void load() {
        String text = controller.model.openFile("openFile");
        String[] formulaeStr = text.split("\\n");

        for (String s: formulaeStr) {
            s = s.trim();
            text = controller.model.parseInput(s);
            enter(text);
        }
        controller.display("", "File Loaded.");
    }
}