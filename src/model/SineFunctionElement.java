package model;

public class SineFunctionElement extends FunctionElement {

    SineFunctionElement(){
        super();
    }
    
    @Override
    public void addArgument(FormulaElement element) {
        if(arguments.isEmpty()){
            arguments.add(element);
        }
        else{
            throw new IllegalVariableUpdate(
                "Function already contains one argument: <" + arguments.get(0) + ">");
        }
    }
    
    @Override
    public double evaluate(){
            return Math.sin(arguments.get(0).evaluate());
    }
    
    @Override
    public String toString(){
        return "sin(" + arguments.get(0) + ")";
    }

    public FormulaElement clone(){
        SineFunctionElement element = new SineFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}