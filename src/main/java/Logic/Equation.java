package Logic;

import java.util.Stack;

public class Equation {

    private String equation;
    private int number;

    public Equation(String equation, int number) {
        this.equation = infixToPostfix(equation);
        this.number = number;
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

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isOperator(String t) {
        return t.matches("[+-/*^]");
    }

    private double evaluate(double op1, double op2, String t) {
        if (t.equals("+")) {
            return op1 + op2;

        } else if (t.equals("-")) {
            return op1 - op2;

        } else if (t.equals("*")) {
            return op1 * op2;

        } else if (t.equals("^")) {
            return Math.pow(op1, op2);
        } else if (t.equals("/")) {
            return op1 / op2;
        }
        return -1;
    }

    private String infixToPostfix(String equation) {
        String[] tokens = equation.split(" ");

        String output = "";

        Stack<String> opStack = new Stack<String>();

        for (String t : tokens) {

            if (isNumber(t) || t.equals("x")) {
                output += t + " ";

            } else if (isFunction(t)) {
                opStack.push(t);

            } else if (isOperator(t)) {

                while (!opStack.empty() && (isFunction(opStack.peek())
                        || prsDifference(opStack.peek(), t) > 0
                        || (prsDifference(opStack.peek(), t) == 0
                        && getAssociation(t) != Association.LEFT)
                        && !t.equals("("))) {

                    output += opStack.pop() + " ";
                }
                opStack.push(t);

            } else if (t.equals("(")) {
                opStack.push(t);

            } else if (t.equals(")")) {

                while (!opStack.peek().equals("(")) {
                    output += opStack.pop() + " ";
                }
                if (opStack.peek().equals("(")) {
                    opStack.pop();
                }
            }
        }
        while (!opStack.empty()) {
            output += opStack.pop() + " ";
        }
        return output;
    }

    private int prsDifference(String op1, String op2) {
        int prs1 = 0;
        int prs2 = 0;

        if (op1.equals("^")) {
            prs1 = 3;

        } else if (op1.equals("*")) {
            prs1 = 2;

        } else if (op1.equals("/")) {
            prs1 = 2;

        } else if (op1.matches("[+]|-")) {
            prs1 = 1;
        }

        if (op2.equals("^")) {
            prs2 = 3;

        } else if (op2.equals("*")) {
            prs2 = 2;

        } else if (op2.equals("/")) {
            prs2 = 2;

        } else if (op2.matches("[+]|-")) {
            prs2 = 1;
        }
        return prs1 - prs2;
    }

    private Association getAssociation(String t) {
        if (t.matches("^")) {
            return Association.RIGHT;

        } else if (t.matches("-|/")) {
            return Association.LEFT;

        } else if (t.matches("[*]|[+]")) {
            return Association.BOTH;
        }
        return null;
    }

    private boolean isFunction(String t) {
        return t.matches("sin|cos|tan");
    }
    
    public int getNumber() {
        return this.number;
    }
}
