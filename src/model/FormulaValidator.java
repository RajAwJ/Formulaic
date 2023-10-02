package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

public class FormulaValidator {
    /** A regular expression matching numeric expressions (floating point numbers) */
    private static final String REGEX_NUMERIC = "(-?[0-9]+([0-9]*|[\\.]?[0-9]+))";
    /** A regular expression matching a valid operation string */
    public static final String REGEX_OPERATION = "(([a-zA-Z][a-zA-Z0-9]+)|([\\*\\/\\+\\-\\|\\?\\:\\@\\&\\^<>'`=%#]{1,2}))";
    /** A regular expression matching a valid parenthesis */
    private static final String REGEX_PARANTHESIS = "([\\(\\)])  ( (\\((.*)\\))* )";
    /** A regular expression matching  a valid assignment */
    private static final String REGEX_ASSIGNMENT = "[a-zA-Z]\\:\\=\\((.*)";
    /** A regular expression matching a valid graph input */
    private static final String REGEX_GRAPH = "[a-zA-Z](\\'|\\'\\')?\\([a-zA-Z]\\=(0|-?[1-9][0-9]*)\\,(0|-?[1-9][0-9]*)(\\,(0|[1-9][0-9]*))?([a-zA-Z]\\=(0|[1-9][0-9]*))*\\)";
    /** A regular expression matching a valid evaluation */
    private static final String REGEX_EVALUATE = "[a-zA-Z]\\(((0|-?[1-9][0-9]*)|([a-zA-Z]\\=(0|-?[1-9][0-9]*))*)\\)";
    /** A regular expression matching a valid integer */
    private static final String REGEX_ONLY_INTEGER = "\\((0|-?[1-9][0-9]*)\\)";
    /** A regular expression matching execute evaluation */
    private static final String REGEX_SIMPLE_EXPRESSION = "\\(*((0|-?[1-9][0-9]*)|cos\\(|sin\\().*";
    /** A regular expression matching a valid differentiation */
    private static final String REGEX_DIFFERENTIATION = "(((0|-?[1-9][0-9]*)?(\\*)?[a-zA-Z](\\^(0|[1-9][0-9]*))?)|(0|-?[1-9][0-9]*))"
                                                      + "((\\-|\\+)(((0|[1-9][0-9]*)?(\\*)?[a-zA-Z](\\^(0|[1-9][0-9]*))?)|(0|-?[1-9][0-9]*)))*";
    // check if input is correct

    public static boolean isGraphFormulaValid(String input) {
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_GRAPH,copyString);
    }
    public static boolean isFormulaValid(String input){
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_PARANTHESIS,copyString);    
    }
    public static boolean isAssignmentFormulaValid(String input){
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_ASSIGNMENT,copyString);    
    }
    public static boolean isEvaluateFormulaValid(String input){
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_EVALUATE,copyString);    
    }
    public static boolean containsOnlyInteger(String input){
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_ONLY_INTEGER,copyString);
    }
    public static boolean isSimpleExpression(String input){
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_SIMPLE_EXPRESSION,copyString);
    }
        public static boolean isSymbolicDifferentiationValid(String input) {
        String copyString = input.replaceAll(" ", "");
        return Pattern.matches(REGEX_DIFFERENTIATION,copyString);
    }
    // not REGEX formulas ****************************************************/
    public static HashMap<String,Vector> getVarNamesAndValuesForGraph(String input){
        String subTokenDigit = "";
        String subTokenAlpha = "";
        String token;
        String varName = "";
        Vector varValue = new Vector();
        StringTokenizer tokenizer; 
        String copyString = input.trim();
        HashMap<String, Vector> result  = new HashMap<String,Vector>();
        // remove name of function, differentiation symbol and empty spaces
        copyString = copyString.substring(1,copyString.length());
        copyString = copyString.replaceAll(" ", "");
        copyString = copyString.replaceAll("\'", "");
        tokenizer = new StringTokenizer(copyString, ",=() \t", true);

        while(tokenizer.hasMoreTokens()){
           token = tokenizer.nextToken();
            System.out.println(token);
            
           if(token.matches("[a-zA-Z]")){
               if(!varValue.isEmpty()){
                   if(result.keySet().contains(varName)){
                       System.out.println("insert error");
                       result.clear();
                       result.put("error",null);
                       break;
                   }
                   else{
                       result.put(varName, varValue);
                       varValue.removeAllElements();
                   }
               }
               else
               varName = token; 
           }
           if(token.matches("(0|-?[1-9][0-9]*)")){
               varValue.add(token);
           }
           if(token.matches("(0|-?[1-9][0-9]*)[a-zA-Z]")){
               for(int i = 0; i < token.length(); i++){
                   if(Character.isDigit(token.charAt(i)) || token.charAt(i) == '-'){
                       subTokenDigit += token.charAt(i);
                   }
                   else
                       subTokenAlpha += token.charAt(i);
               }
               varValue.add(subTokenDigit);
               subTokenDigit = "";
               Vector copyVarValue = (Vector)varValue.clone(); 

               result.put(varName, copyVarValue);
               varName = subTokenAlpha;
               // if key already exists
                if(result.keySet().contains(varName)){
                    result.clear();
                    result.put("error",null);
                    return result;
                }
               subTokenAlpha = "";
               varValue.removeAllElements();
           }
        }
        // insert last set
        result.put(varName, varValue);
        return result;
    }
    
    public static HashMap<String,String> getVarNamesAndValuesForEvaluate(String input){
        StringTokenizer tokenizer; 
        String copyString = input.trim();
        String token;
        String subTokenDigit = "";
        String subTokenAlpha = "";
        String varName = "";
        String varValue = "";
        HashMap<String, String> result  = new HashMap<String,String>();
        // remove name of function and empty spaces
        copyString = copyString.replaceAll(" ", "");
        
        tokenizer = new StringTokenizer(copyString, "=() \t", true);
        while(tokenizer.hasMoreTokens()){
           token = tokenizer.nextToken();
           
           if(token.matches("[a-zA-Z]")){
               if(!varValue.equals("")){
                   if(result.keySet().contains(varName)){
                       System.out.println("insert error");
                       result.clear();
                       result.put("error",null);
                       break;
                   }
                   else{
                       result.put(varName, varValue);
                       varValue = "";
                   }
               }
               else
               varName = token; 
           }
           if(token.matches("(0|-?[1-9][0-9]*)")){
               varValue = token;
           }
           if(token.matches("(0|-?[1-9][0-9]*)[a-zA-Z]")){
               for(int i = 0; i < token.length(); i++){
                   if(Character.isDigit(token.charAt(i)) || token.charAt(i) == '-'){
                       subTokenDigit += token.charAt(i);
                   }
                   else
                       subTokenAlpha += token.charAt(i);
               }
               varValue = subTokenDigit;
               subTokenDigit = ""; 
               result.put(varName, varValue);
               varName = subTokenAlpha;
               // if key already exists
                if(result.keySet().contains(varName)){
                    result.clear();
                    result.put("error",null);
                    return result;
                }
               subTokenAlpha = "";
               varValue = "";
           }
        }
        // insert last set
        result.put(varName, varValue);
        return result;
    }
    
    public static int getInteger(String input){
        System.out.println("inside getInteger()");
        String copyString = input;
        StringTokenizer tokenizer;
        String token;
        int val = 0;
        boolean neg = false;
        copyString = copyString.replaceAll(" ", "");
        
        tokenizer = new StringTokenizer(copyString, "-()= \t", true);
        
        while(tokenizer.hasMoreTokens()){
           token = tokenizer.nextToken();
           if(token.matches("\\-"))
               neg = true;
           if(token.matches("(0|[1-9][0-9]*)")){
               val = Integer.parseInt(token);
           } 
        }
        if(neg)
            return -val;
        return val;
    }
    
    public static boolean areBracketsBalanced(String input){
        String copyString = input;
        StringTokenizer tokenizer;
        String token;
        int counter = 0;
        copyString = copyString.replaceAll(" ", "");
        // remove outside brackets
        copyString = copyString.substring(1,copyString.length() - 1);
        tokenizer = new StringTokenizer(copyString, "*+-/^() \t", true);
        while(tokenizer.hasMoreTokens()){
           token = tokenizer.nextToken();
           if(token.matches("\\("))
              counter++; 
           if(token.matches("\\)")){
               counter--;
               if(counter < 0)
                   return false;
           }
        }
        if(counter == 0)
            return true;
        return false;
        
    }
}