package model;

public class CosineFunctionElement extends FunctionElement {
    
    CosineFunctionElement(){
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
        return Math.cos(arguments.get(0).evaluate());
    }
    
    @Override
    public String toString(){
        return "cos(" + arguments.get(0) + ")";
    }

    public FormulaElement clone(){
        CosineFunctionElement element = new CosineFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}