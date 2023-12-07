package org.example.formula;

import java.util.HashMap;

public abstract class Formula implements IFormula {

    protected HashMap<String, Boolean> context;

    public abstract boolean eval(HashMap<String, Boolean> context);

    @Override
    public IFormula simplify() {
        return this;
    }

    public void setContext(HashMap<String, Boolean> context) {
        this.context = context;
    }

    public HashMap<String, Boolean> getContext() {
        return context;
    }

    public abstract String toStringContext(HashMap<String, Boolean> context);

}
