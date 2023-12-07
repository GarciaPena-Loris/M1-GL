package org.example.formula;

import org.example.formula.nonTerminal.NonTerminalFormula;
import org.example.formula.nonTerminal.Not;
import org.example.formula.terminal.Var;

import java.util.ArrayList;

public class GetVars {
    public static ArrayList<String> exec(Formula formula) {
        ArrayList<String> res = new ArrayList<>();
        if (formula instanceof NonTerminalFormula) {
            if (formula instanceof Not) {
                res.addAll(exec(((NonTerminalFormula) formula).getLeft()));
            } else {
                res.addAll(exec(((NonTerminalFormula) formula).getLeft()));
                res.addAll(exec(((NonTerminalFormula) formula).getRight()));
            }
        } else {
            if (formula instanceof Var) {
                res.add(((Var) formula).getName());
            }
        }
        return res;
    }

}
