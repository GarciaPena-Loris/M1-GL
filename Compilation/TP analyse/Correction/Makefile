# $Id: 007ee4442e1f422952c2b6d2dfa9e15f1ad1a057 $

ANTLR=java -jar /usr/local/lib/antlr-4.6-complete.jar
JAVAC= javac
G4=Hello.g4 ExprArith.g4 ExprArithEval.g4 ExprArithAST.g4 ExprArithList.g4

bin:
	for i in $(G4); do $(ANTLR) $$i; done; \
        for i in $(G4); do tmp=`basename $$i .g4`; $(JAVAC) $$tmp*.java; done

clean:
	rm -f *~ *.class *.tokens *Lexer.java *Parser.java *Listener.java
