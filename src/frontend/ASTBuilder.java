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
    boolean hasConstructor = false;
    for (var def : ctx.children)
      if (def instanceof FuncDefContext) {
        classDef.funcDefList.add((FuncDefNode) visit(def));
      } else if (def instanceof VarDefContext) {
        classDef.varDefList.add((VarDefNode) visit(def));
      } else if (def instanceof ClassBuildContext) {
        if (hasConstructor)
          throw new BaseError(new Position(ctx), "Multiple constructors");
        hasConstructor = true;
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
    funcDef.stmts = ((SuiteNode) visit(ctx.suite())).stmts;
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
  public Node visitType(MxParser.TypeContext ctx) {
    return new TypeNode(new Position(ctx), ctx.typeName().getText(), ctx.LBracket().size());
  }

  @Override
  public Node visitStatement(MxParser.StatementContext ctx) {
    if (ctx.suite() != null)
      return visit(ctx.suite());
    else if (ctx.varDef() != null)
      return visit(ctx.varDef());
    else if (ctx.exprStmt() != null)
      return visit(ctx.exprStmt());
    else if (ctx.ifStmt() != null)
      return visit(ctx.ifStmt());
    else if (ctx.forStmt() != null)
      return visit(ctx.forStmt());
    else if (ctx.whileStmt() != null)
      return visit(ctx.whileStmt());
    else if (ctx.returnStmt() != null)
      return visit(ctx.returnStmt());
    else if (ctx.breakStmt() != null)
      return visit(ctx.breakStmt());
    else if (ctx.continueStmt() != null)
      return visit(ctx.continueStmt());
    else
      return visitChildren(ctx); // no need to change
  }

  @Override
  public Node visitIfStmt(MxParser.IfStmtContext ctx) {
    IfStmtNode ifStmt = new IfStmtNode(new Position(ctx), (ExprNode) visit(ctx.expr()));
    if (ctx.statement(0).suite() != null)
      ifStmt.thenStmts = ((SuiteNode) visit(ctx.statement(0).suite())).stmts;
    else
      ifStmt.thenStmts.add((StmtNode) visit(ctx.statement(0)));
    if (ctx.Else() != null) {
      if (ctx.statement(1).suite() != null)
        ifStmt.elseStmts = ((SuiteNode) visit(ctx.statement(1).suite())).stmts;
      else
        ifStmt.elseStmts.add((StmtNode) visit(ctx.statement(1)));
    }
    return ifStmt;
  }

  @Override
  public Node visitWhileStmt(MxParser.WhileStmtContext ctx) {
    WhileStmtNode whileStmt = new WhileStmtNode(new Position(ctx), (ExprNode) visit(ctx.expr()));
    if (ctx.statement().suite() != null)
      whileStmt.stmts = ((SuiteNode) visit(ctx.statement().suite())).stmts;
    else
      whileStmt.stmts.add((StmtNode) visit(ctx.statement()));
    return whileStmt;
  }

  @Override
  public Node visitForStmt(MxParser.ForStmtContext ctx) {
    ForStmtNode forStmt = new ForStmtNode(new Position(ctx));
    if (ctx.statement().suite() != null)
      forStmt.stmts = ((SuiteNode) visit(ctx.statement().suite())).stmts;
    else
      forStmt.stmts.add((StmtNode) visit(ctx.statement()));
    if (ctx.forInit().varDef() != null)
      forStmt.varDef = (VarDefNode) visit(ctx.forInit().varDef());
    else
      forStmt.init = ((ExprStmtNode) visit(ctx.forInit().exprStmt())).expr;
    forStmt.cond = ((ExprStmtNode) visit(ctx.exprStmt())).expr;
    if (ctx.expr() != null)
      forStmt.step = (ExprNode) visit(ctx.expr());
    return forStmt;
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
    var expr = new ArrayExprNode(new Position(ctx), (ExprNode) visit(ctx.expr(0)), (ExprNode) visit(ctx.expr(1)));
    expr.str = ctx.getText();
    return expr;
  }

  @Override
  public Node visitLambdaExpr(MxParser.LambdaExprContext ctx) {
    LambdaExprNode lambdaExpr = new LambdaExprNode(new Position(ctx));
    lambdaExpr.isCapture = ctx.BAnd() != null;
    if (ctx.parameterList() != null)
      lambdaExpr.params = (ParameterListNode) visit(ctx.parameterList());
    lambdaExpr.stmts = ((SuiteNode) visit(ctx.suite())).stmts;
    if (ctx.exprList() != null)
      lambdaExpr.args = (ExprListNode) visit(ctx.exprList());
    return lambdaExpr;
  }

  @Override
  public Node visitMemberExpr(MxParser.MemberExprContext ctx) {
    var expr = new MemberExprNode(new Position(ctx), (ExprNode) visit(ctx.expr()), ctx.Identifier().getText());
    expr.str = ctx.getText();
    return expr;
  }

  @Override
  public Node visitAtomExpr(MxParser.AtomExprContext ctx) {
    var expr = (ExprNode) visitChildren(ctx); // no need to change
    expr.str = ctx.getText();
    return expr;
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
    return (ExprNode) visit(ctx.expr());
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