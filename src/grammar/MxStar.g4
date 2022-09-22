grammar MxStar;

program: (expr NewLine)* EOF;
expr
  : DecimalInt
  | expr '+' expr
  ;
NewLine: [\r\n]+;
DecimalInt: [1-9][0-9]* | '0';