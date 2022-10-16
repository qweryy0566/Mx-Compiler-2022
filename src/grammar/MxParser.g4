parser grammar MxParser;
options {
	tokenVocab = MxLexer;
}

@header { package grammar; }

program: (funcDef | classDef | varDef)* EOF;

funcDef
  : returnType Identifier '(' parameterList? ')' '{' suite '}';
returnType
  : type | Void;
parameterList
  : (type Identifier) (Comma type Identifier)*;

suite
  : (varDef | statement)*;

classDef
  : Class Identifier '{' (varDef | classBuild | funcDef)* '}' Semi;
classBuild
  : Identifier '(' ')' '{' suite '}';

varDef
  : type varDefUnit (Comma varDefUnit)* Semi;
varDefUnit
  : Identifier (Assign expr)?;
type: typeName ('[' ']')*;
typeName: baseType | Identifier;
baseType: Int | Bool | String;

statement
  : '{' suite '}'
  | ifStmt | whileStmt | forStmt
  | breakStmt | continueStmt | returnStmt
  | exprStmt;

ifStmt
  : If '(' expr ')' statement (Else statement)?;
whileStmt
  : While '(' expr ')' statement;
forStmt
  : For '(' forInit expr? Semi expr? ')' statement;
forInit
  : varDef | exprStmt;

breakStmt: Break Semi;
continueStmt: Continue Semi;
returnStmt: Return expr? Semi;

exprStmt: expr? Semi;
expr
  : '(' expr ')'
  | expr '[' expr ']' 
  | expr Member Identifier
  | expr '(' exprList? ')'
  | preAddSub expr
  | lambdaExpr
  | New typeName ('[' expr? ']')* ('(' expr ')')?
  | <assoc=right> expr postAddSub
  | <assoc=right> opLevel2 expr
  | expr opLevel3 expr
  | expr opLevel4 expr
  | expr opLevel5 expr
  | expr opLevel6 expr
  | expr opLevel7 expr
  | expr BAnd expr
  | expr BXor expr
  | expr BOr expr
  | expr LAnd expr
  | expr LOr expr
  | <assoc=right> expr Assign expr
  | atomExpr
  ;

lambdaExpr
  : '[' BAnd? ']' '(' parameterList? ')' Arrow '{' suite '}' '(' exprList? ')';
// may have problem 
atomExpr
  : IntConst | StringConst | True | False | Null | This
  | Identifier
  | This
  ;

exprList: expr (Comma expr)*;
preAddSub: SelfAdd | SelfSub;
postAddSub: SelfAdd | SelfSub;
opLevel2: LNot | BNot | Add | Sub;
opLevel3: Mul | Div | Mod;
opLevel4: Add | Sub;
opLevel5: LShift | RShift;
opLevel6: LThan | GThan | LEqual | GEqual;
opLevel7: EEqual | NEqual;