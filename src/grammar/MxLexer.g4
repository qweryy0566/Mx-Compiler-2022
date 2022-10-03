lexer grammar MxLexer;

Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';

GThan: '>';
LThan: '<';
GEqual: '>=';
LEqual: '<=';
NEqual: '!=';
EEqual: '==';

LAnd: '&&';
LOr: '||';
LNot: '!';

RShift: '>>';
LShift: '<<';
BAnd: '&';
BOr: '|';
BXor: '^';
BNot: '~';

Assign: '=';

SelfAdd: '++';
SelfSub: '--';

Member: '.';

LBracket: '[';
RBracket: ']';

LParen: '(';
RParen: ')';

Semi: ';';
Comma: ',';
LBrace: '{';
RBrace: '}';

Quote: '"';
Arrow: '->';

Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
True: 'true';
False: 'false';
This: 'this';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';

Identifier: [A-Za-z][0-9A-Za-z_]*;

IntConst: [1-9][0-9]* | '0';
StringConst: Quote (PChar)* Quote;

WhiteSpace: [ \t\r\n]+ -> skip;

CommentLine: '//' ~[\r\n]* -> skip;
CommentPara: '/*' .*? '*/' -> skip;

PChar: [ -~] | '\\n' | '\\\\' | '\\"';
