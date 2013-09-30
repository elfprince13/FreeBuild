/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

parser grammar LDrawParser;
options {
	tokenVocab	= LDrawLexer;
}

parsedModel : mpdModel | ldrdatModel;

mpdModel : mpdEmbeddedModel+;

mpdEmbeddedModel : mpdFileLine EOL ldrdatModel (mpdNoFileLine (EOL | EOF) mpdDiscard)?;

mpdDiscard : (GARBAGE | (ldrawStmt EOL))*?;

ldrdatModel : ldrawLines;

ldrawLines	: ldrawStmt (EOL ldrawStmt)+ EOL*;

ldrawStmt	: stepLine
| pauseLine
| writeLine
| clearLine
| saveLine
| objectLine
| lineLine
| triLine
| quadLine
| fiveLine
| colorLine
| bfcMetaLine
| blankLine
| commentLine;

decInt	: ZERO | ONE | TWO | THREE | FOUR | FIVE | INT;

number		: (HEXINT | FLOAT) | decInt ;

stepLine	: ZERO STEP words;

pauseLine	: ZERO PAUSE words;

writeLine	: ZERO WRITE words;

clearLine	: ZERO CLEAR words;

colorNum	: decInt | HEXINT;

colorLine	: ZERO COLOUR name CODE colorNum VALUE colorNum words;

mpdFileLine	: ZERO FILE name;
mpdNoFileLine   : ZERO NO FILE;

bfcMetaLine : bfcPrefix (bfcCert | bfcNoCert | bfcClipWind | INVERTNEXT);

bfcCert : CERTIFICATION ORIENTATION?;
bfcNoCert : NO CERTIFICATION;
bfcClipWind : (CLIPPING ORIENTATION?) | (ORIENTATION CLIPPING?) | bfcNoClip;
bfcNoClip : NO CLIPPING;
bfcPrefix : ZERO BFC;


saveLine	: ZERO SAVE words;
	
keyWord : STEP
| PAUSE
| WRITE
| CLEAR
| SAVE
| COLOUR
| CODE
| VALUE
| EDGE
| ALPHA
| LUMINANCE
| CHROME
| PEARLESCENT
| RUBBER
| MATTE_METALLIC
| METAL
| MATERIAL
| NO
| FILE
| BFC
| CERTIFICATION
| ORIENTATION
| CLIPPING
| INVERTNEXT;
	
name	: IDENT+;

commentLine	: ZERO (unkeyWord words)?;

transMatrix	: position number number number number number number number number number;

position	: number number number;

objectLine	: ONE colorNum transMatrix name words;

lineLine	: TWO colorNum position position words;

triLine	: THREE colorNum position position position words;

quadLine	: FOUR colorNum position position position position words;

fiveLine	: FIVE colorNum position position position position words;

unkeyWord : name | decInt | (GARBAGE | HEXINT | FLOAT);
word		: keyWord | unkeyWord;

words		: word*;

blankLine	: ;

