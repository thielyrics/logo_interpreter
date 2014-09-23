package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import turtle.*;
import edu.hendrix.earley.*;

public class Parser {
	private Grammar g;
	private ManageCanvas manager;
	private HashMap<String, Procedure> procedures;
	private HashMap<String, Integer> variables;
	
	public Parser(TurtlePane p) throws FileNotFoundException, MalformedGrammarException {
		g = Grammar.makeFrom(new File("grammar.g"));
		procedures = new HashMap<String, Procedure>();
		variables = new HashMap<String, Integer>();
	 	manager = p.getManager();
	}
	
	public String parseMe(String s) throws MalformedGrammarException {
		s = s.replace("]", ' '+"]");
		Tree t = g.parse(s);
		if(t.isError()) {
			return t.errorMessage();
		}
		else {
			traversal(t);
			return "";
		}
	}
	
	public void traversal(Tree t){
		if (t.numChildren() > 0) {
			System.out.println(t.name());
			if(t.name().equals("repeat")) { 
				for(int i = 0; i < getNum(t.namedChild("number")) - 1; i++) {
					traversal(t.namedChild("commands"));
				}
			}
			
			if(t.name().equals("if_statement")) { 
				if(evaluateCompound(t.namedChild("compound_boolean"))) { 
					traversal(t.namedChild("instruction"));
				}
			}
			
			if(t.name().equals("ifelse_statement")) { 
				if(evaluateCompound(t.namedChild("compound_boolean"))) { 
					traversal(t.namedChild("instruction"));
				}
				else { 
					traversal(t.namedChild("instruction", 1));
				}
			}
			if(t.name().equals("command")) { 
				command(t);
			}
			if(t.name().equals("procedure")) { 
				Procedure p = new Procedure(t);
				procedures.put(p.getName(), p);
			}
			if(t.name().equals("procedure_call")) {
				if(procedures.containsKey(t.namedChild("name").toString())) { 
					Procedure p = procedures.get(t.namedChild("name").toString());
					ArrayList<Integer> params = new ArrayList<Integer>(); 
					getParams(t, params);
					if(params.size() == p.numArgs()) {
						for(int i = 0; i < params.size(); i++) { 
							variables.put(p.getParams().get(i), params.get(i));
						}
						traversal(p.getStatement());
					}
					
				}
			}
			else {
				for(int i = 0; i < t.numChildren(); i++) {
					traversal(t.nthChild(0));  
				}
			}
		}
		
	}
	
	public int getParams(Tree t, ArrayList<Integer> params) {
		if(t.hasNamed("numbers")) { 
			return getParams(t.namedChild("numbers"), params);
		}
		
		if(t.hasNamed("number")) { 
			params.add(getNum(t.namedChild("number")));
			return 1;
		}
		
		else { 
			return 0;
		}
	}
	
	public int getNum(Tree t) {
		if(t.name().equals("number")) {
			if(t.hasNamed("parameter")) {
				if(variables.containsKey(t.namedChild("parameter").toString())) { 
					return variables.get(t.namedChild("parameter").toString());
				} else return 0;
			}
			else if(t.hasNamed("equation")) { 
				return equation(t.namedChild("equation"));
			}
			else { 
				return new Integer(t.toString());
			}
		} else { 
			return 0;
		}
	}
	
	public int equation(Tree t) {
		int i = getNum(t.namedChild("number")); 
		int j = getNum(t.namedChild("number", 1));
		
		return doOperation(i, j, t.namedChild("operation").toString());
	}
	
	public int doOperation(int i, int j, String s) { 
		if(s.equals("+")) { return i + j; }
		else if(s.equals("-")) { return i - j; }
		else if(s.equals("/")) { return i / j; }
		else { return i * j; }
	}
	
	public void command(Tree t) { 
		if(t.numChildren() > 1)  { 
			function_args(t);
		}
		else if(t.numChildren() == 1) { 
			function(t);
		}
	}
	
	public void function(Tree t) { 
		if(t.namedChild("function").toString().equals("home")) { 
			manager.sendHome();
		}
		
		else if (t.namedChild("function").toString().equals("pd")) { 
			manager.penDown();
		}
		
		else if (t.namedChild("function").toString().equals("pu")) { 
			manager.penUp();
		}
		
		else if (t.namedChild("function").toString().equals("cs")) { 
			manager.clear();
		}
		
		else if (t.namedChild("function").toString().equals("st")) { 
			manager.showTurtle();
		}
		
		else if (t.namedChild("function").toString().equals("ht")) { 
			manager.hideTurtle();
		}
		
	}
	
	public void function_args(Tree t) { 
		if(t.namedChild("function_with_arguments").toString().equals("fd")) { 
			System.out.println("fd " + getNum(t.namedChild("number")));
		}
		
		else if(t.namedChild("function_with_arguments").toString().equals("rt")) { 
			System.out.println("rt " + t.namedChild("number").toString());
		}
		
		else if(t.namedChild("function_with_arguments").toString().equals("lt")) { 
			System.out.println("lt " + t.namedChild("number").toString());
		}
		
		else if(t.namedChild("function_with_arguments").toString().equals("bk")) { 
			System.out.println("bk " + t.namedChild("number").toString());
		}
	}
	
	public boolean evaluateCompound(Tree t) { 
		if(t.name().equals("compound_boolean")) { 
			if(t.hasNamed("compound_boolean")) { 
				return evaluateOperation(t.namedChild("b_operation").toString(), evaluateCompound(t.namedChild("compound_boolean")), evaluate(t.namedChild("boolean")));
			}
			else if(t.numNamed("boolean") > 1) { 
				return evaluateOperation(t.namedChild("b_operation").toString(), evaluate(t.namedChild("boolean")), evaluate(t.namedChild("boolean", 1))); 
			}
			else { 
				return evaluate(t.namedChild("boolean"));
			}
		}
		return false;
	}
	
	public boolean evaluateOperation(String s, boolean t, boolean u) { 
		if(s.equals("and")) { 
			return t && u;
		}
		else return t || u;
	}
	
	public boolean evaluate(Tree t) { 
		if(t.name().equals("boolean")) {
			int x = getNum(t.namedChild("number"));
			int y = getNum(t.namedChild("number", 1));
			
			if(t.namedChild("op_operation").toString().equals("==")) { 
				return x == y;
			}
			
			if(t.namedChild("op_operation").toString().equals("!=")) { 
				return x != y;
			}
			
			if(t.namedChild("op_operation").toString().equals(">=")) { 
				return x >= y;
			}
			
			if(t.namedChild("op_operation").toString().equals("<=")) { 
				return x <= y;
			}
			
			if(t.namedChild("op_operation").toString().equals(">")) { 
				return x > y;
			}
			
			if(t.namedChild("op_operation").toString().equals("<")) { 
				return x < y;
			}
		}
		return false;
	}
	
	
	public String parse(String in) throws FileNotFoundException, MalformedGrammarException {
		Scanner s = new Scanner(in);
		String returned = "";
		while(s.hasNext()&& returned.equals("")) { 
			returned = parseMe(s.nextLine());
		}
		return returned;
	}
	
	
	
}
