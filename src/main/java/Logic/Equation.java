package Logic;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation {

    private String equation;

    public Equation(String equation) {
        this.equation = equation;
    }

    public double getYValue(double x) {
        String newEquation = equation.replace("x", Double.toString(x));
        
         String value = "-1";
        try {
            
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        value = engine.eval(newEquation).toString();
        
        } catch (ScriptException e) {
            System.out.println(e.getMessage());
        }
        return Double.parseDouble(value);
        
    }
}
