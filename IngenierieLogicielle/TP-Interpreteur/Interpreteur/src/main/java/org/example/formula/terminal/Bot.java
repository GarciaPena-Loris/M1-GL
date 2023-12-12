package org.example.formula.terminal;

import java.util.HashMap;

public class Bot extends TerminalFormula {

    @Override
    public boolean eval(HashMap<String, Boolean> eval) {
        return false;
    }

    @Override
    public String toString() {
        return "⊥";
    }

    public String toStringContext(HashMap<String, Boolean> context) {
        return "⊥";
    }

}
