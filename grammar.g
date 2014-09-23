statement: if_statement |  instruction |  ifelse_statement | procedure | equation ;

ifelse_statement: "ifelse" space compound_boolean space instruction space instruction;
if_statement: "if" space compound_boolean space instruction;
compound_boolean: compound_boolean space b_operation space boolean | boolean;
boolean: oparanthese space number space op_operation space number space cparanthese;
b_operation: 'and' | 'or';
op_operation: '<'| '<=' | '>=' | '>' | '==' | '!=';

operation: '+' | '-' | '*' | '/';
equation: number space operation space number;

instruction: repeat | calls | command;
repeat: "repeat" space number space instruction;

call: commands | procedure_calls;
calls: calls space call | call;

commands: obracket space commands space command space cbracket | obracket command cbracket; 
command: function_with_arguments space number | function;
function_with_arguments: 'fd' | 'rt' | 'lt' | 'bk';
function: 'pd' | 'pu' | 'home' | 'cs' | 'st' | 'ht';

parameters: parameters space parameter | parameter;
procedure: "to" space name space parameters space obracket space statement space cbracket;
procedure_call: obracket space name space numbers space cbracket | name space numbers | name space numbers space commands;
procedure_calls: obracket space procedure_calls space procedure_call space cbracket | procedure_call;
name: "\w+";
parameter: ":\w+";

obracket: "\[*" | "\[";
cbracket: "\]*" | "\]";
oparanthese: "\(*";
cparanthese: "\)*";
space: "\s*";
number: "\d+" | parameter | equation;
numbers: numbers space number | number;



