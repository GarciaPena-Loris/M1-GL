package org.example.formula.terminal;

import java.util.HashMap;

public class Var extends TerminalFormula {
    private final String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public boolean eval(HashMap<String, Boolean> context) {
        return context.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String toStringContext(HashMap<String, Boolean> context) {
        boolean val = context.get(name);
        if (val) {
            return "⊤";
        } else {
            return "⊥";
        }
    }

    public String getName() {
        return name;
    }

}
