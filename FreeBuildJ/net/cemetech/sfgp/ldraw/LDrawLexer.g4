/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

lexer grammar LDrawLexer;

/*
 * Lexer Rules
 */

fragment LETTER : [a-zA-Z];
fragment SPECIAL : [._,\-~/\\#:()[\]];
fragment HEXPREFIX : '0x';
fragment COLPREFIX : '#';
fragment DIGIT : [0-9];
fragment HEXDIGIT : [0-9a-fA-F];
fragment SIGN :	[+\-];
fragment EXP : ([Ee] SIGN? DIGIT+);
fragment META_START : '!';

FIVE : '5';
FOUR : '4';
THREE : '3';
TWO : '2';
ONE : '1';
ZERO : '0';


HEXINT : (HEXPREFIX HEXDIGIT+) 
       | (COLPREFIX HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT);

INT : SIGN? DIGIT+;
FLOAT : (SIGN? DIGIT+ '.' DIGIT* EXP?)
      | (SIGN? DIGIT* '.' (DIGIT)+ EXP?)
      | (SIGN? DIGIT+ EXP);



WS : [ \t]+ -> skip;
EOL : '\n' |('\r' '\n'?);
STEP: 'STEP';
PAUSE:	'PAUSE';
WRITE:	'WRITE' | PRINT;
PRINT:	'PRINT';
CLEAR:	'CLEAR';
SAVE:	'SAVE';
COLOUR:	META_START 'COLOUR';
CODE	: 'CODE';
VALUE	:'VALUE';
EDGE	:'EDGE';
ALPHA	:'ALPHA';
LUMINANCE :'LUMINANCE';
CHROME	:'CHROME';
PEARLESCENT :'PEARLESCENT';
RUBBER :'RUBBER';
MATTE_METALLIC :'MATTE_METALLIC';
METAL :	'METAL';
MATERIAL :'MATERIAL';
FILE :	'FILE';

BFC : 'BFC';
NO : 'NO';
CERTIFICATION : 'CERTIFY';
ORIENTATION : 'C'?'CW';
CLIPPING : 'CLIP';
INVERTNEXT : 'INVERTNEXT';


IDENT : (LETTER|DIGIT|SPECIAL)+ | (STRING);

STRING : '"' (~["\r\n])+? '"';
GARBAGE : (~[ \t\r\n])+?;



