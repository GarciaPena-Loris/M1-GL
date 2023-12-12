package org.example;

import org.example.formula.*;
import org.example.formula.nonTerminal.*;
import org.example.formula.terminal.Var;

import java.util.HashMap;

public class App {

    public static void main(String[] args) {
        System.out.println("--- \uD83D\uDDA9 Logic formula interpretor \uD83D\uDDA9 ---");
        Formula f = new Imp(new And(new Or(new Var("A"), new Var("B")), new Not(new Var("C"))), new Var("D"));
        HashMap<String, Boolean> context = new HashMap<>();
        context.put("A", true);
        context.put("B", false);
        context.put("C", false);
        context.put("D", false);
        System.out.println("Formula ƒ: " + f);
        System.out.println("Context: (A, ⊤), (B, ⊥), (C, ⊥), (D, ⊥)");
        System.out.println("GetVars:" + GetVars.exec(f));
        System.out.println("Formula ƒ context: " + f.toStringContext(context));
        System.out.println("Eval: " + (f.eval(context) ? "⊤" : "⊥"));

        System.out.println("\n--- \uD83D\uDDA9 Logic formula interpretor 2 - XOR \uD83D\uDDA9 ---");
        Formula f2 = new Xor(new Var("A"), new Var("B"));

        HashMap<String, Boolean> context2 = new HashMap<>();
        context2.put("A", true);
        context2.put("B", false);
        System.out.println("Formula ƒ: " + f2);
        System.out.println("Context: (A, ⊤), (B, ⊥)");
        System.out.println("GetVars:" + GetVars.exec(f));
        System.out.println("Formula ƒ context: " + f2.toStringContext(context2));
        System.out.println("Eval: " + (f2.eval(context2) ? "⊤" : "⊥"));

        System.out.println("\n--- \uD83D\uDDA9 Logic formula interpretor 3 - XOR \uD83D\uDDA9 ---");

        System.out.println("Formula ƒ: " + f2);
        HashMap<String, Boolean> context3 = new HashMap<>();
        context3.put("A", true);
        context3.put("B", true);
        System.out.println("Formula ƒ: " + f2);
        System.out.println("Context: (A, ⊤), (B, ⊤)");
        System.out.println("GetVars:" + GetVars.exec(f));
        System.out.println("Formula ƒ context: " + f2.toStringContext(context3));
        System.out.println("Eval: " + (f2.eval(context3) ? "⊤" : "⊥"));

        System.out.println("\n--- \uD83D\uDDA9 Logic formula interpretor 4 - XOR \uD83D\uDDA9 ---");


        HashMap<String, Boolean> context4 = new HashMap<>();
        context4.put("A", false);
        context4.put("B", true);
        System.out.println("Formula ƒ: " + f2);
        System.out.println("Context: (A, ⊥), (B, ⊤)");
        System.out.println("GetVars:" + GetVars.exec(f));
        System.out.println("Formula ƒ context: " + f2.toStringContext(context4));
        System.out.println("Eval: " + (f2.eval(context4) ? "⊤" : "⊥"));

        System.out.println("\n--- \uD83D\uDDA9 Logic formula interpretor 5 - XOR \uD83D\uDDA9 ---");

        HashMap<String, Boolean> context5 = new HashMap<>();
        context5.put("A", false);
        context5.put("B", false);
        System.out.println("Formula ƒ: " + f2);
        System.out.println("Context: (A, ⊥), (B, ⊥)");
        System.out.println("GetVars:" + GetVars.exec(f));
        System.out.println("Formula ƒ context: " + f2.toStringContext(context5));
        System.out.println("Eval: " + (f2.eval(context5) ? "⊤" : "⊥"));

    }
}
