package org.example.formula;

import java.util.HashMap;

public interface IFormula {

    boolean eval(HashMap<String, Boolean> context);
    IFormula simplify();

}

