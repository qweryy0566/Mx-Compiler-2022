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

suite: statement*;

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
  | varDef
  | ifStmt | whileStmt | forStmt
  | breakStmt | continueStmt | returnStmt
  | exprStmt;

ifStmt
  : If '(' expr ')' statement (Else statement)?;
whileStmt
  : While '(' expr ')' statement;
forStmt
  : For '(' forInit exprStmt expr? ')' statement;
forInit
  : varDef | exprStmt;

breakStmt: Break Semi;
continueStmt: Continue Semi;
returnStmt: Return expr? Semi;

exprStmt: expr? Semi;
expr
  : '(' expr ')'                                      #parenExpr
  | '[' BAnd? ']' '(' parameterList? ')' Arrow '{' suite '}' '(' exprList? ')'
                                                      #lambdaExpr
  | New typeName (newArrayUnit)* ('(' ')')?           #newExpr
  // | New typeName ('(' ')')?                           #newClassExpr
  | expr op=Member Identifier                         #memberExpr
  | expr '[' expr ']'                                 #arrayExpr 
  | expr '(' exprList? ')'                            #funcExpr
  | op=(SelfAdd | SelfSub) expr                       #preAddExpr
  | <assoc=right> expr op=(SelfAdd | SelfSub)         #unaryExpr
  | <assoc=right> op=(LNot | BNot | Add | Sub) expr   #unaryExpr
  | expr op=(Mul | Div | Mod) expr                    #binaryExpr 
  | expr op=(Add | Sub) expr                          #binaryExpr                                              
  | expr op=(LShift | RShift) expr                    #binaryExpr 
  | expr op=(LThan | GThan | LEqual | GEqual) expr    #binaryExpr
  | expr op=(EEqual | NEqual) expr                    #binaryExpr
  | expr op=BAnd expr                                 #binaryExpr
  | expr op=BXor expr                                 #binaryExpr
  | expr op=BOr expr                                  #binaryExpr
  | expr op=LAnd expr                                 #binaryExpr
  | expr op=LOr expr                                  #binaryExpr
  | <assoc=right> expr op=Assign expr                 #assignExpr  
  | primary                                           #atomExpr
  ;  

newArrayUnit: '[' expr? ']';
// may have problem 
primary
  : IntConst | StringConst | True | False | Null
  | Identifier
  | This
  ;

exprList: expr (Comma expr)*;