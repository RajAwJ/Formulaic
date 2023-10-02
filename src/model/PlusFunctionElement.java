package model;

public class PlusFunctionElement extends FunctionElement {

    public PlusFunctionElement() {
        super();
    }

    @Override
    public double evaluate(){
        double additionResult = 0.0;
        for(int i = 0; i < arguments.size(); i++){
            additionResult += arguments.get(i).evaluate();
        }
        return additionResult;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.size()-1; i++) {
            sb.append(arguments.get(i).toString()).append("+");
        }
        sb.append(arguments.get(arguments.size()-1));
        
        return sb.toString();
    }

    public FormulaElement clone(){
        PlusFunctionElement element = new PlusFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}
