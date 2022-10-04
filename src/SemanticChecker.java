import grammar.*;
import java.util.HashMap;
import ast.*;

public class SemanticChecker extends MxParserBaseVisitor<Node> {
  HashMap<String, Node> symbolTable = new HashMap<String, Node>();

}