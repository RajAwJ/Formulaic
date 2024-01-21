package model;

public class ConstantElement extends FormulaElement{
    private double value;
    
    ConstantElement(double value){
        this.value = value;
    }
    
    public double getValue(){
        return value;
    }

    @Override
    public boolean isFullyGrounded() {
        return true;
    }
    
    @Override
    public double evaluate(){
        return value;
    }

    @Override
    public String toString(){
        int i = (int)value;
        if(value == i){
           return i + "" ;
        }
        return value + "";
    }

    @Override
    public FormulaElement clone() {
        ConstantElement constantElement = new ConstantElement(value);
        return constantElement; 
    }
}
