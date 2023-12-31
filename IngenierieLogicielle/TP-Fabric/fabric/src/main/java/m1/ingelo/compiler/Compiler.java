package m1.ingelo.compiler;

import java.io.File;

public class Compiler {
    protected CompilerFactory factory;
    protected Lexer lexer;
    protected Parser parser;
    protected PrettyPrinter prettyprinter;
    protected Generator gen;

    public Compiler(String languageName) throws Exception {
        factory = CompilerFactory.getFactory(languageName);
        lexer = factory.createLexer();
        parser = factory.createParser();
        gen = factory.createGenerator();
    }

    public void compile(ProgramText t) {
        System.out.println("Compiling a: " + factory.getLanguage() + " program.");
        ScanedText t2 = lexer.scan(t);
        AST tree = parser.parse(t2);
        File f = gen.generate(tree);
        System.out.println("Compilation finished");
    }

    public static void main(String[] args) {
        try {
            System.out.println("-----------------------");
            Compiler c1 = new Compiler("Java");
            c1.compile(new ProgramText("..."));
            System.out.println("-----------------------");
            Compiler c2 = new Compiler("C++");
            c2.compile(new ProgramText("..."));
            System.out.println("-----------------------");
            Compiler c3 = new Compiler("ADA");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

abstract class Lexer {
    public abstract ScanedText scan(ProgramText t);
};

abstract class Parser {
    public abstract AST parse(ScanedText t);
}

abstract class Generator {
    public abstract File generate(AST a);
}

abstract class PrettyPrinter {
    public abstract ProgramText prettyPrint(AST a);
}

class ProgramText {
    String text;

    ProgramText(String t) {
        text = t;
    }
}

class ScanedText {
}

class AST {
}

