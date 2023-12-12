package org.example.formula.nonTerminal;

import org.example.formula.Formula;

import java.util.HashMap;

public class Not extends NonTerminalFormula {
    public Not(Formula left) {
        super(left, null);
    }

    @Override
    public boolean eval(HashMap<String, Boolean> eval) {
        return !left.eval(eval);
    }

    @Override
    public String toString() {
        return "¬" + left.toString();
    }

    public String toStringContext(HashMap<String, Boolean> context) {
        return "¬" + left.toStringContext(context);
    }

}
