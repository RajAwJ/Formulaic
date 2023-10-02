package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class FunctionElement extends FormulaElement implements Cloneable {
    
    protected Vector<FormulaElement> arguments = new Vector<FormulaElement>();

    public FunctionElement() {
        super();
    }
    
    public FormulaElement clone(){
        FunctionElement element = new FunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
    
    public void addArgument(FormulaElement element) {
        arguments.add(element);   
    }
    
    public void addArgumentAtPosition(FormulaElement element, int position){
        arguments.setElementAt(element, position);
    }
    
    public Vector<FormulaElement> getArguments() {
        return arguments;
    }

    @Override
    public void setVariableValue(String varName, double value) {
        for(int i = 0; i < arguments.size(); i++){
            // if type of object is not a variable - call function again (recursive)
            if(arguments.get(i).getClass() != VariableElement.class ){
                arguments.get(i).setVariableValue(varName, value);
            }
            else{
                // if variable has the same name as varName
                if(VariableElement.class.cast(arguments.get(i)).getName().equals(varName)){
                    // set value
                    VariableElement.class.cast(arguments.get(i)).setValue(value);
                    // set flag
                    VariableElement.class.cast(arguments.get(i)).setFlag(true);
                }
            }
        }
    }

    @Override
    public boolean isFullyGrounded() {
        for(int i = 0; i < arguments.size(); i++){
            // if type of object is not a variable - call function again (recursive)
            if(arguments.get(i).getClass() == VariableElement.class){
                // if variable - check its flag
                if(!VariableElement.class.cast(arguments.get(i)).getFlag()){
                    return false;              
                }
            }
            else
                if(arguments.get(i).getClass() != ConstantElement.class){
                   if(!arguments.get(i).isFullyGrounded())
                       return false;
                }
        }
        // if not false found (all flags of variables are true) - return true
        return true;
    }

    @Override
    public void addDelta(String varName){
        ConstantElement delta = new ConstantElement(0.0000001);
        for(int i = 0; i < arguments.size(); i++){
            // if type of object is not a variable - call function again (recursive)
            if(arguments.get(i).getClass() != VariableElement.class){
                arguments.get(i).addDelta(varName);
            }
            else{
                if(VariableElement.class.cast(arguments.get(i)).getName().equals(varName)){
                    PlusFunctionElement plus = new PlusFunctionElement();
                    plus.addArgument(arguments.get(i));
                    plus.addArgument(delta);
                    arguments.setElementAt(plus,i);
                    //arguments.remove(i);
                    return;
                }
            }
        }
    } 
    @Override
    public ArrayList<String> getVariables(){
        ArrayList variables = new ArrayList<String>();
        for(int i = 0; i < arguments.size(); i++){
            if(arguments.get(i).getClass() != VariableElement.class ){
                if(!arguments.get(i).getVariables().isEmpty())
                   for(int j = 0; j < arguments.get(i).getVariables().size(); j++){
                       if(!variables.contains(arguments.get(i).getVariables().get(j)))
                          variables.add(arguments.get(i).getVariables().get(j));
                   }
            } 
            else{
                System.out.println("before adding: ");
                if(!variables.contains(VariableElement.class.cast(arguments.get(i)).getName())){
                    variables.add(VariableElement.class.cast(arguments.get(i)).getName());
                    System.out.println("adding: " + VariableElement.class.cast(arguments.get(i)).getName());
                }
            }
        }
        return variables;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i));
        }
        return sb.toString();
    }
}