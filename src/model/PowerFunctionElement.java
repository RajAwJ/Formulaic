package model;

public class PowerFunctionElement extends FunctionElement{
    
    PowerFunctionElement(){
        super();
    }
    
    @Override
    public double evaluate(){
        double powerResult = arguments.get(0).evaluate();
        for(int i = 1; i < arguments.size(); i++){
            powerResult = Math.pow(powerResult,arguments.get(i).evaluate());
        }
        return powerResult;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.size() - 1; i++) {
            if (arguments.size() > 1 && (arguments.get(i).getClass() == MinusFunctionElement.class || arguments.size() > 1 && arguments.get(i).getClass() == PlusFunctionElement.class)) {
                sb.append("(").append(arguments.get(i).toString()).append(")^");
            }
            else
                sb.append(arguments.get(i).toString()).append("^");
        }
        sb.append(arguments.get(arguments.size()-1).toString());
        return sb.toString();
    }

    public FormulaElement clone(){
        PowerFunctionElement element = new PowerFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}
