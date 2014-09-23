package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import turtle.TurtlePane;

import edu.hendrix.earley.Grammar;
import edu.hendrix.earley.MalformedGrammarException;
import edu.hendrix.earley.Tree;

public class test {
	private static Grammar g;
	
	
	public static void main (String[] args) throws FileNotFoundException, MalformedGrammarException {
		Parser p = new Parser(null);
		g = Grammar.makeFrom(new File("grammar.g"));
		Scanner s = new Scanner(System.in);
		String str;
	
		
		while(s.hasNext()) { 
			str = s.nextLine();
			System.out.println(str);
			str = str.replace("]", ' '+ "]");
			System.out.println(str);
			Tree t = g.parse(str);
			p.traversal(t);
		}
	}	
}
