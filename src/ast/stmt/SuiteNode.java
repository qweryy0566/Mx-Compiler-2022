package ast.stmt;

import ast.*;
import java.util.ArrayList;

public class SuiteNode extends StmtNode {
    public ArrayList<StmtNode> stmts;
    public SuiteNode(ArrayList<StmtNode> stmts) {
        this.stmts = stmts;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}