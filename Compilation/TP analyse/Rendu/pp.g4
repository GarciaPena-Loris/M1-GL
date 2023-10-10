grammar pp;

p
	returns[PPProg value]: (isglob = 'var' globals = listPair)? defs = listd code = i {
		if ($isglob != null) {
			$value = new PPProg($globals.value, $defs.value, $code.value);
		}
		else {
			$value = new PPProg(new ArrayList<Pair<String, Type>>(), $defs.value, $code.value);
		}
	};

listd
	returns[ArrayList<PPDef> value]
	@init {$value = new ArrayList<PPDef>();}: (dv = d {$value.add($dv.value);})+;

d
	returns[PPDef value]:
	name = ID '(' (args = listPair)? ')' (isret = ':' ret = type)? (isloc = 'var' local = listPair)? code = i {
        if ($isret != null) {
			if ($isloc != null) {
				$value = new PPFun($name.text, $args.value, $local.value, $code.value, $ret.value);
			} else {
				$value = new PPFun($name.text, $args.value, new ArrayList<Pair<String, Type>>(), $code.value, $ret.value);
			}
        } else {
			if ($isloc != null) {
				$value = new PPProc($name.text, $args.value, $local.value, $code.value);
			} else {
				$value = new PPProc($name.text, $args.value, new ArrayList<Pair<String, Type>>(), $code.value);
			}
        }
    };

listPair
	returns[ArrayList<Pair<String, Type>> value]
	@init {$value = new ArrayList<Pair<String, Type>>();}: (pr = pair {$value.add($pr.value);})+;

pair
	returns[Pair<String, Type> value]:
	(n = ID ':' t = type) {$value = new Pair($n.text, $t.value);};

i
	returns[PPInst value]:
	name = ID ':=' val = e {$value = new PPAssign($name.text, $val.value);}
	| arr = e '[' index = e ']' ':=' val = e {$value = new PPArraySet($arr.value, $index.value, $val.value);		
		}
	| 'if' cond = e 'then' i1 = i 'else' i2 = i {$value = new PPCond($cond.value, $i1.value, $i2.value);
		}
	| 'while' cond = e 'do' i1 = i {$value = new PPWhile($cond.value, $i1.value);}
	| callee = phi '(' args = liste ')' {$value = new PPProcCall($callee.value, $args.value);}
	| 'skip' {$value = new PPSkip();}
	| i1 = i ';' i2 = i {$value = new PPSeq($i1.value, $i2.value);};

e
	returns[PPExpr value]:
	cte = k {$value = $cte.value;}
	| id = ID {$value = new PPVar($id.text);}
	| '-' op1 = e {$value = new PPInv($op1.value);}
	| 'not' op2 = e {$value = new PPNot($op2.value);}
	| op1 = e '+' op2 = e {$value = new PPAdd($op1.value, $op2.value);}
	| op1 = e '-' op2 = e {$value = new PPSub($op1.value, $op2.value);}
	| op1 = e '*' op2 = e {$value = new PPMul($op1.value, $op2.value);}
	| op1 = e '/' op2 = e {$value = new PPDiv($op1.value, $op2.value);}
	| op1 = e 'and' op2 = e {$value = new PPAnd($op1.value, $op2.value);}
	| op1 = e 'or' op2 = e {$value = new PPOr($op1.value, $op2.value);}
	| op1 = e '<' op2 = e {$value = new PPLt($op1.value, $op2.value);}
	| op1 = e '<=' op2 = e {$value = new PPLe($op1.value, $op2.value);}
	| op1 = e '=' op2 = e {$value = new PPEq($op1.value, $op2.value);}
	| op1 = e '!=' op2 = e {$value = new PPNe($op1.value, $op2.value);}
	| op1 = e '>=' op2 = e {$value = new PPGe($op1.value, $op2.value);}
	| op1 = e '>' op2 = e {$value = new PPGt($op1.value, $op2.value);}
	| callee = phi '(' args = liste ')' {$value = new PPFunCall($callee.value, $args.value);}
	| arr = e '[' index = e ']' {$value = new PPArrayGet($arr.value, $index.value);}
	| 'new array of' t = type '[' size = e ']' {$value = new PPArrayAlloc($t.value, $size.value);
		};

liste
	returns[ArrayList<PPExpr> value]
	@init {$value = new ArrayList<PPExpr>();}: (expr = e {$value.add($expr.value);})+;

phi
	returns[Callee value]:
	'read' {$value = new Read();}
	| 'write' {$value = new Write();}
	| name = ID {$value = new User($name.text);};

bop
	returns[PPBinOp value]:
	op1 = e '+' op2 = e {$value = new PPAdd($op1.value, $op2.value);}
	| op1 = e '-' op2 = e {$value = new PPSub($op1.value, $op2.value);}
	| op1 = e '*' op2 = e {$value = new PPMul($op1.value, $op2.value);}
	| op1 = e '/' op2 = e {$value = new PPDiv($op1.value, $op2.value);}
	| op1 = e 'and' op2 = e {$value = new PPAnd($op1.value, $op2.value);}
	| op1 = e 'or' op2 = e {$value = new PPOr($op1.value, $op2.value);}
	| op1 = e '<' op2 = e {$value = new PPLt($op1.value, $op2.value);}
	| op1 = e '<=' op2 = e {$value = new PPLe($op1.value, $op2.value);}
	| op1 = e '=' op2 = e {$value = new PPEq($op1.value, $op2.value);}
	| op1 = e '!=' op2 = e {$value = new PPNe($op1.value, $op2.value);}
	| op1 = e '>=' op2 = e {$value = new PPGe($op1.value, $op2.value);}
	| op1 = e '>' op2 = e {$value = new PPGt($op1.value, $op2.value);};

uop
	returns[PPUnOp value]:
	'-' op1 = e {$value = new PPInv($op1.value);}
	| 'not' op2 = e {$value = new PPNot($op2.value);};

k
	returns[PPExpr value]:
	inte = INTEGER {$value = new PPCte(Integer.parseInt($inte.text));}
	| bool = BOOLEAN {
		if ($bool.text == "true") {
            $value = new PPTrue();
        } else {
            $value = new PPFalse();
        }
	};

type
	returns[Type value]:
	'integer' {$value = new Int();}
	| 'boolean' {$value = new Bool();}
	| 'array of' t = type {$value = new Array($t.value);};

INTEGER: ('0' ..'9')+;
// Entiers
BOOLEAN: 'true' | 'false';
// Booléens
ID: [a-zA-Z_][a-zA-Z0-9_]*;
// Identifiants

WS: [ \t\r\n]+ -> skip;
// Skip espace, tab, newline