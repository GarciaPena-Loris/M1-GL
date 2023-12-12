package org.example.formula.nonTerminal;

import org.example.formula.Formula;

import java.util.HashMap;

public class Imp extends NonTerminalFormula {

    public Imp(Formula left, Formula right) {
        super(left, right);
    }

    @Override
    public boolean eval(HashMap<String, Boolean> eval) {
        return !left.eval(eval) || right.eval(eval);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " → " + right.toString() + ")";
    }

    public String toStringContext(HashMap<String, Boolean> context) {
        return "(" + left.toStringContext(context) + " → " + right.toStringContext(context) + ")";
    }

}

