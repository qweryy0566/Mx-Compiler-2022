package frontend;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import grammar.*;
import grammar.MxParser.*;

public class ASTBuilder extends MxParserBaseVisitor<Node> {
  @Override
  public Node visitProgram(MxParser.ProgramContext ctx) {
    ProgramNode program = new ProgramNode(new Position(ctx));
    for (var def : ctx.children)
      if (def instanceof ClassDefContext) {
        program.defList.add((ClassDefNode) visit(def));
        // program.regi
      } else if (def instanceof FuncDefContext) {
        program.defList.add((FuncDefNode) visit(def));
      } else if (def instanceof VarDefContext) {
        program.defList.add((VarDefNode) visit(def));
      }
    return program;
  }

  @Override
  public Node visitClassDef(MxParser.ClassDefContext ctx) {
    ClassDefNode classDef = new ClassDefNode(new Position(ctx), ctx.Identifier().getText());
    for (var def : ctx.children)
      if (def instanceof FuncDefContext) {
        classDef.funcDefList.add((FuncDefNode) visit(def));
      } else if (def instanceof VarDefContext) {
        classDef.varDefList.add((VarDefNode) visit(def));
      } else if (def instanceof ClassBuildContext) {
        classDef.classBuild = (ClassBuildNode) visit(def);
      }
    return classDef;
  }

  @Override
  public Node visitFuncDef(MxParser.FuncDefContext ctx) {
    FuncDefNode funcDef = new FuncDefNode(new Position(ctx), ctx.Identifier().getText());
    funcDef.returnType = (TypeNode) visit(ctx.returnType());
    if (ctx.parameterList() != null)
      funcDef.params = (ParameterListNode) visit(ctx.parameterList());
    funcDef.suite = (SuiteNode) visit(ctx.suite());
    return funcDef;
  }

  @Override
  public Node visitReturnType(MxParser.ReturnTypeContext ctx) {
    if (ctx.Void() != null)
      return new TypeNode(new Position(ctx), ctx.getText());
    else
      return (TypeNode) visit(ctx.type());
  }

  @Override
  public Node visitParameterList(MxParser.ParameterListContext ctx) {
    ParameterListNode parameterList = new ParameterListNode(new Position(ctx));
    for (int i = 0; i < ctx.type().size(); ++i)
      parameterList.units.add(new VarDefUnitNode(
          new Position(ctx.type(i)),
          (TypeNode) visit(ctx.type(i)),
          ctx.Identifier(i).getText(),
          null));
    return parameterList;
  }

  @Override
  public Node visitSuite(MxParser.SuiteContext ctx) {
    SuiteNode suite = new SuiteNode(new Position(ctx));
    ctx.statement().forEach(stmt -> suite.stmts.add((StmtNode) visit(stmt)));
    return suite;
  }

  @Override
  public Node visitClassBuild(MxParser.ClassBuildContext ctx) {
    ClassBuildNode classBuild = new ClassBuildNode(
        new Position(ctx),
        ctx.Identifier().getText(),
        (SuiteNode) visit(ctx.suite()));
    return classBuild;
  }

  @Override
  public Node visitVarDef(MxParser.VarDefContext ctx) {
    VarDefNode varDef = new VarDefNode(new Position(ctx));
    TypeNode type = (TypeNode) visit(ctx.type());
    for (var unit : ctx.varDefUnit())
      varDef.units.add((new VarDefUnitNode(
          new Position(unit),
          type,
          unit.Identifier().getText(),
          unit.expr() == null ? null : (ExprNode) visit(unit.expr()))));
    return varDef;
  }

  @Override
  public Node visitVarDefUnit(MxParser.VarDefUnitContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitType(MxParser.TypeContext ctx) {
    return new TypeNode(new Position(ctx), ctx.typeName().getText(), ctx.LBracket().size());
  }

  @Override
  public Node visitTypeName(MxParser.TypeNameContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitBaseType(MxParser.BaseTypeContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitStatement(MxParser.StatementContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitIfStmt(MxParser.IfStmtContext ctx) {
    return new IfStmtNode(
        new Position(ctx),
        (ExprNode) visit(ctx.expr()),
        (StmtNode) visit(ctx.statement(0)),
        ctx.Else() != null ? (StmtNode) visit(ctx.statement(1)) : null);
  }

  @Override
  public Node visitWhileStmt(MxParser.WhileStmtContext ctx) {
    return new WhileStmtNode(
        new Position(ctx),
        (ExprNode) visit(ctx.expr()),
        (StmtNode) visit(ctx.statement()));

  }

  @Override
  public Node visitForStmt(MxParser.ForStmtContext ctx) {
    ForStmtNode forStmt = new ForStmtNode(
        new Position(ctx),
        (ExprNode) visit(ctx.expr(0)),
        (ExprNode) visit(ctx.expr(1)),
        (StmtNode) visit(ctx.statement()));
    if (ctx.forInit().varDef() != null)
      forStmt.varDef = (VarDefNode) visit(ctx.forInit().varDef());
    else
      forStmt.init = ((ExprStmtNode) visit(ctx.forInit().exprStmt())).expr;
    return forStmt;
  }

  @Override
  public Node visitForInit(MxParser.ForInitContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitBreakStmt(MxParser.BreakStmtContext ctx) {
    return new BreakNode(new Position(ctx));
  }

  @Override
  public Node visitContinueStmt(MxParser.ContinueStmtContext ctx) {
    return new ContinueNode(new Position(ctx));
  }

  @Override
  public Node visitReturnStmt(MxParser.ReturnStmtContext ctx) {
    return new ReturnStmtNode(new Position(ctx),
        ctx.expr() == null ? null : (ExprNode) visit(ctx.expr()));
  }

  @Override
  public Node visitExprStmt(MxParser.ExprStmtContext ctx) {
    return new ExprStmtNode(new Position(ctx), ctx.expr() == null ? null : (ExprNode) visit(ctx.expr()));
  }

  @Override
  public Node visitNewExpr(MxParser.NewExprContext ctx) {
    NewExprNode newExpr = new NewExprNode(new Position(ctx), ctx.typeName().getText());
    newExpr.dim = ctx.newArrayUnit().size();
    boolean isEmpty = false;
    for (var unit : ctx.newArrayUnit()) {
      if (unit.expr() == null)
        isEmpty = true;
      else if (isEmpty)
        throw new BaseError(new Position(ctx), "Array dimension cannot be empty");
      else
        newExpr.sizeList.add((ExprNode) visit(unit.expr()));

    }
    return newExpr;
  }

  @Override
  public Node visitNewArrayUnit(MxParser.NewArrayUnitContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitUnaryExpr(MxParser.UnaryExprContext ctx) {
    return new UnaryExprNode(new Position(ctx), ctx.op.getText(), (ExprNode) visit(ctx.expr()));
  }

  @Override
  public Node visitPreAddExpr(MxParser.PreAddExprContext ctx) {
    return new PreAddExprNode(new Position(ctx), ctx.op.getText(), (ExprNode) visit(ctx.expr()));
  }

  @Override
  public Node visitFuncExpr(MxParser.FuncExprContext ctx) {
    FuncExprNode funcExpr = new FuncExprNode(new Position(ctx), (ExprNode) visit(ctx.expr()));
    if (ctx.exprList() != null)
      funcExpr.args = (ExprListNode) visit(ctx.exprList());
    return funcExpr;
  }

  @Override
  public Node visitArrayExpr(MxParser.ArrayExprContext ctx) {
    return new ArrayExprNode(new Position(ctx), (ExprNode) visit(ctx.expr(0)), (ExprNode) visit(ctx.expr(1)));
  }

  @Override
  public Node visitLambdaExpr(MxParser.LambdaExprContext ctx) {
    LambdaExprNode lambdaExpr = new LambdaExprNode(new Position(ctx));
    lambdaExpr.isCapture = ctx.BAnd() != null;
    if (ctx.parameterList() != null)
      lambdaExpr.params = (ParameterListNode) visit(ctx.parameterList());
    lambdaExpr.suite = (SuiteNode) visit(ctx.suite());
    if (ctx.exprList() != null)
      lambdaExpr.args = (ExprListNode) visit(ctx.exprList());
    return lambdaExpr;
  }

  @Override
  public Node visitMemberExpr(MxParser.MemberExprContext ctx) {
    return new MemberExprNode(new Position(ctx), (ExprNode) visit(ctx.expr()), ctx.Identifier().getText());
  }

  @Override
  public Node visitAtomExpr(MxParser.AtomExprContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitBinaryExpr(MxParser.BinaryExprContext ctx) {
    return new BinaryExprNode(
        new Position(ctx),
        (ExprNode) visit(ctx.expr(0)),
        ctx.op.getText(),
        (ExprNode) visit(ctx.expr(1)));
  }

  @Override
  public Node visitAssignExpr(MxParser.AssignExprContext ctx) {
    return new AssignExprNode(
        new Position(ctx),
        (ExprNode) visit(ctx.expr(0)),
        (ExprNode) visit(ctx.expr(1)));
  }

  @Override
  public Node visitParenExpr(MxParser.ParenExprContext ctx) {
    return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitPrimary(MxParser.PrimaryContext ctx) {
    return ctx.Identifier() == null
        ? new AtomExprNode(new Position(ctx), ctx.getText())
        : new VarExprNode(new Position(ctx), ctx.getText());
  }

  @Override
  public Node visitExprList(MxParser.ExprListContext ctx) {
    ExprListNode exprList = new ExprListNode(new Position(ctx));
    ctx.expr().forEach(expr -> exprList.exprs.add((ExprNode) visit(expr)));
    return exprList;
  }
}