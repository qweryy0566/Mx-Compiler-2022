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

IntConst: [1-9][0-9]* | '0';

PChar: [ -~] | '\\n' | '\\\\' | '\\"';
StringConst: Quote (PChar)* Quote;

Comment: '//' ~[\r\n]* -> skip;

Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
Ture: 'true';
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

WhiteSpace: [ \t\r\n]+ -> skip;