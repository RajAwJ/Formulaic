package model;

public class DivideFunctionElement extends FunctionElement {

    DivideFunctionElement(){
        super();
    }
    
    @Override
    public double evaluate(){
        double divisionResult = arguments.get(0).evaluate();
        for(int i = 1; i < arguments.size(); i++){
            divisionResult /= arguments.get(i).evaluate();
        }
        return divisionResult;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arguments.size() - 1; i++) {
            if (arguments.size() > 1 && (arguments.get(i).getClass() == MinusFunctionElement.class || arguments.get(i).getClass() == PlusFunctionElement.class)) {
                sb.append("(").append(arguments.get(i).toString()).append(")").append("/");
            }
            else{
                sb.append(arguments.get(i).toString()).append("/");
            }     
        }
        if(arguments.size() > 1 && (arguments.get(arguments.size() - 1).getClass() == MinusFunctionElement.class || arguments.get(arguments.size() - 1).getClass() == PlusFunctionElement.class))
            sb.append("(").append(arguments.get(arguments.size() - 1).toString()).append(")");
        else
            sb.append(arguments.get(arguments.size() - 1).toString());
        return sb.toString();
    }

    public FormulaElement clone(){
        DivideFunctionElement element = new DivideFunctionElement();
        for (FormulaElement formulaElement : arguments) {
            element.addArgument(formulaElement.clone());
        }
        return element;
    }
}