package ast;

public interface ASTVisitor {
  void visit(ProgramNode node);

  void visit(FuncDefNode node);
  void visit(ClassDefNode node);
  void visit(VarDefNode node);
  void visit(TypeNode node);
}
