package parser;

import java.util.ArrayList;
import java.util.HashMap;

import edu.hendrix.earley.*;

public class Procedure {
	private Tree tree;
	private String name;
	private int numArgs;
	private Tree statement;
	private ArrayList<String> params;
	
	public Procedure(Tree t) {
		tree = t;
		name = t.namedChild("name").toString();
		params = new ArrayList<String>();
		numArgs = numParams(t);
		statement = t.namedChild("statement");
	}
	
	public ArrayList<String> getParams() { 
		return params;
	}
	
	public Tree getTree() { return tree; }
	
	public int numArgs() { return numArgs; }
	
	public int numParams(Tree t) {
		if(t.hasNamed("parameters")) { 
			return numParams(t.namedChild("parameters"));
		}
		
		else if(t.hasNamed("parameter")) { 
			params.add(t.namedChild("parameter").toString());
			return 1;
		}
		
		else { 
			return 0;
		}
	}
	
	public String getName() { 
		return name;
	}
	
	public Tree getStatement ()
	{
		return statement;
	}
	
	public void call(HashMap<String, Integer> args) {
		
	}
}
