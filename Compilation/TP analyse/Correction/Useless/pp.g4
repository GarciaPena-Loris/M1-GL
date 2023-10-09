grammar pp;

p: ('var' (ID ':' type)+)? d* i;

d:
	ID '(' (ID ':' type)* ')' (':' type)? ('var' (ID ':' type)+)? i;

i:
	ID ':=' e
	| e '[' e ']' ':=' e
	| 'if' e 'then' i 'else' i
	| 'while' e 'do' i
	| phi '(' e* ')'
	| 'skip'
	| i ';' i;

e:
	k
	| ID
	| uop e
	| e bop e
	| phi '(' e* ')'
	| e '[' e ']'
	| 'new array of' type '[' e ']';

phi: 'read' | 'write' | ID;

bop:
	'+'
	| '-'
	| '*'
	| '/'
	| 'and'
	| 'or'
	| '<'
	| '<='
	| '='
	| '!='
	| '>='
	| '>';

uop: '-' | 'not';

k: INTEGER | BOOLEAN;

type: 'integer' | 'boolean' | 'array of' type;

INTEGER: ('0' ..'9')+; // Entiers
BOOLEAN: 'true' | 'false'; // Booléens
ID: [a-zA-Z_][a-zA-Z0-9_]*; // Identifiants

WS: [ \t\r\n]+ -> skip; // Skip espace, tab, newline, etc.