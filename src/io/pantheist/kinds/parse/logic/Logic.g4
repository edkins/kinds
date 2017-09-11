grammar Logic;
r : 'global' ID ':' ID ';' EOF;
ID: [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;
