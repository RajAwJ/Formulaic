package model;

import java.util.ArrayList;

public class VariableElement extends FormulaElement {
    boolean flag;
    double value;
    String name;
    
    VariableElement(String name){
        this.name= name;
        this.flag = false;
    }
    
    public String getName(){
        return name;
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        this.value = value;
    }
    
    public void setFlag(boolean flag){
        this.flag = flag;
    }
    
    public boolean getFlag(){
        return flag;
    }
    public ArrayList<String> getVariables(){
        ArrayList<String> names = new ArrayList<String>();
        names.add(name);
        return names;
    }
    @Override
    public void setVariableValue(String varName, double value) {
        if(varName.equals(name))
            this.value = value;
    }
    
    @Override
    public double evaluate(){
        return value;
    }
    
    @Override
    public String toString(){
        return name;
    }

    @Override
    public FormulaElement clone() {
        VariableElement variableElement = new VariableElement(name);
        return variableElement; 
    }
}
