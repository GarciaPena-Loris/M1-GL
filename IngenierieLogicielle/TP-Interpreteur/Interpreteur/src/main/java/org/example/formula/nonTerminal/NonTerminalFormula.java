package org.example.formula.nonTerminal;

import org.example.formula.Formula;

public abstract class NonTerminalFormula extends Formula {
    protected Formula left;
    protected Formula right;

    public NonTerminalFormula(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Formula getLeft() {
        return left;
    }

    public Formula getRight() {
        return right;
    }
}
