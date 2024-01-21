package model;

import java.util.*;

public abstract class FormulaElement implements Cloneable{

    public abstract FormulaElement clone();

    public static FormulaElement parseFormula(String text){

        String token;
        String subTokenAlpha;
        String subTokenDigit;
        Vector<String> tokens = new Vector<String>();
        Vector<Object> elements = new Vector<Object>();
        StringTokenizer tokenizer = new StringTokenizer(text, "*+-/^() \t", true);

        while(tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            if((!token.equals(" "))){
                // all possible scenarios
                // more than one variable together
                if(token.matches("[a-zA-Z][a-zA-Z]+") && !token.equals("sin") && !token.equals("cos")){
                    for(int i = 0; i < token.length(); i++) {
                        elements.add(new VariableElement(token.charAt(i) + ""));
                    }
                }else{
                    subTokenAlpha = "";
                    subTokenDigit = "";
                    // separate variables or sin/cos from the number
                    if(token.matches("[a-zA-Z][a-zA-Z]*[0-9][0-9]*")){
                        for(int i = 0; i < token.length(); i++){
                            if(Character.isAlphabetic(token.charAt(i))){
                                subTokenAlpha += token.charAt(i);
                            }
                            else{
                                subTokenDigit += token.charAt(i);
                            }
                        }
                        // not sin/cos - create separate tokens
                        if(!subTokenAlpha.equals("sin") && !subTokenAlpha.equals("cos")){
                            for(int i = 0; i < subTokenAlpha.length(); i++) {
                                elements.add(new VariableElement(subTokenAlpha.charAt(i) + ""));
                            }
                        }
                        // sin or cos
                        else
                            elements.add(new VariableElement(subTokenAlpha));
                        elements.add(new ConstantElement(Double.parseDouble(subTokenDigit)));
                    }
                    else{
                        // separate number from variable or sin/cos
                        if(token.matches("[0-9][0-9]*[a-zA-Z][a-zA-Z]*")){
                            for(int i = 0; i < token.length(); i++){
                                if(Character.isDigit(token.charAt(i))){
                                    subTokenDigit += token.charAt(i);
                                }
                                else{
                                    subTokenAlpha += token.charAt(i);
                                }
                            }
                            elements.add(new ConstantElement(Double.parseDouble(subTokenDigit)));
                            // check if it is sin/cos
                            if(!subTokenAlpha.equals("sin") && !subTokenAlpha.equals("cos")){
                                for(int i = 0; i < subTokenAlpha.length(); i++) {
                                    elements.add(new VariableElement(subTokenAlpha.charAt(i) + ""));
                                }
                            }
                            else
                                elements.add(new ConstantElement(Double.parseDouble(subTokenDigit)));
                        }
                        else{
                            if(token.matches("[a-zA-Z]")){
                                elements.add(new VariableElement(token));
                            }
                            else{
                                if(token.matches("[0-9][0-9]*")){
                                    elements.add(new ConstantElement(Double.parseDouble(token)));
                                }
                                else
                                    elements.add(token);
                            }

                        }

                    }
                }
            }
        }

        // first pass - recognize constant and variable
        for(int i = 0; i < tokens.size(); i++){
            // recognize constant
            if(isInteger(tokens.elementAt(i))){
                elements.add(new ConstantElement(Integer.parseInt(tokens.elementAt(i))));
            }else {
                // recognize variable
                if(tokens.elementAt(i).matches("[a-zA-Z]")){
                    elements.add((VariableElement)new VariableElement(tokens.elementAt(i)));
                }
                else{
                    elements.add(tokens.elementAt(i)) ;
                }
            }
        }
        // prepare before recursion
        // add extra brackets at the beginning and end
        elements.insertElementAt("(", 0);
        elements.insertElementAt(")", elements.size());

        // insert "*" between Constant/Variable and "(" / ")"
        for(int i = 1; i < elements.size() - 1; i++){
            if(elements.elementAt(i).equals(")") && ( !elements.elementAt(i + 1).equals("+") &&
                    !elements.elementAt(i + 1).equals("-") &&
                    !elements.elementAt(i + 1).equals("/") &&
                    !elements.elementAt(i + 1).equals("*") &&
                    !elements.elementAt(i + 1).equals(")") &&
                    !elements.elementAt(i + 1).equals("^"))){
                elements.insertElementAt("*", i + 1);
            }
            if(elements.elementAt(i + 1).equals("(") && ( !elements.elementAt(i).equals("+") &&
                    !elements.elementAt(i).equals("-") &&
                    !elements.elementAt(i).equals("/") &&
                    !elements.elementAt(i).equals("*") &&
                    !elements.elementAt(i).equals("cos") &&
                    !elements.elementAt(i).equals("sin") &&
                    !elements.elementAt(i).equals("(") &&
                    !elements.elementAt(i + 1).equals("^"))){
                elements.insertElementAt("*", i + 1);
            }

        }

        Stack stackElements = new Stack<Object>();
        // controls number of brackets
        int counter = 0;
        // controls if cos or sin is in the string
        int skip = 0;
        // stores objects being converted by parseThrough method
        Vector<Object> buffer = new Vector<Object>();
        // loop runs until all objects will be converted into one FormulaElement object
        while(elements.size() > 1 || counter != 0){
            // build stack until closing bracket will be found - removing objects from original elements vector
            if(!elements.elementAt(0).equals(")")){
                if(elements.elementAt(0).equals("(")){
                    // add brackets
                    counter++;
                }
                // cos/sin object in the string
                if(elements.elementAt(0).equals("cos") || elements.elementAt(0).equals("sin"))
                    skip++;
                // push into stack by removing from elements vector
                stackElements.push(elements.remove(0));
            }
            // closing bracket found
            else{
                if(counter > 0){
                    // remove closing bracket from original elements vector
                    elements.remove(0);
                    // build buffer by removing from stack until opening bracket is met 
                    while(!stackElements.peek().equals("(")){
                        //System.out.println(stackElements);
                        buffer.insertElementAt(stackElements.pop(), 0);
                    }
                    // remove opening bracket from stack
                    stackElements.pop();
                    // decrement brackets
                    counter--;
                    // insert converted objects into one object from buffer, into elements vector
                    elements.insertElementAt(parseThrough(buffer),0);
                    // if cos/sin was met in the string - parseThrough again - buffer still contains converted into one FormulaElement object
                    if(skip > 0 && (stackElements.peek().equals("sin") || stackElements.peek().equals("cos"))){
                        // add bracket at the end and the beginig 
                        buffer.add(")");
                        buffer.insertElementAt("(", 0);
                        // remove cos/sin from stack and add to buffer
                        buffer.insertElementAt(stackElements.pop(),0);
                        // convert buffer content - "cos/sin" + "(" + "object" + ")" - into one object
                        elements.setElementAt(parseThrough(buffer), 0);
                        // decrement number of skips
                        skip--;
                    }
                    // reset buffer
                    buffer.clear();
                }
                // if number of openning and closing brackets is not the same
                else
                    throw new IllegalInputStructure(
                            "Illegal order of brackets in the input string!!!.");
            }
        }
        return (FormulaElement)elements.get(0);
    }

    private static Object parseThrough(Vector<Object> elements){

        // second pass - multiply variables by itself or by constants or other way around
        for(int i = 0; i < elements.size() - 1; i++){
            //System.out.println(elements.elementAt(i).getClass().getName());
            if((elements.get(i).getClass() == VariableElement.class && elements.get(i + 1).getClass() == VariableElement.class) ||
                    (elements.get(i).getClass() == ConstantElement.class && elements.get(i + 1).getClass() == VariableElement.class) ||
                    (elements.get(i).getClass() == VariableElement.class && elements.get(i + 1).getClass() == ConstantElement.class) ){
                // create Multiple object
                MultipleFunctionElement multiply = new MultipleFunctionElement();
                // cast arguments to FormulaElements and pass to the function
                multiply.addArgument((FormulaElement)elements.elementAt(i));
                multiply.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i, multiply);
                // remove redundant element
                elements.remove(i + 1);
            }
        }

        // third pass - sine, cosine
        for(int i = 0; i < elements.size() - 3; i++){
            // sine
            if(elements.elementAt(i).equals("sin")){
                SineFunctionElement sine = new SineFunctionElement();
                sine.addArgument((FormulaElement)elements.elementAt(i + 2));
                elements.set(i, sine);
                // remove redundant elements
                for(int j = i + 1; j < i + 4; j++)
                    elements.remove(i + 1);
            }
            // cosine
            if(elements.elementAt(i).equals("cos")){
                CosineFunctionElement cosine = new CosineFunctionElement();
                cosine.addArgument((FormulaElement)elements.elementAt(i + 2));
                elements.set(i, cosine);
                // remove redundant elements
                for(int j = i + 1; j < i + 4; j++)
                    elements.remove(i + 1);
            }
        }

        // fourth pass - power
        for(int i = 1; i < elements.size() - 1; i++){
            if(elements.elementAt(i).equals("^")) {
                PowerFunctionElement power = new PowerFunctionElement();
                power.addArgument((FormulaElement)elements.elementAt(i - 1));
                power.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i - 1, power);
                for(int j = i; j < i + 2; j++)
                    elements.remove(i);
                i--;
            }
        }

        // fifth pass - multiplication and division
        for(int i = 1; i < elements.size() - 1; i++){
            if(elements.elementAt(i).equals("*")){
                MultipleFunctionElement multiply = new MultipleFunctionElement();
                multiply.addArgument((FormulaElement)elements.elementAt(i - 1));
                multiply.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i - 1, multiply);
                for(int j = i; j < i + 2; j++)
                    elements.remove(i);
                i--;
            }
            if(elements.elementAt(i).equals("/")){
                DivideFunctionElement divide = new DivideFunctionElement();
                divide.addArgument((FormulaElement)elements.elementAt(i - 1));
                divide.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i - 1, divide);
                for(int j = i; j < i + 2; j++)
                    elements.remove(i);
                i--;
            }
        }

        // sixth pass - addition, substraction
        for(int i = 1; i < elements.size() - 1; i++){
            if(elements.elementAt(i).equals("+")){
                PlusFunctionElement plus = new PlusFunctionElement();
                plus.addArgument((FormulaElement)elements.elementAt(i - 1));
                plus.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i - 1, plus);
                for(int j = i; j < i + 2; j++)
                    elements.remove(i);
                i--;
            }
        }
        for(int i = 1; i < elements.size() - 1; i++){
            if(elements.elementAt(i).equals("-")){
                MinusFunctionElement minus = new MinusFunctionElement();
                minus.addArgument((FormulaElement)elements.elementAt(i - 1));
                minus.addArgument((FormulaElement)elements.elementAt(i + 1));
                elements.set(i - 1, minus);
                for(int j = i; j < i + 2; j++)
                    elements.remove(i);
                i--;
            }
        }

        return elements.elementAt(0);
    }

    public static FormulaElement numericDifferentiation(FormulaElement formula, String varName){
        ConstantElement delta = new ConstantElement(0.0000001);
        MinusFunctionElement minus = new MinusFunctionElement();
        DivideFunctionElement divide = new DivideFunctionElement();
        FormulaElement copyFormula = formula.clone();

        if(formula.getClass() == VariableElement.class){
            PlusFunctionElement plus = new PlusFunctionElement() ;
            plus.addArgument(formula);
            plus.addArgument(delta);
            minus.addArgument(plus);
            minus.addArgument(formula);
            divide.addArgument(minus);
            divide.addArgument(delta);
        }
        else{
            copyFormula.addDelta(varName);
            minus.addArgument(copyFormula);
            minus.addArgument(formula);
            divide.addArgument(minus);
            divide.addArgument(delta);
        }
        return divide;
    }

    public static String symbolicDifferentiation(String input){

        input = parseforDifferentiation(input);

        StringBuilder output = new StringBuilder();
        String token;
        String subTokenAlpha = "";
        String subTokenDigit = "";
        String var = "";
        String numBuffer = "";
        String powerBuffer = "";
        boolean isNegative = false;
        boolean isPower = false;
        boolean first = true;
        int num, power;
        // remove empty spaces
        String copyInput = input.replaceAll(" ", "");
        copyInput = input.replaceAll("\\*", "");
        // add '+' symbol to initialize the loop
        if(copyInput.charAt(0) != '-')
            copyInput = "+" + copyInput;
        StringTokenizer tokenizer = new StringTokenizer(copyInput, "+-^ \t", true);

        while(tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            if(token.equals("-") || token.equals("+")){
                if(var.isEmpty() && numBuffer.isEmpty()){
                    if(token.equals("-")){
                        isNegative = true;
                    }
                }
                else{
                    if(!numBuffer.isEmpty() && !var.isEmpty()){
                        num = Integer.parseInt(numBuffer);
                        if(isNegative)
                            num = -num;
                        if(isPower){
                            power = Integer.parseInt(powerBuffer);
                            num *= power;
                            power--;
                            if(power > 1){
                                if(isNegative || first)
                                    output.append(num + "" + var + "^" + power);
                                else
                                    output.append("+" + num + "" + var + "^" + power );
                            }
                            else{
                                if(isNegative || first)
                                    output.append(num + "" + var);
                                else
                                    output.append("+" + num + "" + var);
                            }
                        }
                        else{
                            if(num < 0 || first){
                                output.append(num + "");
                            }
                            else
                                output.append("+" + num);
                        }
                    }
                    else{
                        if(!var.isEmpty()){
                            if(!powerBuffer.isEmpty()){
                                power = Integer.parseInt(powerBuffer);
                                num = power;
                                if(isNegative)
                                    num = -num;
                                power--;
                                if(power > 1){
                                    output.append(num + "" + var + "^" + power);
                                }
                                else{
                                    output.append(num + "" + var);
                                }
                            }
                            else{
                                if(isNegative)
                                    output.append("-" + 1);
                                else{
                                    output.append("+" + 1);
                                }
                            }
                        }
                        else{
                            if(first)
                                output.append("" + 0);
                            else
                                output.append("+" + 0);
                        }
                    }
                    //reset
                    var = "";
                    numBuffer = "";
                    powerBuffer = "";
                    subTokenAlpha = "";
                    subTokenDigit = "";
                    isNegative = false;
                    isPower = false;
                }
                // allow adding '+' symbol from now
                first = false;
            }
            if(token.equals("-")){
                isNegative = true;
            }
            if(token.matches("[a-zA-Z]")){
                var = token;
            }
            if(token.equals("^")){
                isPower = true;
            }
            if(token.matches("[0-9]*")){
                if(isPower){
                    powerBuffer = token;
                }
                else
                    numBuffer = token;
            }
            // separate number from variable
            if(token.matches("[0-9][0-9]*[a-zA-Z]")){
                for(int i = 0; i < token.length(); i++){
                    if(Character.isDigit(token.charAt(i))){
                        subTokenDigit += token.charAt(i);
                    }
                    else{
                        subTokenAlpha += token.charAt(i);
                    }
                }
                numBuffer = subTokenDigit;
                var = subTokenAlpha;
            }
        }
        // add the last 
        if(!numBuffer.isEmpty() && !var.isEmpty()){
            num = Integer.parseInt(numBuffer);
            if(isNegative)
                num = -num;
            if(isPower){
                power = Integer.parseInt(powerBuffer);
                num *= power;
                power--;
                if(power > 1){
                    if(isNegative)
                        output.append(num + "" + var + "^" + power);
                    else
                        output.append("+" + num + "" + var + "^" + power);
                }
                else{
                    if(isNegative)
                        output.append(num + "" + var);
                    else
                        output.append("+" + num + "" + var);
                }

            }
            else{
                if(isNegative){
                    output.append(num + "");
                }
                else
                    output.append("+" + num);
            }

        }
        else{
            if(!var.isEmpty()){
                if(!powerBuffer.isEmpty()){
                    power = Integer.parseInt(powerBuffer);
                    num = power;
                    if(isNegative)
                        num = -num;
                    power--;
                    if(power > 1){
                        output.append(num + "" + var + "^" + power);
                    }
                    else{
                        output.append(num + "" + var);
                    }
                }
                else{
                    if(isNegative)
                        output.append("-" + 1);
                    else{
                        output.append("+" + 1);
                    }
                }
            }
            else{
                output.append("+" + 0);
            }
        }
        String finalOutput = output.toString();
        // remove first '+' if one
        if(finalOutput.charAt(0) == '+')
            finalOutput = finalOutput.substring(1,finalOutput.length());
        return finalOutput;
    }
    // suporting method for recognizing integer
    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public void addDelta(String varName){}

    public void setVariableValue(String varName, double value){}

    public boolean isFullyGrounded(){
        return false;
    }

    public double evaluate(){
        return 0.0;
    }

    private static String parseforDifferentiation(String input) {
        input = input.trim();
        char[] c = input.toCharArray();

        if ( (c[0] == '(') && (c[c.length-1] == ')') ) {

            input = "";
            for (int i = 1; i < c.length - 1; i++) {
                input += c[i];
            }
        }

        return input;
    }

    public void addArgumentAtPosition(FormulaElement element, int position){}

    public ArrayList<String> getVariables(){
        ArrayList<String> array = new ArrayList<String>();
        return array;
    }
}