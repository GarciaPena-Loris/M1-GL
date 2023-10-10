import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class ppTest {

    public static void main(String[] args) {

        String programCode = args[0];

        ppLexer lexer = new ppLexer(CharStreams.fromString(programCode));
        ppParser parser = new ppParser(new CommonTokenStream(lexer));

        ParseTree tree = parser.p();

        ParseTreeWalker walker = new ParseTreeWalker();
        ppBaseListener listener = new ppBaseListener();

        walker.walk(listener, tree);
    }
}