package org.example.formula.terminal;

import java.util.HashMap;

public class Top extends TerminalFormula {
    @Override
    public boolean eval(HashMap<String, Boolean> eval) {
        return true;
    }

    @Override
    public String toString() {
        return "⊤";
    }

    public String toStringContext(HashMap<String, Boolean> context) {
        return "⊤";
    }

}
