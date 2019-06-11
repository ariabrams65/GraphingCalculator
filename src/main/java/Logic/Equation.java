package Logic;

import java.util.Stack;

public class Equation {

    private String equation;

    public Equation(String equation) {
        this.equation = equation;
    }

    public double getYValue(double x) {
        String newEquation = equation.replace("x", Double.toString(x));

        Stack<Double> stack = new Stack<Double>();

        String[] list = newEquation.split(" ");

        for (String t : list) {

            if (isNumber(t)) {
                stack.push(Double.parseDouble(t));

            } else if (isOperator(t)) {
                double op2 = stack.pop();
                double op1 = stack.pop();

                stack.push(evaluate(op1, op2, t));
            }
        }
        return stack.pop();
    }

    private static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isOperator(String t) {
        return t.matches("[+-/*^]"); 
    }

    private static double evaluate(double op1, double op2, String t) {
        if (t.equals("+")) {
            return op1 + op2;

        } else if (t.equals("-")) {
            return op1 - op2;

        } else if (t.equals("*")) {
            return op1 * op2;
            
        } else if (t.equals("^")) {
            return Math.pow(op1, op2);
        }
        return -1;
    }
}
