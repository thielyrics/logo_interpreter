package editor;
import java.awt.BorderLayout;

import parser.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class EditorPane extends JFrame {
	JMenuBar menu;
	CommandLine command;
	JTextArea editorArea;
	JTextArea errorTextArea;
	Parser parser;
	JScrollPane console, editor;
	
	public EditorPane(Parser par) {
		super("Editor");
		parser = par;
		
		command = new CommandLine(this, parser);
		add(command, BorderLayout.NORTH);
		
		errorTextArea = new JTextArea();
		errorTextArea.setEditable(false);
		console = new JScrollPane(errorTextArea);
		console.setBorder(BorderFactory.createTitledBorder("Console"));
		
		editorArea = new JTextArea();
		JScrollPane editor = new JScrollPane(editorArea);
		editor.setBorder(BorderFactory.createTitledBorder("Editor"));
		
		JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor, console);
		jsp.setDividerLocation(300);
		add(jsp);
		
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void sendToConsole(String s) {
		errorTextArea.append(s + "\n");
	}
	
	
}
