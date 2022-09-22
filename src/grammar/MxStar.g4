grammar MxStar;

program: 'int main()' suite EOF;
suite: '{' statement* '}';

statement: Identifier '=' plus_minus_expr ';';
plus_minus_expr
  : DecimalInt
  | plus_minus_expr plus_minus_op DecimalInt;
plus_minus_op: '+' | '-';

LeftBrace: '{';
RightBrace: '}';

Plus: '+';
Minus: '-';
Equal: '=';
Semi: ';';

DecimalInt: [1-9][0-9]* | '0';
Identifier: [A-Za-z][0-9A-Za-z_]*;
WhiteSpace: [ \t\r\n]+ -> skip;