package model;

public class MinusFunctionElement extends FunctionElement {

    MinusFunctionElement(){
        super();
    }
    
    @Override
    public double evaluate(){
        double subtractResult = arguments.get(0).evaluate();
        for(int i = 1; i < arguments.size(); i++){
            subtractResult -= arguments.get(i).evaluate();
        }
        return subtractResult;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.size()-1; i++) {
            sb.append(arguments.get(i).toString()).append("-");
        }
        sb.append(arguments.get(arguments.size()-1));
        
        return sb.toString().trim();
    }

    public FormulaElement clone(){
        MinusFunctionElement element = new MinusFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}
